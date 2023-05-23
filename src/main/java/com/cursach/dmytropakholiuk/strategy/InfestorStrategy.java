package com.cursach.dmytropakholiuk.strategy;

import com.cursach.dmytropakholiuk.cells.CellType;
import com.cursach.dmytropakholiuk.cells.Deployable;
import com.cursach.dmytropakholiuk.cells.InactivePlasmodium;
import com.cursach.dmytropakholiuk.cells.RedBloodCell;
import javafx.concurrent.Task;

import javax.xml.transform.Source;
import java.util.ConcurrentModificationException;

public class InfestorStrategy extends MeeleeStrategy{
    public void execute(){
        super.execute();
        if (manageable.isVisible()){

            attack();
        }
    }

    public void attack(){
        if(target != null){
            if (targetInRange(target, attackRange)){
                infest(target);
            }
        }
    }

    public void infest(Deployable infested){

        if (!this.isSleeping()){
            System.out.println("PUTTING TO SLEEP");
            Task<Void> metamorphose = new Task<Void>() {
                @Override
                protected Void call() throws Exception {

//                    try {
                        System.out.println("BEFORE METAMORPHOSIS");
                        Strategy.buffer = manageable;
                        ((RedBloodCell)target).deinfest();
                        System.out.println("DELETING HOST");
                        target.delete();
//                    }
//                    catch (ConcurrentModificationException c){
                        target = null;
                        System.out.println("RIGHT BEFORE");
                        ((InactivePlasmodium)Strategy.buffer).cycleStage();
                        System.out.println("IN METAMORPHOSIS");
//                    } catch (Exception e){
//                        e.printStackTrace();
//                    }



                    return null;
                }
            };

            ((RedBloodCell)target).infestWith(((InactivePlasmodium)this.manageable));
            this.sleep(4, metamorphose);
        }

    }

    public InfestorStrategy(double range, Deployable target){
        this.aggressionRange = range;
        if (!(target == null)){
            setTarget(target);
        }
        this.attackRange = 30;

        this.allowedTargets.add(CellType.RED_BLOOD_CELL);

    }

    public InfestorStrategy(){
        this(300, null);
    }
}
