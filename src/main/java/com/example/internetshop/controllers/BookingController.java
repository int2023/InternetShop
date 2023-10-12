package com.example.internetshop.controllers;
import com.example.internetshop.models.Booking;
import com.example.internetshop.models.Goods;
import com.example.internetshop.repositories.BookingsRepository;
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

    public BookingController(BookingsRepository bookingsRepository) {
        this.bookingsRepository = bookingsRepository;
    }

    @PostMapping
    public ResponseEntity<?> addBooking (@RequestBody Booking booking) {
        int bookingNumber = booking.getNumber();
        Optional<Booking> number = bookingsRepository.findById(bookingNumber);
        if (number.isPresent()) {
            return new ResponseEntity<>
                    ("Product is already exist", HttpStatus.BAD_REQUEST);
        }
        bookingsRepository.save(booking);
        return new ResponseEntity<>(booking, HttpStatus.OK);
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
