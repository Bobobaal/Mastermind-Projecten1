package domein.spel;

import domein.PinEvaluatie;
import domein.rij.RijVulbaar;
import domein.rij.RijKraak;

/**
 * Klasse dat het spelbord van een spel bijhoudt.
 *
 * @author groep 31
 */
public class SpelBord {

    private final RijVulbaar[] rijen;
    private RijKraak teKrakenCode;

    /**
     * Maak een nieuw spelbord aan (met dezelfde parameters als het bovenliggend
     * spel).
     *
     * @param spel Het spel van dit spelbord
     * @param moeilijkheidsgraad De moeilijkheidsgraad als een int
     * @param aantalRijen Het aantal rijen
     * @param aantalPinnenPerRij Het aantal pinnen per rij
     * @param leegMag True als er lege pinnen mogen voorkomen, anders false
     * @param uniekMoet True als alle pinnen uniek moeten zijn, anders false
     */
    public SpelBord(Spel spel, int moeilijkheidsgraad, int aantalRijen, int aantalPinnenPerRij, boolean leegMag, boolean uniekMoet) {
        rijen = new RijVulbaar[aantalRijen];
        for (int i = 0; i < aantalRijen; i++) {
            rijen[i] = spel.rijFactory();
        }

        teKrakenCode = new RijKraak(aantalPinnenPerRij, leegMag, uniekMoet);
    }

    /**
     * Return de rijen van het spelbord.
     *
     * @return De rijen van het spelbord
     */
    public RijVulbaar[] getRijen() {
        return rijen;
    }

    /**
     * Return een rij op een bepaalde index.
     *
     * @param index De positie van een bepaalde rij
     * @return Een rij
     */
    public RijVulbaar getRij(int index) {
        return rijen[index];
    }

    /**
     * Return de te kraken code.
     *
     * @return De te kraken code
     */
    public RijKraak getTeKrakenCode() {
        return teKrakenCode;
    }

    /**
     * Voeg een rij toe aan het spel op een bepaalde positie.
     *
     * @param rij Een rij
     * @param index De positie van de rij
     */
    public void setRij(RijVulbaar rij, int index) {
        rijen[index] = rij;
    }

    /**
     * Verander de te kraken code (om spellen te laden).
     *
     * @param teKrakenCode De (nieuwe) te kraken code
     */
    public final void setTeKrakenCode(RijKraak teKrakenCode) {
        this.teKrakenCode = teKrakenCode;
    }

    /**
     * Voeg een evaluatiepin toe aan een rij op een bepaalde positie in een
     * bepaalde kolom.
     *
     * @param pinEvaluatie De evaluatiepin
     * @param index De positie (rij) van de evaluatiepin
     * @param kolom De positie (kolom) van de evaluatiepin
     */
    public void setPinEvaluatie(PinEvaluatie pinEvaluatie, int index, int kolom) {
        rijen[index].setPinEvaluatie(pinEvaluatie, kolom);
    }
    
    /**
     * Geef de evaluatiepinnen van een rij op een bepaalde index terug.
     * 
     * @param index De positie van de rij
     * @return De evaluatiepinnen van een rij op een bepaalde index
     */
    public PinEvaluatie[] getEvaluatiePinnen(int index) {
        return rijen[index].getPinEvaluaties();
    }

}
