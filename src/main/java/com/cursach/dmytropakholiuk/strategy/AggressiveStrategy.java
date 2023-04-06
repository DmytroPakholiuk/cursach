package com.cursach.dmytropakholiuk.strategy;

import com.cursach.dmytropakholiuk.Application;
import com.cursach.dmytropakholiuk.cells.Cell;
import com.cursach.dmytropakholiuk.cells.CellType;
import com.cursach.dmytropakholiuk.cells.Deployable;

import java.util.ArrayList;
import java.util.List;

public abstract class AggressiveStrategy extends ActiveStrategy{

    public double aggressionRange;
    public List<CellType> allowedTargets = new ArrayList<>();
    public boolean isHostile(Cell deployable){
        return allowedTargets.contains(Cell.getType(deployable));
    }
    public void chooseTarget(){
        for (Cell target: Application.cells){
            if (isHostile(target)){
                if (Math.abs(target.getX() - this.manageable.getX()) < aggressionRange){
                    if (Math.abs(target.getY() - this.manageable.getY()) < aggressionRange){
                        this.setTarget(target);
                        return;
                    }
                }
            }
        }
    }

}
