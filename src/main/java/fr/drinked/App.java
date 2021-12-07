package fr.drinked;

import fr.drinked.controllers.HelloController;
import fr.drinked.database.BeverageDAO;
import fr.drinked.database.OrderDAO;
import fr.drinked.database.ResourceDAO;
import fr.drinked.models.Beverage;
import fr.drinked.models.Order;
import fr.drinked.models.Resource;
import fr.drinked.utils.ConfHandler;
import fr.drinked.utils.FXUtils;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.Getter;

import java.io.IOException;
import java.util.ArrayList;

public class App extends Application {

    public static App instance;

    @Getter
    private static final OrderDAO orderDAO = new OrderDAO();
    @Getter
    private static final ResourceDAO resourceDAO = new ResourceDAO();
    @Getter
    private static final BeverageDAO beverageDAO = new BeverageDAO();

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