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
@RequestMapping("/bookings")
public class BookingController {
    BookingsRepository bookingsRepository;
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    GoodsRepository goodsRepository;
    @Autowired
    OrderPositionsRepository orderPositionsRepository;
    public BookingController(BookingsRepository bookingsRepository) {
        this.bookingsRepository = bookingsRepository;
    }
    @PostMapping
    public ResponseEntity<?> addBooking (@RequestBody Booking booking) {

        if (clientRepository.findById(booking.getClient().getClientINN()).isEmpty()) {
            return new ResponseEntity<>("Client is not registered",
                    HttpStatus.NOT_FOUND);
        }
        Booking booking1 = new Booking();
        booking1.setBookingDate(booking.getBookingDate());
        Optional<Client> client = clientRepository.findById(booking.getClient().getClientINN());
        booking1.setClient(client.get());
        bookingsRepository.save(booking1);

        for (OrderPosition position : booking.getPositions()) {
            OrderPosition orderPosition = new OrderPosition();
            orderPosition.setBooking(booking1);
            int goodID = position.getGoods().getGoodID();
            Optional<Goods> product = goodsRepository.findById(goodID);
            orderPosition.setGoods(product.get());
            orderPosition.setGoodsQuantity(position.getGoodsQuantity());
            orderPositionsRepository.save(orderPosition);
        }
       return new ResponseEntity<>("Successfully", HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getAllBookings () {
        List<Booking> bookings = new ArrayList<>();
        for (Booking booking : bookingsRepository.findAll()) {
            bookings.add(booking);
        }
        return new ResponseEntity<>(bookings,HttpStatus.OK);
    }

    @GetMapping("/{number}")
    public ResponseEntity<?> getByNumber (int bookingNumber) {
         if (bookingsRepository.findById(bookingNumber).isEmpty()) {
         return new ResponseEntity<>
         ("Booking with such number doesn't exist",HttpStatus.NOT_FOUND);
        }
        Booking requestedBooking = bookingsRepository.findById(bookingNumber).get();
        return new ResponseEntity<>(requestedBooking,HttpStatus.OK);
    }

    @DeleteMapping("/{number}")
    public ResponseEntity<?> deleteBooking (int bookingNumber) {
        if (bookingsRepository.findById(bookingNumber).isEmpty()) {
            return new ResponseEntity<>
            ("Booking with such number doesn't exist",HttpStatus.NOT_FOUND);
        }
        bookingsRepository.delete(bookingsRepository.findById(bookingNumber).get());
        return new ResponseEntity<>("Successfully deleted",HttpStatus.OK);
    }



}
