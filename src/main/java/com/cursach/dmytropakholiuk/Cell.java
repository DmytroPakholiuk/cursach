package com.cursach.dmytropakholiuk;

import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

public abstract class Cell {
    static int newx;
    static int newy;
    protected int x, y;
    public int getX(){return x;}
    public void setX(int _x)
    {
        this.x = _x;
        this.group.setLayoutX(_x);
    }
    public int getY(){return y;}
    public void setY(int _y)
    {
        this.y = _y;
        this.group.setLayoutY(_y);
    }
    public static int step = 30;
    protected Circle r;
    protected Color rColour;
    public Color getrRColour(){
        return rColour;
    }
    protected Image image;
    public Image getImage(){
        return image;
    }
    protected ImageView imageView;
    protected Text shownName = new Text();
    protected String name = new String();
    public void setName(String _name) {
        if (_name.equals("")){
            this.setName(Integer.toString(hashCode())); return;
        }
        this.name = (_name);
        this.shownName.setText(_name);
    }
    public String getName(){
        return name;
    }
    protected Group group;

    protected boolean active = false;
    public void setActive(boolean a) {
        this.active = a;
        if (this.active)
            this.r.setFill(Color.RED);
        else
            this.r.setFill(getrRColour());
    }
    public void switchActivation(){
        setActive(!this.isActive());
    }

    public boolean isActive()
    {
        return active;
    }
    protected void configureGroup()
    {
        ImageView imageView = new ImageView(getImage());
        imageView.setFitHeight(50);
        imageView.setFitWidth(50);
        imageView.setPreserveRatio(true);
        this.imageView = imageView;

        Circle aura = new Circle();
        aura.setRadius(40.0f);
        aura.setFill(getrRColour());
        this.r = aura;

        this.group = new Group(r, imageView, shownName);
        imageView.relocate(15, 15);
        shownName.relocate(0, 0);
        r.relocate(0, 0);
    }
    public String getPrettyString(){
        String _x = Integer.toString(getX());
        String _y = Integer.toString(getY());
        String _active = active ? "active" : "inactive";

        return "Cell "+this.name+", x="+_x+", y="+_y+", "+_active;
    }

    public void delete(){
        Application.cellGroup.getChildren().remove(this.group);
        Application.cells.remove(this);
        this.group.setVisible(false);
    }

    public void moveLeft() {
        if (!active) return;
        int x = (int) (group.getLayoutX() - step);
        setX(x);
    }
    public void moveRight() {
        if (!active) return;
        int x = (int) (group.getLayoutX() + step);
        setX(x);
    }
    public void moveUp() {
        if (!active) return;
        int y = (int) (group.getLayoutY() - step);
        setY(y);
    }
    public void moveDown() {
        if (!active) return;
        int y = (int) (group.getLayoutY() + step);
        setY(y);
    }
//    public Exporter exporter;
//    public void bindExporter(Exporter _exporter){
//        this.exporter = _exporter;
//        _exporter.bindExportable(this);
//    };
//    public void unbindExporter(Exporter _exporter){
//        this.exporter = null;
//        _exporter.unbindExportable(this);
//    };
//    public void bindDefaultExporter(){
//        this.bindExporter(Application.jsonExporter);
//    }

}
