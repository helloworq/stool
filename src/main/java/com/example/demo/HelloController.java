package com.example.demo;

import com.github.vertical_blank.sqlformatter.core.FormatConfig;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import com.github.vertical_blank.sqlformatter.SqlFormatter;

import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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
            rightTextArea.setText(toPrettyFormat(text));
        } catch (Exception e) {
            rightTextArea.setText(e.toString());
        }
    }

    @FXML
    public void removeUnnecessaryChars(MouseEvent mouseEvent) {
        String text = leftTextArea.getText();
        try {
            rightTextArea.setText(text.replace("\n", "")
                    .replace("\t", "")
                    .replace(" ", ""));
        } catch (Exception e) {
            rightTextArea.setText(e.toString());
        }
    }

    //idea中ctrl shift j即可，用完后ctrl z回退
    @FXML
    public void beautySql(MouseEvent mouseEvent) {
        String text = leftTextArea.getText();
        try {
            text = text.replace("\n", " ").replace("\t", " ");
            if (text.charAt(0) == '\"') {
                String patternString = "\\\"(.+?)\\\"";
                Pattern pattern = Pattern.compile(patternString);
                Matcher matcher = pattern.matcher(text);

                text = matcher.results().map(MatchResult::group)
                        .map(e -> e.replace("\"", ""))
                        .collect(Collectors.joining(" "));
            }

            text = SqlFormatter.format(text, FormatConfig.builder()
                    .uppercase(false)
                    .build());
            rightTextArea.setText(text);
        } catch (Exception e) {
            rightTextArea.setText(e.toString());
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