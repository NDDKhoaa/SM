package dktech.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dktech.entity.Category;


public interface CategoryRepository extends JpaRepository<Category, Long> {
	Category findByCategory(String category);
}
