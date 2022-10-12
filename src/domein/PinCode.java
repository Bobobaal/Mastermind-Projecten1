package domein;

import java.util.ResourceBundle;
import main.Taal;

/**
 * Een opsomtype voor alle codepinnen.
 *
 * @author groep 31
 */
public enum PinCode {
    ROOD, GROEN, BLAUW, GEEL, ORANJE, ROOS, CYAAN, PAARS, LEEG;

    private static final ResourceBundle TAAL = Taal.getTaal();

    public static final PinCode[] PINCODES = values();
    public static final int AANTAL_PINCODES = PINCODES.length;

    @Override
    public String toString() {
        if (name().equals("LEEG")) {
            return " ------ ";
        } else {
            return TAAL.getString(name());
        }
    }

}
