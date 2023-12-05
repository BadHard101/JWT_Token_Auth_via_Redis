package com.example.studentsretake.Services;

import com.example.studentsretake.Entities.Product;

import java.util.List;

public interface ProductService {
    Product create(Product product);
    Product read(int id);
    Product update(int id, Product product);
    void delete(int id);
    List<Product> read();
}
