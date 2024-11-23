// Fetch and display stores
function loadStores() {
    fetch('http://localhost:8080/api/stores')
        .then(response => response.json())
        .then(data => {
            const storeTable = document.getElementById('storeTable').getElementsByTagName('tbody')[0];
            storeTable.innerHTML = ''; // Clear existing rows
            data.forEach(store => {
                const row = storeTable.insertRow();
                row.innerHTML = `
                    <td>${store.storeID}</td>
                    <td>${store.storeName}</td>
                    <td>${store.taxNumber}</td>
                    <td>${store.telephone || 'N/A'}</td>
                    <td>${store.email || 'N/A'}</td>
                    <td>${store.status}</td>
                    <td>
                        <button onclick="editStore(${store.storeID})">Edit</button>
                        <button class="delete-btn" onclick="deleteStore(${store.storeID})">Delete</button>
                    </td>
                `;
            });
        })
        .catch(error => console.error('Error fetching stores:', error));
}

// Edit store
function editStore(storeID) {
    fetch(`http://localhost:8080/api/stores/${storeID}`)
        .then(response => response.json())
        .then(store => {
            // Ensure the store data is fetched
            if (store) {
                // Pre-fill the form with store data
                document.getElementById('storeID').value = store.storeID; // Set the hidden storeID
                document.getElementById('storeName').value = store.storeName;
                document.getElementById('taxNumber').value = store.taxNumber;
                document.getElementById('telephone').value = store.telephone || '';
                document.getElementById('email').value = store.email || '';
                document.getElementById('status').value = store.status;

                // Now, ensure that when the form is submitted, it will call saveStore() to update the store
                const form = document.getElementById('storeForm');
                form.onsubmit = function(event) {
                    event.preventDefault(); // Prevent default form submission
                    saveStore(); // Call saveStore to update the store
                };
            } else {
                alert('Store not found!');
            }
        })
        .catch(error => {
            console.error('Error fetching store details:', error);
            alert('Failed to fetch store data.');
        });
}

// Save store (Add or Update)
function saveStore() {
    const storeID = document.getElementById('storeID').value; // Get storeID from the hidden input
    const store = {
		storeID: document.getElementById('storeID').value,
        storeName: document.getElementById('storeName').value,
        taxNumber: document.getElementById('taxNumber').value,
        telephone: document.getElementById('telephone').value,
        email: document.getElementById('email').value,
        status: document.getElementById('status').value,
    };

    const method = storeID ? 'PUT' : 'POST'; // If storeID exists, we update (PUT), else we add a new store (POST)
    const url = storeID
        ? `http://localhost:8080/api/stores/${storeID}` // Update the store
        : 'http://localhost:8080/api/stores'; // Create a new store

    // Send the request to add or update the store
    fetch(url, {
        method: method,
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(store),
    })
        .then(response => {
            if (response.ok) {
                alert(`Store ${storeID ? 'updated' : 'added'} successfully!`);
                loadStores(); // Reload the store list to reflect the changes
                document.getElementById('storeForm').reset(); // Reset the form
            } else {
                alert('Failed to save store.');
            }
        })
        .catch(error => console.error('Error:', error));
}

// Delete store
function deleteStore(storeID) {
    if (confirm('Are you sure you want to delete this store?')) {
        fetch(`http://localhost:8080/api/stores/${storeID}`, {
            method: 'DELETE',
        })
            .then(response => {
                if (response.ok) {
                    alert('Store deleted successfully!');
                    loadStores(); // Reload the stores list
                } else {
                    alert('Failed to delete store.');
                }
            })
            .catch(error => console.error('Error deleting store:', error));
    }
}

// Load stores on page load
window.onload = loadStores;