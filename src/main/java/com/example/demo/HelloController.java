package com.example.demo;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TouchEvent;

public class HelloController {
    @FXML
    private Button button1;

    @FXML
    public void click(MouseEvent mouseEvent) {
        System.out.println("aaa");
    }

//
//    @FXML
//    public void buttonEvent(KeyEvent keyEvent) {
//        System.out.println("aaa");
//    }
}