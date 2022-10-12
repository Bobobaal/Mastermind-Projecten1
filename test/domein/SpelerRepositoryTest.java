/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domein;

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
public class SpelerRepositoryTest {
    
    public SpelerRepositoryTest() {
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
     * Test of voegSpelerToe method, of class SpelerRepository.
     */
    @Test
    public void testVoegSpelerToe() throws Exception {
        System.out.println("voegSpelerToe");
        Speler speler = null;
        SpelerRepository instance = new SpelerRepository();
        instance.voegSpelerToe(speler);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of geefSpeler method, of class SpelerRepository.
     */
    @Test
    public void testGeefSpeler() throws Exception {
        System.out.println("geefSpeler");
        String naam = "";
        String wachtwoord = "";
        SpelerRepository instance = new SpelerRepository();
        Speler expResult = null;
        Speler result = instance.geefSpeler(naam, wachtwoord);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of geefAantalWinsten method, of class SpelerRepository.
     */
    @Test
    public void testGeefAantalWinsten() {
        System.out.println("geefAantalWinsten");
        String naam = "";
        SpelerRepository instance = new SpelerRepository();
        int[] expResult = null;
        int[] result = instance.geefAantalWinsten(naam);
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of updateAantalWinsten method, of class SpelerRepository.
     */
    @Test
    public void testUpdateAantalWinsten() {
        System.out.println("updateAantalWinsten");
        String naam = "";
        int[] winsten = null;
        SpelerRepository instance = new SpelerRepository();
        instance.updateAantalWinsten(naam, winsten);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of geefLijstSpelers method, of class SpelerRepository.
     */
    @Test
    public void testGeefLijstSpelers() {
        System.out.println("geefLijstSpelers");
        String naam = "";
        int moeilijkheidsgraad = 0;
        SpelerRepository instance = new SpelerRepository();
        List<String> expResult = null;
        List<String> result = instance.geefUitdaagbareSpelers(naam, moeilijkheidsgraad);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of geefUitdagingID method, of class SpelerRepository.
     */
    @Test
    public void testGeefUitdagingID() {
        System.out.println("geefUitdagingID");
        String naam = "";
        SpelerRepository instance = new SpelerRepository();
        int expResult = 0;
        int result = instance.geefUitdagingID(naam);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of resetUitdagingID method, of class SpelerRepository.
     */
    @Test
    public void testResetUitdagingID() {
        System.out.println("resetUitdagingID");
        String naam = "";
        SpelerRepository instance = new SpelerRepository();
        instance.resetUitdagingID(naam);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of updateUitdagingWinstOfVerlies method, of class SpelerRepository.
     */
    @Test
    public void testUpdateUitdagingWinstOfVerlies() {
        System.out.println("updateUitdagingWinstOfVerlies");
        int speler_id = 0;
        int moeilijkheidsgraad = 0;
        boolean gewonnen = false;
        SpelerRepository instance = new SpelerRepository();
        instance.updateUitdagingWinstOfVerlies(speler_id, moeilijkheidsgraad, gewonnen);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of geefKlassement method, of class SpelerRepository.
     */
    @Test
    public void testGeefKlassement() {
        System.out.println("geefKlassement");
        int moeilijkheid = 0;
        SpelerRepository instance = new SpelerRepository();
        String expResult = "";
        String result = instance.geefKlassement(moeilijkheid);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
