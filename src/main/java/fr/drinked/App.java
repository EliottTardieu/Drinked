package fr.drinked;

import fr.drinked.controllers.LayoutController;
import fr.drinked.database.BeverageDAO;
import fr.drinked.database.OrderDAO;
import fr.drinked.database.ResourceDAO;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.util.Objects;

public class App extends Application {

    private static App instance;

    @FXML @Getter @Setter
    private LayoutController layoutController;

    @Getter
    private final OrderDAO orderDAO = new OrderDAO();
    @Getter
    private final ResourceDAO resourceDAO = new ResourceDAO();
    @Getter
    private final BeverageDAO beverageDAO = new BeverageDAO();

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(App.class.getClassLoader().getResource("views/layout.fxml")));
        Parent root = loader.load();
        this.layoutController = loader.getController();
        App.getInstance().setLayoutController(this.layoutController);
        Scene scene = new Scene(root);
        stage.setTitle("Drinked");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static App getInstance() {
        if(instance == null) instance = new App();
        return instance;
    }
}