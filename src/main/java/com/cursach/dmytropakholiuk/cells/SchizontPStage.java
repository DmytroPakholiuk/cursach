package com.cursach.dmytropakholiuk.cells;

import com.cursach.dmytropakholiuk.Application;
import com.cursach.dmytropakholiuk.strategy.InfestorStrategy;
import com.cursach.dmytropakholiuk.strategy.Strategy;
import com.cursach.dmytropakholiuk.strategy.UsableStrategies;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

public class SchizontPStage extends PStage{

    @Override
    protected void configureImage() {
        System.out.println("setting image for a Schizont");
        this.image = new Image(Application.class.getResource("schizont.png").toString());
    }

    public List<UsableStrategies> allowedStrategies(){
        List<UsableStrategies> list = new ArrayList<>();
        list.add(UsableStrategies.INACTIVE);
        list.add(UsableStrategies.RANDOM);
        list.add(UsableStrategies.INFESTOR);

        return list;
    }

    @Override
    public Strategy defaultStrategy() {
        return new InfestorStrategy();
    }

    public SchizontPStage(){
        this.configureImage();
    }
}
