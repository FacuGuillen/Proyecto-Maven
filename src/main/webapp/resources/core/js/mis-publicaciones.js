
const toggleContainer = document.querySelector('.toggle-container');
const toggleText = document.getElementById('toggleText');

toggleContainer.addEventListener('click', () => {
  toggleContainer.classList.toggle('active');
  toggleText.textContent = toggleContainer.classList.contains('active') ? 'No' : 'Si';
});
