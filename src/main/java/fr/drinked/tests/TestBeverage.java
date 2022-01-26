package fr.drinked.tests;

import fr.drinked.App;
import fr.drinked.models.Beverage;
import fr.drinked.utils.Logger;
import java.util.HashMap;

public class TestBeverage {

    private Beverage awaitedModify = new Beverage("EspressoTest", "Long", 0.70f, 1.0f, 100, 80);
    private String backupName;
    private String backupDescription;
    private float backupPrice_35;
    private float backupPrice_75;
    private int backupQuantity;

    public boolean run() {
        HashMap<String, Object> criterias = new HashMap<>();
        criterias.put("Name", "Espresso");
        criterias.put("Description", "Short");
        Beverage espressoBeverage = App.getInstance().getBeverageDAO().find(criterias);
        backupName = espressoBeverage.getName();
        backupDescription = espressoBeverage.getDescription();
        backupPrice_35 = espressoBeverage.getPrice_35();
        backupPrice_75 = espressoBeverage.getPrice_75();
        backupQuantity = espressoBeverage.getQuantity_available();
        espressoBeverage.setName("EspressoTest");
        espressoBeverage.setDescription("Long");
        espressoBeverage.setPrice_35(0.7f);
        espressoBeverage.setPrice_75(1.0f);
        espressoBeverage.setQuantity_available(100);
        App.getInstance().getBeverageDAO().save(espressoBeverage);

        HashMap<String, Object> criteriasTest = new HashMap<>();
        criteriasTest.put("Name", "EspressoTest");
        criteriasTest.put("Description", "Long");
        Beverage newEspressoBeverage = App.getInstance().getBeverageDAO().find(criteriasTest);
        if (newEspressoBeverage.compare(espressoBeverage) && newEspressoBeverage.compare(awaitedModify)) {
            Logger.fine("BeverageDAO fine");
            this.reset(newEspressoBeverage);
            return true;
        } else {
            Logger.severe("BeverageDAO returned a wrong value");
            this.reset(newEspressoBeverage);
            return false;
        }
    }

    private void reset(Beverage beverage) {
        beverage.setName(backupName);
        beverage.setDescription(backupDescription);
        beverage.setPrice_35(backupPrice_35);
        beverage.setPrice_75(backupPrice_75);
        beverage.setQuantity_available(backupQuantity);
        App.getInstance().getBeverageDAO().save(beverage);
    }
}
