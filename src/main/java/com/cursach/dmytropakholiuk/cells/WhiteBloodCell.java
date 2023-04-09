package com.cursach.dmytropakholiuk.cells;

import com.cursach.dmytropakholiuk.Application;
import com.cursach.dmytropakholiuk.strategy.EaterStrategy;
import com.cursach.dmytropakholiuk.strategy.UsableStrategies;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

public class WhiteBloodCell extends Cell {

    private double digestTime = 7.5;
    public double getDigestTime(){
        return digestTime;
    }
    public void setDigestTime(double _digestTime){
        digestTime = _digestTime;
    }
    protected Image image = configureImage();
    protected Image configureImage(){
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
        allowedStrategies = setAllowedStrategies();
        this.setDefaultStrategy();

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

    public WhiteBloodCell clone() throws CloneNotSupportedException
    {
        WhiteBloodCell cloned = (WhiteBloodCell) super.clone();
        cloned.setDefaultStrategy();

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

    protected List<UsableStrategies> setAllowedStrategies(){
        List<UsableStrategies> strategies = new ArrayList<>();
        strategies.add(UsableStrategies.INACTIVE);
        strategies.add(UsableStrategies.RANDOM);
        strategies.add(UsableStrategies.EATER);

        return strategies;
    }
    @Override
    public void setDefaultStrategy() {
        this.setStrategy(new EaterStrategy(this.digestTime));
    }
}
