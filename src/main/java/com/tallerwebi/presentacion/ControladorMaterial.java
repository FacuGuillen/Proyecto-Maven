package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioMaterial;
import com.tallerwebi.dominio.modelo.Material;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;

@Controller
public class ControladorMaterial {

    private final ServicioMaterial servicioMaterial;

    @Autowired
    public ControladorMaterial(ServicioMaterial servicioMaterial) {
        this.servicioMaterial = servicioMaterial;
    }

    @RequestMapping(value = "/ofertar", method = RequestMethod.GET)
    public ModelAndView ofertarMateriales() {
        return new ModelAndView("ofertar-material");

    }
    @RequestMapping( value = "/materiales" , method = RequestMethod.GET)
    public ModelAndView mostrarMisMateriales() {
        List<Material> materiales = servicioMaterial.listarMateriales();
        return new ModelAndView("comprar-material").addObject("materiales", materiales);

    }
    @RequestMapping( value = "/misPublicaciones" , method = RequestMethod.GET)
    public ModelAndView mostrarMisPublicaciones(){
        return new ModelAndView("mis-publicaciones");
    }


    @RequestMapping(value = "/nuevo-material", method = RequestMethod.GET)
    public ModelAndView crearMaterial() {
        return new ModelAndView("nuevo-material");
    }

    @RequestMapping(value = "/guardar-material", method = RequestMethod.POST)
    public ModelAndView guardarMaterial(Material material) {
        servicioMaterial.guardarMaterial(material);
        return new ModelAndView("redirect:/materiales");
    }

    @RequestMapping(value = "/actualizar-material", method = RequestMethod.POST)
    public ModelAndView actualizarMaterial(Long materialId, String nombre, Double cantidad, String unidad, Double precio) {

        Material material = servicioMaterial.buscarMaterialPorId(materialId);

        if (material == null) {
            return new ModelAndView("error").addObject("mensaje", "Material no encontrado");
        }
        material.setNombre(nombre);
        material.setCantidad(cantidad);
        material.setUnidad(unidad);
        material.setPrecio(precio);

        servicioMaterial.actualizarMaterial(material);

        return new ModelAndView("actualizar-material").addObject("material", material);
    }

    @RequestMapping(value = "/eliminar-material", method = RequestMethod.POST)
    public ModelAndView eliminarMaterial(@RequestParam("id") Long id) {
        servicioMaterial.eliminarMaterial(id);
        return new ModelAndView("redirect:/materiales");
    }
}
