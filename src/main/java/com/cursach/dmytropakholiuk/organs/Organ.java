package com.cursach.dmytropakholiuk.organs;

import com.cursach.dmytropakholiuk.Application;
import com.cursach.dmytropakholiuk.cells.Cell;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;


/**
 * Base organ functionality class.
 */
public abstract class Organ {

    /**
     * Null-object nested class. Better than carrying all those NullPointers, eh?
     */
    public static class NullOrgan extends Organ{
        @Override
        public boolean canEnter(Cell cell) {
            return true;
        }
    }

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
    /**
     * Is overwritten in subclasses
     */
    public Color getrRColour(){
        return rColour;
    }

    protected Group group;
    @JsonIgnore
    public Group getGroup() {
        return group;
    }

    /**
     * Basic group configuration for graphics
     */
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

    public void acceptCell(Cell cell){
        if (this.canEnter(cell)){
            this.moveInside(cell);
        }
    }
    protected void moveInside(Cell cell){
        if (!this.tenants.contains(cell)){
            cell.setX(this.getX());
            cell.setY(this.getY());
            cell.setActive(false);

            Application.cellGroup.getChildren().remove(cell.getGroup());
            cell.setVisible(false);

            this.tenants.add(cell);
        }
    }
    public void moveOutside(Cell cell){
        if (this.tenants.contains(cell)){
            cell.setX(this.getX());
            cell.setY(this.getY());

            Application.cellGroup.getChildren().add(cell.getGroup());
            cell.setVisible(true);

            this.tenants.remove(cell);
        }
    }

    public List<Cell> tenants = new ArrayList<>();

    /**
     * validates if a cell can enter the Organ. Each subclass can define their own rules
     * @param cell
     * @return
     */
    public abstract boolean canEnter(Cell cell);
//    public boolean canEnter(CellType cellType){
//
//    }

}
