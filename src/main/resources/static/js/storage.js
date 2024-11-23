// Fetch products and populate the dropdown
function loadProducts() {
	fetch('/api/products')
		.then(response => response.json())
		.then(products => {
			const productSelect = document.getElementById('productId');
			productSelect.innerHTML = '<option value="" disabled selected>Select Product</option>'; // Reset options

			products.forEach(product => {
				const option = document.createElement('option');
				option.value = product.productID;  // Use productID as the option value
				option.textContent = product.product;  // Display the product name
				productSelect.appendChild(option);
			});
		})
		.catch(error => console.error('Error loading products:', error));
}

// Add or Edit Storage
function saveStorage() {
	const storageId = document.getElementById('storageId').value;
	const productId = document.getElementById('productId').value;
	const quantity = document.getElementById('quantity').value;
	const status = document.getElementById('status').value;

	const payload = {
		productId: productId,
		quantity: quantity,
		status: status
	};

	const method = storageId ? 'PUT' : 'POST';
	const url = storageId
		? `http://localhost:8080/api/storages/${storageId}` // Edit existing storage
		: 'http://localhost:8080/api/storages';  // Add new storage

	fetch(url, {
		method: method,
		headers: { 'Content-Type': 'application/json' },
		body: JSON.stringify(payload)
	})
		.then(response => response.json())
		.then(data => {
			console.log('Storage saved:', data);
			loadStorages();  // Reload storage table after add/edit
			document.getElementById('storageForm').reset(); // Reset the form
			document.getElementById('storageId').value = ''; // Reset hidden storageId field
		})
		.catch(error => console.error('Error saving storage:', error));
}

// Load all storages
function loadStorages() {
	fetch('/api/storages')
		.then(response => response.json())
		.then(storages => {
			const tableBody = document.getElementById('storageTable').querySelector('tbody');
			tableBody.innerHTML = '';  // Clear the table

			storages.forEach(storage => {
				const row = document.createElement('tr');
				row.innerHTML = `
                    <td>${storage.storageID}</td>
                    <td>${storage.product ? storage.product.product : 'No Product'}</td>  <!-- Display product name -->
                    <td>${storage.quantity}</td>
                    <td>${storage.status}</td>
                    <td>
                        <button class="edit-btn" onclick="editStorage(${storage.storageID})">Edit</button>
                        <button class="delete-btn" onclick="deleteStorage(${storage.storageID})">Delete</button>
                    </td>
                `;
				tableBody.appendChild(row);  // Append the row to the table body
			});
		})
		.catch(error => console.error('Error loading storages:', error));
}


// Edit a storage
function editStorage(storageID) {
	fetch(`/api/storages/${storageID}`)
		.then(response => response.json())
		.then(storage => {
			document.getElementById('storageId').value = storage.storageID;
			document.getElementById('productId').value = storage.product.productID;  // Set product ID
			document.getElementById('quantity').value = storage.quantity;
			document.getElementById('status').value = storage.status;
		})
		.catch(error => console.error('Error editing storage:', error));
}

// Delete a storage
function deleteStorage(storageID) {
	if (confirm('Are you sure you want to delete this storage?')) {
		fetch(`/api/storages/${storageID}`, {
			method: 'DELETE',
		})
			.then(response => {
				if (response.ok) {
					alert('Storage deleted successfully!');
					loadStorages();  // Reload storage table after delete
				} else {
					alert('Failed to delete storage');
				}
			})
			.catch(error => console.error('Error deleting storage:', error));
	}
}

// Load products and storages when the page is loaded
document.addEventListener('DOMContentLoaded', () => {
	loadProducts();
	loadStorages();
});
