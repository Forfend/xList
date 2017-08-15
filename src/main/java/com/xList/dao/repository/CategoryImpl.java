package com.xList.dao.repository;

import com.xList.dao.CRUDrepository.CategoryDao;
import com.xList.dao.entities.Category;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;


public class CategoryImpl implements CategoryDao {
    @Override
    public void addCategory(Category category) {

        DataSource source = new DataSource();

        try (Connection connection = source.getConnection();
             PreparedStatement statement = ((category.getId() == 0L) ? connection.prepareStatement("INSERT INTO `categories`(`user_id`,`category_name`) VALUES (?,?)") :
                     connection.prepareStatement("UPDATE categories SET user_id=?,category_name=? WHERE id=" + category.getId()))) {
            statement.setLong(1, category.getUser_id());
            statement.setString(2, category.getCategory_name());

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void deleteCategory(long id) {

        DataSource source = new DataSource();

        if (id > 0L) {
            try (Connection connection = source.getConnection();
                 Statement statement = connection.createStatement()) {

                statement.executeUpdate("DELETE FROM categories WHERE id=?" + id);

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public Category getCategory(long id, long user_id) {
        DataSource source=new DataSource();

        try (Connection connection=source.getConnection();
             Statement statement=connection.createStatement();
             ResultSet set = statement.executeQuery("SELECT * FROM categories WHERE user_id=" + user_id + " AND id=" + id)){
            if (set.next()){
                Long idLocal=set.getLong("id");
                String category_name =set.getString("category_name");

                Category  category = new Category(idLocal.longValue(),user_id, category_name);

                return category;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Category> getAllCategory(long user_id) {
        DataSource source = new DataSource();
        List<Category> categories = new ArrayList<>();

        try (Connection connection=source.getConnection();
             Statement statement=connection.createStatement();
             ResultSet set=statement.executeQuery("SELECT * FROM categories WHERE user_id=\""+user_id+"\";")){
            while (set.next()){
                Long idLocal=set.getLong("id");
                String category_name = set.getString("category_name");

                Category category=new Category(idLocal.longValue(), user_id, category_name);

                categories.add(category);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        return categories;
    }
}
