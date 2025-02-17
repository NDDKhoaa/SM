
document.addEventListener('DOMContentLoaded', function() {
    const storage = JSON.parse(localStorage.getItem('storage'));
    if (storage) {
        document.getElementById('storageId').value = storage.storageID || '';
        document.getElementById('productId').value = storage.productID || '';
        document.getElementById('quantity').value = storage.quantity || '';
        document.getElementById('measurement').value = storage.measurement || '';
        document.getElementById('createdDate').value = storage.createdDate || new Date().toISOString();
    }

    loadProducts();

    function loadProducts() {
        fetch('/product/list')
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok ' + response.statusText);
                }
                return response.json();
            })
            .then(products => {
                const productSelect = document.getElementById('productId');
                productSelect.innerHTML = '<option value="" disabled selected>Select Product</option>';
                products.forEach(product => {
                    const option = document.createElement('option');
                    option.value = product.productID;
                    option.textContent = product.product;
                    productSelect.appendChild(option);
                });
                if (storage) {
                    productSelect.value = storage.productID;
                }
            })
            .catch(error => console.error('Error loading products:', error));
    }

    window.saveStorage = function() {
        const storageId = document.getElementById('storageId').value;
        const productId = document.getElementById('productId').value;
        const quantity = document.getElementById('quantity').value;
        const measurement = document.getElementById('measurement').value;
        const createdDate = document.getElementById('createdDate').value;

        const storage = {
            storageID: storageId ? parseInt(storageId) : null,
            productID: parseInt(productId),
            quantity: parseInt(quantity),
            measurement: measurement,
            createdDate: createdDate,
        };

        const method = storageId ? 'PUT' : 'POST';
        const url = storageId ? `/storage/update/${storageId}` : '/storage/create';

        fetch(url, {
            method: method,
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(storage)
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok ' + response.statusText);
            }
            window.location.href = '/storage';
        })
        .catch(error => console.error('Error saving storage:', error));
    }
});
