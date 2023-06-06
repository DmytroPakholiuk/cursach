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
    public static void moveInDirection(Direction direction, StrategyManageable manageable, double speed){
        switch (direction){
            case NORTH:
                manageable.setY(manageable.getY() - speed);break;
            case SOUTH:
                manageable.setY(manageable.getY() + speed);break;
            case EAST:
                manageable.setX(manageable.getX() + speed);break;
            case WEST:
                manageable.setX(manageable.getX() - speed);break;
            case NORTHEAST:
                manageable.setY(manageable.getY() - speed);
                manageable.setX(manageable.getX() + speed);break;
            case NORTHWEST:
                manageable.setY(manageable.getY() - speed);
                manageable.setX(manageable.getX() - speed);break;
            case SOUTHEAST:
                manageable.setY(manageable.getY() + speed);
                manageable.setX(manageable.getX() + speed);break;
            case SOUTHWEST:
                manageable.setY(manageable.getY() + speed);
                manageable.setX(manageable.getX() - speed);break;
            default:
                throw new RuntimeException();
        }
    }
    public Direction currentDirection;
    protected static StrategyManageable buffer;
    protected boolean isSleeping = false;
    protected LocalDateTime sleepFrom;
    protected double sleepingFor;
    protected Runnable sleepCallback;
    public void sleep(double seconds, Runnable callback){

//        System.out.println("INSIDE SLEEP(1)");
        this.sleepFrom = LocalDateTime.now();
        this.sleepingFor = seconds;
        this.sleepCallback = callback;
        this.isSleeping = true;
//        System.out.println("INSIDE SLEEP(2)");
//        System.out.println(this.isSleeping());
    }


    public boolean isSleeping(){
        return this.isSleeping;
    }

    public void wakeUp(){
        this.isSleeping = false;
    }

    public void checkSleep(){
//        System.out.println("CHECK SLEEP");
        if (ChronoUnit.SECONDS.between(this.sleepFrom, LocalDateTime.now()) > this.sleepingFor){
            Application.strategyTimer.stop();
            if (sleepCallback != null){
                sleepCallback.run();
            }
            this.wakeUp();
            Application.strategyTimer.start();
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
