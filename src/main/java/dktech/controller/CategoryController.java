package dktech.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import dktech.entity.Category;
import dktech.services.CategoryService;

@RestController
@RequestMapping("/api/categories") // Make sure this path is correct
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    // Get all categories
    @GetMapping
    public ResponseEntity<List<Category>> getCategories() {
        List<Category> categories = categoryService.getAllCategorys();
        return categories.isEmpty() 
            ? ResponseEntity.noContent().build() 
            : ResponseEntity.ok(categories);
    }

    // Get category by ID
    @GetMapping("/{categoryID}")
    public ResponseEntity<Category> getCategoryById(@PathVariable long categoryID) {
        Optional<Category> category = Optional.ofNullable(categoryService.getCategoryById(categoryID));
        return category.map(ResponseEntity::ok)
                       .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                                                      .body(null));
    }

    // Add a new category
    @PostMapping
    public ResponseEntity<Category> addCategory(@RequestBody Map<String, Object> payload) {
        try {
            String categoryName = payload.get("category").toString();
            LocalDate createdDate = LocalDate.parse(payload.get("createdDate").toString());

            // Create a new Category object
            Category category = new Category(categoryName, createdDate);

            // Save the new category using the service
            Category createdCategory = categoryService.createCategory(category);

            return ResponseEntity.status(HttpStatus.CREATED).body(createdCategory);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body(null); // Return 400 in case of error
        }
    }

    // Update an existing category
    @PutMapping("/{categoryID}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long categoryID, @RequestBody Category category) {
        Optional<Category> existingCategoryOptional = Optional.ofNullable(categoryService.getCategoryById(categoryID));
        
        if (existingCategoryOptional.isPresent()) {
            Category existingCategory = existingCategoryOptional.get();
            existingCategory.setCategory(category.getCategory());
            existingCategory.setCreatedDate(category.getCreatedDate());

            // Save and return the updated category
            Category updatedCategory = categoryService.createCategory(existingCategory);
            return ResponseEntity.ok(updatedCategory);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Return 404 if category not found
        }
    }

    // Delete a category
    @DeleteMapping("/{categoryID}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long categoryID) {
        Optional<Category> categoryOptional = Optional.ofNullable(categoryService.getCategoryById(categoryID));
        
        if (categoryOptional.isPresent()) {
            categoryService.deleteCategory(categoryID);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
