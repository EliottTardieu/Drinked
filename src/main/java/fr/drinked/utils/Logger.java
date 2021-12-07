package fr.drinked.utils;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;

public class Logger {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Logger.class.getName());
    private static FileHandler fileHandler = fileHandler();

    //Too slow for really fast writing, so it misses alot of code.
    private static FileHandler fileHandler(){
        SimpleDateFormat fileTime = new SimpleDateFormat("yyyy/MM/dd/HH/mm");
        try {
            //Initializing the log file path
            String path = "logs/" + fileTime.format(Calendar.getInstance().getTime()) + ".log";
            File file = new File(path);
            if (!file.exists()) file.getParentFile().mkdirs();
            fileHandler = new FileHandler(path, 8096, 1, true);
            logger.addHandler(fileHandler);
            logger.setLevel(Level.ALL);
            fileHandler.setFormatter(new LogFormatter());
        } catch (IOException e) {
            JOptionPane.showInternalMessageDialog(null, "Impossible de créer un fichier log " + e.getMessage());
        }
        return fileHandler;
    }

    public static void severe(String msg){
        logger.severe(msg);
    }

    public static void warning(String msg){
        logger.warning(msg);
    }

    public static void fine(String msg){
        logger.fine(msg);
    }

    public static void error(String msg, Exception e){ error(msg, e, false); }

    public static void error(String msg, Exception e, boolean quit){ logger.log(Level.SEVERE, msg, e); }

    public static void exit(){
        for(Handler h: logger.getHandlers()) {
            h.close();   //must call h.close or a .LCK file will remain.
        }
    }

}

