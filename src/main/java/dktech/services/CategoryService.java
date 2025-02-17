package dktech.services;

import java.util.List;
import java.util.Optional;

import dktech.entity.Category;

public interface CategoryService {
    List<Category> getAllCategories();
    Optional<Category> getCategoryById(long id);
    Category saveCategory(Category category);
    Category updateCategory(long id, Category categoryDetails);
    void deleteCategory(long id);
}
