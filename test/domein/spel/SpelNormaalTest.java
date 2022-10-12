/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domein.spel;

import domein.rij.RijVulbaar;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author joris
 */
public class SpelNormaalTest {
    
    public SpelNormaalTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of berekenEvaluatiePinnen method, of class SpelNormaal.
     */
    @Test
    public void testBerekenEvaluatiePinnen() {
        System.out.println("berekenEvaluatiePinnen");
        int index = 0;
        SpelNormaal instance = new SpelNormaal();
        instance.berekenEvaluatiePinnen(index);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toString method, of class SpelNormaal.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        SpelNormaal instance = new SpelNormaal();
        String expResult = "";
        String result = instance.toString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of rijFactory method, of class SpelNormaal.
     */
    @Test
    public void testRijFactory() {
        System.out.println("rijFactory");
        SpelNormaal instance = new SpelNormaal();
        RijVulbaar expResult = null;
        RijVulbaar result = instance.rijFactory();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
