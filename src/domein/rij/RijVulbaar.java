package domein.rij;

import domein.PinEvaluatie;

/**
 * Klasse dat een lege (in te vullen) rij op het spelbord voorstelt.
 *
 * @author groep 31
 */
public abstract class RijVulbaar extends Rij {

    protected PinEvaluatie[] pinEvaluaties;

    /**
     * Maak een nieuwe vulbare rij aan.
     *
     * @param aantalPinnen Het aantal pinnen in deze rij
     * @param leegMag True als er lege pinnen mogen voorkomen, anders false
     * @param uniekMoet True als alle pinnen uniek moeten zijn, anders false
     */
    public RijVulbaar(int aantalPinnen, boolean leegMag, boolean uniekMoet) {
        super(aantalPinnen, leegMag, uniekMoet);
        pinEvaluaties = new PinEvaluatie[aantalPinnen];
    }

    /**
     * Return de evaluatiepinnen van deze rij.
     *
     * @return De evaluatiepinnen
     */
    public PinEvaluatie[] getPinEvaluaties() {
        return pinEvaluaties;
    }

    /**
     * Verander een evaluatiepin op een bepaalde index.
     *
     * @param pinEvaluatie De evaluatiepin
     * @param index De positie van de evaluatiepin
     */
    public void setPinEvaluatie(PinEvaluatie pinEvaluatie, int index) {
        pinEvaluaties[index] = pinEvaluatie;
    }

}
