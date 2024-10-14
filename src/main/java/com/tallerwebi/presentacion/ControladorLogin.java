package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.implementacion.interfaces.ServicioLogin;
import com.tallerwebi.dominio.modelo.Cliente;
import com.tallerwebi.dominio.modelo.Profesional;
import com.tallerwebi.dominio.modelo.Usuario;
import com.tallerwebi.dominio.excepcion.UsuarioExistenteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ControladorLogin {

    private ServicioLogin servicioLogin;

    @Autowired
    public ControladorLogin(ServicioLogin servicioLogin){
        this.servicioLogin = servicioLogin;
    }

    @RequestMapping(value = "/login" )
    public ModelAndView irALogin() {
        ModelMap modelo = new ModelMap();
        modelo.put("datosLogin", new DatosLogin());
        return new ModelAndView("login", modelo);
    }

    @RequestMapping(path = "/validar-login", method = RequestMethod.POST)
    public ModelAndView validarLogin(@ModelAttribute("datosLogin") DatosLogin datosLogin, HttpServletRequest request) {
        ModelMap model = new ModelMap();

        Usuario usuarioBuscado = servicioLogin.consultarUsuario(datosLogin.getEmail(), datosLogin.getPassword());
        if (usuarioBuscado != null) {
            request.getSession().setAttribute("ROL", usuarioBuscado.getRol());
            request.getSession().setAttribute("ID", usuarioBuscado.getId());
            request.getSession().setAttribute("name", usuarioBuscado.getNombre());

            return new ModelAndView("redirect:/home");

        } else {
            model.put("error", "Usuario o clave incorrecta");
        }
        return new ModelAndView("login", model);
    }

    @RequestMapping(path = "/registrarme", method = RequestMethod.POST)
    public ModelAndView registrarme(@ModelAttribute("usuario") Usuario usuario) {
        ModelMap model = new ModelMap();
        try{
            String rol = usuario.getRol();
            Usuario nuevoUsuario;
            if ("cliente".equalsIgnoreCase(rol)) {
                nuevoUsuario = new Cliente();
            } else if ("profesional".equalsIgnoreCase(rol)) {
                nuevoUsuario = new Profesional();
            } else if ("admin".equalsIgnoreCase(rol)) {  // Para roles como admin, permitir el registro sin crear entidad.
                nuevoUsuario = usuario;
            }
            else {
                model.put("error", "Tipo de usuario no válido.");
                return new ModelAndView("nuevo-usuario", model);
            }

            nuevoUsuario.setNombre(usuario.getNombre());
            nuevoUsuario.setApellido(usuario.getApellido());
            nuevoUsuario.setEmail(usuario.getEmail());
            nuevoUsuario.setPassword(usuario.getPassword());
            nuevoUsuario.setRol(rol);

            servicioLogin.registrar(nuevoUsuario);

        } catch (UsuarioExistenteException e){
            model.put("error", "El usuario ya existe");
            return new ModelAndView("nuevo-usuario", model);
        } catch (Exception e){
            model.put("error", "Error al registrar el nuevo usuario");
            return new ModelAndView("nuevo-usuario", model);
        }
        return new ModelAndView("redirect:/login");
    }

    @RequestMapping(path = "/nuevo-usuario", method = RequestMethod.GET)
    public ModelAndView nuevoUsuario() {
        ModelMap model = new ModelMap();
        model.put("usuario", new Usuario());
        return new ModelAndView("nuevo-usuario", model);
    }

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public ModelAndView inicio() {
        return new ModelAndView("redirect:/login");
    }

    @RequestMapping(path = "/home", method = RequestMethod.GET)
    public ModelAndView irAHome() {
        return new ModelAndView("home");
    }
    @RequestMapping("/logout")
    public ModelAndView cerrarSesion(HttpServletRequest request) {
        System.out.println("Nombre de usuario en sesión: " + request.getSession().getAttribute("name"));

        request.getSession().invalidate();

        request.getSession().removeAttribute("name");
        System.out.println("Nombre de usuario despues: " + request.getSession().getAttribute("name"));


        ModelMap modelo = new ModelMap();
        modelo.put("datosLogin", new DatosLogin());
        return new ModelAndView("login", modelo);
}

}

