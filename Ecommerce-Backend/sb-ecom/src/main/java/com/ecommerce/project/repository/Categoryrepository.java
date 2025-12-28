package com.ecommerce.project.repository;

import com.ecommerce.project.model.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Categoryrepository extends JpaRepository<Category,Long> {
    Category findByCategoryName(@NotBlank @Size(min = 5, message="Category Name Must Contain atleast 5 Chars") String categoryName);
}
