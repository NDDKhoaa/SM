package dktech.services.impl;

import dktech.entity.Category;
import dktech.repository.CategoryRepository;
import dktech.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository CategoryRepository;

    @Override
    public Category createCategory(Category Category) {
        return CategoryRepository.save(Category);
    }

    @Override
    public List<Category> getAllCategorys() {
        return CategoryRepository.findAll();
    }

    @Override
    public Category getCategoryById(long id) {
        return CategoryRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteCategory(long id) {
        CategoryRepository.deleteById(id);
    }
    
    @Override
    public Category getCategoryByName(String name) {
        return CategoryRepository.findByCategory(name);
    }
}
