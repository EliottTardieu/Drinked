package fr.drinked.database;

import fr.drinked.models.Resource;

import java.util.HashMap;

public class ResourceDAO extends DAO<Resource> {

    public ResourceDAO() {
        super(Resource.class);
    }

    /**
     * Retourne le nom de la table correspondant a l'objet T
     *
     * @return Le nom d'une table SQL
     */
    @Override
    protected String tableName() {
        return "Resources";
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
    protected HashMap<String, Object> getInsertMap(Resource object) {
        HashMap<String, Object> insertMap = new HashMap<>();
        insertMap.put("description", object.getDescription());
        insertMap.put("quantity_available", object.getQuantity_available());
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
    protected HashMap<String, Object> getUpdateMap(Resource object) {
        HashMap<String, Object> updateMap = new HashMap<>();
        updateMap.put("description", object.getDescription());
        updateMap.put("quantity_available", object.getQuantity_available());
        return updateMap;
    }
}
