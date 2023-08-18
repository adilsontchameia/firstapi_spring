package com.adilson.firstapi.controllers;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.adilson.firstapi.dtos.ProductRecordDto;
import com.adilson.firstapi.model.ProductsModel;
import com.adilson.firstapi.repositories.ProductRepository;

import jakarta.validation.Valid;

//Controller para utilizar todos metodos REST
@RestController
public class ProductController {
    // Injeção de dependência
    @Autowired
    ProductRepository productRepository;

    // Mapeamento dos métodos
    @PostMapping("/products")
    public ResponseEntity<ProductsModel> saveProduct(@RequestBody @Valid ProductRecordDto productsRecordDto) {
        // Vai se salvar na db o model
        // Vai se converter o dto => model
        var productModel = new ProductsModel();
        // Recebe o que vai ser convertido e o tipo que será convertido
        // DTO => Model
        BeanUtils.copyProperties(productsRecordDto, productModel);
        // Construção do retorno
        // Se codigo 201 (OK) => Salva na BD
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(productRepository.save(productRepository.save(productModel)));
    }

    // GetAll Products
    @GetMapping("/products")
    public ResponseEntity<List<ProductsModel>> getAllProducts() {
        return ResponseEntity.status(HttpStatus.OK).body(productRepository.findAll());
    }
}
