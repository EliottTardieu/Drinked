package fr.drinked.controllers;

import fr.drinked.App;
import fr.drinked.models.Beverage;
import fr.drinked.models.Order;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class OrderController implements Initializable {

    @FXML @Getter
    private ListView<Beverage> listBeverages;
    @FXML @Getter
    private Label errorLabel;
    @FXML @Getter
    private Label lblPrice;
    @FXML @Getter
    private Label lblSugar;
    @FXML @Getter
    private Slider sliderSugar;
    @FXML @Getter
    private Label lblCup;
    @FXML @Getter
    private CheckBox checkboxCup;
    @FXML @Getter
    private ChoiceBox<String> choiceQuantity;

    @Getter @Setter
    private Order order;

    //TODO Check if quantity available before enable it
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.order = new Order();

        ObservableList<Beverage> obsList = FXCollections.observableArrayList(App.getInstance().getBeverageDAO().getAll());
        listBeverages.setItems(obsList);

        listBeverages.setCellFactory(new Callback<ListView<Beverage>, ListCell<Beverage>>() {
            @Override
            public ListCell<Beverage> call(ListView<Beverage> lv) {
                return new ListCell<Beverage>() {
                    @Override
                    public void updateItem(Beverage item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null) {
                            setText(null);
                        } else {
                            setText(item.getName());
                        }
                    }
                };
            }
        });

        listBeverages.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Beverage>() {
            @Override
            public void changed(ObservableValue<? extends Beverage> observable, Beverage oldValue, Beverage newValue) {
                App.getInstance().getLayoutController().getOrderController().getOrder().setBeverage(newValue);
                App.getInstance().getLayoutController().getOrderController().calculatePrice(newValue);
            }
        });

        choiceQuantity.getItems().add("35cl");
        choiceQuantity.getItems().add("75cl");
        choiceQuantity.setValue("35cl");
        this.order.setBeverage_quantity(35);

        choiceQuantity.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            this.order.setBeverage_quantity(this.chopQuantity());
            this.calculatePrice(this.order.getBeverage());
        });

        sliderSugar.setValue(0);
        lblSugar.setText("0");
        sliderSugar.valueProperty().addListener((obs, oldVal, newVal) -> {
            int newValue = (int) (Math.floor(newVal.intValue()/5) * 5);
            sliderSugar.setValue(newValue);
            this.order.setSugar_quantity(newValue);
            lblSugar.setText(newValue + " g");
        });

        checkboxCup.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                App.getInstance().getLayoutController().getOrderController().setCupSelection(newValue);
                App.getInstance().getLayoutController().getOrderController().calculatePrice(App.getInstance().getLayoutController().getOrderController().getOrder().getBeverage());
            }
        });
    }

    //Bind to button to check DB if it has enough resources. Then proceed to confirmation or print error
    @FXML
    private void preConfirmOrder(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/views/confirmation.fxml")));
        stage.setScene(new Scene(root));
        stage.setTitle("My modal window");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(((Node)event.getSource()).getScene().getWindow());
        stage.show();
    }

    public int chopQuantity() {
        String val = StringUtils.chop(choiceQuantity.getValue());
        val = StringUtils.chop(val);
        return Integer.parseInt(val);
    }

    public void calculatePrice(Beverage beverage) {
        float price = 0;
        this.order.setCup_selection(Order.getNO_CUP());
        if (this.chopQuantity() == 35) {
            price += beverage.getPrice(35);
            if (checkboxCup.isSelected()) {
                price -= 0.1f;
                this.order.setCup_selection(Order.getCUP_35());
            }
        } else if (this.chopQuantity() == 75) {
            price += beverage.getPrice(75);
            if (checkboxCup.isSelected()) {
                price -= 0.15f;
                this.order.setCup_selection(Order.getCUP_75());
            }
        }
        this.order.setPrice(price);
        this.lblPrice.setText(String.valueOf(price));
    }

    public void setCupSelection(boolean personalCup) {
        if (personalCup) {
            lblCup.setText("Cup not included.");
        } else {
            lblCup.setText("Cup included.");
        }
    }
}
