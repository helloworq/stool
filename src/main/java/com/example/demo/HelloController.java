package com.example.demo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;

public class HelloController {
    @FXML
    private Button button;
    @FXML
    private Button button2;
    @FXML
    private Button button3;
    @FXML
    private TextArea leftTextArea;
    @FXML
    private TextArea rightTextArea;

    @FXML
    public void beautyJson(MouseEvent mouseEvent) {
        String text = leftTextArea.getText();
        try {
            leftTextArea.setText(toPrettyFormat(text));
        } catch (Exception e) {
            leftTextArea.setText(e.toString());
        }
    }

    @FXML
    public void removeUnnecessaryChars(MouseEvent mouseEvent) {
        String text = leftTextArea.getText();
        try {
            leftTextArea.setText(text.replace("\n", "")
                    .replace("\t", "")
                    .replace(" ", ""));
        } catch (Exception e) {
            leftTextArea.setText(e.toString());
        }
    }

    @FXML
    public void test(MouseEvent mouseEvent) {
        String text = leftTextArea.getText();
        try {
            leftTextArea.setText(text.replaceAll("[\r\n]+", " "));
        } catch (Exception e) {
            leftTextArea.setText(e.toString());
        }
    }

    /**
     * 格式化输出JSON字符串
     *
     * @return 格式化后的JSON字符串
     */
    private static String toPrettyFormat(String json) {
        JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(jsonObject);
    }
}