package com.xList.Model;


/**
 * Work with database
 */

public abstract class ActiveRecord {

    static final String JDBS_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/xlist?user=root&password=1111";

    public ActiveRecord(){
        try {
            Class.forName(JDBS_DRIVER).newInstance();
        }catch (IllegalAccessException | InstantiationException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }

}
