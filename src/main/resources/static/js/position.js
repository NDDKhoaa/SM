// Fetch departments from the server and populate the dropdown
function loadDepartments() {
	fetch('http://localhost:8080/api/departments') // Ensure the URL is correct
		.then(response => response.json())
		.then(data => {
			const departmentSelect = document.getElementById('departmentId');
			departmentSelect.innerHTML = '<option value="" disabled selected>Select Department</option>'; // Reset dropdown

			// Populate the dropdown with department data
			data.forEach(department => {
				const option = document.createElement('option');
				option.value = department.departmentID; // Use the correct JSON key
				option.textContent = department.department; // Use the correct JSON key
				departmentSelect.appendChild(option);
			});
		})
		.catch(error => console.error('Error loading departments:', error));
}


// Function to add a position
function addPosition() {
	const position = {
		position: document.getElementById('positionName').value,
		departmentId: parseInt(document.getElementById('departmentId').value), // Ensure this matches backend mapping
		description: document.getElementById('description').value
	};

	fetch('http://localhost:8080/api/positions', {
		method: 'POST',
		headers: { 'Content-Type': 'application/json' },
		body: JSON.stringify(position)
	})
		.then(response => {
			if (response.ok) {
				alert('Position added successfully!');
				loadPositions(); // Reload the positions list after adding
			} else {
				alert('Failed to add position.');
				console.error('Error:', response.statusText);
			}
		})
		.catch(error => console.error('Error:', error));
}


// Example function to load positions into the table
// Function to load positions into the table
function loadPositions() {
	fetch('http://localhost:8080/api/positions')
		.then(response => response.json())
		.then(data => {
			// Debug: Log the response data to see the structure
			console.log('Positions:', data);

			const tableBody = document.getElementById('positionTable').getElementsByTagName('tbody')[0];
			tableBody.innerHTML = ''; // Clear existing rows

			if (Array.isArray(data) && data.length > 0) {
				// Add new rows to the table
				data.forEach(position => {
					// Ensure that the position has the expected properties
					console.log('Position:', position);

					const row = document.createElement('tr');
					row.innerHTML = `
                        <td>${position.positionID}</td>
                        <td>${position.position}</td>
                        <td>${position.department ? position.department.name : 'N/A'}</td>
                        <td>${position.description}</td>
                        <td>${position.createdDate}</td>
                        <td>
                            <button onclick="deletePosition(${position.positionID})">Delete</button>
                        </td>
                    `;
					tableBody.appendChild(row);
				});
			} else {
				// If no positions, show a message
				const row = document.createElement('tr');
				row.innerHTML = '<td colspan="6">No positions available</td>';
				tableBody.appendChild(row);
			}
		})
		.catch(error => {
			console.error('Error loading positions:', error);
			// Show a message in case of an error
			const tableBody = document.getElementById('positionTable').getElementsByTagName('tbody')[0];
			tableBody.innerHTML = '<tr><td colspan="6">Failed to load positions.</td></tr>';
		});
}

function deletePosition(positionId) {
	// Confirm the deletion
	if (confirm('Are you sure you want to delete this position?')) {
		fetch(`http://localhost:8080/api/positions/${positionId}`, {
			method: 'DELETE',
		})
			.then(response => {
				if (response.ok) {
					alert('Position deleted successfully!');
					loadPositions();  // Reload the positions list after deleting
				} else {
					alert('Failed to delete position.');
					console.error('Error:', response.statusText);
				}
			})
			.catch(error => console.error('Error:', error));
	}
}


// Initial load of departments and positions when the page is loaded
window.onload = function() {
	loadDepartments();
	loadPositions();  // Load positions when the page is first loaded
};


