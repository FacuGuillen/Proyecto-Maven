function hideAlert() {
    const alertMessage = document.getElementById('alertMessage');
    if (alertMessage) {
        alertMessage.style.display = 'none';
    }
}

setTimeout(hideAlert, 10000);