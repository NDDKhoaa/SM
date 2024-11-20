package dktech.services;

import dktech.entity.Category;
import java.util.List;

public interface CategoryService {
    Category createCategory(Category Category);
    List<Category> getAllCategorys();
    Category getCategoryById(long id);
    void deleteCategory(long id);
    public Category getCategoryByName(String name);
}
