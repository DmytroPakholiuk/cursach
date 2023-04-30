package com.cursach.dmytropakholiuk;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * Simple logger utility class
 */
public class Logger {

    public static Logger instance;

    public static Logger getInstance() {
        if (instance == null){
            instance = new Logger();
        }
        return instance;
    }

    private Logger(){
        file = new File(fileName);

        log("User started a session");
    }

    private BufferedWriter writer;
    private String fileName = "log/log-" + LocalDateTime.now();
    private File file;

    /**
     * writes a line ti log file with adding datetime before it
     * @param s - line you want to log
     */
    public void log(String s){
        String output = "[ " + LocalDateTime.now() + " ] -- " + s + "\n";

        try {
            writer = new BufferedWriter(new FileWriter(file, true));
            writer.write(output);
            writer.close();
        } catch (IOException e) {
            System.out.println("could not log");
            throw new RuntimeException(e);
        }
    }

}
