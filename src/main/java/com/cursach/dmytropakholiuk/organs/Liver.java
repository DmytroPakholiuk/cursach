package com.cursach.dmytropakholiuk.organs;

import com.cursach.dmytropakholiuk.cells.Cell;

public class Liver extends Organ{

    @Override
    public boolean canEnter(Cell cell) {
        return false;
    }
}
