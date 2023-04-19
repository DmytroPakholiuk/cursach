package com.cursach.dmytropakholiuk.cells;

import com.cursach.dmytropakholiuk.Application;
import com.cursach.dmytropakholiuk.strategy.InfestorStrategy;
import com.cursach.dmytropakholiuk.strategy.RushStrategy;
import com.cursach.dmytropakholiuk.strategy.Strategy;
import com.cursach.dmytropakholiuk.strategy.UsableStrategies;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

public class GametocytePStage extends PStage{
    @Override
    protected void configureImage() {
        System.out.println("setting image for a Gametocyte");
        this.image = new Image(Application.class.getResource("gametocyte.png").toString());
    }

    public List<UsableStrategies> allowedStrategies(){
        List<UsableStrategies> list = new ArrayList<>();
        list.add(UsableStrategies.INACTIVE);
        list.add(UsableStrategies.RUSHER);
        list.add(UsableStrategies.INFESTOR);

        return list;
    }

    @Override
    public Strategy defaultStrategy() {
        return new RushStrategy();
    }

    public GametocytePStage(){
        this.configureImage();
    }
}
