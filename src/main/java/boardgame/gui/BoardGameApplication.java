package boardgame.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class BoardGameApplication extends Application {
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/startScene.fxml"));
        stage.setTitle("JavaFX Logic Board Game");
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.show();
    }
}
