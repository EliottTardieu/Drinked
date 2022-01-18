package fr.drinked.models;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

//TODO Do controllers for each Model
public class Resource extends Model {

    @Getter @Setter
    private String description;
    @Getter @Setter
    private int quantity_available;

    public Resource() { super(); }

    public Resource(HashMap<String, Object> data){
        super(data);
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
}
