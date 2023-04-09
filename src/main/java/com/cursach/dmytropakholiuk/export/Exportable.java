package com.cursach.dmytropakholiuk.export;


import com.cursach.dmytropakholiuk.cells.*;
import com.cursach.dmytropakholiuk.cells.GametocytePStage;
import com.cursach.dmytropakholiuk.cells.SchizontPStage;
import com.cursach.dmytropakholiuk.cells.SporozoitPStage;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY)
@JsonSubTypes({
        @JsonSubTypes.Type(value = WhiteBloodCell.class, name = "WhiteBloodCell"),
        @JsonSubTypes.Type(value = RedBloodCell.class, name = "RedBloodCell"),
        @JsonSubTypes.Type(value = InactivePlasmodium.class, name = "InactivePlasmodium"),
        @JsonSubTypes.Type(value = PlasmodiumVivax.class, name = "PlasmodiumVivax"),
        @JsonSubTypes.Type(value = HIVPlasmodium.class, name = "HIVPlasmodium"),

        @JsonSubTypes.Type(value = SchizontPStage.class, name = "SchizontPStage"),
        @JsonSubTypes.Type(value = SporozoitPStage.class, name = "SporozoitPStage"),
        @JsonSubTypes.Type(value = GametocytePStage.class, name = "GametocytePStage"),


        @JsonSubTypes.Type(value = Save.class, name = "Save"),
}
)
public interface Exportable {
    public void bindExporter(Exporter exporter);
    public void unbindExporter(Exporter exporter);
    public Exporter exporter = null;
}
