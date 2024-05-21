package boardgame;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class ResultController {
    @FXML
    public Button backToStartbtn;

    private static final Logger logger = LogManager.getLogger(ResultController.class);


    @FXML
    public void switchToStart(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/startScene.fxml"));
        logger.info("Switched to the start scene.");
        stage.setScene(new Scene(root));
        stage.show();
    }
}
