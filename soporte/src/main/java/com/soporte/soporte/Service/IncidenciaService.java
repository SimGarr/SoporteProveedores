package com.soporte.soporte.Service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soporte.soporte.Model.Incidencia;
import com.soporte.soporte.Repository.IncidenciaRepository;

@Service
public class IncidenciaService {

    @Autowired
    private IncidenciaRepository repo;

    public List<Incidencia> listarTodas() {
        return repo.findAll();
    }

    public Incidencia obtenerPorId(Long id) {
        return repo.findById(id).orElse(null);
    }

    public Incidencia guardar(Incidencia incidencia) {
        return repo.save(incidencia);
    }

    public void eliminar(Long id) {
        repo.deleteById(id);
    }
}