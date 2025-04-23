package com.zhigarevich.servlet1.db;

import com.zhigarevich.servlet1.exception.BusinessException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionPool {
    private static final Logger LOGGER = LogManager.getLogger();
    private static AtomicBoolean flag = new AtomicBoolean();
    private static ConnectionPool instance;
    private static Lock lock = new ReentrantLock();
    private BlockingQueue<ProxyConnection> connectionQueue;
    private final int POOL_SIZE = 45;

    private ConnectionPool() throws BusinessException {
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
        } catch (SQLException e) {
            LOGGER.log(Level.FATAL, e.getMessage());
            throw new RuntimeException(e.getCause());
        }
        connectionQueue = new ArrayBlockingQueue<>(POOL_SIZE);
        try {
            ProxyConnection connection;
            for (int i = 0; i < POOL_SIZE; i++) {
                connection = new ProxyConnection(ConnectionDB.createConnection());
                connectionQueue.offer(connection);
            }
        } catch (SQLException e) {
            throw new BusinessException(e.getCause());
        }
    }

    public static ConnectionPool getInstance() {
        if (!flag.get()) {
            try {
                lock.lock();
                if (!flag.get()) {
                    instance = new ConnectionPool();
                    flag.set(true);
                }
            } catch (BusinessException e) {
                LOGGER.catching(e);
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }

    public ProxyConnection defineConnection() {
        ProxyConnection connection = null;
        try {
            connection = connectionQueue.take();
        } catch (InterruptedException e) {
            LOGGER.catching(e);
        }
        return connection;
    }

    public void releaseConnection(ProxyConnection connection) throws BusinessException {
        try {
            if (connection.getAutoCommit()) {
                try {
                    connectionQueue.put(connection);
                } catch (InterruptedException e) {
                    LOGGER.catching(e);
                }
            }
        } catch (SQLException e) {
            throw new BusinessException(e.getCause());
        }
    }

    public void destroyPool() {
        for (int i = 0; i < POOL_SIZE; i++) {
            ProxyConnection connection;
            try {
                connection = connectionQueue.take();
                connection.closeConnection();
            } catch (InterruptedException | SQLException e) {
                LOGGER.catching(e);
            }
        }
        deregisterDriver();
    }

    private void deregisterDriver() {
        Enumeration<Driver> enumDrivers = DriverManager.getDrivers();
        try {
            while (enumDrivers.hasMoreElements()) {
                Driver driver = enumDrivers.nextElement();
                DriverManager.deregisterDriver(driver);
            }
        } catch (SQLException e) {
            LOGGER.catching(e);
        }
    }
}