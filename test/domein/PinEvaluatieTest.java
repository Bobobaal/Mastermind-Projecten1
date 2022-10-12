/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domein;

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
public class PinEvaluatieTest {
    
    public PinEvaluatieTest() {
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
     * Test of values method, of class PinEvaluatie.
     */
    @Test
    public void testValues() {
        System.out.println("values");
        PinEvaluatie[] expResult = null;
        PinEvaluatie[] result = PinEvaluatie.values();
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of valueOf method, of class PinEvaluatie.
     */
    @Test
    public void testValueOf() {
        System.out.println("valueOf");
        String name = "";
        PinEvaluatie expResult = null;
        PinEvaluatie result = PinEvaluatie.valueOf(name);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toString method, of class PinEvaluatie.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        PinEvaluatie instance = null;
        String expResult = "";
        String result = instance.toString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
