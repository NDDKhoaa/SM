
let selectedProducts = [];
let selectedCustomerId = null;
let selectedBranchId = null;

// Load customers and populate dropdown
function loadCustomers() {
	fetch('/api/customers')
		.then(response => response.json())
		.then(customers => {
			const customerSelect = document.getElementById('customerSelect');
			customers.forEach(customer => {
				const option = document.createElement('option');
				option.value = customer.customerID;
				option.textContent = `${customer.firstName} ${customer.lastName}`;
				customerSelect.appendChild(option);
			});

			customerSelect.addEventListener('change', () => {
				selectedCustomerId = customerSelect.value;
			});
		})
		.catch(error => console.error('Error loading customers:', error));
}

// Load branches and populate dropdown
function loadBranches() {
	fetch('/api/branches')
		.then(response => response.json())
		.then(branches => {
			const branchSelect = document.getElementById('branchSelect');
			branches.forEach(branch => {
				const option = document.createElement('option');
				option.value = branch.branchID;
				option.textContent = branch.branchName;
				branchSelect.appendChild(option);
			});

			branchSelect.addEventListener('change', () => {
				selectedBranchId = branchSelect.value;
			});
		})
		.catch(error => console.error('Error loading branches:', error));
}

// Load products from API
function loadProducts() {
	fetch('/api/products')
		.then(response => response.json())
		.then(products => {
			const productGrid = document.getElementById('productGrid');
			productGrid.innerHTML = ''; // Clear grid

			products.forEach(product => {
				const productCard = document.createElement('div');
				productCard.classList.add('product-card');
				productCard.innerHTML = `
                    <h3>${product.product}</h3>
                    <p>Price: $${product.price.toFixed(2)}</p>
                    <input type="number" id="quantity-${product.productID}" placeholder="Quantity" min="1" value="1">
                    <button onclick="addToBill(${product.productID}, '${product.product}', ${product.price})">Add</button>
                `;
				productGrid.appendChild(productCard);
			});
		})
		.catch(error => console.error('Error loading products:', error));
}

// Add product to bill
function addToBill(productId, productName, price) {
	const quantityInput = document.getElementById(`quantity-${productId}`);
	const quantity = parseInt(quantityInput.value);

	const existingProduct = selectedProducts.find(item => item.productId === productId);

	if (existingProduct) {
		existingProduct.quantity += quantity;
		existingProduct.totalPrice += price * quantity;
	} else {
		selectedProducts.push({
			productId,
			productName,
			quantity,
			totalPrice: price * quantity,
		});
	}

	updateBillDisplay();
}

// Update bill display
function updateBillDisplay() {
	const billItems = document.getElementById('billItems');
	const subtotalElement = document.getElementById('subtotal');
	const taxElement = document.getElementById('tax');
	const totalElement = document.getElementById('total');

	billItems.innerHTML = ''; // Clear bill items

	if (selectedProducts.length === 0) {
		billItems.innerHTML = '<p>No items in the bill.</p>';
	} else {
		selectedProducts.forEach(item => {
			const itemRow = document.createElement('div');
			itemRow.classList.add('bill-item');
			itemRow.innerHTML = `
                <p>${item.productName} x${item.quantity} - $${item.totalPrice.toFixed(2)}</p>
                <button onclick="removeFromBill(${item.productId})">Remove</button>
            `;
			billItems.appendChild(itemRow);
		});
	}

	const subtotal = selectedProducts.reduce((sum, item) => sum + item.totalPrice, 0);
	const tax = subtotal * 0.1; // 10% tax
	const total = subtotal + tax;

	subtotalElement.textContent = subtotal.toFixed(2);
	taxElement.textContent = tax.toFixed(2);
	totalElement.textContent = total.toFixed(2);
}

// Remove product from bill
function removeFromBill(productId) {
	selectedProducts = selectedProducts.filter(item => item.productId !== productId);
	updateBillDisplay();
}

// Checkout
function checkout() {
	if (!selectedCustomerId) {
		alert('Please select a customer.');
		return;
	}

	if (!selectedBranchId) {
		alert('Please select a branch.');
		return;
	}

	const billPayload = {
		employeeId: 1, // Replace with actual employee ID
		branchId: selectedBranchId,
		customerId: selectedCustomerId,
		billInfoRequests: selectedProducts.map(product => ({
			productId: product.productId,
			quantity: product.quantity,
			price: product.totalPrice,
		})),
	};

	fetch('/api/bills/create', {
		method: 'POST',
		headers: { 'Content-Type': 'application/json' },
		body: JSON.stringify(billPayload),
	})
		.then(response => {
			if (response.ok) {
				alert('Bill created successfully!');
				selectedProducts = [];
				updateBillDisplay();
			} else {
				alert('Failed to create bill.');
			}
		})
		.catch(error => console.error('Error during checkout:', error));
}

// Initialize on page load
document.addEventListener('DOMContentLoaded', () => {
	loadCustomers();
	loadBranches();
	loadProducts();

	const checkoutButton = document.getElementById('checkoutButton');
    checkoutButton.addEventListener('click', checkout);
});
