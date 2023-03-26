package com.cursach.dmytropakholiuk;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.util.ArrayList;
import java.util.List;

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
//    public Exportable[] bindedObjects = new Exportable[0];
    public List<Exportable> boundObjects = new ArrayList<>();
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
    private JSONExporter(){

    }
}
