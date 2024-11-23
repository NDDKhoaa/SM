// src/main/resources/static/js/account.js

// Fetch and display accounts
function loadAccounts() {
    fetch('http://localhost:8080/api/accounts')
        .then(response => response.json())
        .then(data => {
            const accountTable = document.getElementById('accountTable').getElementsByTagName('tbody')[0];
            accountTable.innerHTML = ''; // Clear existing rows
            data.forEach(account => {
                const row = accountTable.insertRow();
                row.innerHTML = `
                    <td>${account.id}</td>
                    <td>${account.name}</td>
                    <td>${account.email}</td>
                    <td>${account.status}</td>
                    <td>
                        <button onclick="editAccount(${account.id})">Edit</button>
                        <button class="delete-btn" onclick="deleteAccount(${account.id})">Delete</button>
                    </td>
                `;
            });
        })
        .catch(error => console.error('Error fetching accounts:', error));
}

// Edit account
function editAccount(accountID) {
    fetch(`http://localhost:8080/api/accounts/${accountID}`)
        .then(response => response.json())
        .then(account => {
            if (account) {
                document.getElementById('accountID').value = account.id;
                document.getElementById('accountName').value = account.name;
                document.getElementById('email').value = account.email;
                document.getElementById('status').value = account.status;
            } else {
                alert('Account not found!');
            }
        })
        .catch(error => {
            console.error('Error fetching account details:', error);
            alert('Failed to fetch account data.');
        });
}

// Save account (Add or Update)
function saveAccount() {
    const accountID = document.getElementById('accountID').value;
    const account = {
        name: document.getElementById('accountName').value,
        email: document.getElementById('email').value,
        status: document.getElementById('status').value,
    };

    const method = accountID ? 'PUT' : 'POST';
    const url = accountID
        ? `http://localhost:8080/api/accounts/${accountID}`
        : 'http://localhost:8080/api/accounts';

    fetch(url, {
        method: method,
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(account),
    })
        .then(response => {
            if (response.ok) {
                alert(`Account ${accountID ? 'updated' : 'added'} successfully!`);
                loadAccounts();
                document.getElementById('accountForm').reset();
            } else {
                alert('Failed to save account.');
            }
        })
        .catch(error => console.error('Error:', error));
}

// Delete account
function deleteAccount(accountID) {
    if (confirm('Are you sure you want to delete this account?')) {
        fetch(`http://localhost:8080/api/accounts/${accountID}`, {
            method: 'DELETE',
        })
            .then(response => {
                if (response.ok) {
                    alert('Account deleted successfully!');
                    loadAccounts();
                } else {
                    alert('Failed to delete account.');
                }
            })
            .catch(error => console.error('Error deleting account:', error));
    }
}

// Load accounts on page load
window.onload = loadAccounts;
