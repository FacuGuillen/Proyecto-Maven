package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.excepcion.MaterialConCantidadNullException;
import com.tallerwebi.dominio.excepcion.MaterialConNombreNullException;
import com.tallerwebi.dominio.excepcion.MaterialConUnidadNullException;
import com.tallerwebi.dominio.repositorio.RepositorioMaterial;
import com.tallerwebi.dominio.modelo.Material;
import com.tallerwebi.infraestructura.config.HibernateInfraestructuraTestConfig;
import org.hibernate.SessionFactory;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import javax.persistence.Query;
import javax.transaction.Transactional;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {HibernateInfraestructuraTestConfig.class})
public class RepositorioMaterialImplTest {

    @Autowired
    private SessionFactory sessionFactory;
    private RepositorioMaterial repositorioMaterial;

    @BeforeEach
    public void init(){
        this.repositorioMaterial = new RepositorioMaterialImpl(sessionFactory);
    }

    @Test
    @Transactional
    public void dadoQueGuardoUnMaterialEntoncesLoEncuentroEnLaBaseDeDatos(){
        Material material = crearMaterialConNombreCementoConCantidad50YUnidadKg();

        this.repositorioMaterial.guardar(material);

        Material materialObtenido = this.repositorioMaterial.buscarPorNombre("Cemento");
        assertThat(materialObtenido, equalTo(material));
    }

    @Test
    @Transactional
    public void dadoQueIntentoGuardarMaterialConNombreNuloEntoncesArrojaLaExcepcionMaterialConNombreNullException() {
        Material material = crearUnMaterialConElAtributoNombreEnNull();

        assertThrows(MaterialConNombreNullException.class, () ->{
            this.repositorioMaterial.guardar(material);
        });
    }

    @Test
    @Transactional
    public void dadoQueIntentoGuardarMaterialConCantidadNuloEntoncesArrojaLaExcepcionMaterialConCantidadNullException() {
        Material material = crearMaterialConElAtributoCantidadEnNull();

        assertThrows(MaterialConCantidadNullException.class, () ->{
            this.repositorioMaterial.guardar(material);
        });

    }


    @Test
    @Transactional
    public void dadoQueIntentoGuardarMaterialConUnidadNulaEntoncesArrojaLaExcepcionMaterialConUnidadNullException() {
        Material material = crearUnMaterialConElAtributoUnidadEnNull();

        assertThrows(MaterialConUnidadNullException.class, () ->{
            this.repositorioMaterial.guardar(material);
        });

    }

    @Test
    @Transactional
    public void dadoQueBuscoUnMaterialInexistenteEntoncesDevuelveNull() {
        Material materialObtenido = this.repositorioMaterial.buscarPorNombre("Hierro");
        assertThat(materialObtenido, equalTo(null));
    }

    @Test
    @Transactional
    public void dadoQueGuardoVariosMaterialesEntoncesPuedoBuscarlosTodos() {
        Material material1 = crearMaterialConNombreLadrilloConCantidad100YUnidadPiezas();

        Material material2 = crearMaterialConNombreYesoConCantidad25YUnidadKg();

        this.repositorioMaterial.guardar(material1);
        this.repositorioMaterial.guardar(material2);

        Query query = this.sessionFactory.getCurrentSession().createQuery("FROM Material");
        List<Material> materiales = query.getResultList();

        assertThat(materiales.size(), equalTo(2));
    }

    @Test
    @Transactional
    public void dadoQueActualizoUnMaterialEntoncesLosCambiosSeGuardan() {
        Material material = crearMaterialConNombreMaderaConCantidad20YUnidadPlanchas();

        this.repositorioMaterial.guardar(material);

        Material materialObtenido = this.repositorioMaterial.buscarPorNombre("Madera");
        materialObtenido.setCantidad(30.0);
        this.repositorioMaterial.guardar(materialObtenido);

        Material materialActualizado = this.repositorioMaterial.buscarPorNombre("Madera");
        assertThat(materialActualizado.getCantidad(), equalTo(30.0));
    }

    @Test
    @Transactional
    public void dadoQueEliminoUnMaterialEntoncesYaNoLoEncuentroEnLaBaseDeDatos() {
        Material material = crearUnMaterialConNombreAceroConCantidad15YUnidadKg();

        this.repositorioMaterial.guardar(material);

        Material materialObtenido = this.repositorioMaterial.buscarPorNombre("Acero");
        this.sessionFactory.getCurrentSession().delete(materialObtenido);

        Material materialEliminado = this.repositorioMaterial.buscarPorNombre("Acero");
        assertThat(materialEliminado, equalTo(null));
    }

    @Test
    @Transactional
    public void dadoQueBuscoMaterialesPorUnidadEntoncesObtengoLosCorrectos() {
        Material material1 = crearMaterialConNombreAzulejoConCantidad200YUnidadPiezas();

        Material material2 = crearMaterialConNombreCementoConCantidad50YUnidadKg();

        this.repositorioMaterial.guardar(material1);
        this.repositorioMaterial.guardar(material2);

        String hql = "FROM Material WHERE unidad = :unidad";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("unidad", "piezas");
        List<Material> materiales = query.getResultList();

        assertThat(materiales.size(), equalTo(1));
        assertThat(materiales.get(0).getNombre(), equalTo("Azulejo"));
    }


    // METODOS

    private static @NotNull Material crearMaterialConNombreCementoConCantidad50YUnidadKg() {
        Material material2 = new Material();
        material2.setNombre("Cemento");
        material2.setCantidad(50.0);
        material2.setUnidad("kg");
        return material2;
    }

    private static @NotNull Material crearMaterialConNombreYesoConCantidad25YUnidadKg() {
        Material material2 = new Material();
        material2.setNombre("Yeso");
        material2.setCantidad(25.0);
        material2.setUnidad("kg");
        return material2;
    }

    private static @NotNull Material crearMaterialConNombreLadrilloConCantidad100YUnidadPiezas() {
        Material material1 = new Material();
        material1.setNombre("Ladrillo");
        material1.setCantidad(100.0);
        material1.setUnidad("piezas");
        return material1;
    }

    private static @NotNull Material crearMaterialConNombreMaderaConCantidad20YUnidadPlanchas() {
        Material material = new Material();
        material.setNombre("Madera");
        material.setCantidad(20.0);
        material.setUnidad("planchas");
        return material;
    }

    private static @NotNull Material crearUnMaterialConNombreAceroConCantidad15YUnidadKg() {
        Material material = new Material();
        material.setNombre("Acero");
        material.setCantidad(15.0);
        material.setUnidad("kg");
        return material;
    }

    private static @NotNull Material crearMaterialConNombreAzulejoConCantidad200YUnidadPiezas() {
        Material material1 = new Material();
        material1.setNombre("Azulejo");
        material1.setCantidad(200.0);
        material1.setUnidad("piezas");
        return material1;
    }

    private static @NotNull Material crearUnMaterialConElAtributoNombreEnNull() {
        Material material = new Material();
        material.setNombre(null);  // Nombre nulo
        material.setCantidad(100.0);
        material.setUnidad("kg");
        return material;
    }

    private static @NotNull Material crearMaterialConElAtributoCantidadEnNull() {
        Material material = new Material();
        material.setNombre("cemento");
        material.setCantidad(null);
        material.setUnidad("Kg");
        return material;
    }

    private static @NotNull Material crearUnMaterialConElAtributoUnidadEnNull() {
        Material material = new Material();
        material.setNombre("Cemento");
        material.setCantidad(500.0);
        material.setUnidad(null);  // Unidad nula
        return material;
    }



}
