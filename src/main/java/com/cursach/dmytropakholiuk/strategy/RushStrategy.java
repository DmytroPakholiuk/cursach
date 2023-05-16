package com.cursach.dmytropakholiuk.strategy;

import com.cursach.dmytropakholiuk.cells.Cell;
import com.cursach.dmytropakholiuk.cells.InactivePlasmodium;
import com.cursach.dmytropakholiuk.organs.Organ;
import javafx.application.Platform;
import javafx.concurrent.Task;

public class RushStrategy extends TransportStrategy{

    private static StrategyManageable buffer;
    public void execute(){
        super.execute();
//        if (this.isSleeping()){
//
//        }
        if (manageable.getGroup().getBoundsInParent().intersects(target.getGroup().getBoundsInParent())){
            if (manageable.isVisible()){

                this.approachTarget();
                this.approachTarget();
                this.approachTarget();
                manageable.enterOrgan();

                System.out.println("isVisible");

                if (!this.isSleeping()){
                    System.out.println("PUTTING TO SLEEP");
                    Task<Void> metamorphose = new Task<Void>() {
                        @Override
                        protected Void call() throws Exception {
//                            System.out.println("IN METAMORPHOSIS");
                            RushStrategy.buffer = manageable;
//                            Platform.runLater(() -> {
//                                System.out.println("CYCLING 11");
//                                ((InactivePlasmodium)RushStrategy.buffer).quitOrgan();
//                                System.out.println("CYCLING 22");});
                            RushStrategy.buffer.quitOrgan();
                            ((InactivePlasmodium)RushStrategy.buffer).cycleStage();

//                            ((InactivePlasmodium)buffer).cycleStage(() -> {
//                                System.out.println("CYCLING 11");
//                                ((InactivePlasmodium)RushStrategy.buffer).quitOrgan();
//                                System.out.println("CYCLING 22");
//                            });
//                            ((Cell)buffer).quitOrgan();

                            System.out.println("IN METAMORPHOSIS");

//                        ((Cell)manageable).quitOrgan();
//                        ((InactivePlasmodium)manageable).bi();

//                        ((InactivePlasmodium)target).quitOrgan();
//                            System.out.println("IN METAMORPHOSIS");

                            return null;
                        }
                    };

                    this.sleep(4, metamorphose);
                }
            }
//            System.out.println(manageable);
//            System.out.println(((InactivePlasmodium)manageable).getStage());
//            System.out.println(this);
        }
    }

    public Runnable callback;

    public RushStrategy(Organ where, Runnable callback){
        this.target = where;
        this.callback = callback;
    }
}
