// Fetch and display branches
function loadBranches() {
	fetch('http://localhost:8080/api/branches')
		.then(response => response.json())
		.then(data => {
			const branchTable = document.getElementById('branchTable').getElementsByTagName('tbody')[0];
			branchTable.innerHTML = ''; // Clear existing rows
			data.forEach(branch => {
				const row = branchTable.insertRow();
				row.innerHTML = `
                    <td>${branch.branchID}</td>
                    <td>${branch.branchName}</td>
                    <td>${branch.location || 'N/A'}</td>
                    <td>${branch.telephone || 'N/A'}</td>
                    <td>${branch.status}</td>
                    <td>
                        <button onclick="editBranch(${branch.branchID})">Edit</button>
                        <button class="delete-btn" onclick="deleteBranch(${branch.branchID})" style="background-color: red;">Delete</button>
                    </td>
                `;
			});
		})
		.catch(error => console.error('Error fetching branches:', error));
}

// Fetch and display stores to populate the dropdown
function loadStores() {
	fetch('http://localhost:8080/api/stores')
		.then(response => response.json())
		.then(data => {
			const storeSelect = document.getElementById('storeId');
			storeSelect.innerHTML = ''; // Clear previous options
			const defaultOption = document.createElement('option');
			defaultOption.value = '';
			defaultOption.textContent = 'Select Store';
			storeSelect.appendChild(defaultOption); // Add default option

			// Populate store dropdown
			data.forEach(store => {
				const option = document.createElement('option');
				option.value = store.storeID;
				option.textContent = store.storeName;
				storeSelect.appendChild(option);
			});
		})
		.catch(error => console.error('Error fetching stores:', error));
}

// Add or Update Branch
function saveBranch() {
	const branchID = document.getElementById('branchID').value; // Get branchID if updating
	const branch = {
		branchName: document.getElementById('branchName').value,
		location: document.getElementById('location').value,
		telephone: document.getElementById('telephone').value,
		email: document.getElementById('email').value,
		storeId: parseInt(document.getElementById('storeId').value),
		status: document.getElementById('status').value,
	};

	const method = branchID ? 'PUT' : 'POST'; // Use POST for adding new, PUT for updating
	const url = branchID
		? `http://localhost:8080/api/branches/${branchID}` // Update URL
		: 'http://localhost:8080/api/branches'; // Add URL

	fetch(url, {
		method: method,
		headers: { 'Content-Type': 'application/json' },
		body: JSON.stringify(branch),
	})
		.then(response => {
			if (response.ok) {
				alert(`Branch ${branchID ? 'updated' : 'added'} successfully!`);
				loadBranches(); // Reload the branches list to reflect the changes
				document.getElementById('branchForm').reset(); // Reset the form
				document.getElementById('branchID').value = ''; // Reset hidden branchID field
			} else {
				alert('Failed to save branch.');
			}
		})
		.catch(error => console.error('Error:', error));
}

// Edit Branch
function editBranch(branchID) {
	fetch(`http://localhost:8080/api/branches/${branchID}`)
		.then(response => response.json())
		.then(branch => {
			// Pre-fill the form with branch data
			document.getElementById('branchID').value = branch.branchID;
			document.getElementById('branchName').value = branch.branchName;
			document.getElementById('location').value = branch.location || '';
			document.getElementById('telephone').value = branch.telephone || '';
			document.getElementById('email').value = branch.email || '';
			document.getElementById('status').value = branch.status;

			// Set the selected store ID
			const storeSelect = document.getElementById('storeId');
			storeSelect.value = branch.storeID; // Set the correct store ID in the dropdown

			// Change the form submit behavior to update the branch
			const form = document.getElementById('branchForm');
			form.onsubmit = function(event) {
				event.preventDefault(); // Prevent default form submission
				saveBranch(); // Call saveBranch to update the branch
			};
		})
		.catch(error => {
			console.error('Error fetching branch data:', error);
			alert('Failed to fetch branch data.');
		});
}

// Delete Branch
function deleteBranch(branchID) {
	if (confirm('Are you sure you want to delete this branch?')) {
		fetch(`http://localhost:8080/api/branches/${branchID}`, {
			method: 'DELETE',
		})
			.then(response => {
				if (response.ok) {
					alert('Branch deleted successfully!');
					loadBranches(); // Reload the branches list
				} else {
					alert('Failed to delete branch.');
				}
			})
			.catch(error => console.error('Error deleting branch:', error));
	}
}
// Initial load of stores and branches when the page is loaded
window.onload = function() {
	loadStores(); // Load stores first
	loadBranches(); // Load branches when the page is first loaded
};