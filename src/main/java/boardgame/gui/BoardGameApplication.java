package boardgame.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The main class of the application.
 */
public class BoardGameApplication extends Application {
    /**
     * The entry point of the application.
     *
     * @param stage the primary stage for this application, onto which the application scene can be set.
     * @throws IOException if an error occurs during the loading of the FXML file.
     */
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/startScene.fxml"));
        stage.setTitle("JavaFX Logic Board Game");
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.show();
    }
}
