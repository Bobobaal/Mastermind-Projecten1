package gui;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import main.Taal;

/**
 * FXML Controller class van het TaalKeuzeScherm.
 *
 * @author groep 31
 */
public class TaalKeuzeSchermController extends BorderPane {

    private final HoofdScherm hs;
    private ResourceBundle TAAL;

    @FXML
    private Label lblTaal;

    /**
     * Maak een nieuw TaalKeuzeScherm.
     * 
     * @param hs Het hoofdscherm
     */
    public TaalKeuzeSchermController(HoofdScherm hs) {
        // Scherm laden
        FXMLLoader loader = new FXMLLoader(getClass().getResource("TaalKeuzeScherm.fxml"));
        loader.setController(this);
        loader.setRoot(this);
        try {
            loader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        
        // Init
        this.hs = hs;
        TAAL = Taal.getTaal();
        
        // Elementen opvullen
        lblTaal.setText(TAAL.getString("kiesTaal"));
    }

    @FXML
    private void imvOnMouseClicked(MouseEvent event) {
        hs.toonMeldAanRegistreer();
    }

    @FXML
    private void imvOnMouseEntered(MouseEvent event) {
        // Taal aanpassen
        Locale locale = new Locale(event.getPickResult().getIntersectedNode().getId());
        Taal.setTaal(ResourceBundle.getBundle("properties.i18n", locale));
        
        // Taal opvragen
        TAAL = Taal.getTaal();
        
        // Elementen opvullen
        lblTaal.setText(TAAL.getString("kiesTaal"));
    }

}
