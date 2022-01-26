package fr.drinked.controllers;

import fr.drinked.App;
import fr.drinked.models.Beverage;
import fr.drinked.models.Order;
import fr.drinked.models.Resource;
import fr.drinked.utils.Logger;
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

    private boolean confirmable = false;

    @Override @SuppressWarnings("all")
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
                        if (item == null
                                || item.getQuantity_available() <= App.getInstance().getLayoutController().getOrderController().getOrder().getBeverage_quantity()
                                || App.getInstance().getResourceDAO().findById(1).getQuantity_available() <= item.getWater_percentage()/100 * App.getInstance().getLayoutController().getOrderController().getOrder().getBeverage_quantity()) {
                            listBeverages.getItems().remove(item);
                        } else {
                            setText(item.getName());
                        }
                    }
                };
            }
        });

        if(listBeverages.getItems().size() == 0)
            errorLabel.setText("No beverage available.");

        listBeverages.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Beverage>() {
            @Override @SuppressWarnings("all")
            public void changed(ObservableValue<? extends Beverage> observable, Beverage oldValue, Beverage newValue) {
                if(newValue != null) {
                    if (newValue.getQuantity_available() >= App.getInstance().getLayoutController().getOrderController().getOrder().getBeverage_quantity()
                            && App.getInstance().getResourceDAO().findById(1).getQuantity_available() >= newValue.getWater_percentage() / 100 * App.getInstance().getLayoutController().getOrderController().getOrder().getBeverage_quantity()) {
                        App.getInstance().getLayoutController().getOrderController().updateOrder(newValue);
                        confirmable = true;
                    } else {
                        confirmable = false;
                        App.getInstance().getLayoutController().getOrderController().updateOrder(null);
                        errorLabel.setText("Not enough Water or Beverage.");
                    }
                } else {
                    Logger.warning("Cleared beverage selection.");
                }
            }
        });

        choiceQuantity.getItems().add("35cl");
        choiceQuantity.getItems().add("75cl");

        choiceQuantity.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            this.order.setBeverage_quantity(this.chopQuantity());
            this.updateOrder(this.order.getBeverage());
        });

        sliderSugar.valueProperty().addListener((obs, oldVal, newVal) -> {
            int newValue = (int) (Math.floor(newVal.intValue()/5) * 5);
            if(App.getInstance().getResourceDAO().findById(2).getQuantity_available() >= newValue) {
                sliderSugar.setValue(newValue);
                this.order.setSugar_quantity(newValue);
                lblSugar.setText(newValue + "g");
            } else {
                sliderSugar.setValue(oldVal.intValue());
                lblSugar.setText(oldVal.intValue() + "g");
                errorLabel.setText("Not enough Sugar.");
            }
        });

        checkboxCup.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override @SuppressWarnings("all")
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                int quantity = order == null ? 35 : order.getBeverage_quantity();
                Resource cup;
                if (quantity == 35) {
                    cup = App.getInstance().getResourceDAO().findById(3);
                } else {
                    cup = App.getInstance().getResourceDAO().findById(4);
                }
                if(cup.getQuantity_available() >= 1) {
                    App.getInstance().getLayoutController().getOrderController().setCupSelection(newValue);
                    App.getInstance().getLayoutController().getOrderController().updateOrder(App.getInstance().getLayoutController().getOrderController().getOrder().getBeverage());
                } else {
                    setCupSelection(true);
                    errorLabel.setText("No more cup for "+quantity+"cl.");
                }
            }
        });

        this.init();
    }

    //Bind to button to check DB if it has enough resources. Then proceed to confirmation or print error
    @FXML
    private void preConfirmOrder(ActionEvent event) throws IOException {
        if(this.confirmable) {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/views/confirmation.fxml")));
            stage.setScene(new Scene(root));
            stage.setTitle("Confirmation window");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(((Node) event.getSource()).getScene().getWindow());
            stage.show();
        } else {
            errorLabel.setText("Can't confirm invalid order.");
        }
    }

    public int chopQuantity() {
        String val = StringUtils.chop(choiceQuantity.getValue());
        val = StringUtils.chop(val);
        return Integer.parseInt(val);
    }

    public void updateOrder(Beverage beverage) {
        float price = 0;
        if(beverage != null)
            this.order.setBeverage(beverage);
        this.order.setCup_selection(Order.getNO_CUP());
        if (this.chopQuantity() == 35) {
            if(beverage != null)
                price += beverage.getPrice(35);
            if (checkboxCup.isSelected()) {
                price -= 0.1f;
            } else {
                if(App.getInstance().getResourceDAO().findById(3).getQuantity_available() >= 1)
                    this.order.setCup_selection(Order.getCUP_35());
            }
        } else if (this.chopQuantity() == 75) {
            if(beverage != null)
                price += beverage.getPrice(75);
            if (checkboxCup.isSelected()) {
                price -= 0.15f;
            } else {
                if(App.getInstance().getResourceDAO().findById(4).getQuantity_available() >= 1)
                    this.order.setCup_selection(Order.getCUP_75());
            }
        }
        this.order.setPrice(Math.max(price, 0));
        this.lblPrice.setText(String.valueOf(this.order.getPrice()));
    }

    public void setCupSelection(boolean personalCup) {
        if (personalCup) {
            checkboxCup.setSelected(true);
            lblCup.setText("Cup not included.");
        } else {
            checkboxCup.setSelected(false);
            lblCup.setText("Cup included.");
        }
    }

    private void init() {
        choiceQuantity.setValue("35cl");
        this.order.setBeverage_quantity(35);
        this.setCupSelection(true);
        sliderSugar.setValue(0);
        lblSugar.setText("0g");
    }

    public void reset() {
        confirmable = false;
        this.order = new Order();
        this.init();
        listBeverages.getSelectionModel().clearSelection();
        this.updateOrder(null);
    }
}
