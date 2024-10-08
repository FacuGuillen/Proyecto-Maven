const btn_carrito = document.getElementById("carrito");
const modal_carrito = document.getElementById("modal-carrito");

btn_carrito.addEventListener("click", function(e) {
    e.preventDefault();

    if (modal_carrito.classList.contains("display-none")) {

        modal_carrito.classList.remove("display-none");
        modal_carrito.classList.add("animate_animated", "animate_fadeIn");
    } else {

        modal_carrito.classList.remove("animate__fadeIn");
        modal_carrito.classList.add("animate__fadeOut");


        modal_carrito.addEventListener('animationend', function() {
            modal_carrito.classList.add("display-none");
            modal_carrito.classList.remove("animate_animated", "animate_fadeOut");
        }, { once: true });
    }
});

modal_carrito.addEventListener('click', function(e) {
    e.stopPropagation();
});


document.addEventListener("click", function(e) {
    if (!modal_carrito.contains(e.target) && e.target !== btn_carrito && !modal_carrito.classList.contains("display-none")) {

        modal_carrito.classList.remove("animate__fadeIn");
        modal_carrito.classList.add("animate__fadeOut");

        modal_carrito.addEventListener('animationend', function() {
            modal_carrito.classList.add("display-none");
            modal_carrito.classList.remove("animate_animated", "animate_fadeOut");
        }, { once: true });
    }
});

modal_carrito.addEventListener('click', function(e) {
    e.stopPropagation(); // Evitar cerrar el modal cuando se hace clic dentro de él
});