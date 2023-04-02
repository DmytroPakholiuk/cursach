package com.cursach.dmytropakholiuk;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    public Image getImage(){
        return image;
    }
    @JsonIgnore
    public Color rColour = Color.VIOLET;
    @JsonIgnore
    @Override
    public Color getrRColour(){
        return rColour;
    }
    public WhiteBloodCell(String _name, boolean _active, int _x, int _y, int _step, double _digestTime)
    {
        System.out.println("called specified WhiteBloodCell constructor\n");
//        this.image = new Image(Application.class.getResource("wbc.png").toString());
//        ImageView imageView = new ImageView(this.image);
//        imageView.setFitHeight(50);
//        imageView.setFitWidth(50);
//        imageView.setPreserveRatio(true);
//        this.imageView = imageView;
//
//        Circle aura = new Circle();
//        aura.setRadius(40.0f);
//        aura.setFill(Color.VIOLET);
//        this.r = aura;
//
//        this.group = new Group(r, imageView, shownName);
//        imageView.relocate(20, 15);
//        shownName.relocate(8, 0);
//        r.relocate(0, 0);
        this.shownName = new Text(this.name);

        configureGroup();

        this.setX(_x);
        this.setY(_y);
        this.setName(_name);
        this.setActive(_active);
        this.setStep(_step);
        this.setDigestTime(_digestTime);

        this.group.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                setActive(!active);
            }
        });
        Application.cells.add(this);
        Application.cellGroup.getChildren().add(this.group);

        this.bindDefaultExporter();

        System.out.println("created object "+this.toString());
        System.out.println("exported directly: "+Application.jsonExporter.exportObjectAsString(this));


    }

    public WhiteBloodCell(){
        this("", false,
//                (int) (Math.random() * 1000), (int)(Math.random() * 1000),
                0,0,
                30,
                7.5);

        System.out.println("...via default WhiteBloodCell constructor\n");
    }

    @Override
    @JsonIgnore
    public String getPrettyString(){
        String _digestTime = Double.toString(digestTime);
        return super.getPrettyString()+", digest time: "+_digestTime;
    }
//    public String asExportableString(){
//        return this.exporter.exportObjectAsString(this);
//    }

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
