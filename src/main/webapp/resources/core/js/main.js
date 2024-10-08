
document.addEventListener('DOMContentLoaded', function() {

    document.getElementById('menuToggle').onclick = function() {
        let sidebar = document.getElementById('sidebar');
        sidebar.classList.toggle('active');
    };
    // Agregar evento a los botones de útiles
    document.querySelectorAll('.useful-btn').forEach(button => {
        button.addEventListener('click', function() {
            let comentarioId = this.getAttribute('data-id');

            //fecth permite realizar solicitudes HTTP de manera asíncrona
            fetch(`/spring/agregarUtil?comentarioId=${comentarioId}`, {
                method: 'POST'
            })
                .then(response => {
                    if (!response.ok) {
                        throw new Error('Network response was not ok');
                    }
                    return response.json();
                })
                .then(data => {
                    // Actualizar el contador de útiles
                    this.querySelector('span').textContent = data;

                    // Destacar el comentario con más útiles
                    destacarComentarioMasUtil();
                })
                .catch(error => console.error('Error:', error));
        });
    });

    // Llamar a la función para destacar el comentario con más útiles al cargar la página
    destacarComentarioMasUtil();
});

// Función para destacar el comentario con más útiles
function destacarComentarioMasUtil() {
    // Obtener todos los comentarios
    const comentarios = document.querySelectorAll('#comentariosList .card');
    let maxUtiles = -1;
    let comentarioMasUtil = null;

    // Recorrer los comentarios para encontrar el que tiene más útiles
    comentarios.forEach(comentario => {
        const contadorUtiles = parseInt(comentario.querySelector('.useful-btn span').textContent);
        if (contadorUtiles > maxUtiles) {
            maxUtiles = contadorUtiles;
            comentarioMasUtil = comentario;
        }
    });

    // Limpiar el estilo de todos los comentarios antes de aplicar el nuevo estilo
    comentarios.forEach(c => c.classList.remove('destacar-comentario'));

    // Aplicar un estilo especial al comentario más útil
    if (comentarioMasUtil) {
        comentarioMasUtil.classList.add('destacar-comentario');
    }
}



