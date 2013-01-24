/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.polmos.webchess.web.websocket;

import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.websocket.StreamInbound;
import org.apache.catalina.websocket.WebSocketServlet;

/**
 * Servlet responsible for creation of the new connections to the clients
 */
public class ChatWebSocketServlet extends WebSocketServlet {

    private static final long serialVersionUID = 1L;
    private final AtomicInteger connectionIds = new AtomicInteger(0);

    /**
     * This method is called whenever new socket opening is requested by the
     * container
     *
     * @param subProtocol
     * @param request
     * @return
     */
    @Override
    protected StreamInbound createWebSocketInbound(String subProtocol, HttpServletRequest request) {
        // Retrieve chess table id from request
        return new ClientMessageInbound(connectionIds.incrementAndGet());
    }
}
