package com.cursach.dmytropakholiuk.export;

public interface Adaptable {
    public Adapted adaptToExport();
    public String adaptAndExportAsString();
    public void adaptAndExport();
}
