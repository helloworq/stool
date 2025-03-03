module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires javafx.media;
    requires java.sql;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    requires com.google.gson;
    requires org.apache.commons.text;
    //requires org.apache.commons.lang3;
    opens com.example.demo to javafx.fxml;
    requires sql.formatter;
    requires json.path;
    requires hutool.all;
    requires jdk.jsobject;
    exports com.example.demo;
    exports com.example.demo.javafx;
    exports com.example.demo.javafx.webview;
    exports com.example.demo.javafx.treeview;
    exports com.example.demo.javafx.treetableview;
    exports com.example.demo.javafx.transformations;
    exports com.example.demo.javafx.tabpane;
    exports com.example.demo.javafx.gfx3d;
    exports com.example.demo.javafx.colorpicker;
    exports com.example.demo.javafx.canvas;
    exports com.example.demo.javafx.animation;
    exports com.example.demo.javafx.concurrency;
    exports com.example.demo.javafx.filechooser;
    exports com.example.demo.javafx.htmleditor;
    exports com.example.demo.javafx.media;
    exports com.example.demo.javafx.imageview;
    exports com.example.demo.javafx.scene;
    exports com.example.demo.javafx.progressbar;
    exports com.example.demo.javafx.stage;
}