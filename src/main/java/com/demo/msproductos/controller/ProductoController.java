package com.demo.msproductos.controller;

import com.demo.msproductos.model.ApiResponse;
import com.demo.msproductos.model.Producto;
import com.demo.msproductos.service.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/productos")
@Tag(name = "Productos", description = "API CRUD de gestión de productos - Demo Azure")
@CrossOrigin(origins = "*")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping
    @Operation(summary = "Listar todos los productos",
               description = "Retorna la lista completa de productos disponibles (datos demo precargados)")
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente",
            content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    })
    public ResponseEntity<ApiResponse<List<Producto>>> listar() {
        List<Producto> lista = productoService.listar();
        return ResponseEntity.ok(ApiResponse.ok("Productos obtenidos correctamente", lista));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener producto por ID", description = "Busca un producto específico por su ID")
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Producto encontrado"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    public ResponseEntity<ApiResponse<Producto>> obtenerPorId(
            @Parameter(description = "ID del producto", required = true, example = "1")
            @PathVariable Long id) {

        Optional<Producto> producto = productoService.obtenerPorId(id);

        return producto
                .map(p -> ResponseEntity.ok(ApiResponse.ok("Producto encontrado", p)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error("Producto con ID " + id + " no encontrado")));
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo producto", description = "Registra un nuevo producto en el sistema")
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Producto creado exitosamente"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    public ResponseEntity<ApiResponse<Producto>> crear(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Datos del nuevo producto", required = true)
            @RequestBody Producto producto) {

        Producto nuevo = productoService.crear(producto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok("Producto creado exitosamente", nuevo));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar producto", description = "Actualiza todos los campos de un producto existente")
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Producto actualizado"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    public ResponseEntity<ApiResponse<Producto>> actualizar(
            @Parameter(description = "ID del producto a actualizar", required = true)
            @PathVariable Long id,
            @RequestBody Producto producto) {

        Optional<Producto> actualizado = productoService.actualizar(id, producto);

        return actualizado
                .map(p -> ResponseEntity.ok(ApiResponse.ok("Producto actualizado correctamente", p)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error("Producto con ID " + id + " no encontrado")));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar producto", description = "Elimina un producto del sistema por su ID")
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Producto eliminado"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    public ResponseEntity<ApiResponse<Void>> eliminar(
            @Parameter(description = "ID del producto a eliminar", required = true)
            @PathVariable Long id) {

        boolean eliminado = productoService.eliminar(id);

        if (eliminado) {
            return ResponseEntity.ok(ApiResponse.ok("Producto eliminado correctamente", null));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("Producto con ID " + id + " no encontrado"));
        }
    }
}
