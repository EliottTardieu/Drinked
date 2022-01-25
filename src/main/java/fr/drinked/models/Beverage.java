package fr.drinked.models;

import fr.drinked.utils.Logger;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

public class Beverage extends Model {

    @Getter @Setter
    private String name;
    @Getter @Setter
    private String description;
    @Getter @Setter
    private float price_35;
    @Getter @Setter
    private float price_75;
    @Getter @Setter
    private int quantity_available;

    public Beverage() { super(); }

    public Beverage(HashMap<String, Object> data){
        super(data);
    }

    public Beverage(String name, String description, float price_35, float price_75, int quantity_available) {
        this.name = name;
        this.description = description;
        this.price_35 = price_35;
        this.price_75 = price_75;
        this.quantity_available = quantity_available;
    }

    /**
     * Hydrate un objet en fonction d'un résult set SQL
     *
     * @param data Map associant une colonne a sa valeur
     */
    @Override
    protected void hydrate(HashMap<String, Object> data) {
        this.setId(integer(data.get("id")));
        this.setDescription(string(data.get("description")));
        this.setName(string(data.get("name")));
        this.setPrice_35(floatNumber(data.get("price_35")));
        this.setPrice_75(floatNumber(data.get("price_75")));
        this.setQuantity_available(integer(data.get("quantity_available")));
    }

    public float getPrice(int quantity) {
        if (quantity == 35) {
            return price_35;
        } else if (quantity == 75) {
            return price_75;
        } else {
            Logger.severe("Wrong quantity.");
            return 0;
        }
    }
}
