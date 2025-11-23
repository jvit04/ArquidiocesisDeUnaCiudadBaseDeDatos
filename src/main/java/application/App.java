package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import utilities.Paths;

import java.io.IOException;
import java.util.Objects;

public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        AnchorPane loader = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(Paths.Arquidiocesis)));
        Scene scene = new Scene(loader);
        stage.setScene(scene);
        stage.show();
    }
}
