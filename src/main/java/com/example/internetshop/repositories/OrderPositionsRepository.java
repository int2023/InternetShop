package com.example.internetshop.repositories;

import com.example.internetshop.models.OrderPosition;
import org.springframework.data.repository.CrudRepository;

public interface OrderPositionsRepository
        extends CrudRepository<OrderPosition,Integer> {
}
