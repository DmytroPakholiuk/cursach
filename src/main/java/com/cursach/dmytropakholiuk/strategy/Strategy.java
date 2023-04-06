package com.cursach.dmytropakholiuk.strategy;

public abstract class Strategy {

    public static UsableStrategies type;
    public enum Direction{
        NORTH, NORTHEAST, EAST, SOUTHEAST, SOUTH, SOUTHWEST, WEST, NORTHWEST
    }
    public StrategyManageable manageable;
    public abstract void execute();
    public void bindManageable(StrategyManageable manageable){
        this.manageable = manageable;
    }
    public void unbindManageable(){
        this.manageable = null;
    }
    public Strategy(){

    }
}
