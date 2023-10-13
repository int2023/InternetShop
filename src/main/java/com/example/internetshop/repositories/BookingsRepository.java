package com.example.internetshop.repositories;

import com.example.internetshop.models.Booking;
import org.springframework.data.repository.CrudRepository;

public interface BookingsRepository extends CrudRepository<Booking,Integer> {
}
