package com.cursach.dmytropakholiuk.organs;

import com.cursach.dmytropakholiuk.Application;
import com.cursach.dmytropakholiuk.cells.Cell;
import com.cursach.dmytropakholiuk.cells.InactivePlasmodium;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class Marrow extends Organ{

    @Override
    public boolean canEnter(Cell cell) {
        return !(cell instanceof InactivePlasmodium);
    }

    {
        this.image = new Image(Application.class.getResource("marrow.png").toString());
    }

    public Marrow(int _x, int _y){
        System.out.println("building Marrow");
        this.shownText = new Text("Marrow");



        this.configureGroup();

        this.setY(_y);
        this.setX(_x);

        Application.organGroup.getChildren().add(this.group);

    }

    @Override
    public Color getrRColour() {
        return Color.DARKGRAY;
    }
}
