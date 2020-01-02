package com.jnshu.article.util;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * @author Admin
 * @PackageName com.com.jnshu.academyctrlwebclient.util
 * @ClassName dev
 * @Description
 * @create 2019-12-01 15:44
 */
@WebListener
public class MyContextListener implements ServletContextListener {
    private SSHConnection conexionssh;

    public MyContextListener() {
        super();
    }

    public void contextInitialized(ServletContextEvent arg0) {
        System.out.println("Context initialized ... !");
        try {
            conexionssh = new SSHConnection();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public void contextDestroyed(ServletContextEvent arg0) {
        System.out.println("Context destroyed ... !");
        conexionssh.closeSSH();
    }

}
