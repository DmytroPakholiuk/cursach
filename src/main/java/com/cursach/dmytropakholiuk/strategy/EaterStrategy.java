package com.cursach.dmytropakholiuk.strategy;

import com.cursach.dmytropakholiuk.cells.Deployable;

public class EaterStrategy extends MeeleeStrategy{
    UsableStrategies type = UsableStrategies.EATER;
    public double digestTime;


    public void attack(){
        if(target != null){
            if (targetInRange(attackRange)){
                eat(target);
            }
        }
    }

    public void eat( Deployable target){
        target.delete();

    }

    public void execute(){
        super.execute();
        attack();
    }

    public EaterStrategy(double digestTime, double range, Deployable target){
        this.digestTime = digestTime;
        this.aggressionRange = range;
        if (!(target == null)){
            setTarget(target);
        }
        this.attackRange = 30;
    }
    public EaterStrategy(double digestTime, double range){
        this(digestTime, range, null);
    }
    public EaterStrategy(double digestTime){
        this(digestTime, 200, null);
    }
}
