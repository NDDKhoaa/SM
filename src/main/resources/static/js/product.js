function loadProducts() {
	fetch('http://localhost:8080/api/products')  // Fetch products from the API
		.then(response => response.json())  // Parse the JSON response
		.then(data => {
			console.log('Products data:', data);  // Log the fetched data to verify it

			const productTable = document.getElementById('productTable').getElementsByTagName('tbody')[0];
			productTable.innerHTML = '';  // Clear existing rows

			// Check if data is non-empty
			if (data && data.length > 0) {
				data.forEach(product => {
					const row = productTable.insertRow();
					row.innerHTML = `
                        <td>${product.productID}</td>
                        <td>${product.product}</td>
                        <td>${product.category ? product.category.category : 'N/A'}</td> <!-- Display category name -->
                        <td>${product.manufactureDate}</td>
                        <td>${product.expirationDate}</td>
                        <td>${product.color || 'N/A'}</td>
                        <td>${product.type}</td>
                        <td>${product.price}</td>
                        <td>
                            <button onclick="editProduct(${product.productID})">Edit</button>
                            <button class="delete-btn" onclick="deleteProduct(${product.productID})" style="background-color: red;">Delete</button>
                        </td>
                    `;
				});
			} else {
				// If no products are available, add a placeholder row
				const row = productTable.insertRow();
				row.innerHTML = '<td colspan="8">No products available</td>';
			}
		})
		.catch(error => {
			console.error('Error fetching products:', error);
		});
}


// Fetch and display categories to populate the dropdown
function loadCategories() {
    fetch('http://localhost:8080/api/categories')
        .then(response => response.json())
        .then(categories => {
            const categorySelect = document.getElementById('category');
            categories.forEach(category => {
                const option = document.createElement('option');
                option.value = category.categoryID;
                option.textContent = category.category;
                categorySelect.appendChild(option);
            });
        })
        .catch(error => console.error('Error fetching categories:', error));
}

// Add or Update Product
function saveProduct() {
	const productID = document.getElementById('productID').value;
	const product = {
		product: document.getElementById('product').value,
		categoryID: parseInt(document.getElementById('category').value), // Get selected category ID
		manufactureDate: document.getElementById('manufactureDate').value,
		expirationDate: document.getElementById('expirationDate').value,
		color: document.getElementById('color').value,
		type: document.getElementById('type').value,
		price: parseFloat(document.getElementById('price').value),
	};

	const method = productID ? 'PUT' : 'POST';
	const url = productID
		? `http://localhost:8080/api/products/${productID}`
		: 'http://localhost:8080/api/products';

	fetch(url, {
		method: method,
		headers: { 'Content-Type': 'application/json' },
		body: JSON.stringify(product),
	})
		.then(response => {
			if (response.ok) {
				alert(`Product ${productID ? 'updated' : 'added'} successfully!`);
				loadProducts(); // Reload the products list
				document.getElementById('productForm').reset(); // Reset the form
				document.getElementById('productID').value = ''; // Reset hidden productID field
			} else {
				alert('Failed to save product.');
			}
		})
		.catch(error => console.error('Error saving product:', error));
}


// Edit product (populate form with product data)
function editProduct(productID) {
	fetch(`http://localhost:8080/api/products/${productID}`)
		.then(response => response.json())
		.then(product => {
			// Pre-fill the form with product data
			document.getElementById('productID').value = product.productID;
			document.getElementById('product').value = product.product;
			document.getElementById('category').value = product.category ? product.category.categoryID : ''; // Set category
			document.getElementById('manufactureDate').value = product.manufactureDate;
			document.getElementById('expirationDate').value = product.expirationDate;
			document.getElementById('color').value = product.color || '';
			document.getElementById('type').value = product.type;
			document.getElementById('price').value = product.price;

			// Change the form submit behavior to update the product
			const form = document.getElementById('productForm');
			form.onsubmit = function(event) {
				event.preventDefault(); // Prevent default form submission
				saveProduct(); // Call saveProduct to update the product
			};
		})
		.catch(error => {
			console.error('Error fetching product data:', error);
			alert('Failed to fetch product data.');
		});
}

// Delete product
function deleteProduct(productID) {
	if (confirm('Are you sure you want to delete this product?')) {
		fetch(`http://localhost:8080/api/products/${productID}`, {
			method: 'DELETE',
		})
			.then(response => {
				if (response.ok) {
					alert('Product deleted successfully!');
					loadProducts(); // Reload the product list
				} else {
					alert('Failed to delete product.');
				}
			})
			.catch(error => console.error('Error deleting product:', error));
	}
}

// Initialize the page
window.onload = function() {
	loadProducts(); // Load products on page load
	loadCategories(); // Load categories for dropdown
};
