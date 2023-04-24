package com.cursach.dmytropakholiuk.organs;

import com.cursach.dmytropakholiuk.cells.Cell;
import com.cursach.dmytropakholiuk.cells.CellType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

public abstract class Organ {

    protected double x, y;
    public double getX(){return x;}
    public void setX(double _x)
    {
        this.x = _x;
        this.group.setLayoutX(_x);
    }
    public double getY(){return y;}
    public void setY(double _y)
    {
        this.y = _y;
        this.group.setLayoutY(_y);
    }
    protected Image image;
    public Image getImage(){
        return image;
    }
    protected ImageView imageView;
    protected Text shownText;

    public Text getShownText() {
        return shownText;
    }
    public void setShownText(String text){
        this.shownText.setText(text);
    }
    public Rectangle r;
    protected Color rColour;
    public Color getrRColour(){
        return rColour;
    }

    protected Group group;
    protected void configureGroup()
    {
        ImageView imageView = new ImageView(getImage());
        imageView.setFitHeight(200);
        imageView.setFitWidth(200);
        imageView.setPreserveRatio(true);
        this.imageView = imageView;

        Rectangle aura = new Rectangle();
        aura.setHeight(250);
        aura.setWidth(250);
        aura.setFill(getrRColour());
        this.r = aura;

        this.group = new Group(r, imageView, shownText);
        imageView.relocate(25, 25);
        shownText.relocate(0, 0);
        r.relocate(0, 0);

        group.toBack();

    }

    public List<Cell> tenants = new ArrayList<Cell>();
    public abstract boolean canEnter(Cell cell);
//    public boolean canEnter(CellType cellType){
//
//    }

}
