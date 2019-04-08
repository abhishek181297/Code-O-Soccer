package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Code-o-Soccer");
        Scene scene = new Scene(root, 784, 422);
        scene.getStylesheets().add(
                getClass().getResource("game.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
