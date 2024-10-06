const btn_carrito = document.getElementById("carrito");
const modal_carrito = document.getElementById("modal-carrito");

btn_carrito.addEventListener("click", (e) => {
    e.preventDefault();
    modal_carrito.classList.toggle("display-none");
});