package com.cursach.dmytropakholiuk.cells;

import com.cursach.dmytropakholiuk.Application;
import com.cursach.dmytropakholiuk.organs.Organ;
import com.cursach.dmytropakholiuk.organs.OrganType;
import com.cursach.dmytropakholiuk.strategy.UsableStrategies;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.List;


/**
 * The main difference of plasmodia is that they have most of their defining features delegated to PStage
 */
public class InactivePlasmodium extends Cell implements Cloneable{


    @JsonIgnore
    private Image image;
    protected Image configureImage(){
        System.out.println("setting image for an inactive plasmodium");
        return stage.getImage();
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

    public InactivePlasmodium(String _name, boolean _active, int _x, int _y, int _step, OrganType oType)
    {
        System.out.println("called specified InactivePlasmodium constructor\n");

        this.bindDefaultStage();
        System.out.println(this.stage.toString());
        image = configureImage();

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


        this.bindDefaultExporter();
        allowedStrategies = setAllowedStrategies();
        this.setDefaultStrategy();

        this.enterOrgan(Organ.getOrganByType(oType));


        System.out.println("created object "+this.toString());
        System.out.println("exported directly: "+Application.jsonExporter.exportObjectAsString(this));
    }

    public InactivePlasmodium(){
        this("", false,
//                (int) (Math.random() * 1000), (int)(Math.random() * 1000),

                0,0,
                30,
                OrganType.ORGANTYPE_NULLORGAN);

        System.out.println("...via default InactivePlasmodium constructor\n");
    }


    protected PStage stage;

    public PStage getStage() {
        return stage;
    }
    public void bindStage(PStage stage){
//        System.out.println(stage.toString());
//        throw new RuntimeException("asasdadasadadsadsdadasdasasd");

        stage.plasmodium = this;
        this.stage = stage;

//        this.image = configureImage();
//        if (this.group != null){
//            this.group.setVisible(false);
//        }
//        Application.cellGroup.getChildren().remove(this.group);
//        this.configureGroup();
//        this.group.setOnMouseClicked(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent event) {
//                setActive(!active);
//            }
//        });
//        Application.cellGroup.getChildren().add(this.group);

        this.image = configureImage();
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

        allowedStrategies = setAllowedStrategies();
        this.setDefaultStrategy();
    }
    public void setStage(PStage stage){
        bindStage(stage);
    }
    public void cycleStage(Runnable callback){
        this.bindStage(PStage.createPStageByType(PStage.nextStage(PStage.getPStageType(this.getStage()))));
        if (callback != null){
            callback.run();
        }
    }
    public void cycleStage(){
        this.cycleStage(null);
    }
    public void bindDefaultStage(){
        this.bindStage(new SchizontPStage());
    }

    @Override
    @JsonIgnore
    public String getPrettyString(){
        return super.getPrettyString();
    }

    public InactivePlasmodium clone() throws CloneNotSupportedException
    {
        InactivePlasmodium cloned = (InactivePlasmodium) super.clone();

        cloned.configureClone(cloned);
        cloned.bindStage(PStage.createPStageByType(PStage.getPStageType(this.getStage())));
        cloned.setActive(this.isActive());
        cloned.bindDefaultExporter();


        return cloned;
    }
    public boolean equals(Object o){
        if (o instanceof InactivePlasmodium){
            if (((InactivePlasmodium) o).name.equals(this.name)){
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

    @JsonIgnore
    protected List<UsableStrategies> setAllowedStrategies(){
        return getStage().allowedStrategies();
    }
    @Override
    public void setDefaultStrategy() {
        this.setStrategy(getStage().defaultStrategy());
        System.out.println(strategy.toString());
    }

}
