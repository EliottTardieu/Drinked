package fr.drinked.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.File;
import java.io.IOException;

/**
 * class FXUtils
 *
 * Miscellaneous fx functions
 */
public class FXUtils {

    /**
     * Load a node from a FXML file
     *
     * @param fxml FXML filepath
     * @return The root node extracted from the FXML file
     */
    public static Parent loadFXML(String fxml) {
        String fileName = "views" + File.separator + fxml + ".fxml";
        FXMLLoader fxmlLoader = new FXMLLoader(FXUtils.class.getClassLoader().getResource(fileName));
        try {
            return fxmlLoader.load();
        } catch (IOException exception) {
            Logger.severe("Unable to load fxml file.");
        }
        return null;
    }

}
