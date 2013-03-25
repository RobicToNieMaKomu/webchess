package com.polmos.webchess.service;

import com.polmos.webchess.web.websocket.ClientMessageCreator;
import java.util.HashSet;
import java.util.Set;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

/**
 *
 * @author Piotrek
 */
public class MessageCreatorTest {
    
    private static final Logger logger = Logger.getLogger(MessageCreatorTest.class);
    private final Integer EXPECTED_TABLE_ID = 1;
    private final String EXPECTED_WPLAYER_NAME = "jozef";
    private final String EXPECTED_BPLAYER_NAME = "genek";
    private final Integer EXPEXTED_GAME_TIME = 300;
    

    @Test
    public void chessboardStateMessageTest() throws JSONException {
        Set<String> specs = new HashSet<>();
        specs.add(EXPECTED_WPLAYER_NAME);
        specs.add(EXPECTED_BPLAYER_NAME);
        JSONObject json = ClientMessageCreator.createRoomStateMessage(EXPECTED_TABLE_ID, 
                EXPECTED_WPLAYER_NAME , EXPECTED_BPLAYER_NAME, specs, EXPEXTED_GAME_TIME);
        logger.debug("output JSON:"+json);
    }
}
