package com.example.internetshop.controllers;
import com.example.internetshop.models.Basket;
import com.example.internetshop.models.Client;
import com.example.internetshop.models.Goods;
import com.example.internetshop.repositories.BasketRepository;
import com.example.internetshop.repositories.ClientRepository;
import com.example.internetshop.repositories.GoodsRepository;
import com.example.internetshop.repositories.OrderPositionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/baskets")
public class BasketController {
    BasketRepository basketRepository;
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    GoodsRepository goodsRepository;

    public BasketController(BasketRepository basketRepository) {
        this.basketRepository = basketRepository;
    }

    @PostMapping
    public ResponseEntity<?> addBasket (@RequestBody Basket basket) {
        if (!clientRepository.findById(basket.getClient().getClientINN()).isPresent()) {
            return new ResponseEntity<>("Client with such INN is not registered",
            HttpStatus.NOT_FOUND);
        }
        if (!goodsRepository.findById(basket.getGood().getGoodID()).isPresent()) {
            return new ResponseEntity<>("Product with such ID is not registered",
                    HttpStatus.NOT_FOUND);
        }
            basketRepository.save(basket);
            clientRepository.findById(basket.getClient().
                    getClientINN()).get().getBaskets().add(basket);
            return new ResponseEntity<>("Basket is successfully created",HttpStatus.OK);
     }

    @PostMapping("/{basketID}/inc")
    public ResponseEntity<?> increaseGoodsQuantity (@PathVariable int basketID) {
        Basket basketFromDB = basketRepository.findById(basketID).get();
        basketFromDB.setGoodQuantity(basketFromDB.getGoodQuantity() + 1);
        basketRepository.save(basketFromDB);
        return new ResponseEntity<>(basketFromDB, HttpStatus.OK);
    }

    @PostMapping("/{basketID}/dec")
    public ResponseEntity<?> decreaseGoodsQuantity (@PathVariable int basketID) {
        Basket basketFromDB = basketRepository.findById(basketID).get();
        basketFromDB.setGoodQuantity(basketFromDB.getGoodQuantity() - 1);
        basketRepository.save(basketFromDB);
        return new ResponseEntity<>(basketFromDB, HttpStatus.OK);
    }

    @DeleteMapping("/{basketID}")
    public ResponseEntity<?> deleteProduct (@PathVariable int basketID) {
        basketRepository.deleteById(basketID);
        return new ResponseEntity<>
        ("Basket is successfully deleted", HttpStatus.OK);
    }

    @GetMapping ("/{clientID}")
    public ResponseEntity<?> getAllBasketsByClientID (@RequestParam int clientID) {

        Client client = clientRepository.findById(clientID).get();
        if (client.getBaskets().size() == 0) {
            return new ResponseEntity<>
                    ("Such client doesn't have any baskets",HttpStatus.NOT_FOUND);
        }
        List<Basket> basketList = client.getBaskets();
        return new ResponseEntity<>(basketList,HttpStatus.OK);
    }


    //1) get List <Basket> @RequestParam using clientID; in this class
    //2) confirm order -> transform Basket into OrderPosition; in clientController
    //create Booking and OrderPositions at every element of Basket and save it;

}






