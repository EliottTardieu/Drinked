package fr.drinked.database;

import fr.drinked.models.Beverage;

import java.util.HashMap;

public class BeverageDAO extends DAO<Beverage> {

    public BeverageDAO() {
        super(Beverage.class);
    }

    /**
     * Retourne le nom de la table correspondant a l'objet T
     *
     * @return Le nom d'une table SQL
     */
    @Override
    protected String tableName() {
        return "Beverages";
    }

    /**
     * Retourne une map associant une colonne SQL a sa valeur dans le cas d'un insert
     *
     * @param object Objet a mettre a jour
     * @return Une Map associant une colonne a sa valeur
     * @see this.insert
     * @see this.save
     */
    @Override
    protected HashMap<String, Object> getInsertMap(Beverage object) {
        HashMap<String, Object> insertMap = new HashMap<>();
        insertMap.put("name", object.getName());
        insertMap.put("description", object.getDescription());
        insertMap.put("price_35", object.getPrice_35());
        insertMap.put("price_75", object.getPrice_75());
        insertMap.put("quantity_available", object.getQuantity_available());
        insertMap.put("water_percentage", object.getWater_percentage());
        return insertMap;
    }

    /**
     * Retourne une map associant une colonne SQL a sa valeur dans le cas d'un update
     *
     * @param object Objet a mettre a jour
     * @return Une Map associant une colonne a sa valeur
     * @see this.update
     * @see this.save
     */
    @Override
    protected HashMap<String, Object> getUpdateMap(Beverage object) {
        HashMap<String, Object> updateMap = new HashMap<>();
        updateMap.put("name", object.getName());
        updateMap.put("description", object.getDescription());
        updateMap.put("price_35", object.getPrice_35());
        updateMap.put("price_75", object.getPrice_75());
        updateMap.put("quantity_available", object.getQuantity_available());
        updateMap.put("water_percentage", object.getWater_percentage());
        return updateMap;
    }
}
