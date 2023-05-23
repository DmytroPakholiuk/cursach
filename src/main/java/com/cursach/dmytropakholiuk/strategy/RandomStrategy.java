package com.cursach.dmytropakholiuk.strategy;

import com.cursach.dmytropakholiuk.Application;
import com.cursach.dmytropakholiuk.cells.Deployable;

public class RandomStrategy extends Strategy{
    public void execute(){
        if (currentDirection == null){
            this.currentDirection = RandomStrategy.randomDirection();
        }
        double rand = Math.random() * 1000;
        if (rand < 5){
            this.currentDirection = RandomStrategy.randomDirection();
        }
        if (tooCloseToBorder(manageable)){
            this.currentDirection = RandomStrategy.generateOppositeDirection(this.currentDirection);
        }
        Strategy.moveInDirection(currentDirection, this.manageable, 0.5 * manageable.getSpeed());
    }


    public boolean tooCloseToBorder(Deployable target){
        if (target.getY() < 5 || target.getX() < 5){
            return true;
        }
        if (Application.appWidth - target.getX() < 85 || Application.appHeight - target.getY() < 85){
            return true;
        }
        return false;
    }
    public Direction currentDirection;
    public static Direction randomDirection(){
        int rand = (int)(Math.random() * 8);
        switch (rand){
            case 0:
                return Direction.NORTH;
            case 1:
                return Direction.NORTHEAST;
            case 2:
                return Direction.EAST;
            case 3:
                return Direction.SOUTHEAST;
            case 4:
                return Direction.SOUTH;
            case 5:
                return Direction.SOUTHWEST;
            case 6:
                return Direction.WEST;
            case 7:
                return Direction.NORTHWEST;
            default:
                throw new RuntimeException();
        }
    }
    public static Direction generateOppositeDirection(Direction direction){
        switch (direction){
            case NORTH:
                return Direction.SOUTH;
            case SOUTH:
                return Direction.NORTH;
            case EAST:
                return Direction.WEST;
            case WEST:
                return Direction.EAST;
            case NORTHEAST:
                return Direction.SOUTHWEST;
            case NORTHWEST:
                return Direction.SOUTHEAST;
            case SOUTHEAST:
                return Direction.NORTHWEST;
            case SOUTHWEST:
                return Direction.NORTHEAST;
            default:
                throw new RuntimeException();
        }
    }
}
