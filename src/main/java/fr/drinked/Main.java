package fr.drinked;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Voulez-vous exécuter les tests ?");
        if(scanner.nextLine().equalsIgnoreCase("Oui")) {
            Component.runTest(args);
        } else {
            Component.run(args);
        }

        System.exit(0);
    }
}
