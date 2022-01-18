package fr.drinked.controllers;

import fr.drinked.App;
import fr.drinked.models.Order;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.Stage;

public class ConfirmationController {

    @FXML
    public void cancel(ActionEvent actionEvent) {
        System.out.println("cancel");
        Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        App.getInstance().getLayoutController().getOrderController().getOrder().setValidity(Order.getCANCELED());
        App.getInstance().getOrderDAO().save(App.getInstance().getLayoutController().getOrderController().getOrder());
        //TODO Clean reset (unselect everything / reselect)
        App.getInstance().getLayoutController().getOrderController().setOrder(new Order());
        stage.close();
    }

    //TODO Bind to button, should update DB with updated values for resources & beverages
    //ResourceDAO.save(resource) pour chaque ressource / BeverageDAO.save(beverage) pour la boisson / OrderDAO.save(order) pour la commande
    public void confirm(ActionEvent actionEvent) {
        System.out.println("confirm");
        Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        App.getInstance().getLayoutController().getOrderController().getOrder().setValidity(Order.getVALIDATED());
        App.getInstance().getOrderDAO().save(App.getInstance().getLayoutController().getOrderController().getOrder());
        //TODO Clean reset (unselect everything / reselect)
        App.getInstance().getLayoutController().getOrderController().setOrder(new Order());
        stage.close();
    }
}
