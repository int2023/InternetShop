package com.example.internetshop.controllers.servicesDTO;
import com.example.internetshop.models.Booking;
import com.example.internetshop.models.Goods;
import com.example.internetshop.models.OrderPosition;
import java.time.LocalDate;
import java.util.List;

public class BookingsOfClientDTO {
    private int clientINN;
    private LocalDate bookingDate;
    private int bookingTotalPrice;
    private List<OrderPositionDTO> positionsList;

    public BookingsOfClientDTO(int clientINN, LocalDate bookingDate,
                               int bookingTotalPrice,
                               List<OrderPositionDTO> positionsList) {
        this.clientINN = clientINN;
        this.bookingDate = bookingDate;
        this.bookingTotalPrice = bookingTotalPrice;
        this.positionsList = positionsList;
    }

    public BookingsOfClientDTO() { }

    public int getClientINN() {
        return clientINN;
    }
    public void setClientINN(int clientINN) {
        this.clientINN = clientINN;
    }
    public LocalDate getBookingDate() {
        return bookingDate;
    }
    public void setBookingDate(LocalDate bookingDate) {
        this.bookingDate = bookingDate;
    }
    public int getBookingTotalPrice() {
        return bookingTotalPrice;
    }
    public void setBookingTotalPrice(int bookingTotalPrice) {
        this.bookingTotalPrice = bookingTotalPrice;
    }
    public List<OrderPositionDTO> getPositionsList() {
        return positionsList;
    }
    public void setPositionsList(List<OrderPositionDTO> positionsList) {
        this.positionsList = positionsList;
    }
}
