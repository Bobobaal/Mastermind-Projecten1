package gui;

import domein.DomeinController;
import domein.PinEvaluatie;
import exceptions.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.collections.FXCollections;
import javafx.geometry.HPos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import main.Taal;

/**
 * Controller class van het SpelScherm.
 *
 * @author Groep 31
 */
public class SpelScherm extends GridPane {

    private final ResourceBundle TAAL;
    private final DomeinController dc;
    private final HoofdScherm hs;
    private Button zet;
    private Button opslaan;
    private int aantalPinnen;

    private static Map<String, Color> kleuren;

    /**
     * Maak een nieuw SpelScherm.
     *
     * @param dc De domeincontroller
     * @param laden True als het om een geladen spel gaat, false als het om een
     * nieuw spel gaat
     * @param hs Het hoofdscherm
     */
    public SpelScherm(DomeinController dc, boolean laden, HoofdScherm hs) {
        this.dc = dc;
        this.hs = hs;
        TAAL = Taal.getTaal();

        kleuren = new HashMap<String, Color>() {
            {
                put(TAAL.getString("ROOD"), Color.RED);
                put(TAAL.getString("GROEN"), Color.GREEN);
                put(TAAL.getString("BLAUW"), Color.BLUE);
                put(TAAL.getString("GEEL"), Color.GOLD);
                put(TAAL.getString("ORANJE"), Color.ORANGE);
                put(TAAL.getString("ROOS"), Color.DEEPPINK);
                put(TAAL.getString("CYAAN"), Color.rgb(0, 218, 229));
                put(TAAL.getString("PAARS"), Color.PURPLE);
                put(TAAL.getString("LEEG"), Color.GRAY);
            }
        };

        buildGui(laden);
    }

    /**
     * Bouw de GUI.
     *
     * @param laden True als het om een geladen spel gaat, false als het om een
     * nieuw spel gaat
     */
    private void buildGui(boolean laden) {
        // Witruimte
        this.setHgap(15);
        this.setVgap(15);

        // Cellen maken in de gridpane
        int rijen;
        aantalPinnen = dc.getAantalPinnenPerRij();
        for (rijen = 0; rijen < dc.getAantalRijen(); rijen++) {
            RowConstraints rows = new RowConstraints(30);
            this.getRowConstraints().add(rows);
            for (int kolommen = 0; kolommen < ((aantalPinnen * 2) + 1); kolommen++) {
                ColumnConstraints columns = new ColumnConstraints(90);
                columns.setHalignment(HPos.CENTER);
                this.getColumnConstraints().add(columns);
                this.add(new Label(""), kolommen, rijen);
            }
        }
        rijen++;
        RowConstraints rijControl = new RowConstraints(30);
        this.getRowConstraints().add(rijControl);

        // Knoppen zet en opslaan toevoegen
        zet = new Button(TAAL.getString("zet"));
        zet.setOnMouseClicked(this::toonZetDialoog);
        this.add(zet, 1, rijen);

        opslaan = new Button(TAAL.getString("spelOpslaan"));
        opslaan.setOnMouseClicked(this::toonOpslaanDialoog);
        this.add(opslaan, 2, rijen);

        // Spelbord vullen als een spel geladen wordt
        if (laden && dc.getIngevuldeRijen() != 0) {
            laadSpelBord();
        }
    }

    /**
     * Toon het dialoogvenster om een zet te doen.
     *
     * @param event Het MouseEvent van het klikken op de zet knop
     */
    private void toonZetDialoog(MouseEvent event) {
        Dialog<String> dialoog = new Dialog<>();
        dialoog.setTitle(TAAL.getString("zet"));
        dialoog.setHeaderText(TAAL.getString("zet"));

        Label lblError = new Label(TAAL.getString("foutePin"));
        lblError.setTextFill(Color.RED);
        lblError.setVisible(false);

        try {
            GridPane grid = new GridPane();
            List<ComboBox<String>> comboBoxen = new ArrayList<>();
            for (int kolom = 0; kolom < dc.getAantalPinnenPerRij(); kolom++) {
                ComboBox<String> kleurenBox = new ComboBox<>();
                Set<String> kleurenSet = new HashSet<>();
                for (String string : dc.getKleuren()) {
                    kleurenSet.add(TAAL.getString(string));
                }
                kleurenBox.setItems(FXCollections.observableArrayList(kleurenSet));
                comboBoxen.add(kleurenBox);
                grid.add(kleurenBox, kolom, 1);
            }
            grid.add(lblError, 0, 0);
            dialoog.getDialogPane().setContent(grid);

            ButtonType ok = new ButtonType(TAAL.getString("ok"), ButtonData.OK_DONE);
            ButtonType annuleer = new ButtonType(TAAL.getString("annuleer"), ButtonData.CANCEL_CLOSE);
            dialoog.getDialogPane().getButtonTypes().addAll(ok, annuleer);
            dialoog.setResultConverter(new Callback<ButtonType, String>() {
                @Override
                public String call(ButtonType b) {
                    if (b == ok) {
                        return "true";
                    }
                    return null;
                }
            });

            int ingevuldeRijen = dc.getIngevuldeRijen();
            boolean gelukt = false;
            while (!gelukt) {
                Optional<String> result = dialoog.showAndWait();
                if (result.isPresent()) {
                    if (result.get() != null || !result.get().isEmpty()) {
                        List<String> userInput = new ArrayList<>();

                        boolean leegVeld = false;
                        for (ComboBox<String> comboBox : comboBoxen) {
                            if (comboBox.getSelectionModel().isEmpty()) {
                                leegVeld = true;
                                lblError.setVisible(true);
                                break;
                            } else {
                                userInput.add(TAAL.getString(comboBox.getSelectionModel().getSelectedItem()));
                            }
                        }
                        if (!leegVeld) {
                            dc.setEvaluatieUserPoging(userInput, ingevuldeRijen);
                            gelukt = true;

                            ingevuldeRijen = dc.getIngevuldeRijen();
                            int aantalPinnenPerRij = dc.getAantalPinnenPerRij();

                            PinEvaluatie[] evaluatiePinnen = dc.geefEvaluatiePinnen(ingevuldeRijen - 1);
                            int kolom = 0;
                            for (ComboBox<String> comboBox : comboBoxen) {
                                Label codePin = ((Label) this.getChildren().get((ingevuldeRijen - 1) * (aantalPinnenPerRij * 2 + 1) + kolom));
                                codePin.setText(comboBox.getSelectionModel().getSelectedItem());
                                codePin.setTextFill(kleuren.get(codePin.getText()));

                                Label evaluatiePin = ((Label) this.getChildren().get((ingevuldeRijen - 1) * (aantalPinnenPerRij * 2 + 1) + (kolom + aantalPinnenPerRij + 1)));
                                if (evaluatiePinnen[kolom] != null) {
                                    evaluatiePin.setText(evaluatiePinnen[kolom].toString());
                                } else {
                                    evaluatiePin.setText(PinEvaluatie.L.toString());
                                }

                                kolom++;
                            }

                            // Tussenkolom
                            Label tussenKolom = ((Label) this.getChildren().get((ingevuldeRijen - 1) * (aantalPinnenPerRij * 2) + kolom + dc.getIngevuldeRijen() - 1));
                            tussenKolom.setText("     ");
                        }
                    } else {
                        gelukt = true;
                    }
                } else {
                    gelukt = true;
                }
            }

            boolean geraden = dc.getEvaluatieUserPoging(ingevuldeRijen - 1);
            if (geraden || ingevuldeRijen == dc.getAantalRijen()) {
                handelSpelAf();
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            // Do nothing, gebeurt enkel als je het scherm sluit terwijl je
            // in de combobox zit.
        }
    }

    /**
     * Toon het dialoogvenster om het spel op te slaan.
     *
     * @param event Het MouseEvent van het klikken op de opslaan knop
     */
    private void toonOpslaanDialoog(MouseEvent event) {
        Dialog<String> dialoog = new Dialog<>();
        dialoog.setTitle(TAAL.getString("spelOpslaan"));
        dialoog.setHeaderText(TAAL.getString("opslaan"));

        TextField txfNaam = new TextField();
        Label lblError = new Label(TAAL.getString("nameEmptyError"));
        lblError.setTextFill(Color.RED);
        lblError.setVisible(false);

        GridPane grid = new GridPane();
        grid.add(txfNaam, 1, 1);
        grid.add(lblError, 1, 0);
        dialoog.getDialogPane().setContent(grid);

        ButtonType ok = new ButtonType(TAAL.getString("ok"), ButtonData.OK_DONE);
        ButtonType annuleer = new ButtonType(TAAL.getString("annuleer"), ButtonData.CANCEL_CLOSE);
        dialoog.getDialogPane().getButtonTypes().addAll(ok, annuleer);
        dialoog.setResultConverter(new Callback<ButtonType, String>() {
            @Override
            public String call(ButtonType b) {
                if (b == ok) {
                    return txfNaam.getText();
                }
                return null;
            }
        });

        boolean gelukt = false;
        while (!gelukt) {
            Optional<String> result = dialoog.showAndWait();
            if (result.isPresent()) {
                if (!result.get().isEmpty() && result.get() != null) {
                    try {
                        dc.slaSpelOp(result.get());
                        hs.toonMenu();
                        gelukt = true;
                    } catch (VerplichtVeldException e) {
                        lblError.setVisible(true);
                    }
                } else {
                    lblError.setVisible(true);
                }
            } else {
                gelukt = true;
            }
        }
    }

    /**
     * Toon de statistieken van het spel als het uit gespeeld is.
     *
     * @param geraden True als het spel geraden is, anders false
     */
    private void handelSpelAf() {
        String message = dc.handelGespeeldSpelAf();
        ButtonType ok = new ButtonType(TAAL.getString("ok"), ButtonBar.ButtonData.OK_DONE);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, message, ok);
        alert.setTitle(TAAL.getString("resultaat"));
        alert.setHeaderText(TAAL.getString("resultaat"));
        alert.showAndWait();
        hs.toonMenu();
    }

    /**
     * Vul het spelbord als een spel geladen wordt.
     *
     */
    private void laadSpelBord() {
        int aantalPinnenPerRij = dc.getAantalPinnenPerRij();
        int kolom;
        for (int rij = 0; rij < dc.getIngevuldeRijen(); rij++) {
            PinEvaluatie[] evaluatiePinnen = dc.geefEvaluatiePinnen(rij);
            for (kolom = 0; kolom < aantalPinnenPerRij; kolom++) {
                Label codePin = ((Label) this.getChildren().get(rij * (aantalPinnenPerRij * 2 + 1) + kolom));

                String kleur = dc.geefKleurCodepin(rij, kolom);
                codePin.setText(kleur);
                codePin.setTextFill(kleuren.get(kleur));

                Label evaluatiePin = ((Label) this.getChildren().get((rij) * (aantalPinnenPerRij * 2 + 1) + (kolom + aantalPinnenPerRij + 1)));
                if (evaluatiePinnen[kolom] != null) {
                    evaluatiePin.setText(evaluatiePinnen[kolom].toString());
                } else {
                    evaluatiePin.setText(PinEvaluatie.L.toString());
                }
            }

            Label tussenKolom = ((Label) this.getChildren().get((rij) * (aantalPinnenPerRij * 2 + 1) + kolom));
            tussenKolom.setText("     ");
        }
    }

}
