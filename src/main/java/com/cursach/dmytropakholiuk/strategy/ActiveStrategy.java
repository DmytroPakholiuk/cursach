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


}
