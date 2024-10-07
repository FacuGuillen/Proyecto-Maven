const btn_carrito = document.getElementById("carrito");
const modal_carrito = document.getElementById("modal-carrito");

btn_carrito.addEventListener("click", function(e) {
    e.preventDefault();

    if (modal_carrito.classList.contains("display-none")) {
        // Si el modal está oculto, mostrarlo con la animación fadeIn
        modal_carrito.classList.remove("display-none");
        modal_carrito.classList.add("animate__animated", "animate__fadeIn");
    } else {
        // Si el modal está visible, agregar la animación fadeOut
        modal_carrito.classList.remove("animate__fadeIn");
        modal_carrito.classList.add("animate__fadeOut");

        // Cuando termina la animación de salida, ocultar el modal
        modal_carrito.addEventListener('animationend', function() {
            modal_carrito.classList.add("display-none");
            modal_carrito.classList.remove("animate__animated", "animate__fadeOut");
        }, { once: true });  // Solo ejecutar el listener una vez
    }
});

modal_carrito.addEventListener('click', function(e) {
    e.stopPropagation();
});

// Cerrar modal cuando se hace clic fuera del modal
document.addEventListener("click", function(e) {
    // Verifica que el clic sea fuera del modal y fuera del botón de carrito
    if (!modal_carrito.contains(e.target) && e.target !== btn_carrito && !modal_carrito.classList.contains("display-none")) {
        // Aplicar la animación fadeOut
        modal_carrito.classList.remove("animate__fadeIn");
        modal_carrito.classList.add("animate__fadeOut");

        // Cuando termina la animación, ocultar el modal
        modal_carrito.addEventListener('animationend', function() {
            modal_carrito.classList.add("display-none");
            modal_carrito.classList.remove("animate__animated", "animate__fadeOut");
        }, { once: true });  // Solo ejecutar una vez
    }
});

modal_carrito.addEventListener('click', function(e) {
    e.stopPropagation(); // Evitar cerrar el modal cuando se hace clic dentro de él
});