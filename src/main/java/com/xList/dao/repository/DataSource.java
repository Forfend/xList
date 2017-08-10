package com.xList.dao.repository;

import com.xList.loger.SaveLogError;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;

import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;

public class DataSource implements AutoCloseable{

    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/xlist?user=root&password=1111";

    private Connection connection = null;

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

    public DataSource() {
        try {
            Class.forName(JDBC_DRIVER).newInstance();
        }catch (IllegalAccessException | InstantiationException | ClassNotFoundException e){
            e.printStackTrace();
            show.saveAndShowError(e);
            save.saveAndShowError(e);
        }
    }


    public Connection getConnection() {

        try {
            if (connection == null){
                connection = DriverManager.getConnection(DB_URL);
            }
        }catch (SQLException e){
            System.out.println("Error Occured " + e.toString());
            show.saveAndShowError(e);
            save.saveAndShowError(e);
        }
        return connection;
    }

    @Override
    public void close() throws Exception {
        try {
            if (connection!=null)
                connection.close();
        }catch (SQLException e){
            e.printStackTrace();
            show.saveAndShowError(e);
            save.saveAndShowError(e);
        }
    }
}
