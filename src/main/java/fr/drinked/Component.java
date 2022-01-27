package fr.drinked;

import fr.drinked.tests.TestBeverage;
import fr.drinked.tests.TestOrder;
import fr.drinked.tests.TestResource;
import fr.drinked.utils.Logger;

public class Component {

    public static void run(String... args) {
        App.main(args);
    }

    public static void runTest(String... args) {
        if(new TestResource().run()) {
            if(new TestBeverage().run()) {
                if(new TestOrder().run()) {
                    Logger.fine("Tests passing, now starting the App.");
                    run(args);
                } else {
                    Logger.severe("Test on Orders not passing.");
                }
            } else {
                Logger.severe("Test on Beverages not passing.");
            }
        } else {
            Logger.severe("Test on Resources not passing.");
        }
    }
}
