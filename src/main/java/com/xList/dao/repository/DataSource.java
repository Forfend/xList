package com.xList.dao.repository;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataSource implements AutoCloseable{

    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/xlist?user=root&password=888641wd";

    private Connection connection = null;


    public DataSource() {
        try {
            Class.forName(JDBC_DRIVER).newInstance();
        }catch (IllegalAccessException | InstantiationException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }


    public Connection getConnection() {

        try {
            if (connection == null){
                connection = DriverManager.getConnection(DB_URL);
            }
        }catch (SQLException e){
            System.out.println("Error Occured " + e.toString());
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
        }
    }
}
