package com.cursach.dmytropakholiuk.strategy;

import com.cursach.dmytropakholiuk.cells.Deployable;

import java.util.ArrayList;
import java.util.List;

public interface StrategyManageable extends Deployable {

    public void setStrategy(Strategy strategy);
    public void setDefaultStrategy();
    public void enterOrgan();
    public void quitOrgan();
//    public void move(Strategy.Direction direction);
}
