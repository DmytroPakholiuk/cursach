package com.cursach.dmytropakholiuk.strategy;

import com.cursach.dmytropakholiuk.cells.CellType;
import com.cursach.dmytropakholiuk.cells.Deployable;

public class EaterStrategy extends MeeleeStrategy{
    UsableStrategies type = UsableStrategies.EATER;
    public double digestTime;


    public void attack(){
        if(target != null){
            if (targetInRange(target, attackRange)){
                eat(target);
            }
        }
    }

    public void eat( Deployable target){
        target.delete();
        this.setTarget(null);
        this.sleep(7, null);
    }

    public void execute(){
        if (manageable.isVisible()){
            super.execute();
            attack();
        }
    }

    public EaterStrategy(double digestTime, double range, Deployable target){
        this.digestTime = digestTime;
        this.aggressionRange = range;
        if (!(target == null)){
            setTarget(target);
        }
        this.attackRange = 30;

        this.allowedTargets.add(CellType.HIV_PLASMODIUM);
        this.allowedTargets.add(CellType.INACTIVE_PLASMODIUM);
        this.allowedTargets.add(CellType.PLASMODIUM_VIVAX);

    }
    public EaterStrategy(double digestTime, double range){
        this(digestTime, range, null);
    }
    public EaterStrategy(double digestTime){
        this(digestTime, 200, null);
    }
}
