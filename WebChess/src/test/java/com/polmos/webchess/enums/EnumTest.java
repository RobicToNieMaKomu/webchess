package com.polmos.webchess.enums;

import org.junit.runner.RunWith;
import com.polmos.webchess.exceptions.WebChessException;
import com.polmos.webchess.items.ChessBoardTest;
import junit.framework.Assert;
import org.apache.log4j.Logger;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * @author Piotrek
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:service-context.xml"})
public class EnumTest {

    private static final Logger logger = Logger.getLogger(ChessBoardTest.class);
    private Integer WRONG_COLUMN = 11;
    private Integer COOL_COLUMN = 1;
    private String B_COLUMN = "B";

    public EnumTest() {
    }

    @Test(expected = WebChessException.class)
    public void wrongColumnEnumTest() throws WebChessException {
        logger.debug("Column test starting...");
        ColumnEnum.convertColumnPosition(WRONG_COLUMN);
    }

    @Test
    public void niceColumnNameTest() {
        logger.debug("Nice column name test starting...");
        try {
        ColumnEnum ce = ColumnEnum.convertColumnPosition(COOL_COLUMN);
        assertThat(ce.getColumn(), is(B_COLUMN));
        } catch (WebChessException ex) {
            ex.printStackTrace();
            Assert.fail();
        }
    }
}
