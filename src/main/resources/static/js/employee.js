function loadPositions() {
	fetch('http://localhost:8080/api/positions') // Ensure the URL is correct for positions
		.then(response => response.json())
		.then(data => {
			const positionSelect = document.getElementById('positionId');
			positionSelect.innerHTML = '<option value="" disabled selected>Select Position</option>'; // Reset dropdown

			// Populate the dropdown with position data
			data.forEach(position => {
				const option = document.createElement('option');
				option.value = position.positionID; // Use the correct JSON key for position ID
				option.textContent = position.position; // Use the correct JSON key for position name
				positionSelect.appendChild(option);
			});
		})
		.catch(error => console.error('Error loading positions:', error));
}

function loadStores() {
	fetch('http://localhost:8080/api/stores')
		.then(response => response.json())
		.then(data => {
			console.log("Stores data:", data);  // Log store data
			const storeSelect = document.getElementById('storeId');
			storeSelect.innerHTML = '<option value="" disabled selected>Select Store</option>'; // Reset dropdown

			// Populate the dropdown with store data
			data.forEach(store => {
				// Add store as an option in the store dropdown
				const storeOption = document.createElement('option');
				storeOption.value = store.storeID;
				storeOption.textContent = store.storeName; // Assuming storeName is the correct key for store name
				storeSelect.appendChild(storeOption);

				// Now populate the branch dropdown with the branches for this store
				const branchSelect = document.getElementById('branchId');
				store.branches.forEach(branch => {
					const branchOption = document.createElement('option');
					branchOption.value = branch.branchID;
					branchOption.textContent = branch.branchName; // Assuming branchName is the correct key for branch name
					branchSelect.appendChild(branchOption);
				});
			});
		})
		.catch(error => console.error('Error loading stores:', error));
}



function addEmployee() {
	const positionId = document.getElementById('positionId').value;
	const branchId = document.getElementById('branchId').value;
	const storeId = document.getElementById('storeId').value;

	console.log('Position:', positionId); // Log the selected position
	console.log('Branch:', branchId);     // Log the selected branch
	console.log('Store:', storeId);       // Log the selected store

	const employee = {
		employeeFirstName: document.getElementById('employeeFirstName').value,
		employeeLastName: document.getElementById('employeeLastName').value,
		gender: document.getElementById('gender').value,
		dob: document.getElementById('dob').value,
		idNumber: document.getElementById('idNumber').value,
		address: document.getElementById('address').value,
		nationality: document.getElementById('nationality').value,
		taxNumber: document.getElementById('taxNumber').value,
		positionId: positionId ? parseInt(positionId) : null,
		branchId: branchId ? parseInt(branchId) : null,
		storeId: storeId ? parseInt(storeId) : null,
		accountID: document.getElementById('accountID').value,
		telephone: document.getElementById('telephone').value,
		email: document.getElementById('email').value,
		salary: parseFloat(document.getElementById('salary').value),
		status: document.getElementById('status').value,
	};

	console.log("Sending employee data:", employee);  // Log the employee data

	fetch('http://localhost:8080/api/employees', {
		method: 'POST',
		headers: { 'Content-Type': 'application/json' },
		body: JSON.stringify(employee),
	})
		.then(response => {
			if (response.ok) {
				alert('Employee added successfully!');
				loadEmployees();  // Reload the employee list
			} else {
				alert('Failed to add employee. Check backend logs for errors.');
			}
		})
		.catch(error => {
			console.error('Error:', error);
			alert('Failed to add employee.');
		});
}

function loadEmployees() {
	fetch('http://localhost:8080/api/employees')
		.then(response => response.json())
		.then(data => {
			const employeeTable = document.getElementById('employeeTable').getElementsByTagName('tbody')[0];
			employeeTable.innerHTML = ''; // Clear existing rows

			data.forEach(employee => {
				const position = employee.position ? employee.position.position : 'N/A';
				const branch = employee.branch ? employee.branch.branchName : 'N/A';
				const store = employee.store ? employee.store.storeName : 'N/A';

				const row = employeeTable.insertRow();
				row.innerHTML = `
                    <td>${employee.employeeID}</td>
                    <td>${employee.employeeFirstName}</td>
                    <td>${employee.employeeLastName}</td>
                    <td>${employee.gender}</td>
                    <td>${position}</td>
                    <td>${branch}</td>
                    <td>${store}</td>
                    <td>${employee.status}</td>
                `;
			});
		})
		.catch(error => console.error('Error fetching employees:', error));
}

window.onload = function() {
	loadEmployees();
	loadPositions();
	loadStores();
};
