// Fetch and display branches
        function loadBranches() {
            fetch('http://localhost:8080/api/branches')
                .then(response => response.json())
                .then(data => {
                    const branchTable = document.getElementById('branchTable').getElementsByTagName('tbody')[0];
                    branchTable.innerHTML = ''; // Clear existing rows
                    data.forEach(branch => {
                        const row = branchTable.insertRow();
                        row.innerHTML = `
                            <td>${branch.branchID}</td>
                            <td>${branch.branchName}</td>
                            <td>${branch.location || 'N/A'}</td>
                            <td>${branch.telephone || 'N/A'}</td>
                            <td>${branch.status}</td>
                        `;
                    });
                })
                .catch(error => console.error('Error fetching branches:', error));
        }

        function addBranch() {
            const branch = {
                branchName: document.getElementById('branchName').value,
                location: document.getElementById('location').value,
                telephone: document.getElementById('telephone').value,
                email: document.getElementById('email').value,
                storeId: parseInt(document.getElementById('storeId').value),
                status: document.getElementById('status').value,
            };

            fetch('http://localhost:8080/api/branches', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(branch),
            })
            .then(response => {
                if (response.ok) {
                    alert('Branch added successfully!');
                    loadBranches();
                } else {
                    response.json().then(err => alert('Failed to add branch: ' + err.message));
                }
            })
            .catch(error => console.error('Error:', error));
        }

        // Load stores and branches on page load
        window.onload = function () {
            fetch('http://localhost:8080/api/stores')
                .then(response => response.json())
                .then(data => {
                    const storeSelect = document.getElementById('storeId');
                    data.forEach(store => {
                        const option = document.createElement('option');
                        option.value = store.storeID;
                        option.textContent = store.storeName;
                        storeSelect.appendChild(option);
                    });
                })
                .catch(error => console.error('Error fetching stores:', error));

            loadBranches();
        };