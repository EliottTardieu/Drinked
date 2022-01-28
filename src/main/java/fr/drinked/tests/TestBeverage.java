package fr.drinked.tests;

import fr.drinked.App;
import fr.drinked.models.Beverage;
import fr.drinked.utils.Logger;
import java.util.HashMap;

public class TestBeverage {

    private Beverage awaitedModify = new Beverage("EspressoTest", "TestModify", 0.70f, 1.0f, 100, 80);
    private int backupSize;

    public boolean run() {
        // INSERT
        boolean insert = true;
        Beverage espressoTest = new Beverage("Espresso", "Test",0.70f,1.0f,100,80);
        backupSize = App.getInstance().getBeverageDAO().getAll().size();
        App.getInstance().getBeverageDAO().save(espressoTest);
        HashMap<String, Object> criterias = new HashMap<>();
        criterias.put("Name", "Espresso");
        criterias.put("Description", "Test");
        Beverage espressoBeverage = App.getInstance().getBeverageDAO().find(criterias);
        if(espressoBeverage.compare(espressoTest) && (App.getInstance().getBeverageDAO().getAll().size() == backupSize +1)) {
            Logger.fine("BeverageDAO insert fine");
        } else {
            Logger.severe("BeverageDAO insert failed");
            insert = false;
        }

        // MODIFY
        boolean modify = true;
        espressoBeverage.setName("EspressoTest");
        espressoBeverage.setDescription("TestModify");
        espressoBeverage.setPrice_35(0.7f);
        espressoBeverage.setPrice_75(1.0f);
        espressoBeverage.setQuantity_available(100);
        App.getInstance().getBeverageDAO().save(espressoBeverage);
        HashMap<String, Object> criteriasTest = new HashMap<>();
        criteriasTest.put("Name", "EspressoTest");
        criteriasTest.put("Description", "TestModify");
        Beverage newEspressoBeverage = App.getInstance().getBeverageDAO().find(criteriasTest);
        if (newEspressoBeverage.compare(espressoBeverage) && newEspressoBeverage.compare(awaitedModify)) {
            Logger.fine("BeverageDAO modify fine");
        } else {
            Logger.severe("BeverageDAO modify failed");
            modify = false;
        }

        // DELETE
        boolean delete = true;
        App.getInstance().getBeverageDAO().delete(newEspressoBeverage);
        if(App.getInstance().getBeverageDAO().getAll().size() != backupSize) {
            delete = false;
            Logger.severe("BeverageDAO delete failed");
        } else {
            Logger.fine("BeverageDAO delete fine");
        }

        if(insert && modify && delete) {
            Logger.fine("BeverageDAO test successful");
            return true;
        } else {
            Logger.severe("BeverageDAO test failed");
            return false;
        }
    }
}
