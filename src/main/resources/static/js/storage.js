function addStorage() {
    const productId = document.getElementById('productId').value;
    const quantity = document.getElementById('quantity').value;
    const status = document.getElementById('status').value;

    const payload = {
        productId: productId,
        quantity: quantity,
        status: status
    };

    fetch('/api/storages', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(payload)
    })
    .then(response => response.json())
    .then(data => {
        console.log('Storage created:', data);
        loadStorages();
    });
}

function loadStorages() {
    fetch('/api/storages')
        .then(response => response.json())
        .then(storages => {
            const tableBody = document.getElementById('storageTable').querySelector('tbody');
            tableBody.innerHTML = ''; // Clear the table
            storages.forEach(storage => {
                const row = document.createElement('tr');
                row.innerHTML = `<td>${storage.storageID}</td><td>${storage.product.name}</td><td>${storage.quantity}</td><td>${storage.status}</td>`;
                tableBody.appendChild(row);
            });
        });
}

document.addEventListener('DOMContentLoaded', loadStorages);
