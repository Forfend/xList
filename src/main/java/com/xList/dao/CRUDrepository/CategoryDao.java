package com.xList.dao.CRUDrepository;

import com.xList.dao.entities.Category;

import java.util.List;


public interface CategoryDao {

    void addCategory(Category category);

    void deleteCategory(long id);

    Category getCategory(long id, long user_id, long note_id);

    List<Category> getAllCategory(long user_id);

}
