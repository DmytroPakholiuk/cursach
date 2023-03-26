package com.cursach.dmytropakholiuk;

import java.util.ArrayList;
import java.util.List;

public interface Exporter {
    public String exportObjectAsString(Exportable _exp);
    public void bindExportable(Exportable exportable);
    public void unbindExportable(Exportable exportable);
    public List<Exportable> boundObjects = new ArrayList<>();
}
