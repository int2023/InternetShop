package com.example.internetshop.controllers;
import com.example.internetshop.controllers.servicesDTO.BasketDTO;
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
import java.util.stream.Collectors;

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
        BasketDTO basketDTO = new BasketDTO(basketFromDB.getBasketID(),
                basketFromDB.getGoodQuantity(),basketFromDB.getClient().getClientINN(),
                basketFromDB.getGood().getGoodID(),basketFromDB.getGood().getPrice(),
                basketFromDB.getGood().getGoodName());
        return new ResponseEntity<>(basketDTO, HttpStatus.OK);
    }

    @PostMapping("/{basketID}/dec")
    public ResponseEntity<?> decreaseGoodsQuantity (@PathVariable int basketID) {
        Basket basketFromDB = basketRepository.findById(basketID).get();
        basketFromDB.setGoodQuantity(basketFromDB.getGoodQuantity() - 1);
        basketRepository.save(basketFromDB);
        BasketDTO basketDTO = new BasketDTO(basketFromDB.getBasketID(),
                basketFromDB.getGoodQuantity(),basketFromDB.getClient().getClientINN(),
                basketFromDB.getGood().getGoodID(),basketFromDB.getGood().getPrice(),
                basketFromDB.getGood().getGoodName());
        return new ResponseEntity<>(basketDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{basketID}")
    public ResponseEntity<?> deleteProduct (@PathVariable int basketID) {
        basketRepository.deleteById(basketID);
        return new ResponseEntity<>
        ("Basket is successfully deleted", HttpStatus.OK);
    }

    @GetMapping ("")
    public ResponseEntity<?> getAllBasketsByClientID (@RequestParam int clientID) {

        if (!clientRepository.findById(clientID).isPresent()) {
            return new ResponseEntity<>
                    ("Such client is not registered",HttpStatus.NOT_FOUND);
        }
        Client client = clientRepository.findById(clientID).get();
        if (client.getBaskets().size() == 0) {
            return new ResponseEntity<>
            ("Such client doesn't have any baskets",HttpStatus.NOT_FOUND);
        }
        List<Basket> basketList = client.getBaskets();
        List<BasketDTO> basketDTOList = basketList.stream().map
                (b -> new BasketDTO(b.getBasketID(), b.getGoodQuantity(),
                client.getClientINN(), b.getGood().getGoodID(), b.getGood().getPrice(),
                b.getGood().getGoodName())).collect(Collectors.toList());

        return new ResponseEntity<>(basketDTOList,HttpStatus.OK);
    }


}






