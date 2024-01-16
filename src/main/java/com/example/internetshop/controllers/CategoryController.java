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


    @PostMapping("/{categoryID}/{product}")
    public ResponseEntity<?> addProductToCategory (@RequestBody Goods product,
                                                   @PathVariable int categoryID) {
        if (categoryRepository.findById(categoryID).get().getCategoryID() != 0)
            return new ResponseEntity<>("Category of this product " +
                    "is already exist", HttpStatus.BAD_REQUEST);

        if (categoryRepository.findById(categoryID).isEmpty()) {
            return new ResponseEntity<>("Category with such ID " +
                    "is not registered", HttpStatus.NOT_FOUND);
        }
        categoryRepository.findById(categoryID).get().getGoodsList().add(product);
        return new ResponseEntity<>("Product is successfully added " +
                "in this category", HttpStatus.OK);
    }


    @DeleteMapping("/{categoryID}/{product}")
    public ResponseEntity<?> deleteProductFromCategory
            (@RequestBody Goods product, @PathVariable int categoryID) {

        if (categoryRepository.findById(categoryID).isEmpty()) {
            return new ResponseEntity<>("Category with such ID " +
                    "is not registered", HttpStatus.NOT_FOUND);
        }
        categoryRepository.findById(categoryID).get().getGoodsList().remove(product);
        return new ResponseEntity<>("Product is successfully deleted " +
                "from this category", HttpStatus.OK);
    }

    @GetMapping ("")
    public ResponseEntity<?> getAllGoodsInCategory (@RequestParam int categoryID) {
        if (categoryRepository.findById(categoryID).isEmpty()) {
            return new ResponseEntity<>("Category with such ID " +
                    "is not registered", HttpStatus.NOT_FOUND);
        }
        List<Goods> goodsList = categoryRepository.findById(categoryID).
                get().getGoodsList();

        return new ResponseEntity<>(goodsList,HttpStatus.OK);

    }

}
