package fr.drinked;

import fr.drinked.tests.TestBeverage;
import fr.drinked.tests.TestOrder;
import fr.drinked.tests.TestResource;

public class Component {

    private static boolean testPassing = true;

    public static void run(String... args) {
        App.main(args);
    }

    public static void runTest(String... args) {
        testPassing = new TestResource().run();
        testPassing = new TestBeverage().run();
        //testPassing = new TestOrder().run();

        if (testPassing) {
            System.out.println("OK");
            run(args);
        }
    }
}
