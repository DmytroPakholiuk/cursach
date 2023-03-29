package com.cursach.dmytropakholiuk.export;

public interface Exportable {
    public void bindExporter(Exporter exporter);
    public void unbindExporter(Exporter exporter);
    public Exporter exporter = null;
}
