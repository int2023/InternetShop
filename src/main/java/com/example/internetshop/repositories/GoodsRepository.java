package com.example.internetshop.repositories;

import com.example.internetshop.models.Goods;
import org.springframework.data.repository.CrudRepository;

public interface GoodsRepository extends CrudRepository <Goods, Integer> {
}
