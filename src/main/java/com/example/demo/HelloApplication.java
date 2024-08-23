package com.example.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.DataFormat;
import javafx.stage.Stage;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.IOException;
import java.util.Date;
import java.util.HexFormat;
import java.util.Objects;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("工具箱");
        stage.setScene(scene);
        stage.show();
    }

    public static String getNexHexGuid(Long guid) {
        String format = "%05X";//限制5位16进制(换算十进制最大1048575),超过也不会报错，会变成6位
        if (Objects.isNull(guid)) {
            guid = 0L;
        }
        return String.format(format, ++guid);
    }

    public static void main(String[] args) {
        launch();
    }
}