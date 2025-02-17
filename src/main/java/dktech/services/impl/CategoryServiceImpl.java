package dktech.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dktech.entity.Category;
import dktech.repository.CategoryRepository;
import dktech.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Optional<Category> getCategoryById(long id) {
        return categoryRepository.findById(id);
    }

    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    public Category updateCategory(long id, Category categoryDetails) {
        Category category = categoryRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Category not found"));

        category.setCategory(categoryDetails.getCategory());
        category.setCreatedDate(categoryDetails.getCreatedDate());

        return categoryRepository.save(category);
    }

    public void deleteCategory(long id) {
        categoryRepository.deleteById(id);
    }
}
