package com.cursach.dmytropakholiuk.organs;

import com.cursach.dmytropakholiuk.Application;
import com.cursach.dmytropakholiuk.cells.Cell;
import com.cursach.dmytropakholiuk.cells.InactivePlasmodium;
import com.cursach.dmytropakholiuk.cells.PStage;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class Anopheles extends Organ{

    @Override
    public boolean canEnter(Cell cell) {
        if (cell instanceof InactivePlasmodium){
            if (PStage.getPStageType(((InactivePlasmodium) cell).getStage()) == PStage.PStageType.GAMETOCYTE_PSTAGE){
                return true;
            }
        }
        return false;
    }

    {
        this.image = new Image(Application.class.getResource("anopheles.jpg").toString());
    }

    public Anopheles(int _x, int _y){
        System.out.println("building Anopheles");
        this.shownText = new Text("Anopheles");



        this.configureGroup();

        this.setY(_y);
        this.setX(_x);

        Application.organGroup.getChildren().add(this.group);

    }

    @Override
    public Color getrRColour() {
        return Color.GRAY;
    }
}
