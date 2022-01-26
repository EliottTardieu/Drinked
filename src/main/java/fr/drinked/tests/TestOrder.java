package fr.drinked.tests;

import fr.drinked.App;
import fr.drinked.models.Beverage;
import fr.drinked.models.Order;
import fr.drinked.utils.Logger;
import org.mockito.Mockito;

import java.util.HashMap;

public class TestOrder {

    private Beverage beverage = new Beverage();
    private Beverage beverage2 = new Beverage();
    private Order awaitedModify = new Order(beverage2, 35, 5, "Cup 35cl", 0.8f, "Canceled");
    private boolean toReturn = true;
    private final int backupSizeOrderDAO = App.getInstance().getOrderDAO().getAll().size();;
    private final int backupSizeBeverageDAO = App.getInstance().getBeverageDAO().getAll().size();

    public boolean run() {
        // INSERT
        App.getInstance().getBeverageDAO().save(beverage);
        Order order = new Order(beverage, 75, 15, "Personnal Cup", 1.2f, "Validated");
        App.getInstance().getOrderDAO().save(order);
        Order orderTest1 = App.getInstance().getOrderDAO().findById(order.getId());
        if (order.compare(orderTest1) && (backupSizeOrderDAO + 1 == App.getInstance().getOrderDAO().getAll().size())) {
            Logger.fine("OrderDAO Insert fine");
        } else {
            Logger.severe("OrderDAO Insert failed");
            toReturn = false;
        }

        // MODIFY
        HashMap<String, Object> criteria = new HashMap<>();
        criteria.put("beverage_id", beverage.getId());
        Order orderTest2 = App.getInstance().getOrderDAO().find(criteria);
        App.getInstance().getBeverageDAO().save(beverage2);
        orderTest2.setBeverage(beverage2);
        orderTest2.setBeverage_quantity(35);
        orderTest2.setSugar_quantity(5);
        orderTest2.setCup_selection("Cup 35cl");
        orderTest2.setPrice(0.8f);
        orderTest2.setValidity("Canceled");
        App.getInstance().getOrderDAO().save(orderTest2);

        HashMap<String, Object> criteriaTest = new HashMap<>();
        criteriaTest.put("beverage_id", beverage2.getId());
        Order orderTest3 = App.getInstance().getOrderDAO().find(criteriaTest);
        if (orderTest3.compare(orderTest2) && orderTest3.compare(awaitedModify)) {
            Logger.fine("OrderDAO Modify fine");
        } else {
            Logger.severe("OrderDAO Modify failed");
            toReturn = false;
        }

        // REMOVE
        App.getInstance().getOrderDAO().delete(order);
        App.getInstance().getOrderDAO().delete(orderTest2);
        App.getInstance().getBeverageDAO().delete(beverage);
        App.getInstance().getBeverageDAO().delete(beverage2);
        if (!order.exist() && !orderTest2.exist() && !beverage.exist() && !beverage2.exist()
                && App.getInstance().getOrderDAO().getAll().size() == backupSizeOrderDAO
                && App.getInstance().getBeverageDAO().getAll().size() == backupSizeBeverageDAO) {
            Logger.fine("OrderDAO Delete fine");
        } else {
            Logger.severe("OrderDAO Delete failed");
            toReturn = false;
        }

        return toReturn;
    }
}
