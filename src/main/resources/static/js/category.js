
// Function to add or update a category
function saveCategory() {
    const categoryID = document.getElementById('categoryID').value;  // Get the category ID (if exists for update)
    const category = {
        category: document.getElementById('category').value, // Get the category name
        createdDate: document.getElementById('createdDate').value, // Get the created date
    };

    const method = categoryID ? 'PUT' : 'POST'; // Use PUT if categoryID exists, otherwise POST
    const url = categoryID
        ? `/category/update/${categoryID}`  // Update existing category
        : '/category/create';  // Add new category

    // Send the request
    fetch(url, {
        method: method,
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(category),
    })
        .then(response => {
            if (response.ok) {
                alert(`Category ${categoryID ? 'updated' : 'added'} successfully!`);
                loadCategories(); // Reload the category list after saving
                document.getElementById('categoryForm').reset(); // Reset the form
                document.getElementById('categoryID').value = ''; // Reset hidden categoryID field
            } else {
                alert('Failed to save category.');
            }
        })
        .catch(error => console.error('Error saving category:', error));
}

// Function to load categories into the table
function loadCategories() {
    fetch('/category/list')
        .then(response => response.json())
        .then(data => {
            const tableBody = document.getElementById('categoryTable').getElementsByTagName('tbody')[0];
            tableBody.innerHTML = ''; // Clear existing rows

            data.forEach(category => {
                const row = document.createElement('tr');
                row.innerHTML = `
                    <td>${category.categoryID}</td>
                    <td>${category.category}</td>
                    <td>${category.createdDate}</td>
                    <td>
                        <button class="edit-btn" onclick="editCategory(${category.categoryID})">Edit</button>
                        <button class="delete-btn" onclick="deleteCategory(${category.categoryID})">Delete</button>
                    </td>
                `;
                tableBody.appendChild(row);
            });
        })
        .catch(error => console.error('Error loading categories:', error));
}

// Function to edit an existing category
function editCategory(categoryID) {
    fetch(`/category/view/${categoryID}`)
        .then(response => response.json())
        .then(category => {
            document.getElementById('categoryID').value = category.categoryID;  // Set the category ID in the hidden input
            document.getElementById('category').value = category.category;      // Set the category name
            document.getElementById('createdDate').value = category.createdDate; // Set the created date
        })
        .catch(error => console.error('Error loading category for editing:', error));
}

// Function to delete a category
function deleteCategory(categoryID) {
    if (confirm('Are you sure you want to delete this category?')) {
        fetch(`/category/delete/${categoryID}`, {
            method: 'DELETE',
        })
            .then(response => {
                if (response.ok) {
                    alert('Category deleted successfully!');
                    loadCategories(); // Reload the categories list
                } else {
                    alert('Failed to delete category.');
                }
            })
            .catch(error => console.error('Error deleting category:', error));
    }
}

// Initial load of categories when the page is loaded
window.onload = loadCategories;
