package fr.drinked.tests;

import fr.drinked.App;
import fr.drinked.models.Resource;
import fr.drinked.utils.Logger;
import java.util.HashMap;

public class TestResource {

    private Resource awaitedModify = new Resource("SugarTest", 50);
    private int backupQuantity;
    private String backupDescription;

    public boolean run() {
        HashMap<String, Object> criteria = new HashMap<>();
        criteria.put("description", "Sugar");
        Resource sugarResource = App.getInstance().getResourceDAO().find(criteria);
        backupQuantity = sugarResource.getQuantity_available();
        backupDescription = sugarResource.getDescription();
        sugarResource.setQuantity_available(50);
        sugarResource.setDescription("SugarTest");
        App.getInstance().getResourceDAO().save(sugarResource);

        HashMap<String, Object> criteriaTest = new HashMap<>();
        criteriaTest.put("description", "SugarTest");
        Resource newSugarResource = App.getInstance().getResourceDAO().find(criteriaTest);
        if (newSugarResource.getQuantity_available() == sugarResource.getQuantity_available()
                && newSugarResource.getQuantity_available() == awaitedModify.getQuantity_available()
                && newSugarResource.getDescription().equals(sugarResource.getDescription())
                && newSugarResource.getDescription().equals(awaitedModify.getDescription())) {
            Logger.fine("ResourceDAO fine");
            this.reset(newSugarResource);
            return true;
        } else {
            Logger.severe("ResourceDAO returned a wrong value");
            this.reset(newSugarResource);
            return false;
        }
    }

    private void reset(Resource resource) {
        resource.setQuantity_available(backupQuantity);
        resource.setDescription(backupDescription);
        App.getInstance().getResourceDAO().save(resource);
    }
}
