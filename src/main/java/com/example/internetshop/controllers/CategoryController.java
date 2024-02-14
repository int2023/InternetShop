package com.example.internetshop.controllers;
import com.example.internetshop.models.Basket;
import com.example.internetshop.models.Category;
import com.example.internetshop.models.Goods;
import com.example.internetshop.repositories.BasketRepository;
import com.example.internetshop.repositories.CategoryRepository;
import com.example.internetshop.repositories.ClientRepository;
import com.example.internetshop.repositories.GoodsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    GoodsRepository goodsRepository;
    @Autowired
    CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @PostMapping
    public ResponseEntity<?> createCategory (@RequestBody Category category) {
        if (categoryRepository.findById(category.getCategoryID()).isPresent()) {
            return new ResponseEntity<>("Category with such ID is " +
                    "already registered", HttpStatus.BAD_REQUEST);
        }
        categoryRepository.save(category);
        return new ResponseEntity<>("Product category is successfully created",
                HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteCategory (@RequestBody Category category) {
        if (categoryRepository.findById(category.getCategoryID()).isEmpty()) {
            return new ResponseEntity<>("Category with such ID does not exist",
                    HttpStatus.BAD_REQUEST);
        }
        categoryRepository.delete(category);
        return new ResponseEntity<>("Product category " +
                "is successfully deleted", HttpStatus.OK);
    }


    @PostMapping("/{categoryID}/goods/{goodID}")
    public ResponseEntity<?> addProductToCategory (@PathVariable int goodID,
                                                   @PathVariable int categoryID) {
        if (categoryRepository.findById(categoryID).isEmpty()) {
            return new ResponseEntity<>("Category with such ID " +
                    "is not registered", HttpStatus.NOT_FOUND);
        }
        if (goodsRepository.findById(goodID).isEmpty()) {
            return new ResponseEntity<>("Product with such ID " +
                    "is not registered", HttpStatus.NOT_FOUND);
        }
        Goods product = goodsRepository.findById(goodID).get();
        Category category = categoryRepository.findById(categoryID).get();
        product.setCategory(category);
        goodsRepository.save(product);

        return new ResponseEntity<>("Product is successfully added " +
                "in this category", HttpStatus.OK);
    }

}



