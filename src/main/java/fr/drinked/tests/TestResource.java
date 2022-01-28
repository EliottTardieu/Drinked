package fr.drinked.tests;

import fr.drinked.App;
import fr.drinked.models.Resource;
import fr.drinked.utils.Logger;
import java.util.HashMap;

public class TestResource {

    private Resource awaitedModify = new Resource("SugarTestV2", 50);
    private int backupSize;
    private int backupQuantity;
    private String backupDescription;

    public boolean run() {
        // INSERT
        boolean insert = true;
        Resource sugarTest = new Resource("SugarTestV1", 80);
        backupSize = App.getInstance().getResourceDAO().getAll().size();
        App.getInstance().getResourceDAO().save(sugarTest);
        HashMap<String, Object> criteria = new HashMap<>();
        criteria.put("description", "SugarTestV1");
        Resource sugarResource = App.getInstance().getResourceDAO().find(criteria);
        if(sugarResource.compare(sugarTest) && (App.getInstance().getResourceDAO().getAll().size() == backupSize +1)) {
            Logger.fine("ResourceDAO insert fine");
        } else {
            Logger.severe("ResourceDAO insert failed");
            insert = false;
        }

        // MODIFY
        boolean modify = true;
        backupQuantity = sugarResource.getQuantity_available();
        backupDescription = sugarResource.getDescription();
        sugarResource.setQuantity_available(50);
        sugarResource.setDescription("SugarTestV2");
        App.getInstance().getResourceDAO().save(sugarResource);
        HashMap<String, Object> criteriaTest = new HashMap<>();
        criteriaTest.put("description", "SugarTestV2");
        Resource newSugarResource = App.getInstance().getResourceDAO().find(criteriaTest);
        if (newSugarResource.compare(sugarResource) && newSugarResource.compare(awaitedModify)) {
            Logger.fine("ResourceDAO modify fine");
        } else {
            Logger.severe("ResourceDAO modify failed");
            modify = false;
        }

        // DELETE
        boolean delete = true;
        App.getInstance().getResourceDAO().delete(newSugarResource);
        if(App.getInstance().getResourceDAO().getAll().size() != backupSize) {
            delete = false;
            Logger.severe("ResourceDAO delete failed");
        } else {
            Logger.fine("ResourceDAO delete fine");
        }

        if(insert && modify && delete) {
            Logger.fine("ResourceDAO test successful");
            return true;
        } else {
            Logger.severe("ResourceDAO test failed");
            return false;
        }
    }
}
