package domein.rij;

import domein.PinCode;

/**
 * Abstracte klasse dat een rij voorstelt.
 *
 * @author groep 31
 */
public abstract class Rij {

    protected PinCode[] pinCodes;
    protected int aantalPinnen;
    protected boolean leegMag;
    protected boolean uniekMoet;

    /**
     * Maak een nieuwe rij aan.
     *
     * @param aantalPinnen Het aantal pinnen in deze rij
     * @param leegMag True als er lege pinnen mogen voorkomen, anders false
     * @param uniekMoet True als alle pinnen uniek moeten zijn, anders false
     */
    public Rij(int aantalPinnen, boolean leegMag, boolean uniekMoet) {
        pinCodes = new PinCode[aantalPinnen];
        this.aantalPinnen = aantalPinnen;
        this.leegMag = leegMag;
        this.uniekMoet = uniekMoet;
    }

    /**
     * Return de codepinnen.
     *
     * @return De codepinnen
     */
    public PinCode[] getPinCodes() {
        return pinCodes;
    }

    /**
     * Return of er lege pinnen ingevuld mogen worden in deze rij.
     *
     * @return True als er lege pinnen mogen voorkomen, anders false
     */
    public boolean isLeegMag() {
        return leegMag;
    }

    /**
     * Return de codepin op een bepaalde index.
     *
     * @param index De positie van de codepin
     * @return De codepin
     */
    public PinCode getPinCode(int index) {
        return pinCodes[index];
    }

    /**
     * Voeg een pincode toe op een bepaalde index.
     *
     * @param pinCode De codepin
     * @param index De positie van de codepin
     */
    public void setPinCode(PinCode pinCode, int index) {
        pinCodes[index] = pinCode;
    }

}
