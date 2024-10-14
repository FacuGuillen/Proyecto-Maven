package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.implementacion.interfaces.*;
import com.tallerwebi.dominio.modelo.*;
import com.tallerwebi.dominio.modelo.enums.EstadoProyecto;
import com.tallerwebi.infraestructura.config.HibernateInfraestructuraTestConfig;
import org.hibernate.SessionFactory;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {HibernateInfraestructuraTestConfig.class})
public class RepositorioClienteImplTest {

    @Autowired
    private SessionFactory sessionFactory;
    private RepositorioCliente repositorioCliente;
    private RepositorioProfesional repositorioProfesional;
    private RepositorioFormSatisfaction repositorioFormSatisfaction;
    private RepositorioMaterial repositorioMaterial;
    private RepositorioProyecto repositorioProyecto;


    @BeforeEach
    public void init(){
        this.repositorioCliente = new RepositorioClienteImpl(sessionFactory);
        this.repositorioProfesional = new RepositorioProfesionalImpl(sessionFactory);
        this.repositorioFormSatisfaction = new RepositorioFormSatisfactionImpl(sessionFactory);
        this.repositorioMaterial = new RepositorioMaterialImpl(sessionFactory);
        this.repositorioProyecto = new RepositorioProyectoImpl(sessionFactory);
    }

    @Test
    @Transactional
    @Rollback
    public void dadoQueExisteUnRepositorioClienteCuandoGuardoUnClienteEntoncesLoEncuentroEnLaBaseDeDatos(){
        Cliente cliente = crearClienteConDatos();

        this.repositorioCliente.guardar(cliente);

        String hql = "FROM Cliente WHERE email = :email";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("email", "LeoMessiCampeonDelMundoQatar2022");
        Cliente clienteObtenido = (Cliente) query.getSingleResult();

        assertThat(clienteObtenido, equalTo(cliente));
    }

    @Test
    @Transactional
    @Rollback
    public void dadoQueGuardoUnClienteConTelefonoVacioEntoncesSeGuardaCorrectamente() {
        Cliente cliente = new Cliente();
        cliente.setNombre("Ana Gomez");
        cliente.setEmail("anagomez@mail.com");
        cliente.setPassword("pasword123");
        cliente.setTelefono("");  // Teléfono vacío

        this.repositorioCliente.guardar(cliente);

        Cliente clienteObtenido = buscarClientePorEmail("anagomez@mail.com");
        assertThat(clienteObtenido.getTelefono(), equalTo(""));
    }

    @Test
    @Transactional
    @Rollback
    public void dadoQueActualizoUnClienteEntoncesLosDatosSeActualizanEnLaBase() {
        Cliente cliente = crearCliente("Carlos Ruiz", "carlos@mail.com", "987654321", "password123");
        cliente.setNombre("Carlos Ruiz Actualizado");

        this.repositorioCliente.guardar(cliente);

        Cliente clienteObtenido = buscarClientePorEmail("carlos@mail.com");
        assertThat(clienteObtenido.getNombre(), equalTo("Carlos Ruiz Actualizado"));
    }

    @Test
    @Transactional
    @Rollback
    public void dadoQueBuscoUnClienteInexistenteEntoncesNoLoEncuentro() {
        Cliente clienteObtenido = this.repositorioCliente.buscarPorEmail("noexiste@mail.com");
        assertThat(clienteObtenido, equalTo(null));
    }

    @Test
    @Transactional
    @Rollback
    public void dadoQueBuscoClientesConUnNombreEspecificoEntoncesObtengoResultados() {
        Cliente cliente1 = crearCliente("Miguel Ángel", "miguel1@mail.com", "111111111", "password123");
        Cliente cliente2 = crearCliente("Miguel Antonio", "miguel2@mail.com", "222222222", "password123");
        this.repositorioCliente.guardar(cliente1);
        this.repositorioCliente.guardar(cliente2);

        String hql = "FROM Cliente WHERE nombre LIKE :nombre";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("nombre", "Miguel%");

        List<Cliente> clientes = query.getResultList();
        assertThat(clientes.size(), equalTo(2));
    }

    @Test
    @Transactional
    @Rollback
    public void dadoQueGuardoUnClienteConEmailLargoEntoncesSeGuardaCorrectamente() {
        String emailLargo = "emailmuylargo".repeat(7) + "@mail.com";  // 100 caracteres
        Cliente cliente = crearCliente("Cliente Largo", emailLargo, "555555555", "password123");

        this.repositorioCliente.guardar(cliente);

        Cliente clienteObtenido = buscarClientePorEmail(emailLargo);
        assertThat(clienteObtenido.getEmail(), equalTo(emailLargo));
    }


    @Test
    @Transactional
    @Rollback
    public void dadoQueElClienteLeDa5EstrellasComoFormularioDeSatisfactionAlProfesionalYBusque5EstrellasEnBaseDeDatos() {
        Cliente cliente = crearClienteConDatos();
        Profesional profesional = crearProfesionalConDatos();

        repositorioCliente.guardar(cliente);
        repositorioProfesional.guardar(profesional);

        FormSatisfaction formSatisfaction = crearFormSatisfaction(cliente, profesional);

        repositorioFormSatisfaction.guardar(formSatisfaction);

        String hql = "FROM FormSatisfaction fs WHERE fs.clienteForm.id = :clienteId AND fs.profesional.id = :profesionalId";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("clienteId", cliente.getId());
        query.setParameter("profesionalId", profesional.getId());

        FormSatisfaction formObtenido = (FormSatisfaction) query.getSingleResult();

        assertThat(formObtenido.getPuntuacion(), equalTo(5));

    }

    @Test
    @Transactional
    @Rollback
    public void dadoQueElClienteLeDaUnaCriticaEnElFormularioDeSatisfactionAlProfesionalYBusqueLaCriticaEnBaseDeDatos() {
        Cliente cliente = crearClienteConDatos();
        Profesional profesional = crearProfesionalConDatos();

        repositorioCliente.guardar(cliente);
        repositorioProfesional.guardar(profesional);

        FormSatisfaction formSatisfaction = crearFormSatisfaction(cliente, profesional);

        repositorioFormSatisfaction.guardar(formSatisfaction);

        String hql = "FROM FormSatisfaction fs WHERE fs.clienteForm.id = :clienteId AND fs.profesional.id = :profesionalId";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("clienteId", cliente.getId());
        query.setParameter("profesionalId", profesional.getId());

        FormSatisfaction formObtenido = (FormSatisfaction) query.getSingleResult();

        assertThat(formObtenido.getCritica(), equalTo("Muy buen servicio"));

    }


    @Test
    @Transactional
    @Rollback
    public void clienteCreaUnMaterialEnLaBaseDeDatos() {
        Cliente cliente = crearClienteConDatos();
        repositorioCliente.guardar(cliente);

        Material material = crearMaterial(cliente);
        repositorioMaterial.guardar(material);

        String hql = "FROM Material m WHERE m.nombre = :nombre AND m.clienteMaterial.id = :clienteId";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("nombre", material.getNombre());
        query.setParameter("clienteId", cliente.getId());;

        Material materialObtenido = (Material) query.getSingleResult();

        assertThat(materialObtenido.getNombre(), equalTo(material.getNombre()));
        assertThat(materialObtenido.getCantidad(), equalTo(material.getCantidad()));
        assertThat(materialObtenido.getClienteMaterial().getId(), equalTo(cliente.getId()));
    }


    // Metodos
    private FormSatisfaction crearFormSatisfaction(Cliente cliente, Profesional profesional) {
        FormSatisfaction formSatisfaction = new FormSatisfaction();
        formSatisfaction.setPuntuacion(5);
        formSatisfaction.setCritica("Muy buen servicio");
        formSatisfaction.setClienteForm(cliente); // Usa el cliente existente
        formSatisfaction.setProfesional(profesional); // Usa el profesional existente

        return formSatisfaction;
    }

    public static @NotNull Cliente crearClienteConDatos() {
        Cliente cliente = new Cliente();
        cliente.setNombre("Lionel Andres");
        cliente.setApellido("Messi");
        cliente.setEmail("LeoMessiCampeonDelMundoQatar2022");
        cliente.setTelefono("18122022");
        cliente.setPassword("password123");
        return cliente;
    }

    private Cliente crearCliente(String nombre, String email, String telefono, String password) {
        Cliente cliente = new Cliente();
        cliente.setNombre(nombre);
        cliente.setEmail(email);
        cliente.setTelefono(telefono);
        cliente.setPassword(password);
        return cliente;
    }



    private Profesional crearProfesionalConDatos() {
        Profesional profesional = new Profesional();
        profesional.setNombre("Lionel Sebastian");
        profesional.setApellido("Scaloni");
        profesional.setEmail("LionesScaloniCampeonDelMundoQatar2022");
        profesional.setTelefono("18122022");
        return profesional;
    }

    private Cliente buscarClientePorEmail(String email) {
        String hql = "FROM Cliente WHERE email = :email";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("email", email);
        return (Cliente) query.getSingleResult();
    }

    private Material crearMaterial(Cliente cliente){
        Material material = new Material();
        material.setNombre("Cemento");
        material.setCantidad(2.5);
        material.setUnidad("kg");
        material.setClienteMaterial(cliente);
        return material;
    }

    private Proyecto crearProyecto(){
        Proyecto proyecto = new Proyecto();
        proyecto.setNombreProyecto("Romper la casa entera");
        proyecto.setEstadoProyecto(EstadoProyecto.POR_INICIAR);
        proyecto.setFechaInicioProyecto(LocalDate.of(2024, 10, 15));
        return proyecto;
    }

}
