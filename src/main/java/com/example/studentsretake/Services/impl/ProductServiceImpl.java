package com.example.studentsretake.Services.impl;

import com.example.studentsretake.Entities.Product;
import com.example.studentsretake.Repos.ProductRepo;
import com.example.studentsretake.Services.ProductService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepo productRepo;

    @Override
    public Product create(Product product) {
        // Метод для создания нового продукта. Сохраняет продукт в репозитории.
        return productRepo.save(product);
    }

    @Override
    public Product read(int id) {
        // Метод для чтения продукта по ID. Возвращает продукт из репозитория или выбрасывает исключение, если продукт не найден.
        return productRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("No such product"));
    }

    @Override
    public Product update(int id, Product product) {
        // Метод для обновления информации о продукте по его ID.

        // Получаем продукт из репозитория по ID.
        Product prFromRepo = productRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("No such product"));

        // Обновляем поля продукта из репозитория данными из входного объекта.
        prFromRepo.setName(product.getName());
        prFromRepo.setPrice(product.getPrice());
        prFromRepo.setQty(product.getQty());

        // Сохраняем обновленный продукт в репозиторий.
        return productRepo.save(prFromRepo);
    }

    @Override
    public void delete(int id) {
        // Метод для удаления продукта по его ID.
        productRepo.deleteById(id);
    }

    @Override
    public List<Product> read() {
        // Метод для чтения всех продуктов. Возвращает список всех продуктов из репозитория.
        return productRepo.findAll();
    }
}
