package domein;

import exceptions.VerplichtVeldException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;

/**
 * Klasse om de klasse Speler te testen!
 *
 * @author groep 31
 */
public class SpelerTest {

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    /**
     * Testmethode constructor met parameters die voldoen aan de vereisten.
     * Maakt Speler aan
     */
    @Test
    public void maakSpeler_spelersnaamDieter_wachtwoord1Password1_maaktSpeler() {
        String spelersnaam = "Dieter";
        String wachtwoord = "1Password1";

        Speler s;
        try {
            s = new Speler(spelersnaam, wachtwoord);
            assertEquals(spelersnaam, s.getNaam());
            assertEquals(wachtwoord, s.getWachtwoord());
        } catch (VerplichtVeldException ex) {
            // Do nothing, the try should work!
        }
    }

    /**
     * Testmethode constructor met lege naam en ingevuld wachtwoord volgens de
     * vereisten. Gooit exception
     *
     * @throws exceptions.VerplichtVeldException
     */
    @Test(expected = VerplichtVeldException.class)
    public void maakSpeler_spelersnaamLeeg_wachtwoord1Password1_WerptException() throws VerplichtVeldException {
        String wachtwoord = "1Password1";

        Speler s = new Speler("", wachtwoord);
    }

    /**
     * Testmethode constructor met ingevulde naam en leeg wachtwoord. Gooit
     * exception
     *
     * @throws exceptions.VerplichtVeldException
     */
    @Test(expected = VerplichtVeldException.class)
    public void maakSpeler_spelersnaamDieter_wachtwoordLeeg_WerptException() throws VerplichtVeldException {
        String spelersnaam = "Dieter";

        Speler s = new Speler(spelersnaam, "");
    }

    /**
     * Testmethode constructor met beide parameters ingevuld maar wachtwoord
     * niet volgens vereistte. Gooit exception
     *
     * @throws exceptions.VerplichtVeldException
     */
    @Test(expected = VerplichtVeldException.class)
    public void maakSpeler_spelersnaamDieter_wachtwoordPassword123_WerptException() throws VerplichtVeldException {
        String spelersnaam = "Dieter";
        String wachtwoord = "Password123";

        Speler s = new Speler(spelersnaam, wachtwoord);
    }

    /**
     * Test of getNaam method, of class Speler.
     */
    @Test
    public void testGetNaam() {
        System.out.println("getNaam");
        Speler instance = null;
        String expResult = "";
        String result = instance.getNaam();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getWachtwoord method, of class Speler.
     */
    @Test
    public void testGetWachtwoord() {
        System.out.println("getWachtwoord");
        Speler instance = null;
        String expResult = "";
        String result = instance.getWachtwoord();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getWinsten method, of class Speler.
     */
    @Test
    public void testGetWinsten() {
        System.out.println("getWinsten");
        SpelerRepository spelerRepository = null;
        Speler instance = null;
        int[] expResult = null;
        int[] result = instance.getWinsten(spelerRepository);
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of updateWinsten method, of class Speler.
     */
    @Test
    public void testUpdateWinsten() {
        System.out.println("updateWinsten");
        int moeilijkheidsgraad = 0;
        SpelerRepository spelerRepository = null;
        Speler instance = null;
        instance.updateWinsten(moeilijkheidsgraad, spelerRepository);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isInUitdaging method, of class Speler.
     */
    @Test
    public void testIsInUitdaging() {
        System.out.println("isInUitdaging");
        SpelerRepository spelerRepository = null;
        Speler instance = null;
        boolean expResult = false;
        boolean result = instance.isInUitdaging(spelerRepository);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setUitdaging_id method, of class Speler.
     */
    @Test
    public void testSetUitdaging_id() {
        System.out.println("setUitdaging_id");
        int uitdaging_id = 0;
        Speler instance = null;
        instance.setUitdaging_id(uitdaging_id);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getUitdaging_id method, of class Speler.
     */
    @Test
    public void testGetUitdaging_id() {
        System.out.println("getUitdaging_id");
        Speler instance = null;
        int expResult = 0;
        int result = instance.getUitdaging_id();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
