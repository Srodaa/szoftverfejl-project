package boardgame.gui;

import boardgame.model.BoardGameModel;
import boardgame.model.Position;
import boardgame.model.Square;
import boardgame.util.GameResult;
import boardgame.util.JsonGameResultManager;
import javafx.application.Platform;
import javafx.beans.binding.ObjectBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import puzzle.util.TwoPhaseMoveSelector;
import util.javafx.*;

import java.io.IOException;
import java.nio.file.Path;

/**
 * The controller class of the board game.
 */
public class BoardGameController {
    /**
     * The grid pane of the board.
     */
    @FXML
    public GridPane board;
    /**
     * The text field for the number of moves.
     */
    @FXML
    public TextField numberOfMoves;
    /**
     * The button to switch back to the main menu.
     */
    @FXML
    public Button backToMainMenubtn;

    private static final Logger logger = LogManager.getLogger(BoardGameController.class);

    private BoardGameModel model = new BoardGameModel();

    private ImageStorage<Square> imageStorage = new EnumImageStorage<>(Square.class);

    private TwoPhaseMoveSelector<Position> twoPhaseSelector = new TwoPhaseMoveSelector<>(model);
    private String name;

    private JsonGameResultManager jsonGameResultManager = new JsonGameResultManager(Path.of("results.json"));

    @FXML
    private void initialize() {
        for (var i = 0; i < board.getRowCount(); i++) {
            for (var j = 0; j < board.getColumnCount(); j++) {
                var square = createSquare(i, j);
                board.add(square, j, i);
            }
        }
        numberOfMoves.textProperty().bind(model.numberOfMovesProperty().asString());
        Platform.runLater(()->
        {
            Stage stage = (Stage) numberOfMoves.getScene().getWindow();
            name = (String) stage.getUserData();
        });
    }

    private StackPane createSquare(int i, int j) {
        var square = new StackPane();
        square.getStyleClass().add("square");
        var imageView = new ImageView();
        imageView.setFitWidth(100);
        imageView.setFitHeight(100);
        imageView.imageProperty().bind(
                new ObjectBinding<Image>() {
                    {
                        super.bind(model.squareProperty(i, j));
                    }
                    @Override
                    protected Image computeValue() {
                        return imageStorage.get(model.squareProperty(i, j).get()).orElse(null);
                    }
                }
        );
        square.getChildren().add(imageView);
        square.setOnMouseClicked(this::handleMouseClick);
        return square;
    }

    @FXML
    private void handleMouseClick(MouseEvent event) {
        var square = (StackPane) event.getSource();
        var row = GridPane.getRowIndex(square);
        var col = GridPane.getColumnIndex(square);
        logger.info("Click on row: " + row + ", col: " + col);
        logger.info(twoPhaseSelector.getPhase().toString());
        twoPhaseSelector.select(new Position(row, col));
        if (twoPhaseSelector.isInvalidSelection()){
            twoPhaseSelector.reset();
            return;
        }

        if (twoPhaseSelector.isReadyToMove()){
            twoPhaseSelector.makeMove();
            twoPhaseSelector.reset();
            if (model.isSolved()){
                gameSolvedAlert();
                var gameResult = new GameResult(name, model.numberOfMovesProperty().get());
                try {
                    jsonGameResultManager.add(gameResult);
                } catch (IOException e) {
                    logger.error("Error while saving the game result: {}", e.getMessage());
                }
            }
        }
    }

    private void gameSolvedAlert(){
        var alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Congratulations!");
        alert.setContentText("The game is solved!");
        alert.showAndWait();
    }

    /**
     * Switches back to the start scene.
     * @param event the action event
     * @throws IOException if an error occurs during the switch
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
