
document.addEventListener('DOMContentLoaded', function() {
    const productForm = document.getElementById('productForm');
    const product = JSON.parse(localStorage.getItem('product'));

    if (product) {
        document.getElementById('productId').value = product.productID;
        document.getElementById('name').value = product.product;
        document.getElementById('description').value = product.description;
        document.getElementById('color').value = product.color;
        document.getElementById('type').value = product.type;
        document.getElementById('price').value = product.price;
        document.getElementById('manufactureDate').value = product.manufactureDate;
        document.getElementById('expirationDate').value = product.expirationDate;
        document.getElementById('category').value = product.category;
    }

    if (productForm) {
        productForm.addEventListener('submit', function(event) {
            event.preventDefault();
            const id = document.getElementById('productId').value;
            const name = document.getElementById('name').value;
            const description = document.getElementById('description').value;
            const color = document.getElementById('color').value;
            const type = document.getElementById('type').value;
            const price = document.getElementById('price').value;
            const manufactureDate = document.getElementById('manufactureDate').value;
            const expirationDate = document.getElementById('expirationDate').value;
            const category = document.getElementById('category').value;

            const product = { product: name, description, color, type, price, manufactureDate, expirationDate, category };

            if (id) {
                updateProduct(id, product);
            } else {
                createProduct(product);
            }
        });
    }

    function createProduct(product) {
        fetch('/product/create', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(product)
        })
        .then(response => response.json())
        .then(() => {
            document.getElementById('productForm').reset();
            window.location.href = '/product';
        });
    }

    function updateProduct(id, product) {
        fetch(`/product/update/${id}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(product)
        })
        .then(response => response.json())
        .then(() => {
            document.getElementById('productForm').reset();
            window.location.href = '/product';
        });
    }
});
