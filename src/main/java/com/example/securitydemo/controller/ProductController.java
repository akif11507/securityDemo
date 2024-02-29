//package com.example.securitydemo.controller;
//
//import com.example.securitydemo.model.Product;
//import org.springframework.http.MediaType;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.websocket.server.PathParam;
//import java.util.List;
//
//@RestController
//public class ProductController {
//    private List<Product> products = List.of(
//            new Product("Television", "Samsung", 1145.67, "S001"),
//            new Product("Washing Machine", "LG", 114.67, "L001"),
//            new Product("Laptop", "Apple", 11453.67, "A001"));
//
//    @GetMapping(value = "/products/{id}",
//            produces = MediaType.APPLICATION_XML_VALUE)
//    public @ResponseBody Product fetchProducts(
//            @PathParam("id") String productId) {
//
//        return products.get(1);
//    }
//}
