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

            // Excluir "nombreUsuario" de la ruta /login y /logout
            if (requestURI.equals("/login") || requestURI.equals("/logout")) {
                return null;  // No agregar el atributo en estas rutas
            }

            // Recuperar el nombre del usuario desde la sesión
            String nombreUsuario = (String) request.getSession().getAttribute("name");
            return nombreUsuario != null ? nombreUsuario : ""; // Valor por defecto
        }
    }

