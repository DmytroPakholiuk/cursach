package com.cursach.dmytropakholiuk;

import java.util.ArrayList;
import java.util.List;

public interface Exportable {
    public void bindExporter(Exporter exporter);
    public void unbindExporter(Exporter exporter);
    public Exporter exporter = null;
}
