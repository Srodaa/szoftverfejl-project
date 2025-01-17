package boardgame.gui;

import boardgame.util.GameResult;
import boardgame.util.JsonGameResultManager;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Path;

/**
 * The controller class of the result scene.
 */
public class ResultController {
    /**
     * The button to switch back to the start scene.
     */
    @FXML
    public Button backToStartbtn;
    /**
     * The column for the player name.
     */
    @FXML
    public TableColumn<GameResult, String> playerName;
    /**
     * The column for the number of steps.
     */
    @FXML
    public TableColumn<GameResult, Integer > steps;

    @FXML
    private TableView<GameResult> tableView;

    private static final Logger logger = LogManager.getLogger(ResultController.class);

    @FXML
    private void initialize() throws IOException {
        playerName.setCellValueFactory(cell -> new ReadOnlyStringWrapper(cell.getValue().playerName()));
        steps.setCellValueFactory(new PropertyValueFactory<>("steps"));
        ObservableList<GameResult> observableList = FXCollections.observableArrayList();
        observableList.addAll(new JsonGameResultManager(Path.of("results.json")).getBest(10));
        tableView.setItems(observableList);
    }

    /**
     * Switches back to the start scene.
     * @param event the event that triggered the method
     * @throws IOException if the fxml file cannot be loaded
     */
    @FXML
    public void switchToStart(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/startScene.fxml"));
        logger.info("Switched to the start scene.");
        stage.setScene(new Scene(root));
        stage.show();
    }
}
