package com.tallerwebi.presentacion;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;


    @ControllerAdvice
    public class GlobalControllerAdvice {

        /**
         * Método agregarNombreUsuario que se invoca automáticamente en cada petición para añadir un atributo
         * llamado "nombreUsuario" a todas las vistas, siempre y cuando la solicitud no corresponda a las rutas
         * de "/login" o "/logout". Este atributo se obtiene de la sesión actual del usuario.
         *
         * @param request El objeto HttpServletRequest de la petición actual, utilizado para obtener la URI y
         *                los atributos de la sesión.
         * @return El nombre de usuario almacenado en la sesión o null si la petición corresponde a las rutas
         *         excluidas.
         */
        @ModelAttribute("nombreUsuario")
        public String agregarNombreUsuario(HttpServletRequest request) {
            // Obtener la URI de la solicitud
            String requestURI = request.getRequestURI();
            System.out.println("requestURI = " + requestURI);

            // Recuperar el nombre del usuario desde la sesión
            return (String) request.getSession().getAttribute("name");
        }
    }

