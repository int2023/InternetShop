package com.example.internetshop.controllers.servicesDTO;
import jakarta.persistence.Id;
import java.time.LocalDate;

public class ClientsDTO {
    private int clientINN;
    private String FamilyName;
    private String name;
    private LocalDate birthDate;
    private LocalDate regDate;

    public ClientsDTO(int clientINN, String familyName,
                      String name, LocalDate birthDate,
                      LocalDate regDate) {
        this.clientINN = clientINN;
        FamilyName = familyName;
        this.name = name;
        this.birthDate = birthDate;
        this.regDate = regDate;
    }

    public ClientsDTO() {}

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
}
