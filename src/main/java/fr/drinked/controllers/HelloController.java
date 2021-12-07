package fr.drinked.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import lombok.Getter;

public class HelloController {

    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Hello there!");
    }

}