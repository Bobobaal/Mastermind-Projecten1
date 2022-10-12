package domein.spel;

/**
 * Klasse dat nieuwe spellen kan aanmaken adhv een moeilijkheidsgraad.
 *
 * @author groep 31
 */
public class SpelFactory {

    /**
     * Return een nieuw, leeg spel adhv de opgegeven moeilijkheidsgraad.
     *
     * @param moeilijkheidsgraad De moeilijkheidsgraad als een int
     * @return Een nieuw spel
     */
    public Spel getSpel(int moeilijkheidsgraad) {
        switch (moeilijkheidsgraad) {
            case 1:
                return new SpelMakkelijk();
            case 2:
                return new SpelNormaal();
            case 3:
                return new SpelMoeilijk();
            default:
                return null; // Will never be reached
        }
    }

}
