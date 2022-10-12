/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domein;

import domein.spel.Spel;
import java.util.List;
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
public class SpelRepositoryTest {
    
    public SpelRepositoryTest() {
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
     * Test of voegSpelToe method, of class SpelRepository.
     */
    @Test
    public void testVoegSpelToe() throws Exception {
        System.out.println("voegSpelToe");
        String spelerNaam = "";
        String spelNaam = "";
        Spel spel = null;
        SpelRepository instance = new SpelRepository();
        instance.voegSpelToe(spelerNaam, false, spelNaam, spel);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of geefSpel method, of class SpelRepository.
     */
    @Test
    public void testGeefSpel() {
        System.out.println("geefSpel");
        String spelNaam = "";
        SpelRepository instance = new SpelRepository();
        Spel expResult = null;
        Spel result = instance.geefSpel(spelNaam);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of geefOpgeslagenSpellen method, of class SpelRepository.
     */
    @Test
    public void testGeefSpelNamen() {
        System.out.println("geefSpelNamen");
        String spelerNaam = "";
        SpelRepository instance = new SpelRepository();
        List<String> expResult = null;
        List<String> result = instance.geefOpgeslagenSpellen(spelerNaam);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of voegUitdagingToe method, of class SpelRepository.
     */
    @Test
    public void testVoegUitdagingToe() {
        System.out.println("voegUitdagingToe");
        String uitdager = "";
        String uitgedaagde = "";
        int moeilijkheidsgraad = 0;
        String teKrakenCode = "";
        SpelRepository instance = new SpelRepository();
        int expResult = 0;
        int result = instance.voegUitdagingToe(uitdager, uitgedaagde, moeilijkheidsgraad, teKrakenCode);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of geefUitdagingen method, of class SpelRepository.
     */
    @Test
    public void testGeefUitdagingen() {
        System.out.println("geefUitdagingen");
        String spelerNaam = "";
        SpelRepository instance = new SpelRepository();
        List<String> expResult = null;
        List<String> result = instance.geefUitdagingen(spelerNaam);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of geefUitdaging method, of class SpelRepository.
     */
    @Test
    public void testGeefUitdaging() {
        System.out.println("geefUitdaging");
        int uitdaging_id = 0;
        String spelerNaam = "";
        SpelRepository instance = new SpelRepository();
        Spel expResult = null;
        Spel result = instance.geefUitdaging(uitdaging_id, spelerNaam);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of updateUitdagingZetten method, of class SpelRepository.
     */
    @Test
    public void testUpdateUitdagingZetten() {
        System.out.println("updateUitdagingZetten");
        int uitdaging_id = 0;
        int zetten = 0;
        String naam = "";
        SpelRepository instance = new SpelRepository();
        boolean expResult = false;
        boolean result = instance.updateUitdagingZetten(uitdaging_id, zetten, naam);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of geefUitdagingZetten method, of class SpelRepository.
     */
    @Test
    public void testGeefUitdagingZetten() {
        System.out.println("geefUitdagingZetten");
        int uitdaging_id = 0;
        SpelRepository instance = new SpelRepository();
        int[] expResult = null;
        int[] result = instance.geefUitdagingZetten(uitdaging_id);
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of geefUitdagingSpelerIDs method, of class SpelRepository.
     */
    @Test
    public void testGeefUitdagingSpelerIDs() {
        System.out.println("geefUitdagingSpelerIDs");
        int uitdaging_id = 0;
        SpelRepository instance = new SpelRepository();
        int[] expResult = null;
        int[] result = instance.geefUitdagingSpelerIDs(uitdaging_id);
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of deleteUitdaging method, of class SpelRepository.
     */
    @Test
    public void testDeleteUitdaging() {
        System.out.println("deleteUitdaging");
        int uitdaging_id = 0;
        SpelRepository instance = new SpelRepository();
        instance.deleteUitdaging(uitdaging_id);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
