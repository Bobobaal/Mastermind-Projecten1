package domein;

import exceptions.VerplichtVeldException;
import java.util.ResourceBundle;
import main.Taal;

/**
 * Klasse om spelers voor te stellen.
 *
 * @author groep 31
 */
public class Speler {

    private static final ResourceBundle TAAL = Taal.getTaal();

    private String naam;
    private String wachtwoord;
    private int[] winsten = null;
    private int uitdaging_id = 0;

    /**
     * Maak een nieuwe speler aan met een naam en wachtwoord.
     *
     * @param naam De naam van de speler
     * @param wachtwoord Het wachtwoord van de speler
     * @throws exceptions.VerplichtVeldException
     */
    public Speler(String naam, String wachtwoord) throws VerplichtVeldException {
        setNaam(naam);
        setWachtwoord(wachtwoord);
    }

    /**
     * Vraag de naam van de speler op.
     *
     * @return De naam van de speler
     */
    public String getNaam() {
        return naam;
    }

    /**
     * Vraag het wachtwoord van de speler op.
     *
     * @return Het wachtwoord van de speler
     */
    public String getWachtwoord() {
        return wachtwoord;
    }

    /**
     * Verander de naam van de speler.
     *
     * @param naam De (nieuwe) naam van de speler
     * @throws VerplichtVeldException
     */
    private void setNaam(String naam) throws VerplichtVeldException {
        // Optioneel mogelijk: minimaal aantal karakters forceren
        if (naam == null || naam.isEmpty()) {
            throw new VerplichtVeldException(TAAL.getString("nameIsRequiredError"));
        }
        this.naam = naam;
    }

    /**
     * Verander het wachtwoord van de speler.
     *
     * @param wachtwoord Het (nieuwe) wachtwoord van de speler
     * @throws VerplichtVeldException
     */
    private void setWachtwoord(String wachtwoord) throws VerplichtVeldException {
        if (wachtwoord == null || wachtwoord.isEmpty()) {
            throw new VerplichtVeldException(TAAL.getString("passwordIsRequiredError"));
        }
        if (!(wachtwoord.length() >= 8 && wachtwoord.substring(1, wachtwoord.length() - 2).matches("[a-zA-Z]+") && Character.isDigit(wachtwoord.charAt(0)) && Character.isDigit(wachtwoord.charAt(wachtwoord.length() - 1)))) {
            throw new VerplichtVeldException(TAAL.getString("passwordWrongFormatError"));
        }
        this.wachtwoord = wachtwoord;
    }

    /**
     * Geef het aantal winsten voor de huidige speler.
     *
     * @param spelerRepository De spelerRepository om de winsten op te halen
     * indien dit nog niet gebeurd is
     * @return
     */
    public int[] getWinsten(SpelerRepository spelerRepository) {
        if (winsten == null) {
            winsten = spelerRepository.geefAantalWinsten(naam);
        }
        return winsten;
    }

    /**
     * Update de winsten voor de huidige speler.
     *
     * @param moeilijkheidsgraad De moeilijkheidsgraad als een int
     * @param spelerRepository De spelerRepository om de winsten op te halen
     * indien dit nog niet gebeurd is
     */
    public void updateWinsten(int moeilijkheidsgraad, SpelerRepository spelerRepository) {
        if (winsten == null) {
            winsten = spelerRepository.geefAantalWinsten(naam);
        }
        winsten[moeilijkheidsgraad - 1] = winsten[moeilijkheidsgraad - 1] + 1;
    }

    /**
     * Controleer of de huidige speler al een uitdaging aangegaan is.
     *
     * @param spelerRepository De spelerRepository om het ID van zijn uitdaging
     * op te halen indien dit nog niet gebeurd is
     * @return True als deze speler een openstaande uitdaging heeft, anders
     * false
     */
    public boolean isInUitdaging(SpelerRepository spelerRepository) {
        if (uitdaging_id == 0) {
            uitdaging_id = spelerRepository.geefUitdagingID(naam);
        }
        return uitdaging_id != 0;
    }

    /**
     * Verander het ID van de speler zijn openstaande uitdaging.
     *
     * @param uitdaging_id Het ID van de uitdaging
     */
    public void setUitdaging_id(int uitdaging_id) {
        this.uitdaging_id = uitdaging_id;
    }

    /**
     * Geef het ID van de speler zijn openstaande uitdaging.
     *
     * @return Het ID van de speler zijn openstaande uitdaging
     */
    public int getUitdaging_id() {
        return uitdaging_id;
    }

}
