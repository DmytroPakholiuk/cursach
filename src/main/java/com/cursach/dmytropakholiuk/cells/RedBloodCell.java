package com.cursach.dmytropakholiuk.cells;

import com.cursach.dmytropakholiuk.Application;
import com.cursach.dmytropakholiuk.strategy.EaterStrategy;
import com.cursach.dmytropakholiuk.strategy.InactiveStrategy;
import com.cursach.dmytropakholiuk.strategy.UsableStrategies;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

public class RedBloodCell extends Cell implements Cloneable{

    private Image image = configureImage();
    public Image configureImage(){
        System.out.println("setting image for an RBC");
        return new Image(Application.class.getResource("rbc.png").toString());
    }
    @Override
    @JsonIgnore
    public Image getImage(){
        return image;
    }
    @JsonIgnore
    public Color rColour = Color.PURPLE;
    @JsonIgnore
    @Override
    public Color getrRColour(){
        return rColour;
    }

    public RedBloodCell(String _name, boolean _active, int _x, int _y, int _step)

    {
        System.out.println("called specified RedBloodCell constructor\n");
        this.shownName = new Text(this.name);

        configureGroup();

        this.setX(_x);
        this.setY(_y);
        this.setName(_name);
        this.setActive(_active);
        this.setId(cellQnt + 1);

        this.setStep(_step);


//        this.group.setOnMouseClicked(new EventHandler<MouseEvent>() {
//
//            @Override
//            public void handle(MouseEvent event) {
//                setActive(!active);
//            }
//        });
        Application.cells.add(this);
        Application.cellGroup.getChildren().add(this.group);

        allowedStrategies = setAllowedStrategies();
        this.setDefaultStrategy();
        this.bindDefaultExporter();
        Application.miniMap.addCell(this);

        System.out.println("created object "+this.toString());
        System.out.println("exported directly: "+Application.jsonExporter.exportObjectAsString(this));


    }

    public RedBloodCell(){
        this("", false,
//                (int) (Math.random() * 1000), (int)(Math.random() * 1000),

                0,0,
                30);

        System.out.println("...via default RedBloodCell constructor\n");
    }

    @Override
    @JsonIgnore
    public String getPrettyString(){
        return super.getPrettyString();
    }

    public RedBloodCell clone() throws CloneNotSupportedException
    {
        RedBloodCell cloned = (RedBloodCell) super.clone();
        configureClone(cloned);
        cloned.setActive(this.isActive());
        cloned.bindDefaultExporter();


        return cloned;
    }
    public boolean equals(Object o){
        if (o instanceof RedBloodCell){
            if (((RedBloodCell) o).name.equals(this.name)){
                if (o.hashCode() == this.hashCode()){
                    return true;
                }
            }
        }
        return false;
    }
    public String toString(){
        return this.getPrettyString();
    }

    public void delete(){
        try {
            super.delete();
            if (infestor != null){
                this.infestor.delete();
            }
        } catch (Exception e){
            e.printStackTrace();
        }


    }

    protected List<UsableStrategies> setAllowedStrategies(){
        List<UsableStrategies> strategies = new ArrayList<>();
        strategies.add(UsableStrategies.INACTIVE);
        strategies.add(UsableStrategies.RANDOM);

        return strategies;
    }
    @Override
    public void setDefaultStrategy() {
        this.setStrategy(new InactiveStrategy());
    }


    public InactivePlasmodium infestor;

    public void infestWith(InactivePlasmodium plasmodium){
        if (this.infestor == null){
            this.infestor = plasmodium;
            this.image = new Image(Application.class.getResource("infrbc.png").toString());
            plasmodium.setActive(false);

            Application.cellGroup.getChildren().remove(plasmodium.getGroup());
            if (this.group != null){
                this.imageView.setVisible(false);
                this.group.getChildren().remove(imageView);
                this.imageView = new ImageView(image);
                imageView.setFitHeight(50);
                imageView.setFitWidth(50);
                imageView.setPreserveRatio(true);
                this.group.getChildren().add(imageView);


                imageView.relocate(15, 15);
            }
            plasmodium.setVisible(false);
        }
    }
    public void deinfest(){
        this.infestor.setX(this.getX()+50);
        this.infestor.setY(this.getY()+50);
//        this.image = new Image(Application.class.getResource("rbc.png").toString());
        this.image = new Image(Application.class.getResource("rbc.png").toString());


        Application.cellGroup.getChildren().add(infestor.getGroup());
        if (this.group != null){
            this.imageView.setVisible(false);
            this.group.getChildren().remove(imageView);
            this.imageView = new ImageView(image);
            imageView.setFitHeight(50);
            imageView.setFitWidth(50);
            imageView.setPreserveRatio(true);
            this.group.getChildren().add(imageView);


            imageView.relocate(15, 15);
        }

//        try{
//            Application.cellGroup.getChildren().add(this.infestor.getGroup());
//        }catch (Exception e){
//            e.printStackTrace();
//
//        }

        this.infestor.setVisible(true);

        this.infestor = null;
//        configureGroup();
    }
}
