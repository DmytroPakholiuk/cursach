package com.cursach.dmytropakholiuk.cells;

import com.cursach.dmytropakholiuk.Application;
import com.cursach.dmytropakholiuk.cells.InactivePlasmodium;
import com.cursach.dmytropakholiuk.export.Exportable;
import com.cursach.dmytropakholiuk.export.Exporter;
import com.cursach.dmytropakholiuk.strategy.Strategy;
import com.cursach.dmytropakholiuk.strategy.UsableStrategies;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javafx.scene.image.Image;

import java.util.List;

/**
 * It defines what looks and behaviour will plasmodia have
 */
public abstract class PStage implements Exportable {

    /**
     * Type enum for type comparison
     */
    public enum PStageType{
        SCHIZONT_PSTAGE, GAMETOCYTE_PSTAGE, SPOROZOIT_PSTAGE
    }
    public static PStageType getPStageType(PStage stage){
        if (stage instanceof SporozoitPStage){
            return PStageType.SPOROZOIT_PSTAGE;
        }
        if (stage instanceof SchizontPStage){
            return PStageType.SCHIZONT_PSTAGE;
        }
        if (stage instanceof GametocytePStage){
            return PStageType.GAMETOCYTE_PSTAGE;
        }
        throw new RuntimeException();
    }
    public static PStage createPStageByType(PStageType type){
        PStage stage = null;
        switch (type){
            case SCHIZONT_PSTAGE:
                stage = new SchizontPStage(); break;
            case SPOROZOIT_PSTAGE:
                stage = new SporozoitPStage(); break;
            case GAMETOCYTE_PSTAGE:
                stage = new GametocytePStage(); break;
        }
        return stage;
    }
    public static PStageType nextStage(PStageType stage){
        switch (stage){
            case SCHIZONT_PSTAGE:
                return PStageType.GAMETOCYTE_PSTAGE;
            case SPOROZOIT_PSTAGE:
                return PStageType.SCHIZONT_PSTAGE;
            case GAMETOCYTE_PSTAGE:
                return PStageType.SPOROZOIT_PSTAGE;
        }
        return stage;
    }

    @JsonIgnore
    protected Image image;
    abstract protected void configureImage();
    @JsonIgnore
    public Image getImage(){
        return image;
    }
    @JsonIgnore
    public InactivePlasmodium plasmodium;
    @JsonIgnore
    public Strategy strategy;
    /**
     * n has no real use except it allows to fake some info for exporter
     */
    public int n = 1;
    abstract public List<UsableStrategies> allowedStrategies();
    abstract public Strategy defaultStrategy();


    @JsonIgnore
    public Exporter exporter;
    public void bindExporter(Exporter _exporter){
        if (_exporter != null){
            if (_exporter.equals(this.exporter)) {
                return;
            }
        }
        this.exporter = _exporter;
//        _exporter.bindExportable(this);
    };
    public void unbindExporter(){
        exporter.unbindExportable(this);
        this.exporter = null;

    };
    public void bindDefaultExporter(){
        this.bindExporter(Application.jsonExporter);
    }


}
