// Obtener todos los inputs de solo lectura
const inputsReadOnly = document.querySelectorAll('input[readonly]');

// Asignar el evento click a cada input de solo lectura
inputsReadOnly.forEach(input => {
    input.addEventListener('click', function() {
        mostrarMensaje(input.id); // Llamar a la función para mostrar el mensaje correspondiente
    });
});

// La función que muestra el mensaje
function mostrarMensaje(idInput) {
    // Ocultar todos los mensajes antes de mostrar el correcto
    document.querySelectorAll('[id^="mensaje-"]').forEach(mensaje => {
        mensaje.style.display = "none";
    });

    // Muestra el mensaje correspondiente al input que fue clickeado
    const mensaje = document.getElementById(`mensaje-${idInput}`);
    if (mensaje) {
        mensaje.style.display = "block";

        // Asignar el evento al documento para ocultar el mensaje cuando se haga clic fuera
        document.addEventListener('click', function ocultarMensaje(event) {
            // Si el clic no es en el input ni en el mensaje, ocultar el mensaje
            if (event.target !== document.getElementById(idInput) && event.target !== mensaje) {
                mensaje.style.display = "none";
                document.removeEventListener('click', ocultarMensaje);
            }
        });
    }
}