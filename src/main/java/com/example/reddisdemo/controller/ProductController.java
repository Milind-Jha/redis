package com.example.reddisdemo.controller;

import com.example.reddisdemo.entity.Product;
import com.example.reddisdemo.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;

import java.util.List;

@RestController
@RequestMapping("/product")
@EnableCaching
public class ProductController {

    @Autowired
    private ProductRepo productRepo;
    @PostMapping("/save")
    public Product save(@RequestBody Product product){
        return productRepo.save(product);
    }

    @GetMapping("/allProducts")
    public List<Product> getAll(){
        return productRepo.findAll();
    }

    @GetMapping("/{id}")
    @Cacheable(key = "#id", value = "Product", unless = "#result.price>50")
    public Product getProduct(@PathVariable String id)throws HttpClientErrorException {
        Product product = productRepo.findProductById(id);
        if (product == null) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Product not found");
        }
        return product;
    }

    @DeleteMapping("/{id}")
    @CacheEvict(key = "#id", value = "Product")
    public String removeProduct(@PathVariable String id){
        return productRepo.deleteProduct(id);
    }
}
