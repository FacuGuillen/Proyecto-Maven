package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Cliente;
import com.tallerwebi.dominio.RepositorioCliente;
import com.tallerwebi.infraestructura.config.HibernateInfraestructuraTestConfig;
import org.hibernate.SessionFactory;
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
public class RepositorioClienteImplTest {

    @Autowired
    private SessionFactory sessionFactory;
    private RepositorioCliente repositorioCliente;

    @BeforeEach
    public void init(){
        this.repositorioCliente = new RepositorioClienteImpl(sessionFactory);
    }

    @Test
    @Transactional
    public void dadoQueExisteUnRepositorioClienteCuandoGuardoUnClienteEntoncesLoEncuentroEnLaBaseDeDatos(){
        Cliente cliente = new Cliente();
        cliente.setNombre("Juan Perez");
        cliente.setEmail("juanperez@mail.com");
        cliente.setTelefono("123456789");

        this.repositorioCliente.guardar(cliente);

        String hql = "FROM Cliente WHERE email = :email";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("email", "juanperez@mail.com");
        Cliente clienteObtenido = (Cliente) query.getSingleResult();

        assertThat(clienteObtenido, equalTo(cliente));
    }

    @Test
    @Transactional
    public void dadoQueGuardoUnClienteConTelefonoVacioEntoncesSeGuardaCorrectamente() {
        Cliente cliente = new Cliente();
        cliente.setNombre("Ana Gomez");
        cliente.setEmail("anagomez@mail.com");
        cliente.setTelefono("");  // Teléfono vacío

        this.repositorioCliente.guardar(cliente);

        Cliente clienteObtenido = buscarClientePorEmail("anagomez@mail.com");
        assertThat(clienteObtenido.getTelefono(), equalTo(""));
    }

    @Test
    @Transactional
    public void dadoQueActualizoUnClienteEntoncesLosDatosSeActualizanEnLaBase() {
        Cliente cliente = crearCliente("Carlos Ruiz", "carlos@mail.com", "987654321");
        cliente.setNombre("Carlos Ruiz Actualizado");

        this.repositorioCliente.guardar(cliente);

        Cliente clienteObtenido = buscarClientePorEmail("carlos@mail.com");
        assertThat(clienteObtenido.getNombre(), equalTo("Carlos Ruiz Actualizado"));
    }

    @Test
    @Transactional
    public void dadoQueBuscoUnClienteInexistenteEntoncesNoLoEncuentro() {
        Cliente clienteObtenido = this.repositorioCliente.buscarPorEmail("noexiste@mail.com");
        assertThat(clienteObtenido, equalTo(null));
    }

    @Test
    @Transactional
    public void dadoQueIntentoGuardarUnClienteConNombreNuloEntoncesFalla() {
        Cliente cliente = new Cliente();
        cliente.setNombre(null);
        cliente.setEmail("sinNombre@mail.com");
        cliente.setTelefono("111222333");

        assertThrows(Exception.class, () -> {
            this.repositorioCliente.guardar(cliente);
        });
    }

    @Test
    @Transactional
    public void dadoQueIntentoGuardarUnClienteConEmailNuloEntoncesFalla() {
        Cliente cliente = new Cliente();
        cliente.setNombre("Juan Carlos");
        cliente.setEmail(null);  // Email nulo
        cliente.setTelefono("123456789");

        assertThrows(Exception.class, () -> {
            this.repositorioCliente.guardar(cliente);
        });
    }

    @Test
    @Transactional
    public void dadoQueBuscoClientesConUnNombreEspecificoEntoncesObtengoResultados() {
        Cliente cliente1 = crearCliente("Miguel Ángel", "miguel1@mail.com", "111111111");
        Cliente cliente2 = crearCliente("Miguel Antonio", "miguel2@mail.com", "222222222");
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
    public void dadoQueGuardoUnClienteConEmailLargoEntoncesSeGuardaCorrectamente() {
        String emailLargo = "emailmuylargo".repeat(7) + "@mail.com";  // 100 caracteres
        Cliente cliente = crearCliente("Cliente Largo", emailLargo, "555555555");

        this.repositorioCliente.guardar(cliente);

        Cliente clienteObtenido = buscarClientePorEmail(emailLargo);
        assertThat(clienteObtenido.getEmail(), equalTo(emailLargo));
    }

    private Cliente crearCliente(String nombre, String email, String telefono) {
        Cliente cliente = new Cliente();
        cliente.setNombre(nombre);
        cliente.setEmail(email);
        cliente.setTelefono(telefono);
        return cliente;
    }

    private Cliente buscarClientePorEmail(String email) {
        String hql = "FROM Cliente WHERE email = :email";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("email", email);
        return (Cliente) query.getSingleResult();
    }
}
// Commit //