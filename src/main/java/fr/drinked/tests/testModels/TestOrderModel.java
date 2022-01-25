package fr.drinked.tests.testModels;

import fr.drinked.models.Beverage;
import fr.drinked.models.Order;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestOrderModel {

    Beverage beverage = Mockito.mock(Beverage.class);
    Order order = new Order(beverage, 35, 5, "Cup 35cl", 1.5f, "Validated");

    @Test
    public void getBeverageTest() {
        assertEquals(order.getBeverage(), beverage);
    }

    @Test
    public void setBeverageTest() {
        Beverage beverageTest = Mockito.mock(Beverage.class);
        order.setBeverage(beverageTest);
        assertEquals(order.getBeverage(), beverageTest);
    }

    @Test
    public void getBeverageQuantityTest() {
        assertEquals(order.getBeverage_quantity(), 35);
    }

    @Test
    public void setBeverageQuantityTest() {
        order.setBeverage_quantity(75);
        assertEquals(order.getBeverage_quantity(), 75);
    }

    @Test
    public void getSugarQuantityTest() {
        assertEquals(order.getSugar_quantity(), 5);
    }

    @Test
    public void setSugarQuantityTest() {
        order.setSugar_quantity(10);
        assertEquals(order.getSugar_quantity(), 10);
    }

    @Test
    public void getCupSelectionTest() {
        assertEquals(order.getCup_selection(), "Cup 35cl");
    }

    @Test
    public void setCupSelectionTest() {
        order.setCup_selection("Cup 75cl");
        assertEquals(order.getCup_selection(), "Cup 75cl");
    }

    @Test
    public void getPriceTest() {
        assertEquals(order.getPrice(), 1.5f);
    }

    @Test
    public void setPriceTest() {
        order.setPrice(1.0f);
        assertEquals(order.getPrice(), 1.0f);
    }

    @Test
    public void getValidityTest() {
        assertEquals(order.getValidity(), "Validated");
    }

    @Test
    public void setValidityTest() {
        order.setValidity("Canceled");
        assertEquals(order.getValidity(), "Canceled");
    }
}
