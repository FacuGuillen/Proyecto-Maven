const btn_carrito = document.getElementById("carrito");
const modal_carrito = document.getElementById("modal-carrito");
const contador_productos = document.querySelector(".contador-productos");
let cantidadEnCarrito = 0;

// Función para agregar producto al carrito
function agregarAlCarrito(productName, quantity) {
    cantidadEnCarrito += parseInt(quantity); // Incrementar la cantidad en el carrito
    contador_productos.textContent = cantidadEnCarrito; // Actualizar contador
    mostrarModal(productName); // Mostrar modal con el nombre del producto
}

// Función para mostrar el modal
function mostrarModal(productName) {
    if (modal_carrito.classList.contains("display-none")) {
        modal_carrito.classList.remove("display-none");
        modal_carrito.classList.add("animate__animated", "animate__fadeIn");
        modal_carrito.innerHTML = `<p class="titulo-popup-carrito"> Producto agregado: ${productName}</p>`; // Agregar nombre del producto al modal
    } else {
        modal_carrito.classList.remove("animate__fadeIn");
        modal_carrito.classList.add("animate__fadeOut");

        modal_carrito.addEventListener('animationend', function() {
            modal_carrito.classList.add("display-none");
            modal_carrito.classList.remove("animate__animated", "animate__fadeOut");
        }, { once: true });
    }
}

// Evento para cada botón de agregar al carrito
document.querySelectorAll('.add-to-cart').forEach(btn => {
    btn.addEventListener('click', function(e) {
        e.preventDefault();
        const productName = this.getAttribute('data-product-name');
        const quantity = this.closest('.card-body').querySelector('#cantidad').value; // Obtener cantidad seleccionada
        agregarAlCarrito(productName, quantity); // Llamar a la función para agregar al carrito
    });
});

// Código existente para manejar el modal
modal_carrito.addEventListener('click', function(e) {
    e.stopPropagation();
});

document.addEventListener("click", function(e) {
    if (!modal_carrito.contains(e.target) && e.target !== btn_carrito && !modal_carrito.classList.contains("display-none")) {
        modal_carrito.classList.remove("animate__fadeIn");
        modal_carrito.classList.add("animate__fadeOut");

        modal_carrito.addEventListener('animationend', function() {
            modal_carrito.classList.add("display-none");
            modal_carrito.classList.remove("animate__animated", "animate__fadeOut");
        }, { once: true });
    }
});