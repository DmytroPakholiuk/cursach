package com.cursach.dmytropakholiuk.cells;

import com.cursach.dmytropakholiuk.Application;
import com.cursach.dmytropakholiuk.strategy.*;
import javafx.concurrent.Task;
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
        Task<Void> callback = new Task<Void>() {
            @Override
            protected Void call() {
                plasmodium.cycleStage();
                return null;
            }
        };

        return new RushStrategy(Application.liver, callback);
//        return new InactiveStrategy();
    }

    public SporozoitPStage(){
        this.configureImage();
    }
}
