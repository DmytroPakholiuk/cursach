module com.example.demo1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
//    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires com.fasterxml.jackson.databind;

    opens com.cursach.dmytropakholiuk to javafx.fxml;
    exports com.cursach.dmytropakholiuk;
    exports com.cursach.dmytropakholiuk.export;
    opens com.cursach.dmytropakholiuk.export to javafx.fxml;
}