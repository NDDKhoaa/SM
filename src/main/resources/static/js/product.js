
document.addEventListener('DOMContentLoaded', function() {
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
				const tbody = document.getElementById('productTableBody');
				tbody.innerHTML = '';
				products.forEach(product => {
					const row = document.createElement('tr');
					row.innerHTML = `
                        <td>${product.productID}</td>
                        <td>${product.product}</td>
                        <td>${product.description}</td>
                        <td>${product.color}</td>
                        <td>${product.type}</td>
                        <td>${product.price}</td>
                        <td>${product.manufactureDate}</td>
                        <td>${product.expirationDate}</td>
                        <td>${product.category}</td>
                        <td>
                            <div class="action-buttons">
                                <button class="edit-btn" onclick="editProduct(${product.productID})">Edit</button>
                                <button class="delete-btn" onclick="deleteProduct(${product.productID})">Delete</button>
                            </div>
                        </td>
                    `;
					tbody.appendChild(row);
				});
			})
			.catch(error => console.error('Error loading products:', error));
	}

	window.createProduct = function() {
		localStorage.removeItem('product');
		window.location.href = '/product-form';
	}

	window.editProduct = function(id) {
		fetch(`/product/view/${id}`)
			.then(response => {
				if (!response.ok) {
					throw new Error('Network response was not ok ' + response.statusText);
				}
				return response.json();
			})
			.then(product => {
				localStorage.setItem('product', JSON.stringify(product));
				window.location.href = '/product-form';
			})
			.catch(error => console.error('Error fetching product:', error));
	}

	window.deleteProduct = function(id) {
	    fetch(`/product/delete/${id}`, {
	        method: 'DELETE'
	    })
	    .then(response => {
	        if (!response.ok) {
	            throw new Error('Network response was not ok ' + response.statusText);
	        }
	        loadProducts();
	    })
	    .catch(error => console.error('Error deleting product:', error));
	}
});
