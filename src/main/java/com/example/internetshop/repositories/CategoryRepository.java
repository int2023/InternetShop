package com.example.internetshop.repositories;
import com.example.internetshop.models.Category;
import com.example.internetshop.models.Client;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category,Integer> {
}
