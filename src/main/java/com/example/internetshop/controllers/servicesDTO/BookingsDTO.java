package com.example.internetshop.controllers.servicesDTO;
import com.example.internetshop.models.Goods;
import java.time.LocalDate;
import java.util.List;

public class BookingsDTO {

    private LocalDate bookingDate_DTO;
    private int clientINN_DTO;
    private int sumPrice_DTO;
    private List<GoodsQuantityDTO> goodsList_DTO;

    public BookingsDTO() { }

    public BookingsDTO(LocalDate bookingDate_DTO, int clientINN_DTO,
                       int sumPrice_DTO, List<GoodsQuantityDTO> goodsList_DTO) {
        this.bookingDate_DTO = bookingDate_DTO;
        this.clientINN_DTO = clientINN_DTO;
        this.sumPrice_DTO = sumPrice_DTO;
        this.goodsList_DTO = goodsList_DTO;
    }

    public LocalDate getBookingDate_DTO() {
        return bookingDate_DTO;
    }
    public void setBookingDate_DTO(LocalDate bookingDate_DTO) {
        this.bookingDate_DTO = bookingDate_DTO;
    }
    public int getClientINN_DTO() {
        return clientINN_DTO;
    }
    public void setClientINN_DTO(int clientINN_DTO) {
        this.clientINN_DTO = clientINN_DTO;
    }
    public int getSumPrice_DTO() {
        return sumPrice_DTO;
    }
    public void setSumPrice_DTO(int sumPrice_DTO) {
        sumPrice_DTO = sumPrice_DTO;
    }
    public List<GoodsQuantityDTO> getGoodsList_DTO() {
        return goodsList_DTO;
    }
    public void setGoodsList_DTO(List<GoodsQuantityDTO> goodsList_DTO) {
        this.goodsList_DTO = goodsList_DTO;
    }
}
