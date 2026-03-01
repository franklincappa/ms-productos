package com.demo.msproductos.service;

import com.demo.msproductos.model.Producto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ProductoService {

    private final AtomicLong idCounter = new AtomicLong(6);

    private final List<Producto> productos = new ArrayList<>(Arrays.asList(
            Producto.builder().id(1L).nombre("Laptop Dell XPS 15").descripcion("Laptop de alta gama con procesador Intel Core i7")
                    .precio(1500.00).stock(10).categoria("Electrónica").activo(true).build(),

            Producto.builder().id(2L).nombre("Mouse Logitech MX Master").descripcion("Mouse inalámbrico ergonómico")
                    .precio(89.99).stock(50).categoria("Periféricos").activo(true).build(),

            Producto.builder().id(3L).nombre("Monitor Samsung 27\"").descripcion("Monitor 4K UHD con panel IPS")
                    .precio(450.00).stock(15).categoria("Monitores").activo(true).build(),

            Producto.builder().id(4L).nombre("Teclado Mecánico Corsair").descripcion("Teclado mecánico RGB switches Cherry MX")
                    .precio(130.00).stock(30).categoria("Periféricos").activo(true).build(),

            Producto.builder().id(5L).nombre("Auriculares Sony WH-1000XM5").descripcion("Auriculares inalámbricos con cancelación de ruido")
                    .precio(350.00).stock(20).categoria("Audio").activo(false).build(),
            Producto.builder().id(6L).nombre("Microfono Teraware TW-3000").descripcion("Microfono con control")
                    .precio(350.00).stock(20).categoria("Audio").activo(false).build()
    ));

    public List<Producto> listar() {
        return productos;
    }

    public Optional<Producto> obtenerPorId(Long id) {
        return productos.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();
    }

    public Producto crear(Producto producto) {
        producto.setId(idCounter.getAndIncrement());
        productos.add(producto);
        return producto;
    }

    public Optional<Producto> actualizar(Long id, Producto productoActualizado) {
        return productos.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .map(p -> {
                    p.setNombre(productoActualizado.getNombre());
                    p.setDescripcion(productoActualizado.getDescripcion());
                    p.setPrecio(productoActualizado.getPrecio());
                    p.setStock(productoActualizado.getStock());
                    p.setCategoria(productoActualizado.getCategoria());
                    p.setActivo(productoActualizado.getActivo());
                    return p;
                });
    }

    public boolean eliminar(Long id) {
        return productos.removeIf(p -> p.getId().equals(id));
    }
}
