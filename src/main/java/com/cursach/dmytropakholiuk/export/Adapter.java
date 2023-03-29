package com.cursach.dmytropakholiuk.export;

import com.cursach.dmytropakholiuk.*;

public class Adapter {

    private static Adapter instance = null;
    public static Adapter getInstance() {
        if (instance == null){
            instance = new Adapter();
        }
        return instance;
    }
    private Adapter(){

    }
    private CellType type;
    private void detectCellType(Adaptable adaptable){
        if (adaptable instanceof WhiteBloodCell){
            type = CellType.WHITE_BLOOD_CELL;
        } else if (adaptable instanceof InactivePlasmodium) {
            type = CellType.INACTIVE_PLASMODIUM;
        } else if (adaptable instanceof RedBloodCell) {
            type = CellType.RED_BLOOD_CELL;
        }
    }

    public Adapted adapt(Adaptable adaptable) throws Exception{
        if (adaptable instanceof Cell){
            detectCellType(adaptable);
            Adapted adapted = createAdaptable();
            adapted = configureAdaptedCell(adapted, adaptable);

            switch (type){
                case WHITE_BLOOD_CELL:
                    return configureAdaptedWBC(adapted, adaptable);
                case RED_BLOOD_CELL:
                    return configureAdaptedRBC(adapted, adaptable);
                case INACTIVE_PLASMODIUM:
                    return configureAdaptedPlas(adapted, adaptable);
            }
        }
        throw new Exception("the adaptable could not be adapted");
    }


    private Adapted createAdaptable() {
        Adapted adapted = null;
        switch (type){
            case WHITE_BLOOD_CELL:
                adapted = new AdaptedWhiteBloodCell(); break;
            case RED_BLOOD_CELL:
                adapted = new AdaptedRedBloodCell(); break;
            case INACTIVE_PLASMODIUM:
                adapted = new AdaptedPlasmodium(); break;
        }
        return adapted;
    }
    private AdaptedCell configureAdaptedCell(Adapted adapted, Adaptable adaptable){
        AdaptedCell adaptedCell =  (AdaptedCell)adapted;
        adaptedCell.x = ((Cell) adaptable).getX();
        adaptedCell.y = ((Cell) adaptable).getY();
        adaptedCell.name = ((Cell) adaptable).getName();
        adaptedCell.step = ((Cell) adaptable).getStep();

        return adaptedCell;
    }
    private AdaptedWhiteBloodCell configureAdaptedWBC(Adapted adapted, Adaptable adaptable){
        AdaptedWhiteBloodCell adaptedWhiteBloodCell = (AdaptedWhiteBloodCell) adapted;
        adaptedWhiteBloodCell.digestTime = ((WhiteBloodCell)adaptable).getDigestTime();

        return adaptedWhiteBloodCell;
    }
    private Adapted configureAdaptedRBC(Adapted adapted, Adaptable adaptable) {
        AdaptedRedBloodCell adaptedRedBloodCell = (AdaptedRedBloodCell) adapted;

        return adaptedRedBloodCell;
    }
    private Adapted configureAdaptedPlas(Adapted adapted, Adaptable adaptable) {
        AdaptedPlasmodium adaptedPlasmodium = (AdaptedPlasmodium) adapted;

        return adaptedPlasmodium;
    }

}
