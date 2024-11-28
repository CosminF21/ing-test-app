package com.alten.ing_tech.controller;

import com.alten.ing_tech.model.Product;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Tag(name="Hub Products Management", description = "Hub Products Management")
@SecurityRequirement(name = "Swagger basic authentication")
@RestController
public class HubController {
    private static Logger logger = LoggerFactory.getLogger(HubController.class);

    @GetMapping()
    public void redirectToLogin(HttpServletResponse response) throws IOException {
        logger.info("redirect to /login page...");
        response.sendRedirect("/login");
    }

    @GetMapping("/home")
    public String home(){
        logger.info("/home controller called...");
        return "Hub Home screen";
    }

    @GetMapping("/find-all")
    public List<Product> findAll(){
        logger.info("/find-all called...");
        return Product.products;
    }

    @GetMapping("/find-product/{id}")
    public Optional<Product> findProduct(@PathVariable int id){
        logger.info("/find-product controller called...");
        return Product.findProduct(id);
    }

    @DeleteMapping("/delete-product/{id}")
    public Optional<String> deleteProduct(@PathVariable Integer id){
        logger.info("/delete-product controller called...");
        return Product.deleteProduct(id);
    }

    @PostMapping("/add-product")
    public Product addProduct(@RequestBody Product product){
        logger.info("/add-product controller called...");
        return Product.addProduct(product);
    }

    @PutMapping("/change-price")
    public Optional<Product> changePrice(@RequestBody Product product){
        logger.info("/change-price controller called...");
        return Product.changePrice(product);
    }

}
