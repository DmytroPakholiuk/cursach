package com.cursach.dmytropakholiuk.export;

import com.cursach.dmytropakholiuk.export.Exportable;

import java.util.ArrayList;
import java.util.List;

public interface Exporter {
    public Exportable importObjectFromString(String s);
    public String exportObjectAsString(Exportable _exp);
    public void bindExportable(Exportable exportable);
    public void unbindExportable(Exportable exportable);
    public List<Exportable> boundObjects = new ArrayList<>();
}
