package com.example.internetshop.controllers;
import com.example.internetshop.models.Booking;
import com.example.internetshop.models.OrderPosition;
import com.example.internetshop.repositories.OrderPositionsRepository;
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
    public ResponseEntity<?> getByID (int id) {
        if (orderPositionsRepository.findById(id).isEmpty()) {
            return new ResponseEntity<>
            ("Order position with such id doesn't exist",HttpStatus.NOT_FOUND);
        }
        OrderPosition orderPosition = orderPositionsRepository.findById(id).get();
        return new ResponseEntity<>(orderPosition,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrderPositionByID (int id) {
        if (orderPositionsRepository.findById(id).isEmpty()) {
            return new ResponseEntity<>
            ("Order position with such id doesn't exist",HttpStatus.NOT_FOUND);
        }
        orderPositionsRepository.delete(orderPositionsRepository.findById(id).get());
        return new ResponseEntity<>("Deleted successfully",HttpStatus.OK);
    }


}
