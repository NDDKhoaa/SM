function saveCustomer() {
	const customerID = document.getElementById('customerID').value;
	const customer = {
		firstName: document.getElementById('firstName').value,
		lastName: document.getElementById('lastName').value,
		gender: document.getElementById('gender').value,
		telephone: document.getElementById('telephone').value,
		email: document.getElementById('email').value,
		address: document.getElementById('address').value,
		createdBy: document.getElementById('createdBy').value, // Employee ID
	};

	const method = customerID ? 'PUT' : 'POST';
	const url = customerID
		? `http://localhost:8080/api/customers/${customerID}`
		: 'http://localhost:8080/api/customers';

	fetch(url, {
		method: method,
		headers: { 'Content-Type': 'application/json' },
		body: JSON.stringify(customer),
	})
		.then(response => {
			if (response.ok) {
				alert(`Customer ${customerID ? 'updated' : 'added'} successfully!`);
				loadCustomers(); // Refresh the customer list
				document.getElementById('customerForm').reset(); // Reset the form
			} else {
				alert('Failed to save customer.');
			}
		})
		.catch(error => console.error('Error:', error));
}

function loadCustomers() {
	fetch('http://localhost:8080/api/customers')
		.then(response => response.json())
		.then(data => {
			const customerTable = document.getElementById('customerTable').getElementsByTagName('tbody')[0];
			customerTable.innerHTML = ''; // Clear existing rows
			data.forEach(customer => {
				const row = customerTable.insertRow();
				row.innerHTML = `
                    <td>${customer.customerID || 'N/A'}</td>
                    <td>${customer.firstName || 'N/A'}</td>
                    <td>${customer.lastName || 'N/A'}</td>
                    <td>${customer.gender || 'N/A'}</td>
                    <td>${customer.telephone || 'N/A'}</td>
                    <td>${customer.email || 'N/A'}</td>
                    <td>${customer.address || 'N/A'}</td>
                    <td>${customer.createdBy
						? `${customer.createdBy.employeeFirstName || ''} ${customer.createdBy.employeeLastName || ''}`.trim()
						: 'N/A'
					}</td>
                    <td>
                        <button class="edit-btn" onclick="editCustomer(${customer.customerID})">Edit</button>
                        <button class="delete-btn" onclick="deleteCustomer(${customer.customerID})">Delete</button>
                    </td>
                `;
			});
		})
		.catch(error => console.error('Error loading customers:', error));
}



function loadEmployees() {
	fetch('http://localhost:8080/api/employees')
		.then(response => response.json())
		.then(data => {
			const employeeSelect = document.getElementById('createdBy');
			employeeSelect.innerHTML = ''; // Clear existing options
			data.forEach(employee => {
				const option = document.createElement('option');
				option.value = employee.employeeID;
				option.textContent = `${employee.employeeFirstName || 'Unknown'} ${employee.employeeLastName || ''}`.trim();
				employeeSelect.appendChild(option);
			});
		})
		.catch(error => console.error('Error loading employees:', error));
}


function editCustomer(customerID) {
	fetch(`http://localhost:8080/api/customers/${customerID}`)
		.then(response => response.json())
		.then(customer => {
			document.getElementById('customerID').value = customer.customerID;
			document.getElementById('firstName').value = customer.firstName;
			document.getElementById('lastName').value = customer.lastName;
			document.getElementById('gender').value = customer.gender;
			document.getElementById('telephone').value = customer.telephone || '';
			document.getElementById('email').value = customer.email || '';
			document.getElementById('address').value = customer.address || '';
			document.getElementById('createdBy').value = customer.createdBy.employeeID;
		})
		.catch(error => console.error('Error loading customer:', error));
}

function deleteCustomer(customerID) {
	if (!confirm('Are you sure you want to delete this customer?')) return;

	fetch(`http://localhost:8080/api/customers/${customerID}`, {
		method: 'DELETE',
	})
		.then(response => {
			if (response.ok) {
				alert('Customer deleted successfully!');
				loadCustomers(); // Refresh the customer list
			} else {
				alert('Failed to delete customer.');
			}
		})
		.catch(error => console.error('Error deleting customer:', error));
}

window.onload = function() {
	loadCustomers();
	loadEmployees();
};
