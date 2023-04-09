package com.cursach.dmytropakholiuk.export;
import com.cursach.dmytropakholiuk.Application;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *<p>Helps to create the save files in JSON format. Depends on Jackson lib.</p>
 * <p>Currently, the generated JSON stores many objects inside a Save object</p>
 */
public class JSONExporter implements Exporter{
    public static JSONExporter getInstance() {
        if (instance == null){
            instance = new JSONExporter();
        }
        return instance;
    }
    /**
     * You have to bind all exportables to the exporter separately. Unbound exportables won't be exported
     * @param exportable - must implement the Exportable interface
     */
    public void bindExportable(Exportable exportable){
        if (boundObjects.contains(exportable)){
            return;
        }
        this.boundObjects.add(exportable);
        exportable.bindExporter(this);
    }
    public void unbindExportable(Exportable exportable){
        if (!this.boundObjects.contains(exportable)){
            return;
        }
        this.boundObjects.remove(exportable);
        exportable.unbindExporter();
    }
    private static JSONExporter instance;
    /**
     * where all the bound exportables are stored
     */
    public List<Exportable> boundObjects = new ArrayList<>();

    @Override
    public List<Exportable> getBoundObjects() {
        return boundObjects;
    }

    /**
     * you will have to change this method to delete your exportables
     */
    public void truncateExportables(){
        this.boundObjects.clear();
        Application.truncateCells();
    }
    public void unbindAll(){
        for (int i = boundObjects.toArray().length; i >= 0; i--){
            this.unbindExportable(boundObjects.get(i));
        }
    }

//    private ObjectWriter objectWriter = new ObjectWriter(objectMapper);
    private ObjectMapper objectMapper = new ObjectMapper();
//    private ObjectReader objectReader = new ObjectReader();

    /**
     * Returns a String containing JSON form of object
     * @param _obj  Exportable you wish to export as JSON string
     * @return String
     */
    public String exportObjectAsString(Exportable _obj){
        try {
            return this.objectMapper.writeValueAsString(_obj);
        }catch (Exception e){
            System.out.println("\n--could not export object as JSON string--\n");
            e.printStackTrace();
        }
        return "";
    }

    /**
     * writes a quicksave in "quicksave.json" and overwrites it.
     */
    public void quickSave() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("saves/quicksave.json"));

            Save save = new Save();
            writer.write(this.exportObjectAsString(save));
            System.out.println("quicksaving...");

            writer.close();
        } catch (Exception e){
            System.out.println("could not save");
            throw new RuntimeException();
        }

    }
    private JSONExporter(){

    }

    /**
     * takes in JSON string and creates an Exportable from it.
     * Do notice that it can only create objects of types declared in Exportable interface using annotations
     * @param s
     * @return Exportable
     */
    public Exportable importObjectFromString(String s){
        ObjectMapper mapper = this.objectMapper;
        Exportable exportable = null;
        try {
            exportable = mapper.readValue(s,
                    Exportable.class);
        } catch (JsonProcessingException e) {
            System.out.println("could not import object from string");
            throw new RuntimeException(e);
        }
        return exportable;
    }

    /**
     * loads from quicksave.json
     */
    public void quickLoad() {
        this.truncateExportables();
        Scanner scanner = null;
        try {
            scanner = new Scanner(new File("saves/quicksave.json"));
            while (scanner.hasNextLine()) {
                String serialized = scanner.nextLine();
                this.importObjectFromString(serialized);
            }
            System.out.println("quickloading...");
            scanner.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
