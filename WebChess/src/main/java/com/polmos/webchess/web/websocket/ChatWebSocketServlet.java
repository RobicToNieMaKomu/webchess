/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.polmos.webchess.web.websocket;

import com.polmos.webchess.util.SpringContextProvider;
import java.security.Principal;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.websocket.StreamInbound;
import org.apache.catalina.websocket.WebSocketServlet;
import org.apache.log4j.Logger;

/**
 * Servlet responsible for creation of the new connections to the clients
 */
public class ChatWebSocketServlet extends WebSocketServlet {

    private static Logger logger = Logger.getLogger(ChatWebSocketServlet.class);
    private static final long serialVersionUID = 1L;
    private final AtomicInteger connectionIds = new AtomicInteger(0);
    private static final String TABLE_ID_PREFIX = "chessTableId=";
    private WSConnectionManager wSConnectionManager;

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
        ClientMessageInbound result = null;
        // Retrieve chess table id from request
        String queryString = request.getQueryString();
        String sanitizedQueryString = sanitizeQueryString(queryString);
        if (sanitizedQueryString.isEmpty()) {
            throw new IllegalArgumentException("Request tried to open a new ws connection without giving table id");
        }
        int tableId = Integer.parseInt(sanitizedQueryString);
        // Get username from the request
        Principal userPrincipal = request.getUserPrincipal();
        String userName = userPrincipal.getName();
        // Check whether this user has opened connections or not
        ClientMessageInbound clientMessageInbound = wSConnectionManager.findWSConnectionByUserName(userName);
        if (clientMessageInbound == null) {
            // New connection (there are no registred conncetions for this user)
            result = new ClientMessageInbound(userName);
            wSConnectionManager.addNewWSConnection(result, tableId);
        } else {
            // Another connection (user has connections with at least one table already)
            result = clientMessageInbound;
            wSConnectionManager.addNewWSConnection(clientMessageInbound, tableId);
            // TODO:
            // Resume connection (connection between this user and server has been broken lately)
        }
        return result;
    }

    @Override
    public void init() {
        logger.debug("Initializing WebSocketServlet...");
        wSConnectionManager = (WSConnectionManager) SpringContextProvider.applicationContext.getBean("wSConnectionManager");
        if (wSConnectionManager == null) {
            logger.error("WS Connection Manager not initialized for a servlet:" + this);
        }
    }

    /**
     * Method returns table id from the request's param query
     *
     * @param queryString
     * @return
     */
    private String sanitizeQueryString(String queryString) {
        String result = "";
        // Only chessTableId is expected
        if (queryString != null && queryString.startsWith(TABLE_ID_PREFIX)) {
            String value = queryString.replaceFirst(TABLE_ID_PREFIX, "");
            String regex = "[0-9]*";
            if (value.matches(regex)) {
                result = value;
            }
        }
        return result;
    }
}
