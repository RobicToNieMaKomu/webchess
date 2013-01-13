/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.polmos.webchess.items;

import com.polmos.webchess.service.MoveValidator;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author Piotrek
 */
public class ContextTest {

    private static Logger logger = Logger.getLogger(ContextTest.class);

    public ContextTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        logger.debug("ContextTest setup");
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"service-context.xml"});
        logger.debug(context);
        MoveValidator mv = (MoveValidator) context.getBean(MoveValidator.class);
        logger.debug(mv);
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //

    @Test
    public void hello() {
    }
}
