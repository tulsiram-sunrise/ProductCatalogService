package com.ms.productcatalogservice.repositories;

import com.ms.productcatalogservice.models.Category;
import com.ms.productcatalogservice.models.Product;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
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
        *       whether we do/don't ask for products it runs single JOIN query
        * */
    }

    @Test
    @Transactional
    public void testFetchModesAndTypes() {
        Category category = categoryRepository.findById(1L).orElse(null);
        if (category == null) {
            return;
        }

        System.out.println(category);
        for (Product product : category.getProducts()) {
            System.out.println(product);
        }

        /*
        * Observations:
        *   Fetch Type              Fetch Mode              Ask for product             Result
        *   LAZY                    SELECT                  Don't ASK                   1 SELECT Query
        *   LAZY                    SELECT                  ASK                         2 SELECT Query
        *   EAGER                   SELECT                  Don't ASK                   2 SELECT Query
        *   EAGER                   SELECT                  ASK                         2 SELECT Query
        *
        *   LAZY                    JOIN                    Don't ASK                   1 JOIN Query
        *   LAZY                    JOIN                    ASK                         1 JOIN Query
        *   EAGER                   JOIN                    Don't ASK                   1 JOIN Query
        *   EAGER                   JOIN                    ASK                         1 JOIN Query
        *
        *   LAZY                    SUB SELECT              Don't ASK                   1 SELECT Query
        *   LAZY                    SUB SELECT              ASK                         2 SELECT Query
        *   EAGER                   SUB SELECT              Don't ASK                   2 SELECT Query
        *   EAGER                   SUB SELECT              ASK                         2 SELECT Query
        *
        * */
    }

    @Test
    @Transactional
    public void runSubQueries() {
        List<Category> categories = categoryRepository.findAll();
        for (Category category : categories) {
            System.out.println(category);
            for (Product product : category.getProducts()) {
                System.out.println(product);
            }
        }


        /*
         * Observations:
         *   Fetch Type              Fetch Mode              Ask for product             Result
         *   LAZY                    SELECT                  Don't ASK                   1 SELECT Query
         *   LAZY                    SELECT                  ASK                         1 SELECT Category + multiple SELECT Product
         *   EAGER                   SELECT                  Don't ASK                   1 SELECT Category + multiple SELECT Product
         *   EAGER                   SELECT                  ASK                         1 SELECT Category + multiple SELECT Product
         *
         *   LAZY                    JOIN                    Don't ASK                   1 SELECT Category + multiple SELECT Product
         *   LAZY                    JOIN                    ASK                         1 SELECT Category + multiple SELECT Product
         *   EAGER                   JOIN                    Don't ASK                   1 SELECT Category + multiple SELECT Product
         *   EAGER                   JOIN                    ASK                         1 SELECT Category + multiple SELECT Product
         *
         *   LAZY                    SUB SELECT              Don't ASK                   1 SELECT Query
         *   LAZY                    SUB SELECT              ASK                         2 SELECT Query + 1 Sub Query
         *   EAGER                   SUB SELECT              Don't ASK                   2 SELECT Query + 1 Sub Query
         *   EAGER                   SUB SELECT              ASK                         2 SELECT Query + 1 Sub Query
         *
         * */
    }
}