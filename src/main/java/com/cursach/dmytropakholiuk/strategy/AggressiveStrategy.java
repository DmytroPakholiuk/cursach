package com.cursach.dmytropakholiuk.strategy;

import com.cursach.dmytropakholiuk.Application;
import com.cursach.dmytropakholiuk.cells.Cell;
import com.cursach.dmytropakholiuk.cells.CellType;
import com.cursach.dmytropakholiuk.cells.Deployable;

import java.util.ArrayList;
import java.util.List;

public abstract class AggressiveStrategy extends ActiveStrategy{

    public double aggressionRange;
    public double attackRange;
    public boolean targetInRange(Deployable _target, double range){
        if (_target != null){
            if (Math.abs(_target.getX() - this.manageable.getX()) < range){
                if (Math.abs(_target.getY() - this.manageable.getY()) < range){
                    return true;
                }
            }
        }

        return false;
    }
    public abstract void attack();
    public List<CellType> allowedTargets = new ArrayList<>();
    public boolean isHostile(Cell deployable){
        return allowedTargets.contains(Cell.getType(deployable));
    }
    public void chooseTarget(){
        for (Cell target: Application.cells){
            if (isHostile(target)){
                if (targetInRange(target, aggressionRange)){
                    this.setTarget(target);
                    return;
                }
            }
        }
    }

    @Override
    public void execute() {
        super.execute();
        if (target == null){
            chooseTarget();
        }
    }


}
