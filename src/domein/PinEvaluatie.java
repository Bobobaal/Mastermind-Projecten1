package domein;

import java.util.ResourceBundle;
import main.Taal;

/**
 * Een opsomtype voor alle evaluatiepinnen.
 *
 * @author groep 31
 */
public enum PinEvaluatie {
    Z, W, L; // Zwart, wit of leeg

    private static final ResourceBundle TAAL = Taal.getTaal();

    @Override
    public String toString() {
        if (name().equals("L")) {
            return "-";
        } else {
            return TAAL.getString(name());
        }
    }

}
