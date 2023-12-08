package com.example.internetshop.models;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Client {
    @Id
    private int clientINN;
    private String FamilyName;
    private String name;
    private LocalDate birthDate;
    private LocalDate regDate;

    @OneToMany (mappedBy = "client")
    private List<Basket> baskets;

    @OneToMany(mappedBy = "client")
    private List<Booking> bookings;

    public int getClientINN() {
        return clientINN;
    }
    public void setClientINN(int clientINN) {
        this.clientINN = clientINN;
    }
    public String getFamilyName() {
        return FamilyName;
    }
    public void setFamilyName(String familyName) {
        FamilyName = familyName;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public List<Booking> getBookings() {
        return bookings;
    }
    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }
    public LocalDate getBirthDate() {
        return birthDate;
    }
    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }
    public LocalDate getRegDate() {
        return regDate;
    }
    public void setRegDate(LocalDate regDate) {
        this.regDate = regDate;
    }
    public List<Basket> getBaskets() {
        return baskets;
    }
    public void setBaskets(List<Basket> baskets) {
        this.baskets = baskets;
    }
}
