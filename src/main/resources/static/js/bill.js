function loadBills() {
	fetch('http://localhost:8080/api/bills')
		.then(response => response.json())
		.then(data => {
			const billTable = document.getElementById('billTable').getElementsByTagName('tbody')[0];
			billTable.innerHTML = ''; // Clear existing rows
			data.forEach(bill => {
				const row = billTable.insertRow();
				row.innerHTML = `
                        <td>${bill.billID}</td>
                        <td>${bill.customer.firstName} ${bill.customer.lastName}</td>
                        <td>${bill.branch.branchName}</td>
                        <td>${bill.employee.employeeFirstName} ${bill.employee.employeeLastName}</td>
                        <td>${bill.total}</td>
                        <td>${bill.status}</td>
                    `;
			});
		})
		.catch(error => console.error('Error fetching bills:', error));
}

// Add a product row with input fields for quantity, and promotion
function addProductRow() {
	const productList = document.getElementById('productList');
	const productRow = document.createElement('div');
	productRow.classList.add('product-row');

	const productSelect = document.createElement('select');
	productSelect.name = "billInfos[][productId]";  // BillInfo product ID
	productSelect.required = true;
	productSelect.innerHTML = `<option value="" disabled selected>Select Product</option>`;
	// Populate product options dynamically
	// Example: fetch products and populate options here
	productSelect.addEventListener('change', updatePrice);

	const quantityInput = document.createElement('input');
	quantityInput.type = 'number';
	quantityInput.name = "billInfos[][quantity]";  // BillInfo quantity
	quantityInput.placeholder = 'Quantity';
	quantityInput.required = true;

	const promotionInput = document.createElement('input');
	promotionInput.type = 'text';
	promotionInput.name = "billInfos[][promotion]";  // BillInfo promotion

	const priceInput = document.createElement('input');
	priceInput.type = 'text';
	priceInput.name = "billInfos[][price]";  // BillInfo price
	priceInput.placeholder = 'Price';
	priceInput.readOnly = true; // Make it readonly, will be auto-filled based on the product
	priceInput.classList.add('price');  // For styling, optional

	// Add remove button for the row
	const removeButton = document.createElement('button');
	removeButton.type = 'button';
	removeButton.textContent = 'Remove';
	removeButton.onclick = () => productRow.remove();

	productRow.appendChild(productSelect);
	productRow.appendChild(quantityInput);
	productRow.appendChild(promotionInput);
	productRow.appendChild(priceInput);
	productRow.appendChild(removeButton);
	productList.appendChild(productRow);

	// Fetch product options and populate the select
	fetch('/api/products')  // Example API call to get products
		.then(response => response.json())
		.then(products => {
			products.forEach(product => {
				const option = document.createElement('option');
				option.value = product.id;
				option.textContent = product.name;
				productSelect.appendChild(option);
			});
		});
}

// Update price based on selected product
function updatePrice(event) {
	const productId = event.target.value;
	const priceInput = event.target.closest('.product-row').querySelector('.price');

	// Fetch product details based on the selected product ID
	fetch(`/api/products/${productId}`)
		.then(response => response.json())
		.then(product => {
			priceInput.value = product.price;  // Set the price in the readonly input
		});
}

// Fetch customer, branch, employee, and product data dynamically (example)
document.addEventListener("DOMContentLoaded", function() {
	// Populate select options for customers, branches, and employees
	const customerSelect = document.getElementById('customerId');
	customers.forEach(customer => {
		const option = document.createElement('option');
		option.value = customer;
		option.textContent = customer;
		customerSelect.appendChild(option);
	});

	const branchSelect = document.getElementById('branchId');
	branches.forEach(branch => {
		const option = document.createElement('option');
		option.value = branch;
		option.textContent = branch;
		branchSelect.appendChild(option);
	});

	const employeeSelect = document.getElementById('employeeId');
	employees.forEach(employee => {
		const option = document.createElement('option');
		option.value = employee;
		option.textContent = employee;
		employeeSelect.appendChild(option);
	});
});
window.onload = function() {
	loadBills();
	loadOptions('http://localhost:8080/api/customers', 'customerId', 'firstName');
	loadOptions('http://localhost:8080/api/branches', 'branchId', 'branchName');
	loadOptions('http://localhost:8080/api/employees', 'employeeId', 'employeeFirstName');
};