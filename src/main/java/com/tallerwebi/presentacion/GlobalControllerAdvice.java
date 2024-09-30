package com.tallerwebi.presentacion;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;


    @ControllerAdvice
    public class GlobalControllerAdvice {

        @ModelAttribute("nombreUsuario")
        public String agregarNombreUsuario(HttpServletRequest request) {
            // Obtener la URI de la solicitud
            String requestURI = request.getRequestURI();
            System.out.println("requestURI = " + requestURI);

            // Excluir "nombreUsuario" de la ruta /login y /logout
            if (requestURI.equals("/spring/login")) {
                return null;  // No agregar el atributo en estas rutas
            }

            // Recuperar el nombre del usuario desde la sesión
            return (String) request.getSession().getAttribute("name");
        }
    }

