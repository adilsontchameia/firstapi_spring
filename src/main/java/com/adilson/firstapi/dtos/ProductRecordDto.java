package com.adilson.firstapi.dtos;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

//Inicia como argumento todos atributos
//ID será gerado automáticamente
public record ProductRecordDto(@NotBlank String name, @NotNull BigDecimal value) {

}
