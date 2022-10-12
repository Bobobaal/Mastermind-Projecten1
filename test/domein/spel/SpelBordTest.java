/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domein.spel;

import domein.PinEvaluatie;
import domein.rij.RijKraak;
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
public class SpelBordTest {
    
    public SpelBordTest() {
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
     * Test of getRijen method, of class SpelBord.
     */
    @Test
    public void testGetRijen() {
        System.out.println("getRijen");
        SpelBord instance = null;
        RijVulbaar[] expResult = null;
        RijVulbaar[] result = instance.getRijen();
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getRij method, of class SpelBord.
     */
    @Test
    public void testGetRij() {
        System.out.println("getRij");
        int index = 0;
        SpelBord instance = null;
        RijVulbaar expResult = null;
        RijVulbaar result = instance.getRij(index);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTeKrakenCode method, of class SpelBord.
     */
    @Test
    public void testGetTeKrakenCode() {
        System.out.println("getTeKrakenCode");
        SpelBord instance = null;
        RijKraak expResult = null;
        RijKraak result = instance.getTeKrakenCode();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setRij method, of class SpelBord.
     */
    @Test
    public void testSetRij() {
        System.out.println("setRij");
        RijVulbaar rij = null;
        int index = 0;
        SpelBord instance = null;
        instance.setRij(rij, index);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setTeKrakenCode method, of class SpelBord.
     */
    @Test
    public void testSetTeKrakenCode() {
        System.out.println("setTeKrakenCode");
        RijKraak teKrakenCode = null;
        SpelBord instance = null;
        instance.setTeKrakenCode(teKrakenCode);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setPinEvaluatie method, of class SpelBord.
     */
    @Test
    public void testSetPinEvaluatie() {
        System.out.println("setPinEvaluatie");
        PinEvaluatie pinEvaluatie = null;
        int index = 0;
        int kolom = 0;
        SpelBord instance = null;
        instance.setPinEvaluatie(pinEvaluatie, index, kolom);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
