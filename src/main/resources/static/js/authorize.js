// src/main/resources/static/js/authorize.js

// Fetch and display authorizations
function loadAuthorizations() {
    fetch('http://localhost:8080/api/authorizations')
        .then(response => response.json())
        .then(data => {
            const authorizeTable = document.getElementById('authorizeTable').getElementsByTagName('tbody')[0];
            authorizeTable.innerHTML = ''; // Clear existing rows
            data.forEach(auth => {
                const row = authorizeTable.insertRow();
                row.innerHTML = `
                    <td>${auth.id}</td>
                    <td>${auth.permission}</td>
                    <td>${auth.description || 'N/A'}</td>
                    <td>
                        <button onclick="editAuthorize(${auth.id})">Edit</button>
                        <button class="delete-btn" onclick="deleteAuthorize(${auth.id})">Delete</button>
                    </td>
                `;
            });
        })
        .catch(error => console.error('Error fetching authorizations:', error));
}

// Edit authorization
function editAuthorize(authID) {
    fetch(`http://localhost:8080/api/authorizations/${authID}`)
        .then(response => response.json())
        .then(auth => {
            if (auth) {
                document.getElementById('authorizeID').value = auth.id;
                document.getElementById('permission').value = auth.permission;
                document.getElementById('description').value = auth.description || '';
            } else {
                alert('Authorization not found!');
            }
        })
        .catch(error => {
            console.error('Error fetching authorization details:', error);
            alert('Failed to fetch authorization data.');
        });
}

// Save authorization (Add or Update)
function saveAuthorize() {
    const authID = document.getElementById('authorizeID').value;
    const auth = {
        permission: document.getElementById('permission').value,
        description: document.getElementById('description').value,
    };

    const method = authID ? 'PUT' : 'POST';
    const url = authID
        ? `http://localhost:8080/api/authorizations/${authID}`
        : 'http://localhost:8080/api/authorizations';

    fetch(url, {
        method: method,
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(auth),
    })
        .then(response => {
            if (response.ok) {
                alert(`Authorization ${authID ? 'updated' : 'added'} successfully!`);
                loadAuthorizations();
                document.getElementById('authorizeForm').reset();
            } else {
                alert('Failed to save authorization.');
            }
        })
        .catch(error => console.error('Error:', error));
}

// Delete authorization
function deleteAuthorize(authID) {
    if (confirm('Are you sure you want to delete this authorization?')) {
        fetch(`http://localhost:8080/api/authorizations/${authID}`, {
            method: 'DELETE',
        })
            .then(response => {
                if (response.ok) {
                    alert('Authorization deleted successfully!');
                    loadAuthorizations();
                } else {
                    alert('Failed to delete authorization.');
                }
            })
            .catch(error => console.error('Error deleting authorization:', error));
    }
}

// Load authorizations on page load
window.onload = loadAuthorizations;
