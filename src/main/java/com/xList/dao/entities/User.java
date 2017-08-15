package com.xList.dao.entities;

public class User {

    private long id;
    private String username;
    private String password;
    private String name;
    

    public boolean checkLogin() {
        findByUsername(loginUserName);
        if(getUsername() == null) return false;
        if (getPassword().equals(loginPassword))
            return true;
        return false;
    }

=======
>>>>>>> dev:src/main/java/com/xList/dao/entities/User.java
    public User() {
    }

    public User(long id, String username, String password, String name) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
    }

<<<<<<< HEAD:src/main/java/com/xList/Model/User.java
    private boolean findByUsername(String username) {
        try (Connection connection = DriverManager.getConnection(DB_URL);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT id,username,password,name FROM user WHERE user.username=\"" + username + "\"");
        ){
            if (resultSet.next()) {
                this.id = resultSet.getInt(1);
                this.username = resultSet.getString(2);
                this.password = resultSet.getString(3);
                this.name = resultSet.getString(4);
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public String getLoginUserName() {
        return loginUserName;
    }

    public void setLoginUserName(String loginUserName) {
        this.loginUserName = loginUserName;
    }

    public String getLoginPassword() {
        return loginPassword;
    }

    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
=======
    public User(String username, String password, String name) {
        this.id = 0L;
        this.username = username;
        this.password = password;
        this.name = name;
>>>>>>> dev:src/main/java/com/xList/dao/entities/User.java
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != user.id) return false;
        if (username != null ? !username.equals(user.username) : user.username != null) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        return name != null ? name.equals(user.name) : user.name == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
