package com.cursach.dmytropakholiuk.export;
import com.cursach.dmytropakholiuk.Application;
import com.cursach.dmytropakholiuk.Cell;
import com.cursach.dmytropakholiuk.WhiteBloodCell;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class JSONExporter implements Exporter{
    public static JSONExporter getInstance() {
        if (instance == null){
            instance = new JSONExporter();
        }
        return instance;
    }
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
        exportable.unbindExporter(this);
    }
    private static JSONExporter instance;
    public List<Exportable> boundObjects = new ArrayList<>();
    public void truncateCells(){
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

    public void quickSave() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("save.json"));

            for (Exportable exportable: boundObjects){
                String exported = this.exportObjectAsString(exportable);
                writer.write(exported);
                writer.newLine();
            }

//        Save save = new Save();
//        writer.write(this.exportObjectAsString(save));
//        System.out.println(save.boundObjects.toString());
//        System.out.println(this.boundObjects.toString());

            writer.close();
        } catch (Exception e){
            System.out.println("could not save");
            throw new RuntimeException();
        }

    }
    private JSONExporter(){

    }

    public Exportable importObjectFromString(String s){
        ObjectMapper mapper = this.objectMapper;
        Exportable exportable = null;
        try {
            exportable = mapper.readValue(s,
                    Exportable.class);
        } catch (JsonProcessingException e) {
            System.out.println("could not import cell from string");
            throw new RuntimeException(e);
        }
        return exportable;
    }

    public void quickLoad() {
        this.truncateCells();
        Scanner scanner = null;
        try {
            scanner = new Scanner(new File("save.json"));

            while (scanner.hasNextLine()) {
                String serialized = scanner.nextLine();
                this.importObjectFromString(serialized);
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
