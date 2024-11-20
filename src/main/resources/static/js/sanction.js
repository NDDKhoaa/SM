function addSanction() {
    const description = document.getElementById('description').value;

    const payload = {
        description: description
    };

    fetch('/api/sanctions', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(payload)
    })
    .then(response => response.json())
    .then(data => {
        console.log('Sanction created:', data);
        loadSanctions();
    });
}

function loadSanctions() {
    fetch('/api/sanctions')
        .then(response => response.json())
        .then(sanctions => {
            const tableBody = document.getElementById('sanctionTable').querySelector('tbody');
            tableBody.innerHTML = ''; // Clear the table
            sanctions.forEach(sanction => {
                const row = document.createElement('tr');
                row.innerHTML = `<td>${sanction.sanctionID}</td><td>${sanction.description}</td>`;
                tableBody.appendChild(row);
            });
        });
}

document.addEventListener('DOMContentLoaded', loadSanctions);
