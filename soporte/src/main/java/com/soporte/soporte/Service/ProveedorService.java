package com.soporte.soporte.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soporte.soporte.Model.Proveedor;
import com.soporte.soporte.Repository.ProveedorRepository;

@Service
public class ProveedorService {

    @Autowired
    private ProveedorRepository repo;

    public List<Proveedor> listarTodos() {
        return repo.findAll();
    }

    public Proveedor obtenerPorId(Long id) {
        return repo.findById(id).orElse(null);
    }

    public Proveedor guardar(Proveedor proveedor) {
        return repo.save(proveedor);
    }

    public void eliminar(Long id) {
        repo.deleteById(id);
    }
}
