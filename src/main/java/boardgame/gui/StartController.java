package boardgame.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * The controller class of the start scene.
 */
public class StartController {
    /**
     * The button to switch to the game scene.
     */
    @FXML
    public Button startGame;
    /**
     * The text field for the entered name.
     */
    @FXML
    public TextField enteredName;

    private static final Logger logger = LogManager.getLogger(StartController.class);

    /**
     * Switches to the board game scene.
     * @param event the action event
     * @throws IOException if the fxml file cannot be loaded
     */
    @FXML
    public void switchToBoard(ActionEvent event) throws IOException {
        if (!enteredName.getText().isEmpty()) {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/boardGame.fxml"));
            stage.setUserData(enteredName.getText());
            logger.info("The entered name is {}", enteredName.getText());
            logger.info("Switched to the game scene.");
            stage.setScene(new Scene(root));
            stage.show();
        } else {
            var alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Enter a name!");
            alert.setContentText("You need to set a name to play the game!");
            alert.showAndWait();
        }
    }

    /**
     * Switches to the results scene.
     * @param event the action event
     * @throws IOException if the fxml file cannot be loaded
     */
    public void switchToResults(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/result.fxml"));
        logger.info("Switched to the results scene.");
        stage.setScene(new Scene(root));
        stage.show();
    }
}
