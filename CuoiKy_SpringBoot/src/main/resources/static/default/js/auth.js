/**
 * Authentication JavaScript functions for login and register pages
 */

// Hàm ẩn/hiện mật khẩu
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

// Client-side validation for login form
document.addEventListener('DOMContentLoaded', function() {
    const loginForm = document.getElementById('loginForm');
    
    // Only run validation if we're on the login page
    if (loginForm) {
        const usernameInput = document.getElementById('username');
        const passwordInput = document.getElementById('password');
        
        // Validate form on submit
        loginForm.addEventListener('submit', function(event) {
            let isValid = true;
            
            // Validate username
            if (usernameInput.value.trim() === '') {
                usernameInput.classList.add('is-invalid');
                isValid = false;
            } else {
                usernameInput.classList.remove('is-invalid');
            }
            
            // Validate password
            if (passwordInput.value.trim() === '') {
                passwordInput.classList.add('is-invalid');
                isValid = false;
            } else {
                passwordInput.classList.remove('is-invalid');
            }
            
            if (!isValid) {
                event.preventDefault();
            }
        });
        
        // Clear validation on input
        usernameInput.addEventListener('input', function() {
            if (usernameInput.value.trim() !== '') {
                usernameInput.classList.remove('is-invalid');
            }
        });
        
        passwordInput.addEventListener('input', function() {
            if (passwordInput.value.trim() !== '') {
                passwordInput.classList.remove('is-invalid');
            }
        });
    }
}); 