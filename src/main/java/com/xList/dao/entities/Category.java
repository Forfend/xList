package com.xList.dao.entities;


public class Category {

    private long id;

    private long user_id;

    private String category_name;

    private long note_id;

    public Category() {
    }

    public Category(long user_id, String category_name, long note_id) {
        this.user_id = user_id;
        this.category_name = category_name;
        this.note_id = note_id;
    }

    public Category(long id, long user_id, String category_name, long note_id) {
        this.id = id;
        this.user_id = user_id;
        this.category_name = category_name;
        this.note_id = note_id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public long getNote_id() {
        return note_id;
    }

    public void setNote_id(long note_id) {
        this.note_id = note_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Category category = (Category) o;

        if (id != category.id) return false;
        if (user_id != category.user_id) return false;
        if (note_id != category.note_id) return false;
        return category_name != null ? category_name.equals(category.category_name) : category.category_name == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (user_id ^ (user_id >>> 32));
        result = 31 * result + (category_name != null ? category_name.hashCode() : 0);
        result = 31 * result + (int) (note_id ^ (note_id >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", user_id=" + user_id +
                ", category_name='" + category_name + '\'' +
                ", note_id=" + note_id +
                '}';
    }
}
