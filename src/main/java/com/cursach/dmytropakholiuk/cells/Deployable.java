package com.cursach.dmytropakholiuk.cells;

import javafx.scene.Group;

/**
 * An attempt to unbind cells from the role of microobject
 */
public interface Deployable {
    double getSpeed();
    Group getGroup();
    void setSpeed(double _speed);
    double getX();
    double getY();
    void setX(double _x);
    void setY(double _y);
    void delete();
    boolean isVisible();
}
