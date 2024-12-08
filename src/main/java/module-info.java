module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    requires com.google.gson;
    requires org.apache.commons.text;
    requires org.apache.commons.lang3;
    opens com.example.demo to javafx.fxml;
    requires sql.formatter;
    requires json.path;
    requires hutool.all;
    exports com.example.demo;
}