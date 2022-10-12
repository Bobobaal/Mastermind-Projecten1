package gui;

import domein.DomeinController;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import main.Taal;

/**
 * FXML Controller class van het MoeilijkheidsgraadKeuzeScherm.
 *
 * @author groep 31
 */
public class MoeilijkheidsgraadKeuzeSchermController extends GridPane {

    private final boolean uitdaging;
    private final DomeinController dc;
    private final HoofdScherm hs;
    private final ResourceBundle TAAL;

    private final Map<RadioButton, Integer> radioButtonMap;

    @FXML
    private ToggleGroup tgrMoeilijkheid;
    @FXML
    private RadioButton rdbMakkelijk;
    @FXML
    private RadioButton rdbNormaal;
    @FXML
    private RadioButton rdbMoeilijk;
    @FXML
    private Button btnKeuze;
    @FXML
    private Button btnTerug;

    /**
     * Bouw een nieuw MoeilijkheidsgraadKeuzeScherm.
     *
     * @param dc De domeincontroller
     * @param uitdaging True als het om een uitdaging gaat, anders false
     * @param hs Het hoofdscherm
     */
    public MoeilijkheidsgraadKeuzeSchermController(DomeinController dc, boolean uitdaging, HoofdScherm hs) {
        // Scherm laden
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MoeilijkheidsgraadKeuzeScherm.fxml"));
        loader.setController(this);
        loader.setRoot(this);
        try {
            loader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        // Init
        this.uitdaging = uitdaging;
        this.dc = dc;
        this.hs = hs;
        TAAL = Taal.getTaal();
        radioButtonMap = new HashMap<RadioButton, Integer>() {
            {
                put(rdbMakkelijk, 1);
                put(rdbNormaal, 2);
                put(rdbMoeilijk, 3);
            }
        };

        // GUI bouwen
        buildGui();
    }

    /**
     * Bouw de GUI.
     *
     */
    private void buildGui() {
        // Elementen opvullen
        rdbMakkelijk.setText(TAAL.getString("makkelijk"));
        rdbNormaal.setText(TAAL.getString("normaal"));
        rdbMoeilijk.setText(TAAL.getString("moeilijk"));
        btnKeuze.setText(TAAL.getString("selecteer"));
        btnTerug.setText(TAAL.getString("terug"));

        // Scherm configureren
        int[] winsten = dc.geefWinstenSpeler();
        if (winsten[0] < 20) {
            rdbNormaal.setDisable(true);
            int rest = 20 - winsten[0];
            rdbNormaal.setText(String.format(TAAL.getString("winstenNodig"), rdbNormaal.getText(), rest, TAAL.getString("makkelijk").toLowerCase()));
        }
        if (winsten[1] < 20) {
            rdbMoeilijk.setDisable(true);
            int rest = 20 - winsten[1];
            rdbMoeilijk.setText(String.format(TAAL.getString("winstenNodig"), rdbMoeilijk.getText(), rest, TAAL.getString("normaal").toLowerCase()));
        }

        // Witruimte
        this.setHgap(15);
        this.setVgap(15);
    }
    
    @FXML
    private void btnOnMouseClickedReturn(MouseEvent event) {
        hs.toonMenu();
    }

    @FXML
    private void btnOnMouseClicked(MouseEvent event) {
        int moeilijkheid = radioButtonMap.get((RadioButton) tgrMoeilijkheid.getSelectedToggle());
        if (uitdaging == false) {
            dc.initialiseerNieuwSpel(moeilijkheid);
            hs.toonSpel(false);
        } else {
            hs.toonLijst(2, moeilijkheid);
        }
    }

    /**
     * Update het scherm.
     *
     */
    public void update() {
        // Scherm configureren
        rdbNormaal.setDisable(false);
        rdbMoeilijk.setDisable(false);

        // GUI bouwen
        buildGui();
    }

}
