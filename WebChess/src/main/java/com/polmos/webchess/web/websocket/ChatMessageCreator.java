package com.polmos.webchess.web.websocket;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 *
 * @author RobicToNieMaKomu
 */
public class ChatMessageCreator {
    
    private static final String DATE_FORMAT = "HH:mm:ss";
    
    public static String createChatMessage(String content, String username) {
        StringBuilder message = new StringBuilder();
        
        message.append("[<strong>");
        message.append(username);
        message.append("</strong>]: ");
        message.append(getSanitizedMessage(content));
        
        return message.toString();
    }
    /**
     * Get rid off html tags etc.
     * 
     * @param rawMessage
     * @return 
     */
    private static String getSanitizedMessage(String rawMessage) {
        String result = rawMessage;
        // TBD
        return result;
    }
    
    private String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        String time = sdf.format(Calendar.getInstance().getTime());
        return time;
    }
}
