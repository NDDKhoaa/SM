function saveDepartment() {
    const departmentID = document.getElementById('departmentID').value;
    const department = {
        department: document.getElementById('department').value,
        description: document.getElementById('description').value,
    };

    const method = departmentID ? 'PUT' : 'POST';
    const url = departmentID
        ? `http://localhost:8080/api/departments/${departmentID}`
        : 'http://localhost:8080/api/departments';

    fetch(url, {
        method: method,
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(department),
    })
    .then(response => {
        if (response.ok) {
            if (departmentID) {
                alert('Department updated successfully!');
            } else {
                alert('Department added successfully!');
            }
            loadDepartments(); // Refresh the department list
            document.getElementById('departmentForm').reset(); // Reset the form after saving
            document.getElementById('departmentID').value = ''; // Ensure departmentID is cleared for new entries
        } else {
            alert('Failed to save department.');
        }
    })
    .catch(error => console.error('Error:', error));
}


function loadDepartments() {
    fetch('http://localhost:8080/api/departments')
        .then(response => response.json())
        .then(data => {
            const departmentTable = document.getElementById('departmentTable').getElementsByTagName('tbody')[0];
            departmentTable.innerHTML = ''; // Clear existing rows
            data.forEach(department => {
                const row = departmentTable.insertRow();
                row.innerHTML = `
                    <td>${department.departmentID || 'N/A'}</td>
                    <td>${department.department || 'N/A'}</td>
                    <td>${department.description || 'N/A'}</td>
                    <td>
                        <button class="edit-btn" onclick="editDepartment(${department.departmentID})">Edit</button>
                        <button class="delete-btn" onclick="deleteDepartment(${department.departmentID})">Delete</button>
                    </td>
                `;
            });
        })
        .catch(error => console.error('Error loading departments:', error));
}

function editDepartment(departmentID) {
    fetch(`http://localhost:8080/api/departments/${departmentID}`)
        .then(response => response.json())
        .then(department => {
            document.getElementById('departmentID').value = department.departmentID;
            document.getElementById('department').value = department.department;
            document.getElementById('description').value = department.description || '';
        })
        .catch(error => console.error('Error loading department:', error));
}

function deleteDepartment(departmentID) {
    if (!confirm('Are you sure you want to delete this department?')) return;

    fetch(`http://localhost:8080/api/departments/${departmentID}`, {
        method: 'DELETE',
    })
        .then(response => {
            if (response.ok) {
                alert('Department deleted successfully!');
                loadDepartments(); // Refresh the department list
            } else {
                alert('Failed to delete department.');
            }
        })
        .catch(error => console.error('Error deleting department:', error));
}

window.onload = function() {
    loadDepartments();
};
