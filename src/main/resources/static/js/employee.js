// Fetch and display positions to populate the dropdown
function loadPositions() {
	fetch('http://localhost:8080/api/positions')
		.then(response => response.json())
		.then(data => {
			const positionSelect = document.getElementById('positionId');
			positionSelect.innerHTML = '<option value="" disabled selected>Select Position</option>';
			data.forEach(position => {
				const option = document.createElement('option');
				option.value = position.positionID;
				option.textContent = position.position;
				positionSelect.appendChild(option);
			});
		})
		.catch(error => console.error('Error loading positions:', error));
}

// Fetch and display branches to populate the dropdown
function loadBranches() {
	fetch('http://localhost:8080/api/branches')
		.then(response => response.json())
		.then(data => {
			console.log('Branches:', data); // Log the response data to check its structure
			const branchSelect = document.getElementById('branchId');
			branchSelect.innerHTML = '<option value="" disabled selected>Select Branch</option>';
			data.forEach(branch => {
				const option = document.createElement('option');
				option.value = branch.branchID;
				option.textContent = branch.branchName; // Ensure this matches the actual field in the response
				branchSelect.appendChild(option);
			});
		})
		.catch(error => console.error('Error loading branches:', error));
}

// Fetch and display stores to populate the dropdown
function loadStores() {
	fetch('http://localhost:8080/api/stores')
		.then(response => response.json())
		.then(data => {
			console.log('Stores:', data); // Log the response data to check its structure
			const storeSelect = document.getElementById('storeId');
			storeSelect.innerHTML = '<option value="" disabled selected>Select Store</option>';
			data.forEach(store => {
				const option = document.createElement('option');
				option.value = store.storeID;
				option.textContent = store.storeName; // Ensure this matches the actual field in the response
				storeSelect.appendChild(option);
			});
		})
		.catch(error => console.error('Error loading stores:', error));
}

function loadEmployees() {
	fetch('http://localhost:8080/api/employees')
		.then(response => response.json())
		.then(data => {
			const employeeTable = document.getElementById('employeeTable').getElementsByTagName('tbody')[0];
			employeeTable.innerHTML = ''; // Clear existing rows

			data.forEach(employee => {
				const row = employeeTable.insertRow();

				// Check if the gender field is empty and handle it
				const gender = employee.gender || 'N/A';  // Display 'N/A' if gender is empty

				row.innerHTML = `
                    <td>${employee.employeeID}</td>
                    <td>${employee.employeeFirstName}</td>
                    <td>${employee.employeeLastName}</td>
                    <td>${gender}</td>
                    <td>${employee.position ? employee.position.position : 'N/A'}</td>
                    <td>${employee.branch ? employee.branch.branchName : 'N/A'}</td> <!-- Updated to branchName -->
                    <td>${employee.store ? employee.store.storeName : 'N/A'}</td> <!-- Updated to storeName -->
                    <td>${employee.status}</td>
                    <td>
                        <button onclick="editEmployee(${employee.employeeID})">Edit</button>
                        <button class="delete-btn" onclick="deleteEmployee(${employee.employeeID})" style="background-color: red;">Delete</button>
                    </td>
                `;
			});
		})
		.catch(error => console.error('Error fetching employees:', error));
}

// Save or update employee
function saveEmployee() {
	const employeeID = document.getElementById('employeeID').value;
	const employee = {
		employeeID: employeeID,
		firstName: document.getElementById('employeeFirstName').value,
		lastName: document.getElementById('employeeLastName').value,
		gender: document.getElementById('gender').value,
		dob: document.getElementById('dob').value,
		idNumber: document.getElementById('idNumber').value,
		address: document.getElementById('address').value,
		nationality: document.getElementById('nationality').value,
		taxNumber: document.getElementById('taxNumber').value,
		position: { positionID: document.getElementById('positionId').value },
		branch: { branchID: document.getElementById('branchId').value },
		store: { storeID: document.getElementById('storeId').value },
		accountID: document.getElementById('accountID').value,
		telephone: document.getElementById('telephone').value,
		email: document.getElementById('email').value,
		salary: document.getElementById('salary').value,
		status: document.getElementById('status').value,
	};
	console.log("Employee", employee)

	const method = employeeID ? 'PUT' : 'POST';
	const url = employeeID ? `http://localhost:8080/api/employees/${employeeID}` : 'http://localhost:8080/api/employees';

	fetch(url, {
		method: method,
		headers: { 'Content-Type': 'application/json' },
		body: JSON.stringify(employee),
	})
		.then(response => {
			if (response.ok) {
				alert(`${employeeID ? 'Updated' : 'Added'} employee successfully!`);
				loadEmployees(); // Reload employee list
				document.getElementById('employeeForm').reset(); // Reset the form
			} else {
				alert('Failed to save employee.');
			}
		})
		.catch(error => console.error('Error:', error));
}
// Edit employee
function editEmployee(employeeID) {
	fetch(`http://localhost:8080/api/employees/${employeeID}`)
		.then(response => response.json())
		.then(employee => {

			// Populate the form with employee data
			document.getElementById('employeeID').value = employee.employeeID;
			document.getElementById('employeeFirstName').value = employee.employeeFirstName;
			document.getElementById('employeeLastName').value = employee.employeeLastName;
			document.getElementById('gender').value = employee.gender || ''; // handle empty gender
			document.getElementById('dob').value = employee.dob;
			document.getElementById('idNumber').value = employee.idNumber;
			document.getElementById('address').value = employee.address;
			document.getElementById('nationality').value = employee.nationality;
			document.getElementById('taxNumber').value = employee.taxNumber;
			document.getElementById('positionId').value =employee.position.positionID;
			document.getElementById('branchId').value =employee.branch.branchID;
			document.getElementById('storeId').value = employee.store.storeID;
			document.getElementById('accountID').value = employee.accountID;
			document.getElementById('telephone').value = employee.telephone;
			document.getElementById('email').value = employee.email;
			document.getElementById('salary').value = employee.salary;
			document.getElementById('status').value = employee.status;
			const saveButton = document.getElementById('saveButton');
			saveButton.textContent = 'Update Employee';
		})
		.catch(error => console.error('Error editing employee:', error));
}

// Delete employee
function deleteEmployee(employeeID) {
	if (confirm('Are you sure you want to delete this employee?')) {
		fetch(`http://localhost:8080/api/employees/${employeeID}`, { method: 'DELETE' })
			.then(response => {
				if (response.ok) {
					alert('Employee deleted successfully!');
					loadEmployees(); // Reload employee list
				} else {
					alert('Failed to delete employee.');
				}
			})
			.catch(error => console.error('Error deleting employee:', error));
	}
}

// Initial page load
window.onload = function() {
	loadPositions();
	loadBranches();
	loadStores();
	loadEmployees();
};