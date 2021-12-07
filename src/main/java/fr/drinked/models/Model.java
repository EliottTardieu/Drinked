package fr.drinked.models;

import fr.drinked.utils.Logger;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.UUID;

public abstract class Model {

    protected int id = -1;

    public Model(){}

    public Model(HashMap<String, Object> data){
        this.hydrate(data);
    }

    public void setId(int id){
        this.id = id;
    }

    public int getId(){
        return this.id;
    }

    /**
     * Hydrate un objet en fonction d'un result set SQL
     * @param data Map associant une colonne a sa valeur
     */
    protected abstract void hydrate(HashMap<String,Object> data);

    public boolean exist(){
        return this.id > 0;
    }

    protected String string(Object object){
        if(object instanceof String){
            return (String) object;
        }
        return null;
    }

    protected int integer(Object object){
        if(object instanceof Number){
            return (int) object;
        } else if(object instanceof String){
            return Integer.parseInt((String) object);
        }
        return -1;
    }

    protected float floatNumber(Object object){
        if(object instanceof Number){
            return (float) object;
        } else if(object instanceof String){
            return Float.parseFloat((String) object);
        }
        return -1;
    }

    protected UUID uuid(Object object){
        if(object instanceof UUID){
            return (UUID) object;
        } else if(object instanceof String && !((String) object).trim().equals("")){
            try {
                return UUID.fromString((String) object);
            } catch (Exception ex) {
                Logger.error("Error retrieving UUID from model (" + object + ')', ex);
            }
        }
        return null;
    }

    protected Boolean booleanObject(Object object){
        if(object instanceof String) {
            return ((String)object).length() == 1 ? Integer.parseInt((String) object) == 1 : Boolean.parseBoolean((String) object);
        } else if(object instanceof Boolean){
            return (Boolean) object;
        } else if(object instanceof Number){
            return object.equals(1);
        }
        return null;
    }



}
