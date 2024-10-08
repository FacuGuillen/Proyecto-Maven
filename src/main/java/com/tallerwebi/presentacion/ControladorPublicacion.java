package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.excepcion.ConsultaNoEncontradaException;
import com.tallerwebi.dominio.excepcion.UsuarioNoEncontradoException;
import com.tallerwebi.dominio.excepcion.UsuarioSinPermisosException;
import com.tallerwebi.dominio.implementacion.interfaces.ServicioCliente;
import com.tallerwebi.dominio.implementacion.interfaces.ServicioPublicacion;
import com.tallerwebi.dominio.modelo.Cliente;
import com.tallerwebi.dominio.modelo.Comentario;
import com.tallerwebi.dominio.modelo.Consulta;
import com.tallerwebi.dominio.modelo.Publicacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller //Indica que es responsable de manejar las solicitudes web.
public class ControladorPublicacion {

    private final ServicioPublicacion servicioPublicacion;


    @Autowired
    public ControladorPublicacion(ServicioPublicacion servicioPublicacion) {
        this.servicioPublicacion = servicioPublicacion;
    }


    @RequestMapping(value = "/ofertar", method = RequestMethod.GET)
    public ModelAndView ofertarMateriales() {
        return new ModelAndView("ofertar-material");
    }

    @RequestMapping(value = "/guardarPublicacion", method = RequestMethod.POST)
    public ModelAndView guardarPublicacion(@RequestParam("nombre") String nombre,
                                           @RequestParam("precio") Double precio,
                                           @RequestParam("stock") Integer stock) {
        Publicacion publicacion = new Publicacion();
        publicacion.setNombre(nombre);
        publicacion.setPrecio(precio);
        publicacion.setStock(stock);

        servicioPublicacion.guardarPublicacion(publicacion); // Asumiendo que este es el servicio correcto para guardar la publicación.
        return new ModelAndView("mis-publicaciones");
    }

//    @RequestMapping(value = "/editarPublicacion", method = RequestMethod.PUT)
//    public ModelAndView modificarPublicacion(){
//
//    }

}
