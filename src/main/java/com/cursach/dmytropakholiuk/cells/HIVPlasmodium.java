package com.cursach.dmytropakholiuk.cells;

import com.cursach.dmytropakholiuk.organs.OrganType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class HIVPlasmodium extends PlasmodiumVivax{

    @JsonIgnore
    public Color rColour = Color.CYAN;
    @JsonIgnore
    @Override
    public Color getrRColour(){
        return rColour;
    }

    public HIVPlasmodium(){
        super();
        configureGroup();
    }

    public HIVPlasmodium(String _name, boolean _active, int _x, int _y, int _step, OrganType oType){
        super(_name, _active, _x, _y, _step, oType);

        this.r.setVisible(false);
        this.group.getChildren().remove(this.r);
        Circle aura = new Circle();
        aura.setRadius(40.0f);
        aura.setFill(getrRColour());
        this.r = aura;
        this.group.getChildren().add(this.r);
        this.r.toBack();

        this.r.relocate(0, 0);
    }
}
