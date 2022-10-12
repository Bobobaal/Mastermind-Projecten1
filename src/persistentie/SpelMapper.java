package persistentie;

import main.Taal;
import java.sql.Statement;
import domein.PinCode;
import domein.PinEvaluatie;
import domein.rij.RijKraak;
import domein.rij.RijVulbaar;
import domein.spel.Spel;
import domein.spel.SpelFactory;
import exceptions.VerplichtVeldException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Klasse dat communiceert met de databank mbt spellen.
 *
 * @author groep 31
 */
public class SpelMapper {

    private ResourceBundle TAAL = null;

    private final SpelFactory SPEL_FACTORY = new SpelFactory();

    private final String INSERT_SPEL = "INSERT INTO ID222177_g31.spel (speler_id, naam, moeilijkheidsgraad, uitdaging) VALUES (?, ?, ?, ?)";
    private final String INSERT_RIJ = "INSERT INTO ID222177_g31.rij (spel_id, rijIndex) VALUES (?, ?)";
    private final String INSERT_PIN = "INSERT INTO ID222177_g31.pin (pinIndex, kleur, isPinCode, rij_id) VALUES (?, ?, ?, ?)";
    private final String INSERT_UITDAGING = "INSERT INTO ID222177_g31.uitdaging (uitdager_id, uitgedaagde_id, moeilijkheidsgraad, teKrakenCode) VALUES (?, ?, ?, ?)";

    private final String SELECT_SPELER = "SELECT speler_id FROM ID222177_g31.speler WHERE naam LIKE ?";
    private final String SELECT_SPELNAMEN = "SELECT naam, moeilijkheidsgraad, uitdaging FROM ID222177_g31.spel WHERE speler_id = ?";
    private final String SELECT_SPEL = "SELECT spel_id, naam, moeilijkheidsgraad FROM ID222177_g31.spel WHERE naam LIKE ?";
    private final String SELECT_SPEL_CONTROLE = "SELECT spel_id FROM ID222177_g31.spel WHERE naam LIKE ?";
    private final String SELECT_RIJ = "SELECT rij_id, rijIndex FROM ID222177_g31.rij WHERE spel_id = ?";
    private final String SELECT_PIN = "SELECT pinIndex, isPinCode, kleur FROM ID222177_g31.pin WHERE rij_id = ?";
    private final String SELECT_UITDAGINGEN = "SELECT naam, winMakkelijk, winNormaal, winMoeilijk, ID222177_g31.uitdaging.uitdaging_id, moeilijkheidsgraad FROM ID222177_g31.uitdaging INNER JOIN ID222177_g31.speler ON uitdager_id = speler_id WHERE uitgedaagde_id = ?";
    private final String SELECT_UITDAGING = "SELECT moeilijkheidsgraad, teKrakenCode FROM ID222177_g31.uitdaging WHERE uitdaging_id = ?";
    private final String SELECT_UITDAGING_SPELER = "SELECT speler_id, uitdager_id FROM ID222177_g31.uitdaging INNER JOIN ID222177_g31.speler ON ID222177_g31.uitdaging.uitdaging_id = ID222177_g31.speler.uitdaging_id WHERE ID222177_g31.speler.uitdaging_id = ? AND naam = ?";
    private final String SELECT_UITDAGINGZETTEN = "SELECT zettenUitdager, zettenUitgedaagde FROM ID222177_g31.uitdaging WHERE uitdaging_id = ?";
    private final String SELECT_UITDAGINGSPELERIDS = "SELECT uitdager_id, uitgedaagde_id FROM ID222177_g31.uitdaging WHERE uitdaging_id = ?";

    private final String UPDATE_SPELERSUITDAGINGID = "UPDATE ID222177_g31.speler SET uitdaging_id = ? WHERE speler_id = ?";
    private final String UPDATE_SPELERSUITDAGINGID2 = "UPDATE ID222177_g31.speler SET uitdaging_id = ? WHERE naam = ?";
    private final String UPDATE_UITDAGERZETTEN = "UPDATE ID222177_g31.uitdaging SET zettenUitdager = ? WHERE uitdaging_id = ?";
    private final String UPDATE_UITGEDAAGDEZETTEN = "UPDATE ID222177_g31.uitdaging SET zettenUitgedaagde = ? WHERE uitdaging_id = ?";

    private final String DELETE_SPEL = "DELETE FROM ID222177_g31.spel WHERE naam LIKE ?";
    private final String DELETE_UITDAGING = "DELETE FROM ID222177_g31.uitdaging WHERE uitdaging_id = ?";

    /**
     * Vul de TAAL in voor deze repository.
     * 
     */
    public void vulTaalIn() {
        if (TAAL == null) {
            TAAL = Taal.getTaal();
        }
    }

    /**
     * Voeg een nieuw spel toe in de databank.
     *
     * @param spelerNaam De naam van de speler
     * @param isUitdaging True als dit spel bij een uitdaging hoort
     * @param spelNaam De naam van het spel
     * @param spel Het spel
     * @throws exceptions.VerplichtVeldException
     */
    public void voegSpelToe(String spelerNaam, boolean isUitdaging, String spelNaam, Spel spel) throws VerplichtVeldException {
        // Init
        int speler_id = 0;

        try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
                PreparedStatement querySpeler = conn.prepareStatement(SELECT_SPELER);
                PreparedStatement querySpelControle = conn.prepareStatement(SELECT_SPEL_CONTROLE);
                PreparedStatement querySpel = conn.prepareStatement(INSERT_SPEL, Statement.RETURN_GENERATED_KEYS)) {
            // Eerst controleren of er al een spel bestaat met deze naam in camelCase
            querySpelControle.setString(1, spelNaam);
            try (ResultSet rsSpelControle = querySpelControle.executeQuery()) {
                if (rsSpelControle.next()) {
                    // Er bestaat al een spel met deze naam
                    throw new VerplichtVeldException(TAAL.getString("nameAlreadyUsedError") + " " + TAAL.getString("opnieuw"));
                }
            }

            // Speler_id van de huidige speler opvragen
            querySpeler.setString(1, spelerNaam);
            try (ResultSet rsSpeler = querySpeler.executeQuery()) {
                if (rsSpeler.next()) {
                    speler_id = Integer.parseInt(rsSpeler.getString("speler_id"));
                }
            }

            // Nieuw, leeg spel opslaan voor de huidige speler
            querySpel.setInt(1, speler_id);
            querySpel.setString(2, spelNaam);
            querySpel.setInt(3, spel.getMoeilijkheidsgraad());
            if (isUitdaging) {
                querySpel.setInt(4, 1);
            } else {
                querySpel.setInt(4, 0);
            }
            querySpel.executeUpdate();
            int spel_id = 0;
            try (ResultSet keys = querySpel.getGeneratedKeys()) {
                if (keys.next()) {
                    spel_id = keys.getInt(1);
                }
            }

            // Een lege rij voor de te kraken code opslaan op index 0
            try (PreparedStatement queryRij = conn.prepareStatement(INSERT_RIJ, Statement.RETURN_GENERATED_KEYS);
                    PreparedStatement queryPin = conn.prepareStatement(INSERT_PIN)) {
                queryRij.setInt(1, spel_id);
                queryRij.setInt(2, 0);
                queryRij.executeUpdate();
                int rij_id = 0;
                try (ResultSet keys = queryRij.getGeneratedKeys()) {
                    if (keys.next()) {
                        rij_id = keys.getInt(1);
                    }
                }

                // Alle pinnen van de te kraken code invullen
                int pinIndex = 0;
                for (PinCode pinCode : spel.getTeKrakenCode().getPinCodes()) {
                    queryPin.setInt(1, pinIndex);
                    queryPin.setString(2, pinCode.name());
                    queryPin.setInt(3, 1); // True
                    queryPin.setInt(4, rij_id);
                    queryPin.executeUpdate();
                    pinIndex++;
                }

                // Alle rijen met hun pinnen opslaan vanaf index 1
                int rijIndex = 1;
                for (RijVulbaar rijVulbaar : spel.getRijen()) {
                    if (rijVulbaar.getPinCodes()[0] != null) {
                        // Nieuwe lege rij opslaan
                        queryRij.setInt(1, spel_id);
                        queryRij.setInt(2, rijIndex);
                        queryRij.executeUpdate();
                        try (ResultSet keys = queryRij.getGeneratedKeys()) {
                            if (keys.next()) {
                                rij_id = keys.getInt(1);
                            }
                        }

                        // Zijn pincodes opslaan
                        pinIndex = 0;
                        for (PinCode pinCode : rijVulbaar.getPinCodes()) {
                            if (pinCode != null) {
                                queryPin.setInt(1, pinIndex);
                                queryPin.setString(2, pinCode.name());
                                queryPin.setInt(3, 1); // True
                                queryPin.setInt(4, rij_id);
                                queryPin.executeUpdate();
                            }
                            pinIndex++;
                        }

                        // Zijn pinevaluaties opslaan
                        pinIndex = 0;
                        for (PinEvaluatie pinEvaluatie : rijVulbaar.getPinEvaluaties()) {
                            if (pinEvaluatie != null) {
                                queryPin.setInt(1, pinIndex);
                                queryPin.setString(2, pinEvaluatie.name());
                                queryPin.setInt(3, 0); // False
                                queryPin.setInt(4, rij_id);
                                queryPin.executeUpdate();
                            }
                            pinIndex++;
                        }

                        rijIndex++;
                    }
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Verwijder een spel uit de databank.
     *
     * @param spelNaam De naam van het spel
     */
    private void deleteSpel(String spelNaam) {
        try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
                PreparedStatement query = conn.prepareStatement(DELETE_SPEL)) {
            query.setString(1, spelNaam);
            query.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Zoek een bepaald spel op in de databank.
     *
     * @param spelNaam De naam van het spel
     * @return Het spel
     */
    public Spel geefSpel(String spelNaam) {
        // Init
        Spel spel = null;

        // Probeer het spel op te halen
        try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
                PreparedStatement querySpel = conn.prepareStatement(SELECT_SPEL)) {
            querySpel.setString(1, spelNaam);
            try (ResultSet rsSpel = querySpel.executeQuery()) {
                if (rsSpel.next()) {
                    // Nieuw leeg spel aanmaken
                    spel = SPEL_FACTORY.getSpel(rsSpel.getInt("moeilijkheidsgraad"));
                    spel.setNaam(rsSpel.getString("naam"));

                    // Spel opvullen met rijen
                    RijVulbaar rij;
                    try (PreparedStatement queryRij = conn.prepareStatement(SELECT_RIJ);
                            PreparedStatement queryPin = conn.prepareStatement(SELECT_PIN)) {
                        queryRij.setInt(1, rsSpel.getInt("spel_id")); // spel_id
                        try (ResultSet rsRij = queryRij.executeQuery()) {
                            while (rsRij.next()) {
                                // Nieuwe lege rij aanmaken
                                rij = spel.rijFactory();
                                Map<String, PinCode> kleurenMap = spel.getKleurenMap();

                                // Rij opvullen met pinnen
                                queryPin.setInt(1, rsRij.getInt("rij_id"));
                                try (ResultSet rsPin = queryPin.executeQuery()) {
                                    while (rsPin.next()) {
                                        if (rsPin.getInt("isPinCode") == 1) {
                                            rij.setPinCode(kleurenMap.get(rsPin.getString("kleur")), rsPin.getInt("pinIndex"));
                                        } else {
                                            switch (rsPin.getString("kleur")) {
                                                case "Z":
                                                    rij.setPinEvaluatie(PinEvaluatie.Z, rsPin.getInt("pinIndex"));
                                                    break;
                                                case "W":
                                                    rij.setPinEvaluatie(PinEvaluatie.W, rsPin.getInt("pinIndex"));
                                                    break;
                                            }
                                        }
                                    }
                                }

                                // Rij opslaan in het spel
                                if (rsRij.getInt("rijIndex") == 0) {
                                    spel.setTeKrakenCode(new RijKraak(spel.getAantalPinnenPerRij(), spel.isLeegMag(), spel.isUniekMoet(), rij.getPinCodes()));
                                } else {
                                    spel.setRij(rij, rsRij.getInt("rijIndex") - 1);
                                }
                            }
                        }
                    }

                    // Spel verwijderen uit de databank
                    deleteSpel(spelNaam);
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        // Ingevuld spel terug geven
        return spel;
    }

    /**
     * Zoek de namen van alle opgeslagen spellen op van een bepaalde speler in
     * de databank.
     *
     * @param spelerNaam De naam van de speler
     * @return De namen van alle opgeslagen spellen van deze speler
     */
    public List<String> geefOpgeslagenSpellen(String spelerNaam) {
        // Init (lege lijst van namen maken)
        List<String> spelNamen = new ArrayList<>();

        // Probeer de spelnamen op te halen
        try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
                PreparedStatement querySpeler = conn.prepareStatement(SELECT_SPELER);
                PreparedStatement querySpel = conn.prepareStatement(SELECT_SPELNAMEN)) {
            // Speler_id ophalen
            querySpeler.setString(1, spelerNaam);
            int speler_id = 0;
            try (ResultSet rsSpeler = querySpeler.executeQuery()) {
                if (rsSpeler.next()) {
                    speler_id = rsSpeler.getInt("speler_id");
                }
            }

            // Alle spellen van de speler ophalen (namen) en toevoegen
            String[] gradenStrings = new String[]{TAAL.getString("makkelijk").toLowerCase(), TAAL.getString("normaal").toLowerCase(), TAAL.getString("moeilijk").toLowerCase()};
            querySpel.setInt(1, speler_id); // speler_id
            try (ResultSet rsSpel = querySpel.executeQuery()) {
                while (rsSpel.next()) {
                    String spel = rsSpel.getString("naam") + " (";

                    spel += gradenStrings[rsSpel.getInt("moeilijkheidsgraad") - 1];
                    spel += ")";

                    if (rsSpel.getInt("uitdaging") == 1) {
                        spel += " (" + TAAL.getString("uitdaging") + ")";
                    }

                    spelNamen.add(spel);
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        // Namen terug geven
        return spelNamen;
    }

    /**
     * Voeg een uitdaging toe in de databank en geef zijn id terug.
     *
     * @param uitdagerNaam De naam van de uitdager
     * @param uitgedaagdeNaam De naam van de uitgedaagde
     * @param moeilijkheidsgraad De moeilijkheidsgraad als een int
     * @param teKrakenCode De te kraken code als een string
     * @return Het ID van de uitdaging
     */
    public int voegUitdagingToe(String uitdagerNaam, String uitgedaagdeNaam, int moeilijkheidsgraad, String teKrakenCode) {
        // Init
        int uitdaging_id = 0;
        int uitdager_id = 0;
        int uitgedaagde_id = 0;

        // Probeer een uitdaging toe te voegen
        try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
                PreparedStatement querySpeler = conn.prepareStatement(SELECT_SPELER);
                PreparedStatement querySpelersUitdagingID = conn.prepareStatement(UPDATE_SPELERSUITDAGINGID);
                PreparedStatement queryUitdaging = conn.prepareStatement(INSERT_UITDAGING, Statement.RETURN_GENERATED_KEYS)) {
            // Speler_id uitdagerNaam ophalen
            querySpeler.setString(1, uitdagerNaam);
            try (ResultSet rsUitdager = querySpeler.executeQuery()) {
                if (rsUitdager.next()) {
                    uitdager_id = rsUitdager.getInt("speler_id");
                }
            }

            // Speler_id uitgedaagdeNaam ophalen
            querySpeler.setString(1, uitgedaagdeNaam);
            try (ResultSet rsUitgedaagde = querySpeler.executeQuery()) {
                if (rsUitgedaagde.next()) {
                    uitgedaagde_id = rsUitgedaagde.getInt("speler_id");
                }
            }

            // De uitdaging toevoegen in de database
            queryUitdaging.setInt(1, uitdager_id);
            queryUitdaging.setInt(2, uitgedaagde_id);
            queryUitdaging.setInt(3, moeilijkheidsgraad);
            queryUitdaging.setString(4, teKrakenCode);
            queryUitdaging.executeUpdate();

            try (ResultSet keys = queryUitdaging.getGeneratedKeys()) {
                if (keys.next()) {
                    uitdaging_id = keys.getInt(1);
                }
            }

            // Het ID van de uitdaging toevoegen aan de uitdager
            querySpelersUitdagingID.setInt(1, uitdaging_id);
            querySpelersUitdagingID.setInt(2, uitdager_id);
            querySpelersUitdagingID.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        // Return uitdaging_id
        return uitdaging_id;
    }

    /**
     * Vraag een lijst van alle uitdagingen voor de huidige speler op aan de
     * databank.
     *
     * @param spelerNaam De naam van de speler
     * @return Een lijst van alle uitdagingen voor de huidige speler
     */
    public List<String> geefUitdagingen(String spelerNaam) {
        // Init
        int speler_id;
        List<String> uitdagingen = new ArrayList<>();

        // Probeer de uitdagingen op te halen
        try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
                PreparedStatement querySpeler = conn.prepareStatement(SELECT_SPELER);
                PreparedStatement queryUitdaging = conn.prepareStatement(SELECT_UITDAGINGEN)) {
            // Speler_id van de huidige speler opvragen
            querySpeler.setString(1, spelerNaam);
            try (ResultSet rsSpeler = querySpeler.executeQuery()) {
                if (rsSpeler.next()) {
                    speler_id = Integer.parseInt(rsSpeler.getString("speler_id"));

                    // Alle uitdagingen van de speler ophalen
                    String[] gradenStrings = new String[]{TAAL.getString("makkelijk"), TAAL.getString("normaal"), TAAL.getString("moeilijk")};
                    queryUitdaging.setInt(1, speler_id);
                    try (ResultSet rsUitdaging = queryUitdaging.executeQuery()) {
                        while (rsUitdaging.next()) {
                            // Naam van de uitdager en moeilijkheidsgraad toevoegen aan lijst van uitdagingen
                            String uitdagerNaam = rsUitdaging.getString("naam");
                            int moeilijkheidsgraad = rsUitdaging.getInt("moeilijkheidsgraad");

                            int[] winstenArray = new int[]{rsUitdaging.getInt("winMakkelijk"), rsUitdaging.getInt("winNormaal"), rsUitdaging.getInt("winMoeilijk")};
                            int winsten = winstenArray[moeilijkheidsgraad - 1];
                            String moeilijkheidsgraadString = gradenStrings[moeilijkheidsgraad - 1];

                            String result = String.format("%s, %s %s, %s %d (%d", uitdagerNaam, TAAL.getString("moeilijkheidsgraad").toLowerCase() + ": ", moeilijkheidsgraadString.toLowerCase(), TAAL.getString("winsten"), winsten, rsUitdaging.getInt("uitdaging_id"));
                            uitdagingen.add(result);
                        }
                    }
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        // Uitdagingen terug geven
        return uitdagingen;
    }

    /**
     * Geef een spel terug voor de uitgedaagde speler horende bij een bepaalde
     * uitdaging.
     *
     * @param uitdaging_id Het ID van de uitdaging
     * @param spelerNaam De naam van de speler
     * @return Een spel dat hoort bij de opgegeven uitdaging
     */
    public Spel geefUitdaging(int uitdaging_id, String spelerNaam) {
        // Init
        Spel spel = null;

        // Probeer een uitdaging op te halen
        try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
                PreparedStatement queryUitdaging = conn.prepareStatement(SELECT_UITDAGING);
                PreparedStatement querySpelersUitdagingID = conn.prepareStatement(UPDATE_SPELERSUITDAGINGID2)) {
            queryUitdaging.setInt(1, uitdaging_id);
            // Uitdaging ophalen
            try (ResultSet rsUitdaging = queryUitdaging.executeQuery()) {
                if (rsUitdaging.next()) {
                    // Spel maken horend bij de uitdaging
                    spel = SPEL_FACTORY.getSpel(rsUitdaging.getInt("moeilijkheidsgraad"));
                    List<String> kleurenCode = new ArrayList<>(Arrays.asList(rsUitdaging.getString("teKrakenCode").split(" ")));

                    // Te kraken code instellen
                    int teller = 0;
                    Map<String, PinCode> kleurenMap = spel.getKleurenMap();
                    for (String string : kleurenCode) {
                        spel.getTeKrakenCode().setPinCode(kleurenMap.get(string), teller);
                        teller++;
                    }

                    // Van dit spel een uitdaging maken
                    spel.setUitdaging();

                    // Uitgedaagde speler linken aan uitdaging
                    querySpelersUitdagingID.setInt(1, uitdaging_id);
                    querySpelersUitdagingID.setString(2, spelerNaam);
                    querySpelersUitdagingID.executeUpdate();
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        // Spel terug geven
        return spel;
    }

    /**
     * Update de speler zijn aantal zetten bij een bepaalde uitdaging en zeg of
     * de speler de uitdager was of niet.
     *
     * @param uitdaging_id Het ID van de uitdaging
     * @param zetten Het aantal zetten
     * @param spelerNaam De naam van de speler
     * @return True als deze speler de uitdager was, anders false
     */
    public boolean updateUitdagingZetten(int uitdaging_id, int zetten, String spelerNaam) {
        // Init
        boolean uitdager = false;

        try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
                PreparedStatement queryUitdagingSpeler = conn.prepareStatement(SELECT_UITDAGING_SPELER)) {
            // Bepaal of de speler de uitdager if of de uitgedaagde
            queryUitdagingSpeler.setInt(1, uitdaging_id);
            queryUitdagingSpeler.setString(2, spelerNaam);
            try (ResultSet rsUitdagingSpeler = queryUitdagingSpeler.executeQuery()) {
                if (rsUitdagingSpeler.next()) {
                    if (rsUitdagingSpeler.getInt("speler_id") == rsUitdagingSpeler.getInt("uitdager_id")) {
                        uitdager = true;
                    }
                }
            }

            // Verander zijn aantal zetten bij de uitdaging op de gepaste plaats
            try (PreparedStatement queryUitdagerOfUitgedaagde = uitdager ? conn.prepareStatement(UPDATE_UITDAGERZETTEN) : conn.prepareStatement(UPDATE_UITGEDAAGDEZETTEN)) {
                queryUitdagerOfUitgedaagde.setInt(1, zetten);
                queryUitdagerOfUitgedaagde.setInt(2, uitdaging_id);
                queryUitdagerOfUitgedaagde.executeUpdate();
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        return uitdager;
    }

    /**
     * Geef de zetten van beide spelers van een uitdaging terug.
     *
     * @param uitdaging_id Het ID van de uitdaging
     * @return Het aantal zetten van beide spelers
     */
    public int[] geefUitdagingZetten(int uitdaging_id) {
        // Init
        int[] zetten = new int[2];

        // Probeer de zetten van beide spelers van een uitdaging op te halen
        try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
                PreparedStatement queryZetten = conn.prepareStatement(SELECT_UITDAGINGZETTEN)) {
            queryZetten.setInt(1, uitdaging_id);
            try (ResultSet rsZetten = queryZetten.executeQuery()) {
                if (rsZetten.next()) {
                    zetten[0] = rsZetten.getInt("zettenUitdager");
                    zetten[1] = rsZetten.getInt("zettenUitgedaagde");
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        // Geef de zetten terug
        return zetten;
    }

    /**
     * Geef de spelerIDs van beide spelers van een uitdaging terug.
     *
     * @param uitdaging_id Het ID van de uitdaging
     * @return De IDs van beide spelers van deze uitdaging
     */
    public int[] geefUitdagingSpelerIDs(int uitdaging_id) {
        // Init
        int[] ids = new int[2];

        // Probeer IDs op te halen van de beide spelers van een uitdaging
        try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
                PreparedStatement querySpelerIDs = conn.prepareStatement(SELECT_UITDAGINGSPELERIDS)) {
            querySpelerIDs.setInt(1, uitdaging_id);
            try (ResultSet rsSpelerIDs = querySpelerIDs.executeQuery()) {
                if (rsSpelerIDs.next()) {
                    ids[0] = rsSpelerIDs.getInt("uitdager_id");
                    ids[1] = rsSpelerIDs.getInt("uitgedaagde_id");
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        // Geef IDs terug
        return ids;
    }

    /**
     * Verwijder een uitdaging uit de databank.
     *
     * @param uitdaging_id Het ID van de uitdaging
     */
    public void deleteUitdaging(int uitdaging_id) {
        try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
                PreparedStatement query = conn.prepareStatement(DELETE_UITDAGING)) {
            query.setInt(1, uitdaging_id);
            query.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

}
