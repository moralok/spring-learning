package com.moralok.servlet.listener;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * @author moralok
 * @since 2021/1/26 3:58 下午
 */
public class SessionCounter implements HttpSessionListener {

    private int activeSessions;

    public int getActiveSessions() {
        return activeSessions;
    }

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        activeSessions++;
        System.out.println("activeSessions++ now: " + activeSessions);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        activeSessions--;
        System.out.println("activeSessions-- now: " + activeSessions);
    }
}
