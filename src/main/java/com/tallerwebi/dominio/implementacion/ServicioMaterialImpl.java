package com.tallerwebi.dominio.implementacion;

import com.tallerwebi.dominio.implementacion.interfaces.ServicioMaterial;
import com.tallerwebi.dominio.modelo.Material;
import com.tallerwebi.dominio.implementacion.interfaces.RepositorioMaterial;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("servicioMaterial")
@Transactional
public class ServicioMaterialImpl implements ServicioMaterial {

    private RepositorioMaterial repositorioMaterial;
    @Autowired
    public ServicioMaterialImpl(RepositorioMaterial repositorioMaterial) {    this.repositorioMaterial = repositorioMaterial;
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
        repositorioMaterial.eliminar(repositorioMaterial.buscarPorId(id));
    }

    @Override
    public void actualizarMaterial(Material material) {
        repositorioMaterial.actualizar(material);
    }

}
