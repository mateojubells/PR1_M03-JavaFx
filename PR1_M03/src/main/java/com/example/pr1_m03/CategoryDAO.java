package com.example.pr1_m03;

import java.util.List;

public interface CategoryDAO {
    // Operaciones CRUD
    void insertCategory(String categoryName);
    List<Category> getAllCategories();
    void updateCategory(Category category);
    void deleteCategory(Category category);
}
