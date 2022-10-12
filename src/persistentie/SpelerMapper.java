package persistentie;

import main.Taal;
import domein.Speler;
import exceptions.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Klasse dat communiceert met de databank mbt spelers.
 *
 * @author groep 31
 */
public class SpelerMapper {

    private ResourceBundle TAAL = null;

    private final String INSERT_SPELER = "INSERT INTO ID222177_g31.speler (naam, wachtwoord) VALUES (?, ?)";

    private final String SELECT_SPELER = "SELECT naam, wachtwoord FROM ID222177_g31.speler WHERE naam LIKE ?";
    private final String SELECT_SPELERNAAM = "SELECT naam FROM ID222177_g31.speler WHERE naam LIKE ?";
    private final String SELECT_WINSTEN = "SELECT winMakkelijk, winNormaal, winMoeilijk FROM ID222177_g31.speler WHERE naam = ?";
    private final String SELECT_SPELERS_MAKKELIJK = "SELECT naam, winMakkelijk AS winst FROM ID222177_g31.speler WHERE naam NOT LIKE ?";
    private final String SELECT_SPELERS_NORMAAL = "SELECT naam, winNormaal AS winst FROM ID222177_g31.speler WHERE naam NOT LIKE ? AND winMakkelijk >= 20";
    private final String SELECT_SPELERS_MOEILIJK = "SELECT naam, winMoeilijk AS winst FROM ID222177_g31.speler WHERE naam NOT LIKE ? AND winNormaal >= 20";
    private final String SELECT_UITDAGINGID = "SELECT uitdaging_id FROM ID222177_g31.speler WHERE naam = ?";
    private final String SELECT_UWINST = "SELECT uWinMakkelijk, uWinNormaal, uWinMoeilijk FROM ID222177_g31.speler WHERE speler_id = ?";
    private final String SELECT_UVERLIES = "SELECT uLossMakkelijk, uLossNormaal, uLossMoeilijk FROM ID222177_g31.speler WHERE speler_id = ?";
    private final String SELECT_TOP15MAKKELIJK = "SELECT naam, (uWinMakkelijk * 3) - uVerliesMakkelijk AS score, uWinMakkelijk + uVerliesMakkelijk as totaleUitdagingen FROM ID222177_g31.speler ORDER BY score DESC, totaleUitdagingen LIMIT 15";
    private final String SELECT_TOP15NORMAAL = "SELECT naam, (uWinNormaal * 3) - uVerliesNormaal AS score, uWinNormaal + uVerliesNormaal as totaleUitdagingen FROM ID222177_g31.speler ORDER BY score DESC, totaleUitdagingen LIMIT 15";
    private final String SELECT_TOP15MOEILIJK = "SELECT naam, (uWinMoeilijk * 3) - uVerliesMoeilijk AS score, uWinMoeilijk + uVerliesMoeilijk as totaleUitdagingen FROM ID222177_g31.speler ORDER BY score DESC, totaleUitdagingen LIMIT 15";

    private final String UPDATE_WINSTEN = "UPDATE ID222177_g31.speler SET winMakkelijk = ?, winNormaal = ?, winMoeilijk = ? WHERE naam = ?";
    private final String UPDATE_UITDAGINGID = "UPDATE ID222177_g31.speler SET uitdaging_id = 0 WHERE naam = ?";
    private final String UPDATE_UWINST = "UPDATE ID222177_g31.speler SET uWinMakkelijk = ?, uWinNormaal = ?, uWinMoeilijk = ? WHERE speler_id = ?";
    private final String UPDATE_UVERLIES = "UPDATE ID222177_g31.speler SET uVerliesMakkelijk = ?, uVerliesNormaal = ?, uVerliesMoeilijk = ? WHERE speler_id = ?";

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
     * Voeg een nieuwe speler toe in de databank.
     *
     * @param speler Een speler
     */
    public void voegSpelerToe(Speler speler) {
        try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
                PreparedStatement query = conn.prepareStatement(INSERT_SPELER)) {
            query.setString(1, speler.getNaam());
            query.setString(2, speler.getWachtwoord());
            query.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Zoek een bepaalde speler op in de databank.
     *
     * @param naam De naam van de speler
     * @param wachtwoord Het wachtwoord van de speler
     * @return Een speler
     * @throws exceptions.VerkeerdWachtwoordException
     * @throws exceptions.VerplichtVeldException
     */
    public Speler geefSpeler(String naam, String wachtwoord) throws VerkeerdWachtwoordException, VerplichtVeldException {
        // Init
        Speler speler = null;

        // Probeer de speler te zoeken
        try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
                PreparedStatement query = conn.prepareStatement(SELECT_SPELER)) {
            query.setString(1, naam);
            try (ResultSet rs = query.executeQuery()) {
                if (rs.next()) {
                    String spelerNaam = rs.getString("naam");
                    String spelerWachtwoord = rs.getString("wachtwoord");

                    if (wachtwoord.equals(spelerWachtwoord)) {
                        speler = new Speler(spelerNaam, spelerWachtwoord);
                    } else {
                        throw new VerkeerdWachtwoordException(TAAL.getString("nameAndPassCombError") + " " + TAAL.getString("opnieuw"));
                    }
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        // Geef de speler terug
        return speler;
    }

    /**
     * Zoek een bepaalde spelersnaam op in de databank.
     *
     * @param naam De naam van een speler
     * @return Het aantal winsten per moeilijkheidsgraad voor deze speler
     */
    public String geefSpelerNaam(String naam) {
        // Init
        String spelerNaam = null;

        // Probeer de spelersnaam op te halen
        try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
                PreparedStatement query = conn.prepareStatement(SELECT_SPELERNAAM)) {
            query.setString(1, naam);
            try (ResultSet rs = query.executeQuery()) {
                if (rs.next()) {
                    spelerNaam = rs.getString("naam");
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        // Geef de spelersnaam terug
        return spelerNaam;
    }

    /**
     * Geef het aantal winOfVerlies per moeilijkheidsgraad voor de opgegeven
     * speler terug uit de databank.
     *
     * @param spelerNaam De spelerNaam van een speler
     * @return Het aantal winsten per moeilijkheidsgraad voor deze speler
     */
    public int[] geefAantalWinsten(String spelerNaam) {
        // Init
        int[] winsten = new int[3];

        // Probeer de winOfVerlies op te halen
        try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
                PreparedStatement query = conn.prepareStatement(SELECT_WINSTEN)) {
            query.setString(1, spelerNaam);
            try (ResultSet rs = query.executeQuery()) {
                if (rs.next()) {
                    winsten[0] = rs.getInt("winMakkelijk");
                    winsten[1] = rs.getInt("winNormaal");
                    winsten[2] = rs.getInt("winMoeilijk");
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        // Geef winOfVerlies terug
        return winsten;
    }

    /**
     * Geef het aantal winOfVerlies per moeilijkheidsgraad voor de opgegeven
     * speler terug uit de databank.
     *
     * @param spelerNaam De spelerNaam van een speler
     * @param winsten Zijn (nieuwe) winsten
     */
    public void updateAantalWinsten(String spelerNaam, int[] winsten) {
        try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
                PreparedStatement query = conn.prepareStatement(UPDATE_WINSTEN)) {
            query.setInt(1, winsten[0]);
            query.setInt(2, winsten[1]);
            query.setInt(3, winsten[2]);
            query.setString(4, spelerNaam);
            query.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Geeft een lijst van mogelijke spelers die je kan uitdagen afhankelijk van
     * je gekozen moeilijkheidsgraad.
     *
     * @param spelerNaam De spelerNaam van de speler
     * @param moeilijkheidsgraad De moeilijkheidsgraad als een int
     * @return Een lijst van spelers die uitgedaagd kunnen worden op een
     * bepaalde moeilijkheidsgraad
     */
    public List<String> geefUitdaagbareSpelers(String spelerNaam, int moeilijkheidsgraad) {
        // Init
        List<String> spelers = new ArrayList<>();

        // Maak een lijst van spelers die de huidige speler kan uitdagen
        try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL)) {
            PreparedStatement querySpelers = null;
            switch (moeilijkheidsgraad) {
                case 1:
                    querySpelers = conn.prepareStatement(SELECT_SPELERS_MAKKELIJK);
                    break;
                case 2:
                    querySpelers = conn.prepareStatement(SELECT_SPELERS_NORMAAL);
                    break;
                case 3:
                    querySpelers = conn.prepareStatement(SELECT_SPELERS_MOEILIJK);
                    break;
            }
            if (querySpelers != null) {
                querySpelers.setString(1, spelerNaam);
                try (ResultSet rsSpelers = querySpelers.executeQuery()) {
                    while (rsSpelers.next()) {
                        String naam = rsSpelers.getString("naam");
                        int winst = rsSpelers.getInt("winst");
                        String result = String.format("%s, %s %d", naam, TAAL.getString("winsten"), winst);
                        spelers.add(result);
                    }
                }
                querySpelers.close();
            } // else: querySpelers kan nooit null zijn hier
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        // Lijst terug geven
        return spelers;
    }

    /**
     * Controleer of de huidige speler al een uitdaging open heeft staan.
     *
     * @param spelerNaam De naam van een speler
     * @return True als deze speler een openstaande uitdaging heeft, anders
     * false
     */
    public int geefUitdagingID(String spelerNaam) {
        // Init
        int uitdaging_id = 0;

        // Controleer of de speler een openstaande uitdaging heeft
        try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
                PreparedStatement queryUitdagingID = conn.prepareStatement(SELECT_UITDAGINGID)) {
            // ID van de huidige speler ophalen
            queryUitdagingID.setString(1, spelerNaam);
            try (ResultSet rsUitdagingID = queryUitdagingID.executeQuery()) {
                if (rsUitdagingID.next()) {
                    uitdaging_id = rsUitdagingID.getInt("uitdaging_id");
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        // Returnen
        return uitdaging_id;
    }

    /**
     * Zet de uitdaging_id bij de huidige speler op 0 (uitdaging voltooid)
     *
     * @param spelerNaam De naam van een speler
     */
    public void resetUitdagingID(String spelerNaam) {
        try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
                PreparedStatement queryResetUitdagingID = conn.prepareStatement(UPDATE_UITDAGINGID)) {
            // Uitdaging_id resetten
            queryResetUitdagingID.setString(1, spelerNaam);
            queryResetUitdagingID.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Geef de speler een winst of verlies bij na een uitdaging.
     *
     * @param speler_id Het ID van de speler
     * @param moeilijkheidsgraad De moeilijkheidsgraad als een int
     * @param gewonnen True als de speler gewonnen heeft, anders false
     */
    public void updateUitdagingWinstOfVerlies(int speler_id, int moeilijkheidsgraad, boolean gewonnen) {
        // Init
        int[] winOfVerlies = new int[3];

        try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL);
                PreparedStatement querySelectWinstOfVerlies = gewonnen ? conn.prepareStatement(SELECT_UWINST) : conn.prepareStatement(SELECT_UVERLIES);
                PreparedStatement queryUpdateWinstOfVerlies = gewonnen ? conn.prepareStatement(UPDATE_UWINST) : conn.prepareStatement(UPDATE_UVERLIES)) {

            // Haal de/het winst/verlies op en pas dit aan
            querySelectWinstOfVerlies.setInt(1, speler_id);
            try (ResultSet rs = querySelectWinstOfVerlies.executeQuery()) {
                if (rs.next()) {
                    winOfVerlies[0] = rs.getInt("uWinMakkelijk");
                    winOfVerlies[1] = rs.getInt("uWinNormaal");
                    winOfVerlies[2] = rs.getInt("uWinMoeilijk");
                }
            }
            winOfVerlies[moeilijkheidsgraad - 1]++;

            // Update winst/verlies
            queryUpdateWinstOfVerlies.setInt(1, winOfVerlies[0]);
            queryUpdateWinstOfVerlies.setInt(2, winOfVerlies[1]);
            queryUpdateWinstOfVerlies.setInt(3, winOfVerlies[2]);
            queryUpdateWinstOfVerlies.setInt(4, speler_id);
            queryUpdateWinstOfVerlies.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Geef het klassement terug voor een bepaalde moeilijkheidsgraad.
     *
     * @param moeilijkheidsgraad De moeilijkheidsgraad als een int
     * @return het klassement voor een bepaalde moeilijkheidsgraad als een
     * string
     */
    public String geefKlassement(int moeilijkheidsgraad) {
        // Init
        String klassement = "";

        // Probeer een klassement op te halen
        try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL)) {
            PreparedStatement queryKlassement = null;
            switch (moeilijkheidsgraad) {
                case 1:
                    queryKlassement = conn.prepareStatement(SELECT_TOP15MAKKELIJK);
                    break;
                case 2:
                    queryKlassement = conn.prepareStatement(SELECT_TOP15NORMAAL);
                    break;
                case 3:
                    queryKlassement = conn.prepareStatement(SELECT_TOP15MOEILIJK);
                    break;
            }
            if (queryKlassement != null) {
                try (ResultSet rsKlassement = queryKlassement.executeQuery()) {
                    int teller = 1;

                    // Spelers toevoegen
                    while (rsKlassement.next()) {
                        klassement += String.format("\t%2d. %s %d%n", teller, rsKlassement.getString("naam"), rsKlassement.getInt("score"));
                        teller++;
                    }
                }
                queryKlassement.close();
            } // else: queryKlassement kan nooit null zijn hier
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        // Geef het klassement terug
        return klassement;
    }

}
