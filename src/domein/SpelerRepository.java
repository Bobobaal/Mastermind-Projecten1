package domein;

import exceptions.*;
import java.util.List;
import java.util.ResourceBundle;
import persistentie.SpelerMapper;
import main.Taal;

/**
 * Klasse dat kan communiceren met de spelermapper om spelers toe te voegen en
 * op te zoeken.
 *
 * @author groep 31
 */
public class SpelerRepository {

    private ResourceBundle TAAL;

    private final SpelerMapper mapper;

    /**
     * Maak een nieuwe Speler Repository aan.
     *
     */
    public SpelerRepository() {
        TAAL = null;
        mapper = new SpelerMapper();
    }

    /**
     * Vul de TAAL in voor deze repository.
     *
     */
    public void vulTaalIn() {
        if (TAAL == null) {
            TAAL = Taal.getTaal();
            mapper.vulTaalIn();
        }
    }

    /**
     * Voeg een nieuwe speler toe in de databank.
     *
     * @param speler Een speler
     * @throws exceptions.NaamInGebruikException
     */
    public void voegSpelerToe(Speler speler) throws NaamInGebruikException {
        if (mapper.geefSpelerNaam(speler.getNaam()) != null) {
            throw new NaamInGebruikException(TAAL.getString("nameAlreadyUsedError") + " " + TAAL.getString("opnieuw"));
        }

        mapper.voegSpelerToe(speler);
    }

    /**
     * Geef een speler terug uit de databank.
     *
     * @param spelerNaam De spelerNaam van de speler
     * @param wachtwoord Het wachtwoord van de speler
     * @return Een speler
     * @throws exceptions.VerkeerdWachtwoordException
     * @throws exceptions.VerplichtVeldException
     */
    public Speler geefSpeler(String spelerNaam, String wachtwoord) throws VerkeerdWachtwoordException, VerplichtVeldException {
        return mapper.geefSpeler(spelerNaam, wachtwoord);
    }

    /**
     * Geef het aantal winsten per moeilijkheidsgraad voor de opgegeven speler
     * terug uit de databank.
     *
     * @param spelerNaam De spelerNaam van een speler
     * @return Het aantal winsten per moeilijkheidsgraad voor deze speler
     */
    public int[] geefAantalWinsten(String spelerNaam) {
        return mapper.geefAantalWinsten(spelerNaam);
    }

    /**
     * Pas de winsten aan voor een bepaalde speler.
     *
     * @param spelerNaam De spelerNaam van een speler
     * @param winsten Zijn (nieuwe) winsten
     */
    public void updateAantalWinsten(String spelerNaam, int[] winsten) {
        mapper.updateAantalWinsten(spelerNaam, winsten);
    }

    /**
     * Geef een lijst van spelers die uitgedaagd kunnen worden op een bepaalde
     * moeilijkheidsgraad.
     *
     * @param spelerNaam De naam van de speler
     * @param moeilijkheidsgraad De moeilijkheidsgraad als een int
     * @return Een lijst van spelers die uitgedaagd kunnen worden op een
     * bepaalde moeilijkheidsgraad
     */
    public List<String> geefUitdaagbareSpelers(String spelerNaam, int moeilijkheidsgraad) {
        return mapper.geefUitdaagbareSpelers(spelerNaam, moeilijkheidsgraad);
    }

    /**
     * Controleer of de huidige speler al een uitdaging open heeft staan.
     *
     * @param spelerNaam De naam van een speler
     * @return True als deze speler een openstaande uitdaging heeft, anders
     * false
     */
    public int geefUitdagingID(String spelerNaam) {
        return mapper.geefUitdagingID(spelerNaam);
    }

    /**
     * Zet de uitdaging_id bij de huidige speler op 0 (uitdaging voltooid).
     *
     * @param spelerNaam De naam van een speler
     */
    public void resetUitdagingID(String spelerNaam) {
        mapper.resetUitdagingID(spelerNaam);
    }

    /**
     * Geef de speler een winst of verlies bij na een uitdaging.
     *
     * @param speler_id Het ID van de speler
     * @param moeilijkheidsgraad De moeilijkheidsgraad als een int
     * @param gewonnen True als de speler gewonnen heeft, anders false
     */
    public void updateUitdagingWinstOfVerlies(int speler_id, int moeilijkheidsgraad, boolean gewonnen) {
        mapper.updateUitdagingWinstOfVerlies(speler_id, moeilijkheidsgraad, gewonnen);
    }

    /**
     * Geef het klassement terug voor een bepaalde moeilijkheidsgraad.
     *
     * @param moeilijkheidsgraad De moeilijkheidsgraad als een int
     * @return het klassement voor een bepaalde moeilijkheidsgraad als een
     * string
     */
    public String geefKlassement(int moeilijkheidsgraad) {
        return mapper.geefKlassement(moeilijkheidsgraad);
    }

}
