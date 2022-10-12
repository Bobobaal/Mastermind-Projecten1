package domein;

import domein.spel.Spel;
import domein.rij.RijVulbaar;
import domein.spel.SpelFactory;
import exceptions.*;
import java.util.List;
import java.util.Map;
import cui.Consolekleuren;
import domein.spel.SpelBord;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
import main.Taal;

/**
 * Klasse dat dient als centrale controller van het volledige spel.
 *
 * @author groep 31
 */
public class DomeinController {

    private ResourceBundle TAAL = null;

    private final SpelerRepository spelerRepository;
    private final SpelRepository spelRepository;
    private Speler speler;
    private Spel spel;
    private boolean geraden = false;
    private final SpelFactory spelFactory;

    private Map<String, PinCode> kleurenMap;

    /**
     * Maak een nieuwe domeincontroller aan.
     *
     */
    public DomeinController() {
        spelerRepository = new SpelerRepository();
        spelRepository = new SpelRepository();
        spelFactory = new SpelFactory();
    }

    /**
     * Registreer een nieuwe speler in de databank en meld deze ook meteen aan.
     *
     * @param naam De naam van de speler
     * @param wachtwoord Het wachtwoord van de speler
     * @param wachtwoordBevestiging De bevestiging van het wachtwoord
     * @throws WachtwoordBevestigingException
     * @throws VerplichtVeldException
     * @throws NaamInGebruikException
     */
    public void registreer(String naam, String wachtwoord, String wachtwoordBevestiging) throws WachtwoordBevestigingException, VerplichtVeldException, NaamInGebruikException {
        // Taal instellen indien leeg
        vulTaalIn();

        // Wachtwoord en bevestiging controleren
        if (!wachtwoord.equals(wachtwoordBevestiging)) {
            throw new WachtwoordBevestigingException(TAAL.getString("passwordConfirmationError") + " " + TAAL.getString("opnieuw"));
        }

        // Nieuwe speler registreren en aanmelden
        try {
            Speler nieuweSpeler = new Speler(naam, wachtwoord);
            spelerRepository.voegSpelerToe(nieuweSpeler);
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Meld een al reeds bestaande speler aan.
     *
     * @param naam De naam van de speler
     * @param wachtwoord Het wachtwoord van de speler
     * @throws SpelerBestaatNietException
     * @throws VerkeerdWachtwoordException
     * @throws exceptions.VerplichtVeldException
     */
    public void meldAan(String naam, String wachtwoord) throws SpelerBestaatNietException, VerkeerdWachtwoordException, VerplichtVeldException {
        // Taal instellen indien leeg
        vulTaalIn();

        // Verplichte velden controleren
        if (naam == null || wachtwoord == null || naam.isEmpty() || wachtwoord.isEmpty()) {
            throw new VerplichtVeldException(TAAL.getString("nameOrPasswordEmptyError") + " " + TAAL.getString("opnieuw"));
        }

        // Controleer of de combinatie naam/wachtwoord juist is
        Speler gevondenSpeler = null;
        try {
            gevondenSpeler = spelerRepository.geefSpeler(naam, wachtwoord);
        } catch (VerkeerdWachtwoordException e) {
            throw e;
        }

        // Geef de speler terug als hij gevonden is, zoniet throw een error
        if (gevondenSpeler != null) {
            setSpeler(gevondenSpeler);
        } else {
            throw new SpelerBestaatNietException(TAAL.getString("nameDoesNotExistError") + " " + TAAL.getString("opnieuw"));
        }
    }

    /**
     * Initialiseer een nieuw spel van een bepaalde moeilijkheidsgraad.
     *
     * @param moeilijkheidsgraad De moeilijkheidsgraad als een int
     */
    public void initialiseerNieuwSpel(int moeilijkheidsgraad) {
        spel = spelFactory.getSpel(moeilijkheidsgraad);
        initialiseer();
    }

    /**
     * Initialiseer een bestaand spel.
     *
     * @param spel Een spel
     */
    private void initialiseerSpel(Spel spel) {
        this.spel = spel;
        initialiseer();
    }

    /**
     * Hulpmethode voor het initialiseren van (nieuwe) spellen.
     *
     */
    private void initialiseer() {
        kleurenMap = spel.getKleurenMap();
        geraden = false;
    }

    /**
     * Print het spel(bord) af.
     *
     * @return Het spelbord in stringformaat
     */
    public String toonSpelbord() {
        return spel.toString();
    }

    /**
     * Geef het aantal ingevulde rijen.
     *
     * @return Het aantal ingevulde rijen
     */
    public int getIngevuldeRijen() {
        return spel.getIngevuldeRijen();
    }

    /**
     * Geef het totaal aantal rijen.
     *
     * @return Het totaal aantal rijen.
     */
    public int getAantalRijen() {
        return spel.getAantalRijen();
    }

    /**
     * Geef het aantal pinnen per rij.
     *
     * @return Het aantal pinnen per rij
     */
    public int getAantalPinnenPerRij() {
        return spel.getAantalPinnenPerRij();
    }

    /**
     * Geef de geldige codepinkleuren van een spel.
     *
     * @return De geldige codepinkleuren van een spel
     */
    public Set<String> getKleuren() {
        return spel.getKleuren();
    }

    /**
     * Evalueer een zet van de speler en vul deze in in het spel.
     *
     * @param userInput De zet van een speler
     * @param index De plaats van de zet op het spelbord
     */
    public void setEvaluatieUserPoging(List<String> userInput, int index) {
        // Lijst van kleuren (user input) omzetten naar een rij voor het spel
        RijVulbaar input = spel.rijFactory();
        for (int i = 0; i < userInput.size(); i++) {
            input.setPinCode(kleurenMap.get(userInput.get(i)), i);
        }

        // User input invullen
        spel.setRij(input, index);

        // Evalueer de input van de speler
        spel.berekenEvaluatiePinnen(index);
    }

    /**
     * Controleer of een zet de code heeft gekraakt.
     *
     * @param index De plaats van een zet op het spelbord
     * @return True als de code gekraakt is door deze zet, anders false
     */
    public boolean getEvaluatieUserPoging(int index) {
        // Controleer of het de juiste code was
        if (spel.evalueerRij(index)) {
            geraden = true;
        }
        return geraden;
    }

    /**
     * Evalueer de uitkomst van een gespeeld spel.
     *
     * @return eindeString
     */
    public String handelGespeeldSpelAf() {
        // Init
        String[] statistiek = null;
        String eindeString = "";

        // Het spel in ten einde
        if (geraden) {
            // Het spel is geraden
            speler.updateWinsten(spel.getMoeilijkheidsgraad(), spelerRepository);
            spelerRepository.updateAantalWinsten(speler.getNaam(), geefWinstenSpeler());
            eindeString += "\n" + TAAL.getString("gefeliciteerd") + " " + TAAL.getString("codeKraak");
            statistiek = geefStatistiek();
            eindeString += "\n" + TAAL.getString("codeWas") + " " + statistiek[0];
            eindeString += "\n" + TAAL.getString("aantalPog") + " " + statistiek[1];
        } else {
            // Het spel is niet geraden
            eindeString += "\n" + TAAL.getString("gefaald");
            eindeString += "\n" + TAAL.getString("codeWas") + " " + spel.getTeKrakenCode();
        }

        // Als dit een uitdaging was, uitdaging correct afhandelen
        if (this.spel.isUitdaging()) {
            // Aantal zetten bepalen, op 13 zetten als iemand gefaald heeft (zodat gefaalde uitdager niet wint van uitegdaagde die 12 beurten nodig had om te kraken bij gelijkspel)
            if (statistiek == null) {
                statistiek = geefStatistiek();
            }
            if (!geraden && Integer.parseInt(statistiek[1]) == 12) {
                statistiek[1] = "13";
            }
            boolean uitdager = spelRepository.updateUitdagingZetten(speler.getUitdaging_id(), Integer.parseInt(statistiek[1]), speler.getNaam());

            // Als beide spelers de uitdaging gespeeld hebben = boodschap geven of je gewonnen of verloren hebt
            int[] zetten = spelRepository.geefUitdagingZetten(speler.getUitdaging_id());
            if (zetten[0] != 0 && zetten[1] != 0) {
                // Bepalen wie er gewonnen heeft
                boolean gewonnen = bepaalWinnaar(zetten[0], zetten[1], this.spel.getMoeilijkheidsgraad(), spelRepository.geefUitdagingSpelerIDs(speler.getUitdaging_id()), uitdager);
                if (gewonnen) {
                    eindeString += "\n" + TAAL.getString("uGewonnen");
                } else {
                    eindeString += "\n" + TAAL.getString("uVerloren");
                }

                // Uitdaging verwijderen
                spelRepository.deleteUitdaging(speler.getUitdaging_id());
            }

            // Uitdaging verwijderen bij de speler
            speler.setUitdaging_id(0); // Lokaal updaten
            spelerRepository.resetUitdagingID(speler.getNaam()); // DB updaten
        }

        // Sterren tonen
        int[] sterren = berekenSterrenSpeler();
        eindeString += "\n" + TAAL.getString("aantalSter") + " " + sterren[0];
        if (sterren[0] < 5) {
            eindeString += "\n" + TAAL.getString("aantalWinst") + " " + sterren[1];
        }
        eindeString += "\n\n";

        return eindeString;
    }

    /**
     * Bereken de sterren van een speler op het einde van een spel.
     *
     * @return Het aantal sterren én het aantal winsten nodig tot de volgende
     * ster
     */
    private int[] berekenSterrenSpeler() {
        // Eerste waarde = aantal sterren
        // Tweede waarde = aantal winsten nodig tot volgende ster
        int[] sterren = new int[2];

        // Som van alle gewonnen spellen per graad berekenen
        int som = 0;
        int[] winstenSpeler = geefWinstenSpeler();
        for (int i = 0; i < winstenSpeler.length; i++) {
            som += winstenSpeler[i];
        }

        // Sterren en aantal winsten tot volgende ster bepalen adhv de som
        if (som >= 250) {
            sterren[0] = 5;
        }
        if (som >= 100 && som < 250) {
            sterren[0] = 4;
            sterren[1] = 250 - som;
        }
        if (som >= 50 && som < 100) {
            sterren[0] = 3;
            sterren[1] = 100 - som;
        }
        if (som >= 20 && som < 50) {
            sterren[0] = 2;
            sterren[1] = 50 - som;
        }
        if (som >= 10 && som < 20) {
            sterren[0] = 1;
            sterren[1] = 20 - som;
        }
        if (som < 10) {
            //sterren[0] = 0; should be initalized on zero already
            sterren[1] = 10 - som;
        }

        // Sterren data returnen
        return sterren;
    }

    /**
     * Bepaal de winnaar van een uitdaging.
     *
     * @param zettenUitdager Aantal zetten van de uitdager
     * @param zettenUitgedaagde Aantal zetten van de uitgedaagde
     * @param moeilijkheidsgraad De moeilijkheidsgraad als een int
     * @param spelerIDs De spelerIDs van beide spelers
     * @param uitdager True als de uitdager geëvalueerd wordt, anders false
     * @return True als de uitdager wint, anders false
     */
    private boolean bepaalWinnaar(int zettenUitdager, int zettenUitgedaagde, int moeilijkheidsgraad, int[] spelerIDs, boolean uitdager) {
        if (zettenUitdager <= zettenUitgedaagde) {
            // Uitdager wint
            spelerRepository.updateUitdagingWinstOfVerlies(spelerIDs[0], moeilijkheidsgraad, true);
            spelerRepository.updateUitdagingWinstOfVerlies(spelerIDs[1], moeilijkheidsgraad, false);
            return uitdager;
        } else {
            // Uitgedaagde wint
            spelerRepository.updateUitdagingWinstOfVerlies(spelerIDs[0], moeilijkheidsgraad, false);
            spelerRepository.updateUitdagingWinstOfVerlies(spelerIDs[1], moeilijkheidsgraad, true);
            return !uitdager;
        }
    }

    /**
     * Geef de statistieken voor het gespeelde spel (teKrakenCode en
     * aantalPogingen).
     *
     * @return De te kraken code van het spel en het aantal ingevulde rijen
     */
    private String[] geefStatistiek() {
        String[] statistiek = new String[2];
        statistiek[0] = spel.getTeKrakenCode().toString();
        statistiek[1] = String.valueOf(spel.getIngevuldeRijen());
        return statistiek;
    }

    /**
     * Sla het spel op.
     *
     * @param spelNaam De naam van het spel
     * @return null
     * @throws exceptions.VerplichtVeldException
     */
    public RijVulbaar slaSpelOp(String spelNaam) throws VerplichtVeldException {
        try {
            // Naam geven gelukt
            spelRepository.voegSpelToe(speler.getNaam(), spel.isUitdaging(), spelNaam, spel);
            return null;
        } catch (VerplichtVeldException e) {
            // Naam bestaat al tussen de speler zijn opgeslagen spellen
            throw e;
        }
    }

    /**
     * Laad een spel.
     *
     * @param spelNaam De naam van het spel
     */
    public void laadSpel(String spelNaam) {
        // Spel laden
        spel = spelRepository.geefSpel(spelNaam);

        // Spel initialiseren
        initialiseerSpel(spel);
    }

    /**
     * Daag iemand uit.
     *
     * @param naamUitgedaagde De naam van de uitgedaagde speler
     * @param moeilijkheidsgraad De moeilijkheidsgraad als een int
     * @throws exceptions.SpelerIsInUitdagingException
     */
    public void daagUit(String naamUitgedaagde, int moeilijkheidsgraad) throws SpelerIsInUitdagingException {
        // Aanmaken van een spel, met de code die men moet kraken
        spel = spelFactory.getSpel(moeilijkheidsgraad);

        // Opslaan van de uitdaging in de databank
        int uitdaging_id = spelRepository.voegUitdagingToe(geefSpelerNaam(), naamUitgedaagde, moeilijkheidsgraad, spel.getTeKrakenCode().getString());

        // Speler linken aan uitdaging
        speler.setUitdaging_id(uitdaging_id);

        // Markeer het spel als een uitdaging
        spel.setUitdaging();

        // Initialiseer het spel voor de uitdager
        initialiseerSpel(spel);
    }

    /**
     * Accepteer een uitdaging.
     *
     * @param uitdaging_id Het ID van de uitdaging
     * @throws SpelerIsInUitdagingException
     */
    public void accepteerUitdaging(int uitdaging_id) throws SpelerIsInUitdagingException {
        // Speler linken aan uitdaging
        speler.setUitdaging_id(uitdaging_id);

        // Maak en start een nieuw spel horende bij de uitdaging
        initialiseerSpel(spelRepository.geefUitdaging(uitdaging_id, speler.getNaam()));
    }

    /**
     * Geef de naam van de huidige speler.
     *
     * @return De naam van de huidige speler
     */
    public String geefSpelerNaam() {
        return speler.getNaam();
    }

    /**
     * Geef een lijst van opgeslagen spellen voor de huidige speler.
     *
     * @return Een lijst van opgeslagen spellen voor de huidige speler
     */
    public List<String> geefOpgeslagenSpellen() {
        return spelRepository.geefOpgeslagenSpellen(speler.getNaam());
    }

    /**
     * Controleer of de speler een openstaande uitdaging heeft.
     *
     * @return True als de speler een openstaande uitdaging heeft, anders false
     */
    public boolean isSpelerInUitdaging() {
        return speler.isInUitdaging(spelerRepository);
    }

    /**
     * Geef een lijst van uitdaagbare spelers.
     *
     * @param moeilijkheidsgraad De moeilijkheidsgraad als een int
     * @return Een lijst van uitdaagbare spelers
     */
    public List<String> geefUitdaagbareSpelers(int moeilijkheidsgraad) {
        return spelerRepository.geefUitdaagbareSpelers(geefSpelerNaam(), moeilijkheidsgraad);
    }

    /**
     * Geef een lijst van opgeslagen uitdagingen voor de huidige speler.
     *
     * @return Een lijst van opgeslagen uitdagingen voor de huidige speler
     */
    public List<String> geefOpgeslagenUitdagingen() {
        return spelRepository.geefUitdagingen(speler.getNaam());
    }

    /**
     * Geef de winsten van de huidige speler.
     *
     * @return De winsten van de huidige speler
     */
    public int[] geefWinstenSpeler() {
        return speler.getWinsten(spelerRepository);
    }

    /**
     * Geef de stringrepresentatie van een bepaald klassement.
     *
     * @param moeilijkheidsgraad De moeilijkheidsgraad als een int
     * @return De stringrepresentatie van een bepaald klassement
     */
    public String geefKlassement(int moeilijkheidsgraad) {
        return spelerRepository.geefKlassement(moeilijkheidsgraad);
    }

    /**
     * Geef de evaluatiepinnen van een rij op een bepaalde index terug.
     *
     * @param index De positie van de rij
     * @return De evaluatiepinnen van een rij op een bepaalde index
     */
    public PinEvaluatie[] geefEvaluatiePinnen(int index) {
        return spel.getEvaluatiePinnen(index);
    }

    /**
     * Geef de naam van het kleur van een codepin op een bepaalde positie terug
     *
     * @param rij De rij van de codepin
     * @param kolom De kolom van de codepin
     * @return De naam van het kleur van de codepin
     */
    public String geefKleurCodepin(int rij, int kolom) {
        return spel.getRijen()[rij].getPinCode(kolom).name();
    }

    /**
     * Verander de huidige speler.
     *
     * @param speler Een speler
     */
    private void setSpeler(Speler speler) {
        this.speler = speler;
    }

    /**
     * Vul de TAAL in voor de domeincontroller.
     *
     */
    private void vulTaalIn() {
        TAAL = Taal.getTaal();
        spelerRepository.vulTaalIn();
        spelRepository.vulTaalIn();
    }

}
