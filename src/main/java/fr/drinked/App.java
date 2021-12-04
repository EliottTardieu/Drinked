package fr.drinked;

import fr.drinked.utils.ConfHandler;
import fr.drinked.utils.FXUtils;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    public static App instance;

    public final String FXML_LOCATION = "src/main/resources/fr/drinked/views/";

    @Override
    public void start(Stage stage) throws IOException {
        Scene scene = new Scene(FXUtils.loadFXML("hello-view"), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public static App getInstance(){
        if(instance == null) instance = new App();
        return instance;
    }
}