// Fetch categories from the server and populate the dropdown
        function loadCategories() {
            fetch('http://localhost:8080/categories')
                .then(response => response.json())
                .then(data => {
                    const categorySelect = document.getElementById('categoryId');
                    data.forEach(category => {
                        const option = document.createElement('option');
                        option.value = category.categoryID;
                        option.textContent = category.category;
                        categorySelect.appendChild(option);
                    });
                })
                .catch(error => console.error('Error loading categories:', error));
        }

        // Function to add the product
        function addProduct() {
            const product = {
                productName: document.getElementById('productName').value,
                manufactureDate: document.getElementById('manufactureDate').value,
                expirationDate: document.getElementById('expirationDate').value,
                color: document.getElementById('color').value,
                categoryId: parseInt(document.getElementById('categoryId').value),
                type: document.getElementById('type').value,
                price: parseFloat(document.getElementById('price').value),
                status: document.getElementById('status').value,
            };

            fetch('http://localhost:8080/products', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(product),
            })
            .then(response => {
                if (response.ok) {
                    alert('Product added successfully!');
                    loadProducts(); // Reload the product list after adding
                } else {
                    alert('Failed to add product.');
                }
            })
            .catch(error => console.error('Error:', error));
        }

        // Example function to load products into the table
        function loadProducts() {
            fetch('http://localhost:8080/products')
                .then(response => response.json())
                .then(data => {
                    const tableBody = document.getElementById('productTable').getElementsByTagName('tbody')[0];
                    tableBody.innerHTML = ''; // Clear existing rows

                    data.forEach(product => {
                        const row = document.createElement('tr');
                        row.innerHTML = `
                            <td>${product.id}</td>
                            <td>${product.productName}</td>
                            <td>${product.manufactureDate}</td>
                            <td>${product.expirationDate}</td>
                            <td>${product.color}</td>
                            <td>${product.categoryId}</td>
                            <td>${product.type}</td>
                            <td>${product.price}</td>
                            <td>${product.status}</td>
                        `;
                        tableBody.appendChild(row);
                    });
                })
                .catch(error => console.error('Error loading products:', error));
        }

        // Initial load of categories and products when the page is loaded
        window.onload = function() {
            loadCategories();
            loadProducts();
        };