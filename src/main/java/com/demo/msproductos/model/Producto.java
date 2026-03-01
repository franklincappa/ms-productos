package com.demo.msproductos.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Entidad Producto")
public class Producto {

    @Schema(description = "ID único del producto", example = "1")
    private Long id;

    @Schema(description = "Nombre del producto", example = "Laptop Dell XPS")
    private String nombre;

    @Schema(description = "Descripción del producto", example = "Laptop de alta gama")
    private String descripcion;

    @Schema(description = "Precio del producto", example = "1500.00")
    private Double precio;

    @Schema(description = "Cantidad en stock", example = "25")
    private Integer stock;

    @Schema(description = "Categoría del producto", example = "Electrónica")
    private String categoria;

    @Schema(description = "Estado activo/inactivo del producto", example = "true")
    private Boolean activo;
}
