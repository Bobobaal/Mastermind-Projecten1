package main;

import javafx.application.Application;
import domein.DomeinController;
import gui.HoofdScherm;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * De main klasse van de volledige applicatie (met GUI!) waar het spel initieel
 * wordt opgestart.
 *
 * @author groep 31
 */
public class StartUpGUI extends Application {

    @Override
    public void start(Stage stage) {
        // Domeincontroller aanmaken
        DomeinController dc = new DomeinController();
        
        // Hoofdscherm GUI aanmaken
        HoofdScherm hs = new HoofdScherm(dc);
        
        // Scene en stage maken met hoofdscherm
        Scene scene = new Scene(hs);
        stage.setScene(scene);
        stage.setTitle("Groep 31's Mastermind");
        hs.setStage(stage);
        stage.show();
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                ResourceBundle TAAL = Taal.getTaal();
                ButtonType ja = new ButtonType(TAAL.getString("ja"), ButtonBar.ButtonData.OK_DONE);
                ButtonType nee = new ButtonType(TAAL.getString("nee"), ButtonBar.ButtonData.CANCEL_CLOSE);
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, TAAL.getString("zeker"), ja, nee);
                alert.setTitle(TAAL.getString("afsluiten"));
                alert.setHeaderText(TAAL.getString("menu0"));
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() != ja) {
                    event.consume();
                }
            }
        });
    }

    /**
     * Main.
     *
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }

}
