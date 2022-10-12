package gui;

import domein.DomeinController;
import java.io.IOException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import main.Taal;

/**
 * FXML Controller class van het MeldAanRegistreerScherm.
 *
 * @author groep 31
 */
public class MeldAanRegistreerSchermController extends GridPane {

    private final DomeinController dc;
    private final HoofdScherm hs;
    private final ResourceBundle TAAL;
    private boolean aanmelden;

    @FXML
    private RadioButton rdbMeldAan;
    @FXML
    private RadioButton rdbRegistreer;
    @FXML
    private Label lblNaam;
    @FXML
    private Label lblWachtwoord;
    @FXML
    private Label lblBWachtwoord;
    @FXML
    private TextField txfNaam;
    @FXML
    private PasswordField pwfWachtwoord;
    @FXML
    private PasswordField pwfBWachtwoord;
    @FXML
    private Button btnRegMeld;
    @FXML
    private Label lblError;

    /**
     * Maak een nieuw MeldAanRegistreerScherm.
     * 
     * @param dc De domeincontroller
     * @param hs  Het hoofdscherm
     */
    public MeldAanRegistreerSchermController(DomeinController dc, HoofdScherm hs) {
        // Scherm laden
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MeldAanRegistreerScherm.fxml"));
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
        aanmelden = true;

        // GUI bouwen
        buildGui();
    }

    /**
     * Bouw de GUI.
     * 
     */
    private void buildGui() {
        // Elementen opvullen
        rdbMeldAan.setText(TAAL.getString("aanmelden"));
        rdbRegistreer.setText(TAAL.getString("registreren"));
        lblNaam.setText(TAAL.getString("naam") + ":");
        lblWachtwoord.setText(TAAL.getString("wachtwoord") + ":");
        lblBWachtwoord.setText(TAAL.getString("wachtwoordBevestiging") + ":");
        lblBWachtwoord.setVisible(false);
        pwfBWachtwoord.setVisible(false);
        btnRegMeld.setText(TAAL.getString("aanmelden"));

        // Witruimte
        this.setHgap(15);
        this.setVgap(15);
    }

    @FXML
    private void btnRegMeldOnMouseClicked(MouseEvent event) {
        String naam, wachtwoord, bwachtwoord;
        try {
            naam = txfNaam.getText();
            wachtwoord = pwfWachtwoord.getText();
            if (aanmelden) {
                dc.meldAan(naam, wachtwoord);
            } else {
                bwachtwoord = pwfBWachtwoord.getText();
                dc.registreer(naam, wachtwoord, bwachtwoord);
            }
            hs.toonMenu();
        } catch (Exception e) {
            lblError.setText(e.getMessage());
        }
    }

    @FXML
    private void rdbOnAction(ActionEvent event) {
        if (event.getSource().equals(rdbMeldAan)) {
            // Aanmelden
            lblBWachtwoord.setVisible(false);
            pwfBWachtwoord.setVisible(false);
            btnRegMeld.setText(TAAL.getString("aanmelden"));
            aanmelden = true;
        } else {
            // Registreren
            lblBWachtwoord.setVisible(true);
            pwfBWachtwoord.setVisible(true);
            btnRegMeld.setText(TAAL.getString("registreren"));
            aanmelden = false;
        }
        lblError.setText("");
    }

}
