package boardgame;

import boardgame.model.BoardGameModel;
import boardgame.model.Position;
import boardgame.model.Square;
import boardgame.util.BoardGameMoveSelector;
import boardgame.util.EnumImageStorage;
import boardgame.util.ImageStorage;
import javafx.application.Platform;
import javafx.beans.binding.ObjectBinding;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BoardGameController {
    @FXML
    public GridPane board;

    @FXML
    public TextField numberOfMoves;

    private static final Logger logger = LogManager.getLogger(BoardGameController.class);

    private BoardGameModel model = new BoardGameModel();

    private ImageStorage<Square> imageStorage = new EnumImageStorage<>(Square.class);

    private BoardGameMoveSelector selector = new BoardGameMoveSelector(model);


    @FXML
    private void initialize() {
        for (var i = 0; i < board.getRowCount(); i++) {
            for (var j = 0; j < board.getColumnCount(); j++) {
                var square = createSquare(i, j);
                board.add(square, j, i);
            }
        }
        numberOfMoves.textProperty().bind(model.numberOfMovesProperty().asString());
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
                        return imageStorage.get(model.squareProperty(i, j).get());
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
        selector.select(new Position(row, col));
        if (selector.isReadyToMove()){
            selector.makeMove();
            if (model.isSolved()){
                gameSolvedAlert();
            }
        }
    }

    private void gameSolvedAlert(){
        var alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Congratulations!");
        alert.setContentText("The game is solved!");
        alert.showAndWait();
        Platform.exit();
    }

}
