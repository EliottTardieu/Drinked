package fr.drinked.controllers;

import fr.drinked.App;
import fr.drinked.models.Beverage;
import fr.drinked.models.Order;
import fr.drinked.models.Resource;
import fr.drinked.utils.Logger;
import javafx.application.Platform;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.order = new Order();

        ObservableList<Beverage> obsList = FXCollections.observableArrayList(App.getInstance().getBeverageDAO().getAll());
        listBeverages.setItems(obsList);

        listBeverages.setCellFactory(new Callback<>() {
            @Override
            public ListCell<Beverage> call(ListView<Beverage> lv) {
                return new ListCell<>() {
                    @Override
                    public void updateItem(Beverage item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null
                                || (item.getQuantity_available() < 35)
                                || ((item.getWater_percentage() / 100 * 35) > App.getInstance().getResourceDAO().findById(1).getQuantity_available())) {
                            Platform.runLater(() -> {
                                listBeverages.getItems().remove(item);
                                if(listBeverages.getItems().size() == 0)
                                    errorLabel.setText("No beverage available.");
                            });
                            setText(null);
                        } else {
                            setText(item.getName());
                        }
                    }
                };
            }
        });

        listBeverages.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue != null) {
                if(order.getBeverage_quantity() <= newValue.getQuantity_available()
                        && App.getInstance().getResourceDAO().findById(1).getQuantity_available() >= newValue.getWater_percentage()/100 * order.getBeverage_quantity()) {
                    App.getInstance().getLayoutController().getOrderController().updateOrder(newValue);
                    confirmable = true;
                    updateErrorMessage(0);
                } else {
                    confirmable = false;
                    App.getInstance().getLayoutController().getOrderController().updateOrder(null);
                    updateErrorMessage(1, order.getBeverage_quantity());
                }
            } else {
                Logger.warning("Cleared beverage selection.");
            }
        });

        choiceQuantity.getItems().add("35cl");
        choiceQuantity.getItems().add("75cl");

        choiceQuantity.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            if(this.chopQuantity(newValue) > this.order.getBeverage().getQuantity_available()
                    || App.getInstance().getResourceDAO().findById(1).getQuantity_available() < this.chopQuantity(newValue) * this.order.getBeverage().getWater_percentage()/100) {
                updateErrorMessage(1, 75);
                choiceQuantity.getSelectionModel().selectFirst();
                this.order.setBeverage_quantity(35);
            } else {
                this.order.setBeverage_quantity(this.chopQuantity(newValue));
                this.updateOrder(this.order.getBeverage());
            }
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
                updateErrorMessage(2);
            }
        });

        checkboxCup.selectedProperty().addListener((observable, oldValue, newValue) -> {
            int quantity = order == null ? 35 : order.getBeverage_quantity();
            Resource cup;
            if (quantity == 35) {
                cup = App.getInstance().getResourceDAO().findById(3);
            } else {
                cup = App.getInstance().getResourceDAO().findById(4);
            }
            if (cup.getQuantity_available() >= 1) {
                setCupSelection(newValue);
                updateOrder(order.getBeverage());
            } else {
                setCupSelection(true);
                updateErrorMessage(4, quantity);
            }
        });

        this.init();
    }

    private void init() {
        choiceQuantity.getSelectionModel().selectFirst();
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
            updateErrorMessage(3);
        }
    }

    private void updateErrorMessage(int type, int... quantity){
        switch (type) {
            case 1:
                if(quantity.length != 0)
                    errorLabel.setText("Not enough beverage for "+quantity[0]+"cl.");
                else
                    errorLabel.setText("Not enough beverage.");
                break;
            case 2:
                errorLabel.setText("Not enough Sugar.");
                break;
            case 3:
                errorLabel.setText("Can't confirm invalid order.");
                break;
            case 4:
                errorLabel.setText("No more cup for "+quantity[0]+"cl.");
                break;
            default:
                errorLabel.setText("");
                break;
        }
    }

    private void updateOrder(Beverage beverage) {
        float price = 0;
        if(beverage != null)
            this.order.setBeverage(beverage);
        this.order.setCup_selection(Order.getNO_CUP());
        if (this.order.getBeverage_quantity() == 35) {
            if(beverage != null)
                price += beverage.getPrice(35);
            if (checkboxCup.isSelected()) {
                price -= 0.1f;
            } else {
                if(App.getInstance().getResourceDAO().findById(3).getQuantity_available() >= 1)
                    this.order.setCup_selection(Order.getCUP_35());
            }
        } else if (this.order.getBeverage_quantity() == 75) {
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

    public int chopQuantity(String quantity) {
        return Integer.parseInt(StringUtils.chop(StringUtils.chop(quantity)));
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
}
