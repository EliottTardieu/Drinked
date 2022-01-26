package fr.drinked.controllers;

import fr.drinked.App;
import fr.drinked.models.Beverage;
import fr.drinked.models.Order;
import fr.drinked.models.Resource;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.Stage;

public class ConfirmationController {

    @FXML
    public void cancel(ActionEvent actionEvent) {
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
        Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        App.getInstance().getLayoutController().getOrderController().getOrder().setValidity(Order.getVALIDATED());
        this.updateDAO();
        App.getInstance().getOrderDAO().save(App.getInstance().getLayoutController().getOrderController().getOrder());
        //TODO Clean reset (unselect everything / reselect)
        App.getInstance().getLayoutController().getOrderController().setOrder(new Order());
        stage.close();
    }

    private void updateDAO() {
        // To save the order
        Order order = App.getInstance().getLayoutController().getOrderController().getOrder();
        App.getInstance().getOrderDAO().save(order);

        // To save the resources
        Resource water = App.getInstance().getResourceDAO().findById(1);
        water.setQuantity_available((int) (water.getQuantity_available() - order.getBeverage_quantity()*(order.getBeverage().getWater_percentage()/100)));
        App.getInstance().getResourceDAO().save(water);
        Resource sugar = App.getInstance().getResourceDAO().findById(2);
        sugar.setQuantity_available(sugar.getQuantity_available() - order.getSugar_quantity());
        App.getInstance().getResourceDAO().save(sugar);
        Resource cup;
        if(order.getBeverage_quantity() == 35) {
            cup = App.getInstance().getResourceDAO().findById(3);
        } else {
            cup = App.getInstance().getResourceDAO().findById(4);
        }
        cup.setQuantity_available(cup.getQuantity_available() - 1);
        App.getInstance().getResourceDAO().save(cup);

        // To save the beverage
        Beverage beverage = order.getBeverage();
        beverage.setQuantity_available(beverage.getQuantity_available() - order.getBeverage_quantity());
        App.getInstance().getBeverageDAO().save(beverage);
    }
}
