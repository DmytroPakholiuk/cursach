package com.cursach.dmytropakholiuk.strategy;


import com.almasb.fxgl.dsl.components.HealthIntComponent;
import com.cursach.dmytropakholiuk.Application;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * Subclasses define automatic behaviour of cells
 */
public abstract class Strategy {

    public static UsableStrategies getType(Strategy strategy){
        if (strategy instanceof InactiveStrategy){
            return UsableStrategies.INACTIVE;
        }
        if (strategy instanceof RandomStrategy){
            return UsableStrategies.RANDOM;
        }
        if (strategy instanceof InfestorStrategy){
            return UsableStrategies.INFESTOR;
        }
        if (strategy instanceof EaterStrategy){
            return UsableStrategies.EATER;
        }
        if (strategy instanceof RushStrategy){
            return UsableStrategies.RUSHER;
        }
        return null;
    }
    public static UsableStrategies type;
    public enum Direction{
        NORTH, NORTHEAST, EAST, SOUTHEAST, SOUTH, SOUTHWEST, WEST, NORTHWEST
    }

    protected boolean isSleeping = false;
    protected LocalDateTime sleepFrom;
    protected double sleepingFor;
    protected Runnable sleepCallback;
    public void sleep(double seconds, Runnable callback){

        System.out.println("INSIDE SLEEP()");
        this.sleepFrom = LocalDateTime.now();
        this.sleepingFor = seconds;
        this.sleepCallback = callback;
        this.isSleeping = true;
    }


    public boolean isSleeping(){
        return this.isSleeping;
    }

    public void wakeUp(){
        this.isSleeping = false;
    }

    public void checkSleep(){
        System.out.println("CHECK SLEEP");
        if (ChronoUnit.SECONDS.between(this.sleepFrom, LocalDateTime.now()) > this.sleepingFor){
            sleepCallback.run();
            this.wakeUp();
        }
    }

    public StrategyManageable manageable;

    /**
     * Main method that gets called all the time
     */
    public void execute(){
        if (this.isSleeping()){
            this.checkSleep();
        } else {

        }
    };
    public void bindManageable(StrategyManageable manageable){
        this.manageable = manageable;
    }
    public void unbindManageable(){
        this.manageable = null;
    }
    public Strategy(){

    }
}
