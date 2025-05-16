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

import com.soporte.soporte.Model.Incidencia;
import com.soporte.soporte.Service.IncidenciaService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/incidencias")
public class IncidenciaController {

    @Autowired
    private IncidenciaService service;

    @Operation(summary = "Crear una nueva incidencia")
    @PostMapping
    public ResponseEntity<Incidencia> crearIncidencia(@RequestBody Incidencia incidencia) {
        try {
            Incidencia nuevaIncidencia = service.guardar(incidencia);
            return new ResponseEntity<>(nuevaIncidencia, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Obtener incidencia por ID")
    @GetMapping("/{id}")
    public ResponseEntity<Incidencia> obtenerIncidenciaPorId(@PathVariable Long id) {
        Incidencia incidencia = service.obtenerPorId(id);
        if (incidencia == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(incidencia, HttpStatus.OK);
    }

    @Operation(summary = "Verificar si la incidencia existe por ID")
    @GetMapping("/existe/{id}")
    public ResponseEntity<String> existeIncidenciaPorId(@PathVariable Long id) {
        boolean existe = service.obtenerPorId(id) != null;
        String mensaje = existe
                ? "La incidencia con ID " + id + " SÍ existe"
                : "La incidencia con ID " + id + " NO existe";
        return ResponseEntity.ok(mensaje);
    }

    @Operation(summary = "Eliminar incidencia por ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarIncidenciaPorId(@PathVariable Long id) {
        Incidencia incidencia = service.obtenerPorId(id);
        if (incidencia == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        service.eliminar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Actualizar incidencia por ID")
    @PutMapping("/{id}")
    public ResponseEntity<Incidencia> actualizarIncidencia(@PathVariable Long id, @RequestBody Incidencia incidencia) {
        Incidencia existente = service.obtenerPorId(id);
        if (existente == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Actualiza solo los campos necesarios no la fecha de creación
        existente.setTitulo(incidencia.getTitulo());
        existente.setDescripcion(incidencia.getDescripcion());
        existente.setEstado(incidencia.getEstado());

        Incidencia incidenciaActualizada = service.guardar(existente);
        return new ResponseEntity<>(incidenciaActualizada, HttpStatus.OK);
    }

    @Operation(summary = "Obtener todas las incidencias")
    @GetMapping
    public ResponseEntity<List<Incidencia>> obtenerTodasLasIncidencias() {
        List<Incidencia> incidencias = service.listarTodas();
        if (incidencias.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(incidencias, HttpStatus.OK);
    }
}
