package com.example.reddisdemo.repo;

import com.example.reddisdemo.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;

import java.util.List;

@Repository
public class ProductRepo {

    public static final String HASH_KEY = "Product";
    @Autowired
    private RedisTemplate redisTemplate;

    public Product save(Product product){
        redisTemplate.opsForHash().put(HASH_KEY,product.getId(),product);
        return product;
    }

    public List<Product> findAll(){
        return redisTemplate.opsForHash().values(HASH_KEY);
    }

    public Product findProductById(String id) throws HttpClientErrorException{
        System.out.println("Data taken from db");
        Product product = (Product) redisTemplate.opsForHash().get(HASH_KEY, id);
        System.out.println("product : "+ product);
        return product;
    }

    public String deleteProduct(String id){
        redisTemplate.opsForHash().delete(HASH_KEY,id);
        return "product removed";
    }
}
