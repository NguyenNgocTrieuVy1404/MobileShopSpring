function togglePassword(inputId) {
    const passwordInput = document.getElementById(inputId);
    const passwordToggle = document.getElementById(inputId + 'Toggle');
    
    if (passwordInput.type === 'password') {
        passwordInput.type = 'text';
        passwordToggle.classList.remove('bi-eye');
        passwordToggle.classList.add('bi-eye-slash');
    } else {
        passwordInput.type = 'password';
        passwordToggle.classList.remove('bi-eye-slash');
        passwordToggle.classList.add('bi-eye');
    }
}