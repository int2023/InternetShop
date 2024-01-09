package com.example.internetshop.controllers;
import com.example.internetshop.models.Booking;
import com.example.internetshop.models.Client;
import com.example.internetshop.models.Goods;
import com.example.internetshop.models.OrderPosition;
import com.example.internetshop.repositories.BookingsRepository;
import com.example.internetshop.repositories.ClientRepository;
import com.example.internetshop.repositories.GoodsRepository;
import com.example.internetshop.repositories.OrderPositionsRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

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
    public ResponseEntity<?> addBooking(@RequestBody Booking booking) {

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
    public ResponseEntity<?> getAllBookings() {
        List<Booking> bookings = new ArrayList<>();
        for (Booking booking : bookingsRepository.findAll()) {
            bookings.add(booking);
        }
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }

    @GetMapping("/{number}")
    public ResponseEntity<?> getByNumber(int bookingNumber) {
        if (bookingsRepository.findById(bookingNumber).isEmpty()) {
            return new ResponseEntity<>
                    ("Booking with such number doesn't exist", HttpStatus.NOT_FOUND);
        }
        Booking requestedBooking = bookingsRepository.findById(bookingNumber).get();
        return new ResponseEntity<>(requestedBooking, HttpStatus.OK);
    }

    @DeleteMapping("/{number}")
    public ResponseEntity<?> deleteBooking(int bookingNumber) {
        if (bookingsRepository.findById(bookingNumber).isEmpty()) {
            return new ResponseEntity<>
                    ("Booking with such number doesn't exist", HttpStatus.NOT_FOUND);
        }
        bookingsRepository.delete(bookingsRepository.findById(bookingNumber).get());
        return new ResponseEntity<>("Successfully deleted", HttpStatus.OK);
    }


    @PostMapping("/excel")
    public ResponseEntity<?> exportExcel() {

        List<Client> clients = new ArrayList<>();

        for (Client client : clientRepository.findAll()) {
            clients.add(client);
        }
        Workbook res = new XSSFWorkbook();

        try (FileOutputStream fileOutputStream = new FileOutputStream(
                ("C:\\Users\\Bogdan\\Documents\\Bookings.xlsx"))) {

            for (Client c : clients) {
                Sheet sheet = res.createSheet(c.getClientINN() + "");
                int index = 1;
                for (Booking b : c.getBookings()) {
                    List<OrderPosition> positions = b.getPositions();
                    int sum = 0;
                    for (OrderPosition p : positions) {
                        sum = sum + p.getGoods().getPrice();
                    }
                    Row row = sheet.createRow(index);
                    Cell cellForBookingDate = row.createCell(0);
                    Cell cellForBookingSum = row.createCell(1);
                    CellStyle cellStyle = res.createCellStyle();
                    cellStyle.setDataFormat((short)14);
                    cellForBookingDate.setCellStyle(cellStyle);
                    cellForBookingDate.setCellValue(b.getBookingDate());
                    cellForBookingSum.setCellValue(sum);
                    index++;

                    // create page in Excel
                    // for every client - include booking date and
                    // sum of every booking
                }
            }
            res.write(fileOutputStream);
            res.close();

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return new ResponseEntity<>("OK", HttpStatus.OK);
    }
}




