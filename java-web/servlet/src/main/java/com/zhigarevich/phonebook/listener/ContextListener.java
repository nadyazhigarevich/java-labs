package com.zhigarevich.phonebook.listener;

import com.zhigarevich.phonebook.db.ConnectionPool;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class ContextListener implements ServletContextListener {
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ConnectionPool.getInstance().destroyPool();
        ServletContextListener.super.contextDestroyed(sce);
    }
}
