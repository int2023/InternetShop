package com.example.internetshop.controllers;
import com.example.internetshop.models.Booking;
import com.example.internetshop.models.Client;
import com.example.internetshop.models.Goods;
import com.example.internetshop.models.OrderPosition;
import com.example.internetshop.repositories.BookingsRepository;
import com.example.internetshop.repositories.ClientRepository;
import com.example.internetshop.repositories.GoodsRepository;
import com.example.internetshop.repositories.OrderPositionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/orders")
public class OrderPosController {
    OrderPositionsRepository orderPositionsRepository;
    @Autowired
    GoodsRepository goodsRepository;
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    BookingsRepository bookingsRepository;

    public OrderPosController(OrderPositionsRepository orderPositionsRepository) {
        this.orderPositionsRepository = orderPositionsRepository;
    }
    @PostMapping
    public ResponseEntity<?> addOrderPosition
            (@RequestBody OrderPosition orderPosition) {
        int orderID = orderPosition.getId();
        Optional<OrderPosition> byId = orderPositionsRepository.findById(orderID);
        if (byId.isPresent()) {
            return new ResponseEntity<>
                    ("Order position is already exist", HttpStatus.BAD_REQUEST);
        }
        orderPositionsRepository.save(orderPosition);
        return new ResponseEntity<>(orderPosition, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getAllOrderPositions () {
        List<OrderPosition> orderPositions = new ArrayList<>();
        for (OrderPosition orderPosition : orderPositionsRepository.findAll()) {
            orderPositions.add(orderPosition);
        }
        return new ResponseEntity<>(orderPositions,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getByID (@PathVariable int id) {
        if (orderPositionsRepository.findById(id).isEmpty()) {
            return new ResponseEntity<>
            ("Order position with such id doesn't exist",HttpStatus.NOT_FOUND);
        }
        OrderPosition orderPosition = orderPositionsRepository.findById(id).get();
        return new ResponseEntity<>(orderPosition,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrderPositionByID (@PathVariable int id) {
        if (orderPositionsRepository.findById(id).isEmpty()) {
            return new ResponseEntity<>
            ("Order position with such id doesn't exist",HttpStatus.NOT_FOUND);
        }
        orderPositionsRepository.delete(orderPositionsRepository.findById(id).get());
        return new ResponseEntity<>("Deleted successfully",HttpStatus.OK);
    }

    @GetMapping ("/{goodID}")
    public ResponseEntity<?> getOrdersByGoodID (@PathVariable int goodID) {
        if (goodsRepository.findById(goodID).isEmpty()) {
            return new ResponseEntity<>
            ("Such product is absent",HttpStatus.NOT_FOUND);
        };
        List<OrderPosition> orderPositions =
                goodsRepository.findById(goodID).get().getOrderPositions();

        return new ResponseEntity<>(orderPositions,HttpStatus.OK);
    }

    @GetMapping ("/byName")
    public ResponseEntity<?> getOrdersByProductName (@RequestParam String name) {
        List <String> goodsNames = new ArrayList<>();
        Iterable<Goods> allGoods = goodsRepository.findAll();
        for (Goods product : allGoods) {
            if (! product.getGoodName().toLowerCase().contains(name.toLowerCase())) {
            return new ResponseEntity<>("Such product is absent",HttpStatus.NOT_FOUND);
            }
            goodsNames.add(product.getGoodName());
        }
        return new ResponseEntity<>(goodsNames,HttpStatus.OK);
    }

    @GetMapping ("/byPrice")
    public ResponseEntity<?> getGoodsByPriceFrames
            (@RequestParam int min, @RequestParam int max) {
        List <Goods> goodsList = new ArrayList<>();
        int count = 0;
        Iterable<Goods> allGoods = goodsRepository.findAll();

        for (Goods product : allGoods) {
            if (product.getPrice() <= max && product.getPrice() >= max) {
                goodsList.add(product);
                count ++;
            }
        }
        if (count == 0) {
            return new ResponseEntity<>("No such goods",HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(goodsList,HttpStatus.OK);
    }

}
