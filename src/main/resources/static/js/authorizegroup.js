// src/main/resources/static/js/authorize-group.js

// Fetch and display authorization groups
function loadAuthorizeGroups() {
    fetch('http://localhost:8080/api/authorization-groups')
        .then(response => response.json())
        .then(data => {
            const groupTable = document.getElementById('authorizeGroupTable').getElementsByTagName('tbody')[0];
            groupTable.innerHTML = ''; // Clear existing rows
            data.forEach(group => {
                const row = groupTable.insertRow();
                row.innerHTML = `
                    <td>${group.id}</td>
                    <td>${group.name}</td>
                    <td>
                        <button class="edit-btn" onclick="editAuthorizeGroup(${group.id})">Edit</button>
                        <button class="delete-btn" onclick="deleteAuthorizeGroup(${group.id})">Delete</button>
                    </td>
                `;
            });
        })
        .catch(error => console.error('Error fetching authorization groups:', error));
}

// Edit authorization group
function editAuthorizeGroup(groupID) {
    fetch(`http://localhost:8080/api/authorization-groups/${groupID}`)
        .then(response => response.json())
        .then(group => {
            if (group) {
                document.getElementById('groupID').value = group.id;
                document.getElementById('groupName').value = group.name;
            } else {
                alert('Authorization group not found!');
            }
        })
        .catch(error => {
            console.error('Error fetching group details:', error);
            alert('Failed to fetch group data.');
        });
}

// Save authorization group (Add or Update)
function saveAuthorizeGroup() {
    const groupID = document.getElementById('groupID').value;
    const group = {
        name: document.getElementById('groupName').value,
    };

    const method = groupID ? 'PUT' : 'POST';
    const url = groupID
        ? `http://localhost:8080/api/authorization-groups/${groupID}`
        : 'http://localhost:8080/api/authorization-groups';

    fetch(url, {
        method: method,
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(group),
    })
        .then(response => {
            if (response.ok) {
                alert(`Authorization group ${groupID ? 'updated' : 'added'} successfully!`);
                loadAuthorizeGroups();
                document.getElementById('authorizeGroupForm').reset();
            } else {
                alert('Failed to save authorization group.');
            }
        })
        .catch(error => console.error('Error:', error));
}

// Delete authorization group
function deleteAuthorizeGroup(groupID) {
    if (confirm('Are you sure you want to delete this authorization group?')) {
        fetch(`http://localhost:8080/api/authorization-groups/${groupID}`, {
            method: 'DELETE',
        })
            .then(response => {
                if (response.ok) {
                    alert('Authorization group deleted successfully!');
                    loadAuthorizeGroups();
                } else {
                    alert('Failed to delete authorization group.');
                }
            })
            .catch(error => console.error('Error deleting authorization group:', error));
    }
}

// Load authorization groups on page load
window.onload = loadAuthorizeGroups;
