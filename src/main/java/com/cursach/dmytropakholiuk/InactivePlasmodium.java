package com.cursach.dmytropakholiuk;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class InactivePlasmodium extends Cell implements Cloneable{


    private Image image = configureImage();
    public Image configureImage(){
        throw new RuntimeException("not implemented yet");
//        System.out.println("setting image for an inactive plasmodium");
//        return new Image(Application.class.getResource("rbc.png").toString());
    }
    @Override
    @JsonIgnore
    public Image getImage(){
        return image;
    }
    @JsonIgnore
    public Color rColour = Color.BLUE;
    @JsonIgnore
    @Override
    public Color getrRColour(){
        return rColour;
    }

    public InactivePlasmodium(String _name, boolean _active, int _x, int _y, int _step)

    {
        System.out.println("called specified InactivePlasmodium constructor\n");
        this.shownName = new Text(this.name);

        configureGroup();

        this.setX(_x);
        this.setY(_y);
        this.setName(_name);
        this.setActive(_active);

        this.setStep(_step);


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

    public InactivePlasmodium(){
        this("", false,
//                (int) (Math.random() * 1000), (int)(Math.random() * 1000),

                0,0,
                30);

        System.out.println("...via default InactivePlasmodium constructor\n");
    }

    @Override
    @JsonIgnore
    public String getPrettyString(){
        return super.getPrettyString();
    }

    public WhiteBloodCell clone() throws CloneNotSupportedException
    {
        WhiteBloodCell cloned = (WhiteBloodCell) super.clone();

        return cloned;
    }
    public boolean equals(Object o){
        if (o instanceof RedBloodCell){
            if (((RedBloodCell) o).name.equals(this.name)){
                return true;
            }
        }
        return false;
    }
    public String toString(){
        return this.getPrettyString();
    }

}
