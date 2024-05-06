package com.example.pr1_m03;

import java.util.List;

public class CategoryList {
    private List<Category> allCategories;

    public CategoryList() {
        loadCategoriesFromDatabase();
    }

    private void loadCategoriesFromDatabase() {
        CategoryDAO categoryDAO = new CategoryDAOImpl();
        allCategories = categoryDAO.getAllCategories();
    }

    public List<Category> getAllCategories() {
        return allCategories;
    }
}
