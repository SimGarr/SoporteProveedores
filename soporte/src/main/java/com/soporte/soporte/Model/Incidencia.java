package com.soporte.soporte.Model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Incidencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String descripcion;

    //Se usa para guardar enums en la base de datos.
    @Enumerated(EnumType.STRING)
    private EstadoIncidencia estado;

    private String usuarioReporta;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;

    public enum EstadoIncidencia {
        ABIERTA, EN_PROGRESO, RESUELTA, CERRADA
    }

    //Se ejecuta antes de guardar una nueva entidad.
    //Ideal para asignar fechas de creación automáticamente.
    @PrePersist
    protected void onCreate() {
        this.fechaCreacion = LocalDateTime.now();
        this.fechaActualizacion = LocalDateTime.now();
    }
    //Se ejecuta antes de actualizar una entidad existente.
    //Útil para actualizar la fecha de modificación.
    @PreUpdate
    protected void onUpdate() {
        this.fechaActualizacion = LocalDateTime.now();
    }
}
