package com.cursach.dmytropakholiuk.export;


import com.cursach.dmytropakholiuk.*;
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

}
)
public interface Exportable {
    public void bindExporter(Exporter exporter);
    public void unbindExporter(Exporter exporter);
    public Exporter exporter = null;
}
