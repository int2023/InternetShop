package com.example.internetshop.controllers;
import com.example.internetshop.models.Goods;
import com.example.internetshop.repositories.GoodsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/goods")
public class GoodsController {
    GoodsRepository goodsRepository;
    public GoodsController(GoodsRepository goodsRepository) {
        this.goodsRepository = goodsRepository;
    }

    @PostMapping
    public ResponseEntity<?> addGoods(@RequestBody Goods product) {
        int productId = product.getGoodID();
        Optional<Goods> byId = goodsRepository.findById(productId);
        if (byId.isPresent()) {
            return new ResponseEntity<>("Product is already exist",HttpStatus.BAD_REQUEST);
        }
        goodsRepository.save(product);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getAllGoods () {
        List<Goods> listOfGoods = new ArrayList<>();
        for (Goods product : goodsRepository.findAll()) {
            listOfGoods.add(product);
        }
        return new ResponseEntity<>(listOfGoods,HttpStatus.OK);
    }

    @GetMapping ("/{id}")
    public ResponseEntity<?> getProductByID (@PathVariable int goodID) {
        if (goodsRepository.findById(goodID).isEmpty()) {
            return new ResponseEntity<>
                    ("This product is abcent",HttpStatus.NOT_FOUND);
        }
        Goods requestedProduct = goodsRepository.findById(goodID).get();
        return new ResponseEntity<>(requestedProduct,HttpStatus.OK);
    }

    @DeleteMapping ("/{goodID}")
    public ResponseEntity<?> deleteGoods (@PathVariable int goodID) {
        if (goodsRepository.findById(goodID).isEmpty()) {
            return new ResponseEntity<>
                    ("This product is abcent",HttpStatus.BAD_REQUEST);
        }
        goodsRepository.delete(goodsRepository.findById(goodID).get());
        return new ResponseEntity<>("Successfully deleted",HttpStatus.OK);
    }







}
