package fr.drinked.tests.testModels;

import fr.drinked.models.Beverage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestBeverageModel {

    Beverage beverage = new Beverage("Ristretto", "Short", 0.3f, 1.0f, 50);

    @Test
    public void getNameTest() {
        assertEquals(beverage.getName(), "Ristretto");
    }

    @Test
    public void setBeverageTest() {
        beverage.setName("RistrettoTest");
        assertEquals(beverage.getName(), "RistrettoTest");
    }

    @Test
    public void getDescriptionTest() {
        assertEquals(beverage.getDescription(), "Short");
    }

    @Test
    public void setDescriptionTest() {
        beverage.setDescription("ShortTest");
        assertEquals(beverage.getDescription(), "ShortTest");
    }

    @Test
    public void getPrice35Test() {
        assertEquals(beverage.getPrice_35(), 0.3f);
    }

    @Test
    public void setPrice35Test() {
        beverage.setPrice_35(0.5f);
        assertEquals(beverage.getPrice_35(), 0.5f);
    }

    @Test
    public void getPrice75Test() {
        assertEquals(beverage.getPrice_75(), 1.0f);
    }

    @Test
    public void setPrice75Test() {
        beverage.setPrice_75(1.2f);
        assertEquals(beverage.getPrice_75(), 1.2f);
    }

    @Test
    public void getQuantityAvailableTest() {
        assertEquals(beverage.getQuantity_available(), 50);
    }

    @Test
    public void setQuantityAvailableTest() {
        beverage.setQuantity_available(100);
        assertEquals(beverage.getQuantity_available(), 100);
    }
}
