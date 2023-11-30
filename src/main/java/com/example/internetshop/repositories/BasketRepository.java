package com.example.internetshop.repositories;

import com.example.internetshop.models.Basket;
import org.springframework.data.repository.CrudRepository;

public interface BasketRepository extends CrudRepository <Basket, Integer> {

}
