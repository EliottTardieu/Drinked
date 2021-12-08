package fr.drinked.database;

import fr.drinked.models.Order;

import java.util.HashMap;

public class OrderDAO extends DAO<Order> {

    public OrderDAO() {
        super(Order.class);
    }

    /**
     * Retourne le nom de la table correspondant a l'objet T
     *
     * @return Le nom d'une table SQL
     */
    @Override
    protected String tableName() {
        return "Orders";
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
    protected HashMap<String, Object> getInsertMap(Order object) {
        HashMap<String, Object> insertMap = new HashMap<>();
        insertMap.put("beverage_quantity", object.getBeverage_quantity());
        insertMap.put("sugar_quantity", object.getSugar_quantity());
        insertMap.put("cup_selection", object.getCup_selection());
        insertMap.put("price", object.getPrice());
        insertMap.put("validity", object.getValidity());
        insertMap.put("beverage_id", object.getBeverage().getId());
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
    protected HashMap<String, Object> getUpdateMap(Order object) {
        HashMap<String, Object> updateMap = new HashMap<>();
        updateMap.put("beverage_quantity", object.getBeverage_quantity());
        updateMap.put("sugar_quantity", object.getSugar_quantity());
        updateMap.put("cup_selection", object.getCup_selection());
        updateMap.put("price", object.getPrice());
        updateMap.put("validity", object.getValidity());
        updateMap.put("beverage_id", object.getBeverage().getId());
        return updateMap;
    }
}
