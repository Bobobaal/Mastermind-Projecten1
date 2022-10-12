package domein.spel;

import domein.PinCode;
import domein.PinEvaluatie;
import domein.rij.RijVulbaar;
import domein.rij.RijKraak;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import cui.Consolekleuren;
import java.util.ResourceBundle;
import main.Taal;

/**
 * Abstracte klasse dat een spel voorstelt.
 *
 * @author groep 31
 */
public abstract class Spel {

    protected static final ResourceBundle TAAL = Taal.getTaal();

    protected SpelBord spelBord;
    protected int moeilijkheidsgraad;
    protected int aantalRijen;
    protected int ingevuldeRijen;
    protected int aantalPinnenPerRij;
    protected boolean leegMag;
    protected boolean uniekMoet;

    protected Set<String> kleuren;
    protected Map<String, PinCode> kleurenMap;

    protected String naam;

    protected boolean uitdaging;

    /**
     * Maak een nieuw spel aan.
     *
     * @param moeilijkheidsgraad De moeilijkheidsgraad als een int
     * @param aantalRijen Het aantal rijen
     * @param aantalPinnenPerRij Het aantal pinnen per rij
     * @param leegMag True als er lege pinnen mogen voorkomen, anders false
     * @param uniekMoet True als alle pinnen uniek moeten zijn, anders false
     */
    public Spel(int moeilijkheidsgraad, int aantalRijen, int aantalPinnenPerRij, boolean leegMag, boolean uniekMoet) {
        this.moeilijkheidsgraad = moeilijkheidsgraad;
        this.aantalRijen = aantalRijen;
        this.leegMag = leegMag;
        this.uniekMoet = uniekMoet;
        this.aantalPinnenPerRij = aantalPinnenPerRij;
        ingevuldeRijen = 0;
        spelBord = new SpelBord(this, moeilijkheidsgraad, aantalRijen, aantalPinnenPerRij, leegMag, uniekMoet);

        kleuren = new HashSet<>();
        kleurenMap = new HashMap<>();
        for (PinCode pinCode : PinCode.PINCODES) {
            String kleur = pinCode.name();
            if (!(!leegMag && kleur.equals("LEEG"))) {
                kleuren.add(kleur);
                kleurenMap.put(kleur, pinCode);
            }
        }

        uitdaging = false;
    }

    /**
     * Controleer of dit spel bij een uitdaging hoort.
     *
     * @return True als dit spel hoort bij een uitdaging, anders false
     */
    public boolean isUitdaging() {
        return uitdaging;
    }

    /**
     * Return de naam van het spel.
     *
     * @return De naam van het spel
     */
    public String getNaam() {
        return naam;
    }

    /**
     * Return de moeilijkheidsgraad als een integer.
     *
     * @return De moeilijkheidsgraad als een int
     */
    public int getMoeilijkheidsgraad() {
        return moeilijkheidsgraad;
    }

    /**
     * Return het aantal ingevulde rijen in dit spel.
     *
     * @return Het aantal ingevulde rijen in dit spel
     */
    public int getIngevuldeRijen() {
        return ingevuldeRijen;
    }

    /**
     * Return het aantal pinnen per rij.
     *
     * @return Het aantal pinnen per rij
     */
    public int getAantalPinnenPerRij() {
        return aantalPinnenPerRij;
    }

    /**
     * Return of er lege velden mogen ingevuld worden in dit spel.
     *
     * @return True als er lege pinnen mogen voorkomen, anders false
     */
    public boolean isLeegMag() {
        return leegMag;
    }

    /**
     * Return of alle velden uniek moeten zijn in dit spel.
     *
     * @return True als alle pinnen uniek moeten zijn, anders false
     */
    public boolean isUniekMoet() {
        return uniekMoet;
    }

    /**
     * Return het aantal rijen dat dit spel bevat.
     *
     * @return Het aantal rijen
     */
    public int getAantalRijen() {
        return aantalRijen;
    }

    /**
     * Return de kleuren van de codepinnen dat gebruikt worden in dit spel.
     *
     * @return Een set van de kleuren toegestaan in dit spel
     */
    public Set<String> getKleuren() {
        return kleuren;
    }

    /**
     * Return de kleuren van de codepinnen dat gebruikt worden in dit spel.
     *
     * @return Een map van kleurenstrings naar codepinnen
     */
    public Map<String, PinCode> getKleurenMap() {
        return kleurenMap;
    }

    /**
     * Return de te kraken code van het spel.
     *
     * @return De te kraken code
     */
    public RijKraak getTeKrakenCode() {
        return spelBord.getTeKrakenCode();
    }

    /**
     * Return het spelbord van dit spel.
     *
     * @return Het spelbord (alle rijen)
     */
    public RijVulbaar[] getRijen() {
        return spelBord.getRijen();
    }

    /**
     * Zet de uitdaging flag aan.
     *
     */
    public void setUitdaging() {
        uitdaging = true;
    }

    /**
     * Geef dit spel een naam.
     *
     * @param naam De naam van het spel
     */
    public void setNaam(String naam) {
        if (naam != null && !naam.isEmpty()) {
            this.naam = naam;
        }
    }

    /**
     * Voeg een rij toe aan het spel op een bepaalde positie.
     *
     * @param rij Een nieuwe rij
     * @param index De positie van deze rij
     */
    public void setRij(RijVulbaar rij, int index) {
        spelBord.setRij(rij, index);
        ingevuldeRijen++;
    }

    /**
     * Verander de te kraken code (om spellen te laden).
     *
     * @param teKrakenCode De (nieuwe) te kraken code
     */
    public void setTeKrakenCode(RijKraak teKrakenCode) {
        spelBord.setTeKrakenCode(teKrakenCode);
    }

    /**
     * Controleer of een ingevoerde rij de code heeft gekraakt.
     *
     * @param index De positie van de te controleren rij (zet)
     * @return True als deze rij (zet) de code gekraakt heeft
     */
    public boolean evalueerRij(int index) {
        // Init
        RijVulbaar rij = spelBord.getRij(index);
        boolean evaluatie = true;

        // Evalueren
        for (PinEvaluatie pinEvaluatie : rij.getPinEvaluaties()) {
            if (pinEvaluatie == null || !pinEvaluatie.name().equals("Z")) {
                // Geen zwarte pin
                evaluatie = false;
            }
        }

        // Returnen
        return evaluatie;
    }
    
    /**
     * Geef de evaluatiepinnen van een rij op een bepaalde index terug.
     * 
     * @param index De positie van de rij
     * @return De evaluatiepinnen van een rij op een bepaalde index
     */
    public PinEvaluatie[] getEvaluatiePinnen(int index) {
        return spelBord.getEvaluatiePinnen(index);
    }

    /**
     * Bereken de evaluatiepinnen van een rij op een bepaalde index.
     *
     * @param index De positie van de te evalueren rij (zet)
     */
    public abstract void berekenEvaluatiePinnen(int index);

    /**
     * Geef een lege rij terug (factory).
     *
     * @return Een nieuwe, lege rij horende bij dit spel
     */
    public abstract RijVulbaar rijFactory();

    @Override
    public String toString() {
        // Maak lege string aan
        String string = "";
        string += "\n==================================================================================\n";
        int rijIndex = 1;

        // Vul de string met de rijen van het spel
        for (RijVulbaar rij : spelBord.getRijen()) {
            if (rij != null) {
                // Nummering rijen
                if (rijIndex < 10) {
                    string += "0";
                }
                string += rijIndex + ")   ";
                rijIndex++;

                // Codepinnen rijen
                for (PinCode pinCode : rij.getPinCodes()) {
                    // Kleur per kleur toevoegen voor elke rij
                    PinCode kleur;
                    if (!(pinCode == null || pinCode.toString().isEmpty())) {
                        kleur = pinCode;
                    } else {
                        kleur = PinCode.LEEG;
                    }
                    string += String.format(Consolekleuren.KLEUREN.get(kleur.name()) + " %s" + Consolekleuren.KLEUREN.get("LEEG"), centerStringSpaces(kleur.toString(), 8));
                }

                string += "   |   ";

                // Evaluatiepinnen rijen
                for (PinEvaluatie pinEvaluatie : rij.getPinEvaluaties()) {
                    PinEvaluatie kleur;
                    if (!(pinEvaluatie == null || pinEvaluatie.toString().isEmpty())) {
                        kleur = pinEvaluatie;
                    } else {
                        kleur = PinEvaluatie.L;
                    }
                    string += kleur + " ";
                }
            }
            string += "\n";
        }
        string += "==================================================================================";

        // Geef de stringrepresentatie terug
        return string;
    }

    /**
     * Hulpmethode om een string te centreren.
     *
     * @param string De string dat je wilt centreren
     * @param size De kolomgrootte waarin je de string zogezegd wilt centreren
     * @return Een gecentreerde string van een bepaalde grootte
     */
    private String centerStringSpaces(String string, int size) {
        // Maak een nieuwe string aan
        String centeredString = string;

        // Bepaal de witruimte links en rechts
        int spacesLeft = (int) Math.floor((double) (size - string.length()) / 2.0);
        int spacesRight = (int) Math.ceil((double) (size - string.length()) / 2.0);

        // Vul de witruimte in
        for (int i = 0; i < spacesLeft; i++) {
            centeredString = " " + centeredString;
        }
        for (int i = 0; i < spacesRight; i++) {
            centeredString = centeredString + " ";
        }

        // Geef de gecentreerde string terug
        return centeredString;
    }

}
