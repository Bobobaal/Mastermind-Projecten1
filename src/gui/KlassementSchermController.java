package gui;

import domein.DomeinController;
import java.io.IOException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import main.Taal;

/**
 * FXML Controller class van het KlassementScherm.
 *
 * @author groep 31
 */
public class KlassementSchermController extends GridPane {

    private final DomeinController dc;
    private final ResourceBundle TAAL;
    private final HoofdScherm hs;

    @FXML
    private Label lblMakkelijk;
    @FXML
    private Label lblMoeilijk;
    @FXML
    private Label lblNormaal;
    @FXML
    private TextArea txaMakkelijk;
    @FXML
    private TextArea txaNormaal;
    @FXML
    private TextArea txaMoeilijk;
    @FXML
    private Button btnTerug;

    /**
     * Maak een nieuw KlassementScherm.
     * 
     * @param dc De domeincontroller
     * @param hs Het hoofdscherm
     */
    public KlassementSchermController(DomeinController dc, HoofdScherm hs) {
        // Scherm laden
        FXMLLoader loader = new FXMLLoader(getClass().getResource("KlassementScherm.fxml"));
        loader.setController(this);
        loader.setRoot(this);
        try {
            loader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        
        // Init
        this.dc = dc;
        this.hs = hs;
        TAAL = Taal.getTaal();
        
        // GUI bouwen
        buildGui();
    }

    /**
     * Bouw de GUI.
     * 
     */
    private void buildGui() {
        // Elementen opvullen
        lblMakkelijk.setText(TAAL.getString("makkelijk") + ":");
        lblNormaal.setText(TAAL.getString("normaal") + ":");
        lblMoeilijk.setText(TAAL.getString("moeilijk") + ":");
        txaMakkelijk.setText(dc.geefKlassement(1).replaceAll("\t", ""));
        txaNormaal.setText(dc.geefKlassement(2).replaceAll("\t", ""));
        txaMoeilijk.setText(dc.geefKlassement(3).replaceAll("\t", ""));
        btnTerug.setText(TAAL.getString("terug"));
    }
    
    @FXML
    private void btnOnMouseClicked(MouseEvent event) {
        // Terug gaan naar het menu
        hs.toonMenu();
    }

    /**
     * Update het scherm
     * 
     */
    public void update() {
        // Elementen opvullen
        txaMakkelijk.setText(dc.geefKlassement(1));
        txaNormaal.setText(dc.geefKlassement(2));
        txaMoeilijk.setText(dc.geefKlassement(3));
    }

}
