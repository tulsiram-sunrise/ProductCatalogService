package com.ms.productcatalogservice.repositories;

import com.ms.productcatalogservice.models.Category;
import com.ms.productcatalogservice.models.Product;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CategoryRepositoryTest {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryRepositoryTest(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Test
    @Transactional
    public void testFetchTypes() {
        Optional<Category> categoryOptional = categoryRepository.findById(1L);

        if (categoryOptional.isEmpty()) {
            return;
        }

        Category category = categoryOptional.get();
        System.out.println(category);

        for (Product product : category.getProducts()) {
            System.out.println(product);
        }

        /*
        * Conclusion:
        *   If Fetch type is not specified then it is by default LAZY
        *   Fetch Type: LAZY - fetch category and
        *       1 - do not ask for products then it runs 1 SELECT query
        *       2 - ask for products then it runs 2 SELECT query
        *   Fetch Type: EAGER - fetch category and
        *       whether we ask/don't ask for products then it runs single JOIN query
        * */
    }
}