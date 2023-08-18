package com.adilson.firstapi.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adilson.firstapi.model.ProductsModel;

//Repositório

//@Repository
public interface ProductRepository extends JpaRepository<ProductsModel, UUID> {

}
