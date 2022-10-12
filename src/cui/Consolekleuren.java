package cui;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Een klasse dat de ainsi-codes van consolekleuren bijhoudt.
 *
 * @author groep 31
 */
public class Consolekleuren {

    /**
     * Een map dat kleuren mapt op AINSI color codes voor gekleurde java output.
     *
     */
    public static Map<String, String> KLEUREN = Collections.unmodifiableMap(new HashMap<String, String>() {
        {
            put("LEEG", "\u001B[0m"); // Normaal
            put("ZWART", "\u001B[0m"); // Normaal
            put("WIT", "\u001B[0m"); // Normaal

            put("ROOD", "\u001B[31m");
            put("GROEN", "\u001B[32m");
            put("GEEL", "\u001B[33m");
            put("BLAUW", "\u001B[34m");
            put("PAARS", "\u001B[35m");
            put("CYAAN", "\u001B[36m");
            put("ROOS", "\u001B[0m"); // Normaal
            put("ORANJE", "\u001B[0m"); // Normaal
        }
    });

}
