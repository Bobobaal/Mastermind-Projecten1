package gui;

import domein.DomeinController;
import exceptions.SpelerIsInUitdagingException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import main.Taal;

/**
 * FXML Controller class van het KeuzeLijstScherm.
 *
 * @author groep 31
 */
public class KeuzeLijstSchermController extends BorderPane {

    private final DomeinController dc;
    private final HoofdScherm hs;
    private final ResourceBundle TAAL;
    private int type;
    private int moeilijkheid;
    
    private final String[] errors;

    @FXML
    private ListView<String> lsvLijst;
    @FXML
    private Button btnTerug;
    @FXML
    private Button btnKeuze;
    @FXML
    private Label lblError;
    @FXML
    private Label lblLeeg;

    /**
     * Maak een nieuw KeuzeLijstScherm.
     *
     * @param dc De domeincontroller
     * @param type Het type als een int
     * @param moeilijkheid De moeilijkheidsgraad als een int
     * @param hs Het hoofdscherm
     */
    public KeuzeLijstSchermController(DomeinController dc, int type, int moeilijkheid, HoofdScherm hs) {
        // Scherm laden
        FXMLLoader loader = new FXMLLoader(getClass().getResource("KeuzeLijstScherm.fxml"));
        loader.setController(this);
        loader.setRoot(this);
        try {
            loader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        // Init
        this.type = type;
        this.moeilijkheid = moeilijkheid;
        this.dc = dc;
        this.hs = hs;
        TAAL = Taal.getTaal();

        // Elementen opvullen
        errors = new String[]{TAAL.getString("selecteerSpel"), TAAL.getString("selecteerSpeler"), TAAL.getString("selecteerUitdaging")};
        btnKeuze.setText(TAAL.getString("selecteer"));
        btnTerug.setText(TAAL.getString("terug"));

        // GUI bouwen
        buildGui();
    }

    /**
     * Bouw de GUI.
     *
     */
    private void buildGui() {
        ObservableList<String> items = null;
        List<String> lijst;
        switch (type) {
            case 1:
                lijst = dc.geefOpgeslagenSpellen();
                if (lijst.isEmpty()) {
                    lblLeeg.setVisible(true);
                    btnKeuze.setDisable(true);
                    lblLeeg.setText(TAAL.getString("geenSpellen"));
                } else {
                    items = FXCollections.observableArrayList(lijst);
                }
                break;
            case 2:
                lijst = dc.geefUitdaagbareSpelers(moeilijkheid);
                if (lijst.isEmpty()) {
                    lblLeeg.setVisible(true);
                    btnKeuze.setDisable(true);
                    lblLeeg.setText(TAAL.getString("geenSpelers"));
                } else {
                    items = FXCollections.observableArrayList(lijst);
                }
                break;
            case 3:
                List<String> lijstTemp = dc.geefOpgeslagenUitdagingen();
                lijst = new ArrayList<>();
                for (String string : lijstTemp) {
                    lijst.add(string.substring(0, string.indexOf('(')).trim());
                }
                if (lijst.isEmpty()) {
                    lblLeeg.setVisible(true);
                    btnKeuze.setDisable(true);
                    lblLeeg.setText(TAAL.getString("geenUitdagingen"));
                } else {
                    items = FXCollections.observableArrayList(lijst);
                }
                break;
        }
        lsvLijst.setItems(items);
    }

    @FXML
    private void btnOnMouseClickedReturn(MouseEvent event) {
        // Terug gaan naar het menu
        hs.toonMenu();
    }

    @FXML
    private void btnOnMouseClicked(MouseEvent event) {
        String item = lsvLijst.getSelectionModel().getSelectedItem();
        if (item != null) {
            switch (type) {
                case 1:
                    // Een spel laden
                    String spelNaam = item.substring(0, item.indexOf("(")).trim();
                    dc.laadSpel(spelNaam);
                    hs.toonSpel(true);
                    break;
                case 2:
                    // Een speler uitdagen
                    String spelerNaam = item.substring(0, item.indexOf(",")).trim().toLowerCase();
                    try {
                        dc.daagUit(spelerNaam, moeilijkheid);
                        hs.toonSpel(false);
                    } catch (SpelerIsInUitdagingException e) {
                        lblError.setText(TAAL.getString("userAlreadyInChallengeError"));
                    }
                    break;
                case 3:
                    // Een uitdaging accepteren
                    List<String> lijst = dc.geefOpgeslagenUitdagingen();
                    String uitdaging = lijst.get(lsvLijst.getSelectionModel().getSelectedIndex());
                    int uitdaging_id = Integer.parseInt(uitdaging.substring(uitdaging.indexOf('(') + 1));
                    try {
                        dc.accepteerUitdaging(uitdaging_id);
                        hs.toonSpel(false);
                    } catch (SpelerIsInUitdagingException e) {
                        lblError.setText(TAAL.getString("userAlreadyInChallengeError"));
                    }
                    break;
            }
        } else {
            // Error
            lblError.setText(errors[type]);
            lblError.setVisible(true);
        }
    }

    /**
     * Update dit scherm.
     *
     * @param type Het type als een int
     * @param moeilijkheid De moeilijkheidsgraad als een int
     */
    public void update(int type, int moeilijkheid) {
        this.type = type;
        this.moeilijkheid = moeilijkheid;
        lblError.setVisible(false);
        lblLeeg.setVisible(false);
        buildGui();
    }

}
