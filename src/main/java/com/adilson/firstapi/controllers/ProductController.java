package com.adilson.firstapi.controllers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
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
        List<ProductsModel> productsList = productRepository.findAll();
        if (!productsList.isEmpty()) {
            for (ProductsModel product : productsList) {
                UUID id = product.getIdProduct();
                product.add(linkTo(methodOn(ProductController.class).getOneProduct(id)).withSelfRel());
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(productsList);
    }

    // GetProducts by Id
    // Object => Porque teremos 2 tipos de respostas
    @GetMapping("/products/{id}")
    public ResponseEntity<Object> getOneProduct(@PathVariable(value = "id") UUID id) {
        Optional<ProductsModel> productO = productRepository.findById(id);
        if (productO.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        } else {
            productO.get().add(linkTo(methodOn(ProductController.class).getAllProducts()).withSelfRel());
            return ResponseEntity.status(HttpStatus.OK).body(productO.get());
        }
    }

    // UpdateProduct
    // Object => Porque teremos 2 tipos de respostas
    @PutMapping("/products/{id}")
    public ResponseEntity<Object> updateProduct(@PathVariable(value = "id") UUID id,
            @RequestBody @Valid ProductRecordDto productRecordDto) {
        Optional<ProductsModel> productO = productRepository.findById(id);
        if (productO.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }
        var productModel = productO.get(); // Atribuindi o valor obtido na base de dados
        BeanUtils.copyProperties(productRecordDto, productModel);
        return ResponseEntity.status(HttpStatus.OK).body(productRepository.save(productModel));

    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable(value = "id") UUID id) {
        Optional<ProductsModel> productO = productRepository.findById(id);
        if (productO.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }
        productRepository.delete(productO.get());

        return ResponseEntity.status(HttpStatus.OK).body("Product delected succesfully.");

    }
}
