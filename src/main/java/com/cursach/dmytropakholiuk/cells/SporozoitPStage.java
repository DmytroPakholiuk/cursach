package com.cursach.dmytropakholiuk.cells;

import com.cursach.dmytropakholiuk.Application;
import com.cursach.dmytropakholiuk.strategy.InfestorStrategy;
import com.cursach.dmytropakholiuk.strategy.RushStrategy;
import com.cursach.dmytropakholiuk.strategy.Strategy;
import com.cursach.dmytropakholiuk.strategy.UsableStrategies;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

public class SporozoitPStage extends PStage{
    @Override
    protected void configureImage() {
        System.out.println("setting image for a Sporozoit");
        this.image = new Image(Application.class.getResource("sporozoit.png").toString());
    }

    public List<UsableStrategies> allowedStrategies(){
        List<UsableStrategies> list = new ArrayList<>();
        list.add(UsableStrategies.INACTIVE);
        list.add(UsableStrategies.RANDOM);
        list.add(UsableStrategies.RUSHER);

        return list;
    }

    @Override
    public Strategy defaultStrategy() {
        return new RushStrategy();
    }

    public SporozoitPStage(){
        this.configureImage();
    }
}
