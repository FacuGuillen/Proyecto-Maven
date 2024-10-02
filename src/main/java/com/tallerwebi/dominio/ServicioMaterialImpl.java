package com.tallerwebi.dominio;

import com.tallerwebi.dominio.modelo.Material;
import com.tallerwebi.dominio.repositorio.RepositorioMaterial;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServicioMaterialImpl implements ServicioMaterial {


    private final RepositorioMaterial repositorioMaterial;

    @Autowired
    public ServicioMaterialImpl(RepositorioMaterial repositorioMaterial) {
        this.repositorioMaterial = repositorioMaterial;
    }

    @Override
    public void guardarMaterial(Material material) {
        repositorioMaterial.guardar(material);
    }

    @Override
    public Material buscarMaterialPorId(Long id) {
        return repositorioMaterial.buscarPorId(id);
    }

    @Override
    public List<Material> listarMateriales() {
        return repositorioMaterial.listar();
    }

    @Override
    public void eliminarMaterial(Long id) {
        Material material = repositorioMaterial.buscarPorId(id);
        if (material != null) {
            repositorioMaterial.eliminar(material);
        }
    }

    @Override
    public void actualizarMaterial(Material material) {
        repositorioMaterial.actualizar(material);
    }

    @Override
    public List<Material> listarMaterialesSobrantes() {
        List<Material> todosLosMateriales = repositorioMaterial.listar();
        List<Material> materialesSobrantes = new ArrayList<>();

        for (Material material : todosLosMateriales) {
            if (material.isSobrante()) {
                materialesSobrantes.add(material);
            }
        }

        return materialesSobrantes;
    }

    @Override
    public void agregarMaterialSobrante(Material material) {
        if (material != null && material.isSobrante()) {
            repositorioMaterial.guardar(material);
        }
    }
}