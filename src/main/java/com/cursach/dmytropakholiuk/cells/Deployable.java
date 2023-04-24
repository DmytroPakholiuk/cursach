package com.cursach.dmytropakholiuk.cells;

public interface Deployable {
    double getSpeed();
    void setSpeed(double _speed);
    double getX();
    double getY();
    void setX(double _x);
    void setY(double _y);
    void delete();
    boolean isVisible();
}
