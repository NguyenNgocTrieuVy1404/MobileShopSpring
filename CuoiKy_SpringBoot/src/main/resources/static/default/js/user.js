// Đảm bảo Bootstrap dropdown hoạt động đúng
document.addEventListener('DOMContentLoaded', function() {
    // Kiểm tra nếu Bootstrap đã được tải
    if (typeof bootstrap !== 'undefined') {
        // Kích hoạt tất cả các dropdown
        var dropdownElementList = [].slice.call(document.querySelectorAll('.dropdown-toggle'));
        dropdownElementList.map(function (dropdownToggleEl) {
            return new bootstrap.Dropdown(dropdownToggleEl);
        });
    } else {
        console.error('Bootstrap JavaScript không được tải đúng cách');
    }
});

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