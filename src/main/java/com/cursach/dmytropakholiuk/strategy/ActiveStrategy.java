package com.cursach.dmytropakholiuk.strategy;

import com.cursach.dmytropakholiuk.Application;
import com.cursach.dmytropakholiuk.cells.Deployable;

public abstract class ActiveStrategy extends Strategy{
    protected Deployable target;

    public void setTarget(Deployable target) {
        this.target = target;
    }

    public Deployable getTarget() {
        return target;
    }

    public void approachTarget(){
        if (!(target == null)){
            if (target.getX() > manageable.getX()){
                manageable.setX(manageable.getX() + manageable.getSpeed());
            }
            if (target.getX() < manageable.getX()){
                manageable.setX(manageable.getX() + manageable.getSpeed());
            }
            if (target.getY() > manageable.getY()){
                manageable.setY(manageable.getY() + manageable.getSpeed());
            }
            if (target.getY() < manageable.getY()){
                manageable.setY(manageable.getY() + manageable.getSpeed());
            }
        }
    }
    @Override
    public void execute(){
        approachTarget();
    }


}
