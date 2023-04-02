package com.cursach.dmytropakholiuk.export;

import com.cursach.dmytropakholiuk.Application;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

public class Save implements Exportable{

    public List<Exportable> boundObjects;
    @JsonIgnore
    public Exporter exporter;

    public Save() {
        this.bindDefaultExporter();
        boundObjects = new ArrayList<>(exporter.boundObjects);

    }

    public void bindExporter(Exporter _exporter){
        this.exporter = _exporter;
    };
    public void unbindExporter(Exporter _exporter){
        this.exporter = null;
    };
    public void bindDefaultExporter(){
        this.bindExporter(Application.jsonExporter);
    }

}
