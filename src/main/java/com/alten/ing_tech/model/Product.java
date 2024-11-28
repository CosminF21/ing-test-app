package com.alten.ing_tech.model;

import java.util.*;

public record Product(Integer id,
                      String name,
                      String description,
                      Double price,
                      String currency) {

    public static List<Product> products = new ArrayList<>(Arrays.asList(
            new Product(1,"Product1", "exceptional product", 10.0, "RON"),
            new Product(2,"Product2", "cheap product", 2.0, "RON"),
            new Product(3,"Product3", "expensive product", 1000.0, "RON"),
            new Product(4,"Product4", "foreign product", 23.0, "EUR")));

    public static Optional<Product> findProduct(Integer id) {
        return products.stream()
                .filter(p -> p.id.equals(id))
                .findFirst();
    }

    public static Optional<String> deleteProduct(Integer id){
        try{
            Product product = findProduct(id).orElseThrow();
            products.remove(product);
            return Optional.of(id + " removed");
        }catch (NoSuchElementException e){
            return Optional.empty();
        }
    }

    public static Product addProduct(Product product){
        Integer id = products.stream()
                        .map(p -> p.id)
                        .sorted((a,b) -> b-a)
                        .findFirst().orElse(0) + 1;
       Product newProduct = new Product(id, product.name, product.description, product.price, product.currency);
       products.add(newProduct);
       return newProduct;
    }

    public static Optional<Product> changePrice(Product product){
        try{
            Product foundProduct = products.stream()
                    .filter(p -> p.id.equals(product.id))
                    .findFirst().orElseThrow();
            Product replaceProduct = new Product(foundProduct.id, foundProduct.name, foundProduct.description, product.price, foundProduct.currency);
            products.set(products.indexOf(foundProduct), replaceProduct);
            return Optional.of(replaceProduct);
        }catch (NoSuchElementException e){
            return Optional.empty();
        }
    }
}
