package com.cursach.dmytropakholiuk;

import javafx.scene.image.Image;

public class RedBloodCell extends Cell implements Cloneable{


    public RedBloodCell()
    {
        this.image = new Image("src/main/resources/com/example/demo1/rbc.png");
    }

    public RedBloodCell clone() throws CloneNotSupportedException
    {
        RedBloodCell cloned = (RedBloodCell) super.clone();

        return cloned;
    }


}
