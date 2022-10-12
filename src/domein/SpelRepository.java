package domein;

import domein.spel.Spel;
import exceptions.VerplichtVeldException;
import java.util.List;
import java.util.ResourceBundle;
import main.Taal;
import persistentie.SpelMapper;

/**
 * Klasse dat kan communiceren met de spelmapper om spellen toe te voegen en op
 * te zoeken.
 *
 * @author groep 31
 */
public class SpelRepository {

    private ResourceBundle TAAL;

    private final SpelMapper mapper;

    /**
     * Maak een nieuwe Speler Repository aan.
     *
     */
    public SpelRepository() {
        TAAL = null;
        mapper = new SpelMapper();
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
     * Voeg een nieuw spel toe in de databank.
     *
     * @param spelerNaam De naam van de speler
     * @param isUitdaging True als dit spel bij een uitdaging hoort
     * @param spelNaam De naam van het spel
     * @param spel Het spel
     * @throws exceptions.VerplichtVeldException
     */
    public void voegSpelToe(String spelerNaam, boolean isUitdaging, String spelNaam, Spel spel) throws VerplichtVeldException {
        mapper.voegSpelToe(spelerNaam, isUitdaging, spelNaam, spel);
    }

    /**
     * Zoek een bepaald spel op in de databank.
     *
     * @param spelNaam De naam van het spel
     * @return Het spel
     */
    public Spel geefSpel(String spelNaam) {
        return mapper.geefSpel(spelNaam);
    }

    /**
     * Zoek de namen van alle opgeslagen spellen op van een bepaalde speler in
     * de databank.
     *
     * @param spelerNaam De naam van de speler
     * @return De namen van alle opgeslagen spellen van deze speler
     */
    public List<String> geefOpgeslagenSpellen(String spelerNaam) {
        return mapper.geefOpgeslagenSpellen(spelerNaam);
    }

    /**
     * Voeg een uitdaging toe in de databank.
     *
     * @param uitdagerNaam De naam van de uitdager
     * @param uitgedaagdeNaam De naam van de uitgedaagde
     * @param moeilijkheidsgraad De moeilijkheidsgraad als een int
     * @param teKrakenCode De te kraken code als een string
     * @return Het ID van de uitdaging
     */
    public int voegUitdagingToe(String uitdagerNaam, String uitgedaagdeNaam, int moeilijkheidsgraad, String teKrakenCode) {
        return mapper.voegUitdagingToe(uitdagerNaam, uitgedaagdeNaam, moeilijkheidsgraad, teKrakenCode);
    }

    /**
     * Vraag een lijst van alle uitdagingen voor de huidige speler op aan de
     * databank.
     *
     * @param spelerNaam De naam van de speler
     * @return Een lijst van alle uitdagingen voor de huidige speler
     */
    public List<String> geefUitdagingen(String spelerNaam) {
        return mapper.geefUitdagingen(spelerNaam);
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
        return mapper.geefUitdaging(uitdaging_id, spelerNaam);
    }

    /**
     * Update de speler zijn aantal zetten bij een bepaalde uitdaging.
     *
     * @param uitdaging_id Het ID van de uitdaging
     * @param zetten Het aantal zetten
     * @param spelerNaam De naam van de speler
     * @return True als deze speler de uitdager was, anders false
     */
    public boolean updateUitdagingZetten(int uitdaging_id, int zetten, String spelerNaam) {
        return mapper.updateUitdagingZetten(uitdaging_id, zetten, spelerNaam);
    }

    /**
     * Geef de zetten van beide spelers van een uitdaging terug.
     *
     * @param uitdaging_id Het ID van de uitdaging
     * @return Het aantal zetten van beide spelers
     */
    public int[] geefUitdagingZetten(int uitdaging_id) {
        return mapper.geefUitdagingZetten(uitdaging_id);
    }

    /**
     * Geef de spelerIDs van beide spelers van een uitdaging terug.
     *
     * @param uitdaging_id Het ID van de uitdaging
     * @return De IDs van beide spelers van deze uitdaging
     */
    public int[] geefUitdagingSpelerIDs(int uitdaging_id) {
        return mapper.geefUitdagingSpelerIDs(uitdaging_id);
    }

    /**
     * Verwijder een uitdaging uit de databank.
     *
     * @param uitdaging_id Het ID van de uitdaging
     */
    public void deleteUitdaging(int uitdaging_id) {
        mapper.deleteUitdaging(uitdaging_id);
    }

}
