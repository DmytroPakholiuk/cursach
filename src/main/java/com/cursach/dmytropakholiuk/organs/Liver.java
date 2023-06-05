package com.cursach.dmytropakholiuk.organs;

import com.cursach.dmytropakholiuk.Application;
import com.cursach.dmytropakholiuk.cells.*;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class Liver extends Organ{

    @Override
    public boolean canEnter(Cell cell) {
        if (cell instanceof InactivePlasmodium){
            if (PStage.getPStageType(((InactivePlasmodium) cell).getStage()) == PStage.PStageType.SPOROZOIT_PSTAGE){
                return true;
            }
        }
        if (cell instanceof WhiteBloodCell){
            return true;
        }
        if (cell instanceof RedBloodCell){
            return true;
        }
        return false;
    }

    {
        this.image = new Image(Application.class.getResource("liver.png").toString());
    }

    public Liver(int _x, int _y){
        System.out.println("building Liver");
        this.shownText = new Text("Liver");



        this.configureGroup();

        this.setY(_y);
        this.setX(_x);

        Application.organGroup.getChildren().add(this.group);
        Application.miniMap.addOrgan(this);

    }

    @Override
    public Color getrRColour() {
        return Color.LIGHTGRAY;
    }
}
