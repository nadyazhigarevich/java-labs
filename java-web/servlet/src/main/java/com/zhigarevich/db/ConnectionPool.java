package com.zhigarevich.db;

import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public final class ConnectionPool {

    private static final int DEFAULT_POOL_SIZE = 10;
    private static final String DB_URL = "jdbc:mysql://localhost:3306/example";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "12101906Nn#06";
    private static BlockingQueue<Connection> pool;

    static {
        initConnection();
    }

    private ConnectionPool() {
    }

    public static Connection get() {
        try {
            return pool.take();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Failed to get connection from pool", e);
        }
    }

    private static Connection open() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException("Failed to open connection", e);
        }
    }

    private static void initConnection() {
        pool = new ArrayBlockingQueue<>(DEFAULT_POOL_SIZE);
        for (int i = 0; i < DEFAULT_POOL_SIZE; i++) {
            Connection connection = open();
            Connection proxyConnection = (Connection) Proxy.newProxyInstance(
                    Connection.class.getClassLoader(),
                    new Class[]{Connection.class},
                    (proxy, method, args) -> {
                        if ("close".equals(method.getName())) {
                            pool.add((Connection) proxy);
                            return null;
                        }
                        return method.invoke(connection, args);
                    }
            );
            pool.add(proxyConnection);
        }
    }

    public static void close() {
        while (!pool.isEmpty()) {
            Connection connection = pool.poll();
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException("Failed to close connection", e);
                }
            }
        }
    }
}