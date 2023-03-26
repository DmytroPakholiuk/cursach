package com.cursach.dmytropakholiuk;

public class InactivePlasmodium extends Cell implements Cloneable{


    public InactivePlasmodium()
    {
    }

    public InactivePlasmodium clone() throws CloneNotSupportedException
    {
        InactivePlasmodium cloned = (InactivePlasmodium) super.clone();

        return cloned;
    }
}
