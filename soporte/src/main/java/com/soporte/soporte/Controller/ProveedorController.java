package com.soporte.soporte.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.soporte.soporte.Model.Proveedor;
import com.soporte.soporte.Service.ProveedorService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/proveedores")
public class ProveedorController {

    @Autowired
    private ProveedorService service;

    @Operation(summary = "Listar todos los proveedores")
    @GetMapping
    public ResponseEntity<List<Proveedor>> listar() {
        List<Proveedor> proveedores = service.listarTodos();
        if (proveedores.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(proveedores, HttpStatus.OK);
    }

    @Operation(summary = "Obtener proveedor por ID")
    @GetMapping("/{id}")
    public ResponseEntity<Proveedor> obtener(@PathVariable Long id) {
        Proveedor proveedor = service.obtenerPorId(id);
        if (proveedor == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(proveedor, HttpStatus.OK);
    }

    @Operation(summary = "Crear nuevo proveedor")
    @PostMapping
    public ResponseEntity<Proveedor> crear(@RequestBody Proveedor proveedor) {
        try {
            Proveedor nuevoProveedor = service.guardar(proveedor);
            return new ResponseEntity<>(nuevoProveedor, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Actualizar proveedor por ID")
    @PutMapping("/{id}")
    public ResponseEntity<Proveedor> actualizar(@PathVariable Long id, @RequestBody Proveedor nuevoProveedor) {
        Proveedor actual = service.obtenerPorId(id);
        if (actual == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        actual.setNombre(nuevoProveedor.getNombre());
        actual.setTipoServicio(nuevoProveedor.getTipoServicio());
        actual.setContacto(nuevoProveedor.getContacto());
        actual.setTelefono(nuevoProveedor.getTelefono());
        actual.setEmail(nuevoProveedor.getEmail());
        actual.setDireccion(nuevoProveedor.getDireccion());

        Proveedor proveedorActualizado = service.guardar(actual);
        return new ResponseEntity<>(proveedorActualizado, HttpStatus.OK);
    }

    @Operation(summary = "Eliminar proveedor por ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        try {
            service.eliminar(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
