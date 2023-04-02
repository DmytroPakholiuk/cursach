package com.cursach.dmytropakholiuk.export;

import com.cursach.dmytropakholiuk.Application;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

/**
 * A container class to help generate valid JSON
 */
public class Save implements Exportable{
    /**
     * a link to exporter's boundObjects
     */
    public List<Exportable> boundObjects;
    @JsonIgnore
    public Exporter exporter;

    public Save() {
        this.bindDefaultExporter();
        boundObjects = new ArrayList<>(exporter.getBoundObjects());
    }

    public void bindExporter(Exporter _exporter){
        this.exporter = _exporter;
    };
    public void unbindExporter(Exporter _exporter){
        this.exporter = null;
    };

    /**
     * modify this method if you need to use different Exporter
     */
    public void bindDefaultExporter(){
        this.bindExporter(Application.jsonExporter);
    }

}
