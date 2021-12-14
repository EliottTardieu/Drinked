package fr.drinked;

public class Main {

    public static void main(String[] args) {
        //App.launch();
        System.out.println(App.getInstance().getOrderDAO().getAll().get(0).getBeverage().getName());

        System.exit(0);
    }
}
