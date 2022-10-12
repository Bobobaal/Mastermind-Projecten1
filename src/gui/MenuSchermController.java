package gui;

import domein.DomeinController;
import java.io.IOException;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import main.Taal;

/**
 * FXML Controller class van het MenuScherm.
 *
 * @author groep 31
 */
public class MenuSchermController extends BorderPane {

    private final DomeinController dc;
    private final HoofdScherm hs;
    private final ResourceBundle TAAL;

    @FXML
    private Label lblHallo;
    @FXML
    private ListView<String> lsvMenu;
    @FXML
    private Button btnKeuze;

    /**
     * Maak een nieuw MenuScherm.
     *
     * @param dc De domeincontroller
     * @param hs Het hoofdscherm
     */
    public MenuSchermController(DomeinController dc, HoofdScherm hs) {
        // Scherm laden
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MenuScherm.fxml"));
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
        // Elementen opvullen en button disablen
        if (lsvMenu.getSelectionModel().getSelectedIndex() == -1) {
            btnKeuze.setDisable(true);
        }
        lblHallo.setText(TAAL.getString("hallo") + ", " + dc.geefSpelerNaam() + "!\n" + TAAL.getString("wat"));
        lsvMenu.setItems(FXCollections.observableArrayList(TAAL.getString("menu1"), TAAL.getString("menu2"), TAAL.getString("menu3"), TAAL.getString("menu4"), TAAL.getString("menu5"), TAAL.getString("menu0")));
        btnKeuze.setText(TAAL.getString("selecteer"));
    }

    @FXML
    private void btnOnMouseClicked(MouseEvent event) {
        int menu = lsvMenu.getSelectionModel().getSelectedIndex();
        switch (menu) {
            case 0:
                // Start een nieuw spel
                hs.toonMoeilijkheid(false);
                break;
            case 1:
                // Laad een spel
                hs.toonLijst(1, 0);
                break;
            case 2:
                // Daag iemand uit op een bepaalde moeilijkheidsgraad
                if (!dc.isSpelerInUitdaging()) {
                    hs.toonMoeilijkheid(true);
                } else {
                    toonUitdagingAlert();
                }
                break;
            case 3:
                // Accepteer een uitdaging
                if (!dc.isSpelerInUitdaging()) {
                    hs.toonLijst(3, 0);
                } else {
                    toonUitdagingAlert();
                }
                break;
            case 4:
                // Toon het klassement
                hs.toonKlassement();
                break;
            case 5:
                // Afsluiten
                sluitApp();
                break;
        }
    }

    @FXML
    private void lsvOnMouseClicked(MouseEvent event) {
        // Enabled de button
        if (lsvMenu.getSelectionModel().getSelectedIndex() != -1) {
            btnKeuze.setDisable(false);
        }
    }

    /**
     * Sluit de applicatie.
     *
     */
    private void sluitApp() {
        // Elementen opvullen
        ButtonType ja = new ButtonType(TAAL.getString("ja"), ButtonBar.ButtonData.OK_DONE);
        ButtonType nee = new ButtonType(TAAL.getString("nee"), ButtonBar.ButtonData.CANCEL_CLOSE);

        // Alertscherm bouwen
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, TAAL.getString("zeker"), ja, nee);
        alert.setTitle(TAAL.getString("afsluiten"));
        alert.setHeaderText(TAAL.getString("menu0"));

        // Wachten op input en afhandelen
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ja) {
            Platform.exit();
        }
    }

    /**
     * Toon een error omdat de speler al een openstaande uitdaging heeft.
     *
     */
    private void toonUitdagingAlert() {
        // Alertscherm bouwen
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(TAAL.getString("uitdaging"));
        alert.setHeaderText(TAAL.getString("userAlreadyInChallengeError"));
        alert.showAndWait();
    }

    /**
     * Update het scherm.
     *
     */
    public void update() {
        // GUI bouwen
        buildGui();
    }

}
