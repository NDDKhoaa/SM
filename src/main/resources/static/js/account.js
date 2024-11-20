function addAccount() {
    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;
    const email = document.getElementById('email').value;
    const employeeId = document.getElementById('employeeId').value;

    const payload = {
        username,
        password,
        email,
        employeeId
    };

    fetch('/api/accounts', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(payload)
    })
    .then(response => response.json())
    .then(data => {
        console.log('Account created:', data);
        loadAccounts();
    });
}

function loadAccounts() {
    fetch('/api/accounts')
        .then(response => response.json())
        .then(accounts => {
            const tableBody = document.getElementById('accountTable').querySelector('tbody');
            tableBody.innerHTML = ''; // Clear the table
            accounts.forEach(account => {
                const row = document.createElement('tr');
                row.innerHTML = `<td>${account.accountID}</td><td>${account.username}</td><td>${account.email}</td>`;
                tableBody.appendChild(row);
            });
        });
}

document.addEventListener('DOMContentLoaded', loadAccounts);
