package com.example.studentsretake.Controllers;

import com.example.studentsretake.Entities.Product;
import com.example.studentsretake.Services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ProductController {

    private final ProductService productService;

    @PostMapping("/seller/product/add")
    public ResponseEntity<?> create(@RequestBody Product product){
        return ResponseEntity.ok(productService.create(product));
    }

    @GetMapping("/user/products")
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(productService.read());
    }

    @DeleteMapping("/seller/product/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id){
        productService.delete(id);
        return ResponseEntity.ok("Deleted");
    }
}
