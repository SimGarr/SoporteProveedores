package com.soporte.soporte;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.soporte.soporte.Model.Incidencia;
import com.soporte.soporte.Repository.IncidenciaRepository;
import com.soporte.soporte.Service.IncidenciaService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class IncidenciaServiceTests {

    @Mock
    private IncidenciaRepository repo;

    @InjectMocks
    private IncidenciaService service;

    private Incidencia incidencia;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        incidencia = new Incidencia();
        incidencia.setId(1L);
        incidencia.setTitulo("Error en login");
        incidencia.setDescripcion("No se puede iniciar sesión");

    }

    @Test
    public void testListarTodas() {
        when(repo.findAll()).thenReturn(Arrays.asList(incidencia));

        List<Incidencia> resultado = service.listarTodas();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Error en login", resultado.get(0).getTitulo());
    }

    @Test
    public void testObtenerPorId_Existe() {
        when(repo.findById(1L)).thenReturn(Optional.of(incidencia));

        Incidencia resultado = service.obtenerPorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
    }

    @Test
    public void testObtenerPorId_NoExiste() {
        when(repo.findById(2L)).thenReturn(Optional.empty());

        Incidencia resultado = service.obtenerPorId(2L);

        assertNull(resultado);
    }

    @Test
    public void testGuardar() {
        when(repo.save(any(Incidencia.class))).thenReturn(incidencia);

        Incidencia resultado = service.guardar(incidencia);

        assertNotNull(resultado);
        assertEquals("Error en login", resultado.getTitulo());
    }

    @Test
    public void testEliminar() {
        doNothing().when(repo).deleteById(1L);

        service.eliminar(1L);

        verify(repo, times(1)).deleteById(1L);
    }
}