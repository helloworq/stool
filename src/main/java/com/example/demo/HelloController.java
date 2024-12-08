package com.example.demo;

import cn.hutool.http.HttpUtil;
import com.github.vertical_blank.sqlformatter.core.FormatConfig;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.ToNumberPolicy;
import com.jayway.jsonpath.JsonPath;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import com.github.vertical_blank.sqlformatter.SqlFormatter;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HelloController {
    public Button button4;
    @FXML
    private Button button;
    @FXML
    private Button button2;
    @FXML
    private Button button3;
    @FXML
    private Button extract;
    @FXML
    private Button diff;
    @FXML
    private TextField textField;
    @FXML
    private TextArea leftTextArea;
    @FXML
    private TextArea rightTextArea;

    public static final String DesktopPath = System.getProperty("user.home") + "\\Desktop\\";

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

    @FXML
    public void jsonPath() {
        String json = leftTextArea.getText();
        List<Object> list = JsonPath.read(json, textField.getText());
        rightTextArea.setText(new Gson().toJson(list));
    }

    @FXML
    public void diff() {
        String left = leftTextArea.getText();
        String right = rightTextArea.getText();

        List<String> leftList = new ArrayList<>(List.of(left.split("\n")));
        List<String> rightList = new GsonBuilder()
                .setObjectToNumberStrategy(ToNumberPolicy.LAZILY_PARSED_NUMBER)
                .create().fromJson(right, List.class)
                .stream().map(e -> e.toString()).toList();
        //去除两个list中相同的部分
        List<String> restLeft = leftList.stream().filter(e -> !rightList.contains(e))
                .map(Object::toString).toList();
        List<String> restRight = rightList.stream().filter(e -> !leftList.contains(e))
                .map(Object::toString).toList();

        leftTextArea.setText(String.join(",", restLeft));
        rightTextArea.setText(String.join(",", restRight));
    }

    public static void main(String[] args) {
        String url = "https://v.cdnlz3.com/20241206/30539_d819a37d/2000k/hls/mixed.m3u8";//21

        System.out.println(HttpUtil.get(url));
    }

    public void downloadM3u8() throws IOException {
        new Thread(() -> {
            try {
                String url = leftTextArea.getText();
                long now = System.currentTimeMillis();
                String basePath = DesktopPath + "m3u8\\";
                String curPath = basePath + now + File.separator;
                new File(basePath).mkdirs();
                new File(curPath).mkdirs();

                String urlPrefix = url.substring(0, url.lastIndexOf("/") + 1);
                String r = HttpUtil.get(url);
                List<String> urls = Stream.of(r.split("\n"))
                        .filter(e -> !e.startsWith("#") && e.length() < 22)
                        .toList();

                List<String> saveList = new ArrayList<>();
                AtomicInteger counter = new AtomicInteger(0);
                urls.stream().parallel().forEach(u -> {
                    String savePath = curPath + u;
                    HttpUtil.downloadFile(urlPrefix + u, savePath);
                    saveList.add("file \'" + savePath + "\'");

                    Platform.runLater(() -> rightTextArea.setText(counter.getAndIncrement() + " / " + (urls.size() - 1)));
                });

                saveList.sort(Comparator.comparing(e -> e));
                Files.writeString(Paths.get(curPath + now + ".txt"),
                        String.join("\n", saveList), StandardOpenOption.CREATE_NEW);

                String order = basePath + "ffmpeg.exe -f concat -safe 0 -i " + curPath + now + ".txt -c copy " + curPath + now + ".mp4";
                Runtime.getRuntime().exec(order);
                Platform.runLater(() -> rightTextArea.setText(urls.size() + " 分片下载完毕，正在合并视频"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void bytes2File() throws IOException {
        String json = leftTextArea.getText();
        String bytes = JsonPath.read(json, "data.bytes");
        OutputStream out = Files.newOutputStream(Paths.get("C:\\Users\\zhoudashuai\\Desktop\\" +
                        System.currentTimeMillis() + ".xlsx"),
                StandardOpenOption.CREATE_NEW);
        out.write(Base64.getDecoder().decode(bytes));
        out.close();
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