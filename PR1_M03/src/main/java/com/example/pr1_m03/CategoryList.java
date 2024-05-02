package com.example.pr1_m03;

import java.util.List;

public class CategoryList {
    private List<Category> allCategories;

    public CategoryList() {
        CategoryDAO categoryDAO = new CategoryDAOImpl();
        allCategories = categoryDAO.getAllCategories();
    }

    // Resto del código...
}
