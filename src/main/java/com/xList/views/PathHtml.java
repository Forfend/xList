package com.xList.views;

import com.xList.loger.SaveLogError;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;

import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;

public class PathHtml {

    private String path = "";
    private static PathHtml ourInstance = new PathHtml();
    private SaveLogError show = e -> {
        System.out.println(e);
        System.out.println(LocalDate.now());
    };

    private SaveLogError save = e -> {
        Path file = Paths.get("E:/Project/xList/LogOfError.txt").toAbsolutePath();
        Charset charset = Charset.forName("UTF-8");
        BufferedWriter writer = null;
        try {
            writer= Files.newBufferedWriter(file,charset,APPEND,CREATE);
            String message = e.toString();
            writer.write(message,0,message.length());
            writer.write(LocalDate.now().toString());
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    };

    public static PathHtml getInstance() {
        return ourInstance;
    }

    private PathHtml() {
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPartialHtml(String filename) {
        StringBuilder builder = new StringBuilder();
        Path file = Paths.get(this.path + filename);
        Charset charset = Charset.forName("UTF-8");

        try (BufferedReader reader = Files.newBufferedReader(file, charset)) {
            String line = null;
            while ((line=reader.readLine())!=null){
                builder.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
            show.saveAndShowError(e);
            save.saveAndShowError(e);
        }

        return builder.toString();
    }
}
