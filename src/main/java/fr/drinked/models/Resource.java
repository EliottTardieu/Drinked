package fr.drinked.models;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

public class Resource extends Model<Resource> {

    @Getter @Setter
    private String description;
    @Getter @Setter
    private int quantity_available;

    public Resource() { super(); }

    public Resource(HashMap<String, Object> data){
        super(data);
    }

    public Resource(String description, int quantity_available) {
        this.description = description;
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
        this.setQuantity_available(integer(data.get("quantity_available")));
    }

    @Override
    protected boolean compare(Resource model) {
        if(this.getQuantity_available() != model.getQuantity_available()) return false;
        if(!this.getDescription().equals(model.getDescription())) return false;
        return true;
    }
}
