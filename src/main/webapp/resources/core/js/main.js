let completedSteps = 0;

function toggleStep(element) {
    element.classList.toggle('completed');

    if (element.classList.contains('completed')) {
        completedSteps++;
    } else {
        completedSteps--;
    }

    updateProgressBar();
}
function updateProgressBar() {
    const totalSteps = document.querySelectorAll('.step').length; // Calcular dinámicamente el total de pasos
    const totalMaterials = document.querySelectorAll('.table tbody tr').length; // Total de materiales
    const checkedMaterials = document.querySelectorAll('.table tbody tr input:checked').length; // Materiales seleccionados

    // Calcular el porcentaje basado en pasos y materiales
    const percentage = Math.min((completedSteps / totalSteps) * 50 + (checkedMaterials / totalMaterials) * 50, 100);

    const progressBar = document.getElementById('progressBar');
    progressBar.style.width = percentage + '%';
    progressBar.setAttribute('aria-valuenow', percentage);
    progressBar.textContent = Math.round(percentage) + '%';

    // Habilitar o deshabilitar el botón según el progreso
    const btnComenzar = document.getElementById('btnComenzar');
    btnComenzar.disabled = percentage !== 100;
}
function updateMaterialStatus(checkbox) {
    if (checkbox.checked) {
        checkbox.closest('tr').style.backgroundColor = '#d4edda';
    } else {
        checkbox.closest('tr').style.backgroundColor = ''; // Reinicia el fondo
    }
    updateProgressBar();
}
document.addEventListener('DOMContentLoaded', function() {

    document.getElementById('menuToggle').onclick = function() {
        let sidebar = document.getElementById('sidebar');
        sidebar.classList.toggle('active');
    };

    document.getElementById('commentToggleCemento').addEventListener('click', function() {
        let commentsSection = document.getElementById('commentsCemento');
        commentsSection.style.display = commentsSection.style.display === 'none' ? 'block' : 'none';
    });
});



