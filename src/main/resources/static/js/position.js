// Fetch and display departments to populate the dropdown
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

// Fetch and display positions
function loadPositions() {
    fetch('http://localhost:8080/api/positions')
        .then(response => response.json())
        .then(data => {
            const positionTable = document.getElementById('positionTable').getElementsByTagName('tbody')[0];
            positionTable.innerHTML = ''; // Clear existing rows
            data.forEach(position => {
                const row = positionTable.insertRow();
                row.innerHTML = `
                    <td>${position.positionID}</td>
                    <td>${position.position}</td>
                    <td>${position.department ? position.department.department : 'N/A'}</td>
                    <td>${position.description || 'N/A'}</td>
                    <td>
                        <button class="edit-btn" onclick="editPosition(${position.positionID})">Edit</button>
                        <button class="delete-btn" onclick="deletePosition(${position.positionID})" style="background-color: red;">Delete</button>
                    </td>
                `;
            });
        })
        .catch(error => console.error('Error fetching positions:', error));
}

// Edit position
function editPosition(positionID) {
    console.log('Editing position with ID:', positionID); // Debugging line
    fetch(`http://localhost:8080/api/positions/${positionID}`)
        .then(response => response.json())
        .then(position => {
            console.log('Position data:', position); // Debugging line
            // Pre-fill the form with position data
            document.getElementById('positionID').value = position.positionID;
            document.getElementById('positionName').value = position.position;
            document.getElementById('departmentId').value = position.department ? position.department.departmentID : ''; // Safely handle missing department
            document.getElementById('description').value = position.description || '';

            // Ensure the form calls savePosition() to update the position
            const form = document.getElementById('positionForm');
            form.onsubmit = function(event) {
                event.preventDefault(); // Prevent default form submission
                savePosition(); // Call savePosition to update the position
            };
        })
        .catch(error => {
            console.error('Error fetching position data:', error);
            alert('Failed to fetch position data.');
        });
}

// Save position (Add or Update)
function savePosition() {
    const positionID = document.getElementById('positionID').value; // Get positionID if updating
    console.log('Saving position with ID:', positionID); // Debugging line
    
    const position = {
		positionID: document.getElementById('positionID').value,// Get positionID if updating
        position: document.getElementById('positionName').value,
        department: {
            departmentID: parseInt(document.getElementById('departmentId').value) // Send departmentID
        },
        description: document.getElementById('description').value,
    };
	console.log('Saving position:', position); // Debugging line
    const method = positionID ? 'PUT' : 'POST'; // Use POST for adding new, PUT for updating
    const url = positionID
        ? `http://localhost:8080/api/positions/${positionID}` // Update URL
        : 'http://localhost:8080/api/positions'; // Add URL

    console.log('Request URL:', url); // Debugging line

    // Send the request to add or update the position
    fetch(url, {
        method: method,
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(position),
    })
        .then(response => {
            if (response.ok) {
                alert(`Position ${positionID ? 'updated' : 'added'} successfully!`);
                loadPositions(); // Reload the positions list to reflect the changes
                document.getElementById('positionForm').reset(); // Reset the form
            } else {
                alert('Failed to save position.');
            }
        })
        .catch(error => console.error('Error:', error));
}

// Delete position
function deletePosition(positionID) {
    if (confirm('Are you sure you want to delete this position?')) {
        fetch(`http://localhost:8080/api/positions/${positionID}`, {
            method: 'DELETE',
        })
            .then(response => {
                if (response.ok) {
                    alert('Position deleted successfully!');
                    loadPositions(); // Reload the positions list
                } else {
                    alert('Failed to delete position.');
                }
            })
            .catch(error => console.error('Error deleting position:', error));
    }
}

// Initial load of departments and positions when the page is loaded
window.onload = function() {
    loadDepartments();
    loadPositions(); // Load positions when the page is first loaded
};
