package fr.drinked.controllers;

import fr.drinked.App;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import lombok.Getter;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class LayoutController implements Initializable {

    @FXML @Getter private OrderController orderController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
