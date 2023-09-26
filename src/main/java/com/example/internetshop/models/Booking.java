package com.example.internetshop.models;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;
@Entity
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int number;
    private LocalDate bookingDate;
    @ManyToOne
    @JoinColumn (name = "clientINN")
    @JsonIgnore
    private Client client;
    @OneToMany (mappedBy = "booking")
    @JsonIgnore
    private List<OrderPosition> positions;

    public Client getClient() {
        return client;
    }
    public void setClient(Client client) {
        this.client = client;
    }
    public int getBookingNumber() {
        return number;
    }
    public void setBookingNumber(int bookingNumber) {
        this.number = bookingNumber;
    }
    public LocalDate getBookingDate() {
        return bookingDate;
    }
    public void setBookingDate(LocalDate bookingDate) {
        this.bookingDate = bookingDate;
    }
    public int getNumber() {
        return number;
    }
    public void setNumber(int number) {
        this.number = number;
    }
    public List<OrderPosition> getPositions() {
        return positions;
    }
    public void setPositions(List<OrderPosition> positions) {
        this.positions = positions;
    }
}



