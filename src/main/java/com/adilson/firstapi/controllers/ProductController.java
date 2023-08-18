package com.adilson.firstapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.adilson.firstapi.repositories.ProductRepository;

//Controller para utilizar todos metodos REST
@RestController
public class ProductController {
    // Injeção de dependência
    @Autowired
    ProductRepository productRepository;

    // Mapeamento dos métodos

}
