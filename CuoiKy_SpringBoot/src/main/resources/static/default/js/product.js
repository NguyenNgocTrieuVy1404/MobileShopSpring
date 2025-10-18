/**
 * JavaScript cho trang chi tiết sản phẩm
 */

document.addEventListener('DOMContentLoaded', function() {
    // Lấy các phần tử
    const quantityInput = document.getElementById('quantity');
    const decreaseBtn = document.getElementById('decreaseBtn');
    const increaseBtn = document.getElementById('increaseBtn');
    const errorMsg = document.getElementById('quantityError');
    const addToCartForm = document.getElementById('addToCartForm');
    
    // Lấy giá trị tồn kho và số lượng hiện có trong giỏ hàng
    const maxStock = parseInt(quantityInput.getAttribute('data-stock')) || 0;
    const currentCartQuantity = parseInt(document.getElementById('currentCartQuantity').value) || 0;
    
    // Đặt lại số lượng ban đầu là 1
    quantityInput.value = 1;
    
    // Xử lý nút giảm số lượng
    decreaseBtn.addEventListener('click', function() {
        let currentValue = parseInt(quantityInput.value) || 1;
        if (currentValue > 1) {
            quantityInput.value = currentValue - 1;
            // Đảm bảo ẩn thông báo lỗi nếu đang hiển thị
            errorMsg.classList.remove('show');
        }
    });
    
    // Xử lý nút tăng số lượng
    increaseBtn.addEventListener('click', function() {
        let currentValue = parseInt(quantityInput.value) || 1;
        const availableStock = maxStock - currentCartQuantity;
        
        if (currentValue < maxStock) {
            quantityInput.value = currentValue + 1;
            
            // Kiểm tra nếu vượt quá số lượng còn lại sau khi trừ đi số trong giỏ hàng
            if (currentValue + 1 + currentCartQuantity > maxStock) {
                showError('Thêm số lượng này sẽ vượt quá tồn kho');
            }
        } else {
            showError('Vượt quá số lượng tồn kho');
        }
    });
    
    // Validate form trước khi submit
    window.validateQuantity = function() {
        const quantity = parseInt(quantityInput.value) || 0;
        
        if (isNaN(quantity) || quantity < 1) {
            quantityInput.value = 1;
            return true;
        }
        
        if (quantity > maxStock) {
            quantityInput.value = maxStock;
            showError('Vượt quá số lượng tồn kho');
            return false;
        }
        
        // Kiểm tra tổng với số lượng trong giỏ
        if (quantity + currentCartQuantity > maxStock) {
            // Có thể chọn tự động điều chỉnh số lượng thay vì ngăn chặn submit
            const availableQty = Math.max(1, maxStock - currentCartQuantity);
            quantityInput.value = availableQty;
            
            // Hiển thị thông báo
            showError('Đã điều chỉnh số lượng để phù hợp với tồn kho');
            
            // Cho phép form submit sau khi điều chỉnh
            return true;
        }
        
        return true;
    };
    
    // Hiển thị thông báo lỗi
    function showError(message) {
        errorMsg.textContent = message;
        errorMsg.classList.add('show');
        
        // Tự động ẩn sau 3 giây
        setTimeout(function() {
            errorMsg.classList.remove('show');
        }, 3000);
    }
}); 