package com.cursach.dmytropakholiuk.strategy;


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
    public StrategyManageable manageable;

    /**
     * Main method that gets called all the time
     */
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
