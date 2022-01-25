package fr.drinked.tests.testModels;

import fr.drinked.models.Resource;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestResourceModel {

    Resource resource = new Resource("Chocolate", 30);

    @Test
    public void getDescriptionTest() {
        assertEquals(resource.getDescription(), "Chocolate");
    }

    @Test
    public void setDescriptionTest() {
        resource.setDescription("ChocolateTest");
        assertEquals(resource.getDescription(), "ChocolateTest");
    }

    @Test
    public void getQuantityAvailableTest() {
        assertEquals(resource.getQuantity_available(), 30);
    }

    @Test
    public void setQuantityAvailableTest() {
        resource.setQuantity_available(50);
        assertEquals(resource.getQuantity_available(), 50);
    }
}
