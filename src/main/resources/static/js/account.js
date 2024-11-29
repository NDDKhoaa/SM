
// Fetch and display accounts
function loadAccounts() {
	fetch('http://localhost:8080/api/accounts?s=1')
		.then(response => response.json())
		.then(data => {
			const accountTable = document.getElementById('accountTable').getElementsByTagName('tbody')[0];
			accountTable.innerHTML = ''; // Clear existing rows
			data.forEach(account => {
				const row = accountTable.insertRow();
				row.innerHTML = `
                    <td>${account.accountID}</td>
                    <td>${account.username}</td>
                    <td>${account.email}</td>
                    <td>${account.employeeID}</td>
                    <td>
                        <button class="edit-btn" onclick="editAccount(${account.accountID})">Edit</button>
                        <button class="delete-btn" onclick="deleteAccount(${account.accountID})">Delete</button>
                    </td>
                `;
			});
		})
		.catch(error => console.error('Error fetching accounts:', error));
}

// Fetch and populate employee dropdown
function loadEmployees() {
	fetch('http://localhost:8080/api/employees?s=1')
		.then(response => response.json())
		.then(data => {
			const employeeSelect = document.getElementById('employeeId');
			employeeSelect.innerHTML = ''; // Clear existing options
			data.forEach(employee => {
				const option = document.createElement('option');
				option.value = employee.employeeID;
				option.text = employee.employeeFirstName + " " + employee.employeeLastName;
				employeeSelect.appendChild(option);
			});
		})
		.catch(error => console.error('Error fetching employees:', error));
}

// Edit account
function editAccount(accountID) {
	fetch(`http://localhost:8080/api/accounts/${accountID}`)
		.then(response => response.json())
		.then(account => {
			if (account) {
				document.getElementById('accountID').value = account.accountID;
				document.getElementById('accountName').value = account.username;
				document.getElementById('password').value = ''; // Clear password field
				document.getElementById('email').value = account.email;
				document.getElementById('employeeId').value = account.employeeID;
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
        username: document.getElementById('accountName').value,
        password: document.getElementById('password').value,
        email: document.getElementById('email').value,
        employeeID: document.getElementById('employeeId').value,
    };

    const method = accountID ? 'PUT' : 'POST';
    const url = accountID
        ? `http://localhost:8080/api/accounts/${accountID}`
        : 'http://localhost:8080/api/accounts?s=1/create';

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
                return response.json().then(errorData => {
                    console.error('Failed to save account:', errorData);
                    alert('Failed to save account. See console for details.');
                });
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

// Load accounts and employees on page load
window.onload = function() {
	loadAccounts(); // Load accounts when the page is first loaded
	loadEmployees(); // Load employees for the dropdown
};
