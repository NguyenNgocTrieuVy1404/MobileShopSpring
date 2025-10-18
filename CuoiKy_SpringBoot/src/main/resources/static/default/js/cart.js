/**
 * JavaScript cho trang giỏ hàng
 */

function updateCartQuantity(input) {
    const productId = input.getAttribute('data-product-id');
    const maxStock = parseInt(input.getAttribute('data-stock'));
    let quantity = parseInt(input.value);
    const errorMsg = input.closest('.d-flex.flex-column').querySelector('.stock-error');

    // Kiểm tra và điều chỉnh số lượng
    if (isNaN(quantity) || quantity < 1) {
        quantity = 1;
        input.value = 1;
    } else if (quantity > maxStock) {
        quantity = maxStock;
        input.value = maxStock;
        errorMsg.style.display = 'block';
        setTimeout(() => errorMsg.style.display = 'none', 3000);
        return;
    }

    // Gửi request AJAX để cập nhật giỏ hàng
    fetch(`/shop/cart/update?productId=${productId}&quantity=${quantity}`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
        },
    })
    .then(response => {
        if (response.ok) {
            // Reload trang sau khi cập nhật thành công
            window.location.reload();
        }
    })
    .catch(error => console.error('Error:', error));
} 