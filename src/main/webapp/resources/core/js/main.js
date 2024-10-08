
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



