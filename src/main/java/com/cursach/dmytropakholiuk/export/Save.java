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
        boundObjects = exporter.boundObjects;

    }

    public void bindExporter(Exporter _exporter){
        this.exporter = _exporter;
        _exporter.bindExportable(this);
    };
    public void unbindExporter(Exporter _exporter){
        this.exporter = null;
        _exporter.unbindExportable(this);
    };
    public void bindDefaultExporter(){
        this.bindExporter(Application.jsonExporter);
    }

}
