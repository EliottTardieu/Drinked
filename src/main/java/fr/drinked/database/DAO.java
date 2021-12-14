package fr.drinked.database;

import fr.drinked.models.Model;
import fr.drinked.utils.Logger;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.*;

public abstract class DAO<T extends Model> {

    private static String HOST = "jdbc:mysql://localhost:3306/drinked";
    private static String USER;
    private static String PASSWORD;

    protected Connection connection;
    private Class<T> type;

    public DAO(Class<T> type){
        try {
            this.type = type;
            DAO.loadProperties();
            this.connection = DriverManager.getConnection(HOST, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Properties loadProperties() {
        try (InputStream inputStream = DAO.class.getClassLoader().getResourceAsStream("config/config.properties")) {
            Properties properties = new Properties();
            properties.load(inputStream);
            USER = properties.getProperty("db.user");
            PASSWORD = properties.getProperty("db.password");
            return properties;
        } catch (FileNotFoundException e) {
            Logger.error("Unable to find config.properties", e);
        } catch (IOException e) {
            Logger.error("Error loading config.properties", e);
        }
        return null;
    }

    /**
     * Retourne le nom de la table correspondant a l'objet T
     * @return Le nom d'une table SQL
     */
    protected abstract String tableName();

    /**
     * Retourne une map associant une colonne SQL a sa valeur dans le cas d'un insert
     * @param object Objet a mettre a jour
     * @return Une Map associant une colonne a sa valeur
     * @see this.insert
     * @see this.save
     */
    protected abstract HashMap<String, Object> getInsertMap(T object);

    /**
     * Retourne une map associant une colonne SQL a sa valeur dans le cas d'un update
     * @param object Objet a mettre a jour
     * @return Une Map associant une colonne a sa valeur
     * @see this.update
     * @see this.save
     */
    protected abstract HashMap<String, Object> getUpdateMap(T object);

    /**
     * Met a jour un objet dans une base de donnée
     * @param object Objet a mettre a jour
     */
    private void update(T object){
        StringBuilder query = new StringBuilder("UPDATE " + tableName() + " SET ");
        HashMap<String, Object> updateMap = getUpdateMap(object);
        for(String column : updateMap.keySet()){
            query.append(column).append("=").append(formatVariable(updateMap.get(column))).append(",");
        }
        query = new StringBuilder(query.substring(0, query.length() - 1)); //On enlève la dernière virgule
        query.append(" WHERE id=").append(formatVariable(object.getId()));
        try {
            rawQuery(query.toString());
        } catch (SQLException e) {
            Logger.warning("Unable to update "+object.getClass().getName()+" object " + query);
        }
    }

    /**
     * Insère un objet dans une base de donnée
     * @param object Objet a sauvegarder
     */
    private void insert(T object){
        StringBuilder query = new StringBuilder("INSERT INTO " + tableName() + " ");
        StringBuilder columns = new StringBuilder();
        StringBuilder values = new StringBuilder();
        HashMap<String, Object> insertMap = getInsertMap(object);
        for(String column : insertMap.keySet()){
            columns.append(column).append(",");
            values.append(formatVariable(insertMap.get(column))).append(",");
        }
        columns = new StringBuilder(columns.substring(0, columns.length() - 1)); //On enlève la dernière virgule
        values = new StringBuilder(values.substring(0, values.length() - 1)); //On enlève la dernière virgule
        query.append("(").append(columns.toString()).append(") VALUES(").append(values.toString()).append(")");
        try {
            int id = insertAndReturnId(query.toString());
            object.setId(id);
        } catch (SQLException e) {
            Logger.severe("Unable to insert "+object.getClass().getName()+" object " + query);
        }
    }

    /**
     * Met a jour l'objet en base de donnée ou l'insère s'il n'existe pas
     * @param object Objet a sauvegarder
     */
    public void save(T object){
        if(object.getId() > 0){
            this.update(object);
        } else {
            this.insert(object);
        }
    }

    /**
     * Retire l'objet de la base de donnée
     * @param object Objet a détruire
     */
    public void delete(T object){
        try {
            rawQuery("DELETE FROM " + tableName() + " WHERE id="+formatVariable(object.getId()));
        } catch (SQLException e) {
            Logger.warning("Unable to delete on " + tableName() + " where id=" + object.getId());
        }
    }

    /**
     * Trouve un objet T par son ID
     * @param id Identifiant de l'objet
     * @return Un objet T
     */
    public T findById(int id){
        HashMap<String, Object> criterias = new HashMap<>();
        criterias.put("id", formatVariable(id));
        return this.find(criterias);
    }

    /**
     * Cherche un unique objet T répondant aux critères, retourne le 1er si plusieurs trouvés
     * @param criterias HashMap associant la colonne a sa valeur
     * @return Un objet T
     */
    public T find(HashMap<String, Object> criterias){
        List<T> objects = this.getAll(criterias);
        return objects.isEmpty() ? null : objects.get(0);
    }

    /**
     * Récupère la liste de tout les objets avec un critère
     * @param criterias HashMap associant la colonne a sa valeur
     * @return Une liste d'objet T
     */
    public List<T> getAll(HashMap<String, Object> criterias){
        List<T> result = new ArrayList<>();
        StringBuilder query = new StringBuilder("SELECT * FROM " + tableName() + (criterias.isEmpty() ? "" : " WHERE"));
        try {
            String[] columns = criterias.keySet().toArray(new String[0]);
            for(int i = 0; i < columns.length; i++){
                if(i != 0) query.append(" AND ");
                query.append(" ").append(columns[i]).append("=").append(formatVariable(criterias.get(columns[i])));
            }
            for(HashMap<String,Object> data : rawQuery(query.toString())){
                result.add(type.getConstructor(HashMap.class).newInstance(data));
            }
        } catch (SQLException e) {
            Logger.error("Unable to getAll on " + tableName() + " with criteria " + query.toString(),e);
        } catch (IllegalAccessException | InstantiationException | NoSuchMethodException | IllegalArgumentException | InvocationTargetException e) {
            Logger.error("Unknown error",e);
        }
        return result;
    }

    /**
     * Récupère la liste de tout les objets
     * @return Une liste d'objet T
     */
    public List<T> getAll(){
        List<T> result = new ArrayList<>();
        try {
            for(HashMap<String,Object> data : rawQuery("SELECT * FROM " + tableName())){
                result.add(type.getConstructor(HashMap.class).newInstance(data));
            }
        } catch (SQLException e) {
            Logger.warning("Unable to getAll on " + tableName());
        } catch (IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
            Logger.warning(e.getMessage());
        }
        return result;
    }

    /**
     * Formate une variable a sa syntaxe SQL
     * @param variable Variable a formater
     * @return Une chaine de caractère utilisable en SQL pour décrire la variable
     */
    protected String formatVariable(Object variable){
        if(variable instanceof String){
            return "'"+variable+"'";
        } else if(variable instanceof Number){
            return ""+variable+"";
        } else if(variable instanceof java.sql.Date){
            return "'"+new SimpleDateFormat("yy-MM-dd HH:mm:ss").format((Date) variable)+"'";
        } else if(variable instanceof Boolean){
            return ""+variable+"";
        }
        return ""+"NULL"+"";
    }

    /**
     * Effectue une requête en retournant une liste associant les colonnes à leur valeurs
     * @param fullCommand Commande a executer
     * @return Une Array contenant chaque Ligne sous la forme d'une Map associant colonne a sa valeur
     */
    protected ArrayList<HashMap<String,Object>> rawQuery(String fullCommand) throws SQLException {
        Statement stm = null;
        stm = connection.createStatement();

        ResultSet result = null;
        boolean returningRows = stm.execute(fullCommand);
        if (returningRows) result = stm.getResultSet();
        else return new ArrayList<HashMap<String,Object>>();

        ResultSetMetaData meta = null;
        meta = result.getMetaData();

        int colCount = meta.getColumnCount();
        ArrayList<String> cols = new ArrayList<String>();
        for (int index=1; index<=colCount; index++)
            cols.add(meta.getColumnName(index));

        ArrayList<HashMap<String,Object>> rows = new ArrayList<HashMap<String,Object>>();

        while (result.next()) {
            HashMap<String,Object> row = new HashMap<String,Object>();
            for (String colName:cols) {
                Object val = result.getObject(colName);
                row.put(colName,val);
            }
            rows.add(row);
        }

        stm.close();
        return rows;
    }

    /**
     * Execute a query and return the id if its an insert query
     * @param query Query to execute
     * @return Id of generated row
     */
    public Integer insertAndReturnId(String query) throws SQLException {
        int id = -1;
        Statement stmt = connection.createStatement();
        stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
        ResultSet rs = stmt.getGeneratedKeys();
        if (rs.next()){
            id=rs.getInt(1);
        }
        stmt.close();
        return id;
    }
}
