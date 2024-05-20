package boardgame;

import javafx.application.Platform;
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

public class startController {
    @FXML
    public Button startGame;
    @FXML
    public TextField enteredName;

    private static final Logger logger = LogManager.getLogger(startController.class);

    @FXML
    public void switchToStart(ActionEvent event) throws IOException {
        if (!enteredName.getText().equals("")) {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/boardGame.fxml"));
            stage.setScene(new Scene(root));
            stage.show();
            logger.info("The entered name is " + enteredName.getText());
        } else {
            var alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Enter a name!");
            alert.setContentText("You need to set a name to play the game!");
            alert.showAndWait();
        }
    }
}
