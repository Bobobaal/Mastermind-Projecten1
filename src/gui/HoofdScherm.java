package gui;

import domein.DomeinController;
import java.util.ResourceBundle;
import javafx.geometry.Insets;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import main.Taal;

/**
 * Het hoofdscherm van de GUI.
 *
 * @author groep 31
 */
public class HoofdScherm extends BorderPane {

    private final DomeinController dc;
    private final ResourceBundle TAAL;
    private MenuSchermController menu;
    private MoeilijkheidsgraadKeuzeSchermController mksc;
    private KeuzeLijstSchermController klsc;
    private KlassementSchermController ksc;
    private SpelScherm ssc;
    private Stage stage;

    /**
     * Maak een nieuw hoofdscherm aan.
     *
     * @param dc De domeincontroller van de applicatie
     */
    public HoofdScherm(DomeinController dc) {
        // Init
        this.dc = dc;
        TAAL = ResourceBundle.getBundle("properties.i18n");
        Taal.setTaal(TAAL);

        // GUI bouwen
        buildGui();
    }

    /**
     * Bouw de GUI.
     *
     */
    private void buildGui() {
        // Witruimte
        this.setPadding(new Insets(15));

        // Laat gebruiker eerst een taal kiezen
        TaalKeuzeSchermController taal = new TaalKeuzeSchermController(this);
        this.setCenter(taal);
    }

    /**
     * Toon het aanmelden/registreren scherm.
     *
     */
    public void toonMeldAanRegistreer() {
        // Lengte en breedte aanpassen van de stage voor het scherm
        stage.setWidth(530);
        stage.setHeight(300);
        
        MeldAanRegistreerSchermController mars = new MeldAanRegistreerSchermController(dc, this);
        this.setCenter(mars);
    }

    /**
     * Toon het menu.
     *
     */
    public void toonMenu() {
        // Lengte en breedte aanpassen van de stage voor het scherm
        stage.setWidth(420);
        stage.setHeight(285);
        
        // Bepalen of het scherm al bestaat ja/nee
        if (menu == null) {
            menu = new MenuSchermController(dc, this);
        } else {
            menu.update();
        }
        this.setCenter(menu);
    }

    /**
     * Toon het moeilijkheidsgraadkeuze scherm.
     *
     * @param uitdaging
     */
    public void toonMoeilijkheid(boolean uitdaging) {
        // Bepalen of het scherm al bestaat ja/nee
        if (mksc == null) {
            mksc = new MoeilijkheidsgraadKeuzeSchermController(dc, uitdaging, this);
        } else {
            mksc.update();
        }
        this.setCenter(mksc);
    }

    /**
     * Toon een bepaalde lijst.
     *
     * @param type Het type/soort scherm als een int
     * @param moeilijkheid De moeilijkheidsgraad als een int
     */
    public void toonLijst(int type, int moeilijkheid) {
        // Lengte en breedte aanpassen van de stage voor het scherm
        stage.setWidth(400);
        stage.setHeight(275);
        
        // Bepalen of het scherm al bestaat ja/nee
        if (klsc == null) {
            klsc = new KeuzeLijstSchermController(dc, type, moeilijkheid, this);
        } else {
            klsc.update(type, moeilijkheid);
        }
        this.setCenter(klsc);
    }

    /**
     * Toon het spel spelen scherm.
     *
     * @param laden True als een spel geladen wordt, false als een nieuw spel
     * gestart wordt
     */
    public void toonSpel(boolean laden) {
        // Lengte en breedte aanpassen van de stage voor het scherm
        stage.setWidth(1225);
        stage.setHeight(680);
        
        ssc = new SpelScherm(dc, laden, this);
        this.setCenter(ssc);
    }

    /**
     * Toon het klassement scherm.
     * 
     */
    public void toonKlassement() {
        // Lengte en breedte aanpassen van de stage voor het scherm
        stage.setWidth(550);
        stage.setHeight(420);

        // Bepalen of het scherm al bestaat ja/nee
        if (ksc == null) {
            ksc = new KlassementSchermController(dc, this);
        } else {
            ksc.update();
        }
        this.setCenter(ksc);
    }

    /**
     * Verander de stage van het hoofdscherm.
     * 
     * @param stage De (nieuwe) stage
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

}
