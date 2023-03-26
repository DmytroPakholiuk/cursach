package com.cursach.dmytropakholiuk;

import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class WhiteBloodCell extends Cell {

    private double digestTime = 7.5;
    public double getDigestTime(){
        return digestTime;
    }
    public void setDigestTime(double _digestTime){
        digestTime = _digestTime;
    }
    private Image image = configureImage();
    public Image configureImage(){
        System.out.println("setting image for a WBC");
        return new Image(Application.class.getResource("wbc.png").toString());
    }
    @Override
    public Image getImage(){
        return image;
    }
    public Color rColour = Color.VIOLET;
    @Override
    public Color getrRColour(){
        return rColour;
    }
    public WhiteBloodCell(String _name, boolean _active, int _x, int _y)
    {
        System.out.println("called specified WhiteBloodCell constructor\n");
        this.shownName = new Text(this.name);

        configureGroup();

        this.setX(_x);
        this.setY(_y);
        this.setName(_name);
        this.setActive(_active);

        this.group.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                setActive(!active);
            }
        });
        Application.cells.add(this);
        Application.cellGroup.getChildren().add(this.group);

        System.out.println("created object "+this.toString());

    }

    public WhiteBloodCell(){
        this("", false,
//                (int) (Math.random() * 1000), (int)(Math.random() * 1000),
                0,0);

        System.out.println("...via default WhiteBloodCell constructor\n");
    }

    @Override
    public String getPrettyString(){
        String _digestTime = Double.toString(digestTime);
        return super.getPrettyString()+", digest time: "+_digestTime;
    }
    public WhiteBloodCell clone() throws CloneNotSupportedException
    {
        WhiteBloodCell cloned = (WhiteBloodCell) super.clone();

        return cloned;
    }
    public boolean equals(Object o){
        if (o instanceof WhiteBloodCell){
            if (((WhiteBloodCell) o).name.equals(this.name)){
                return true;
            }
        }
        return false;
    }
    public String toString(){
        return this.getPrettyString();
    }
}
