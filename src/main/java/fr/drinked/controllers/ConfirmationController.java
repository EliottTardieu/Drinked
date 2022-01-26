package fr.drinked.controllers;

import fr.drinked.App;
import fr.drinked.models.Beverage;
import fr.drinked.models.Order;
import fr.drinked.models.Resource;
import fr.drinked.utils.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.Stage;

public class ConfirmationController {

    @FXML
    public void cancel(ActionEvent actionEvent) {
        Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        App.getInstance().getLayoutController().getOrderController().getOrder().setValidity(Order.getCANCELED());
        this.updateDAO(false);
        App.getInstance().getLayoutController().getOrderController().reset();
        stage.close();
    }

    public void confirm(ActionEvent actionEvent) {
        Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        App.getInstance().getLayoutController().getOrderController().getOrder().setValidity(Order.getVALIDATED());
        this.updateDAO(true);
        App.getInstance().getLayoutController().getOrderController().reset();
        stage.close();
    }

    private void updateDAO(boolean status) {
        // To save the order
        Order order = App.getInstance().getLayoutController().getOrderController().getOrder();
        App.getInstance().getOrderDAO().save(order);

        if(status) {
            // To save the resources
            Resource water = App.getInstance().getResourceDAO().findById(1);
            water.setQuantity_available((int) (water.getQuantity_available() - order.getBeverage_quantity() * (order.getBeverage().getWater_percentage() / 100)));
            App.getInstance().getResourceDAO().save(water);
            Resource sugar = App.getInstance().getResourceDAO().findById(2);
            sugar.setQuantity_available(sugar.getQuantity_available() - order.getSugar_quantity());
            App.getInstance().getResourceDAO().save(sugar);
            if(!order.getCup_selection().equals(Order.getNO_CUP())) {
                Resource cup;
                if (order.getBeverage_quantity() == 35) {
                    cup = App.getInstance().getResourceDAO().findById(3);
                } else {
                    cup = App.getInstance().getResourceDAO().findById(4);
                }
                cup.setQuantity_available(cup.getQuantity_available() - 1);
                App.getInstance().getResourceDAO().save(cup);
            }
            // To save the beverage
            Beverage beverage = order.getBeverage();
            beverage.setQuantity_available(beverage.getQuantity_available() - order.getBeverage_quantity());
            App.getInstance().getBeverageDAO().save(beverage);
            Logger.fine("Updated DAO after confirmation.");
        } else {
            Logger.fine("Updated DAO after cancelling.");
        }
    }
}
