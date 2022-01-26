package fr.drinked.models;

import fr.drinked.App;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

public class Order extends Model<Order> {

    @Getter @Setter
    private int beverage_quantity;
    @Getter @Setter
    private Beverage beverage;
    @Getter @Setter
    private int sugar_quantity;
    @Getter @Setter
    private String cup_selection;
    @Getter @Setter
    private float price;
    @Getter @Setter
    private String validity;

    @Getter
    private static final String CUP_75 = "Cup 75cl";
    @Getter
    private static final String CUP_35 = "Cup 35cl";
    @Getter
    private static final String NO_CUP = "Personal Cup";

    @Getter
    private static final String CANCELED = "Canceled";
    @Getter
    private static final String VALIDATED = "Validated";

    public Order() { super(); }

    public Order(HashMap<String, Object> data){
        super(data);
    }

    public Order(Beverage beverage, int beverage_quantity, int sugar_quantity, String cup_selection, float price, String validity) {
        this.beverage = beverage;
        this.beverage_quantity = beverage_quantity;
        this.sugar_quantity = sugar_quantity;
        this.cup_selection = cup_selection;
        this.price = price;
        this.validity = validity;
    }

    /**
     * Hydrate un objet en fonction d'un résult set SQL
     *
     * @param data Map associant une colonne a sa valeur
     */
    @Override
    protected void hydrate(HashMap<String, Object> data) {
        this.setId(integer(data.get("id")));
        this.setBeverage_quantity(integer(data.get("beverage_quantity")));
        this.setBeverage(App.getInstance().getBeverageDAO().findById(integer(data.get("beverage_id"))));
        this.setSugar_quantity(integer(data.get("sugar_quantity")));
        this.setCup_selection(string(data.get("cup_selection")));
        this.setPrice(floatNumber(data.get("price")));
        this.setValidity(string(data.get("validity")));
    }

    @Override
    protected boolean compare(Order model) {
        if(this.getBeverage() != model.getBeverage()) return false;
        if(this.getBeverage_quantity() != model.getBeverage_quantity()) return false;
        if(this.getSugar_quantity() != model.getSugar_quantity()) return false;
        if(this.getCup_selection() != model.getCup_selection()) return false;
        if(this.getPrice() != model.getPrice()) return false;
        if(this.getValidity() != model.getValidity()) return false;
        return true;
    }
}
