// Function to add a new category
      function addCategory() {
          const category = {
              category: document.getElementById('categoryName').value,
              createdDate: document.getElementById('createdDate').value
          };

          fetch('http://localhost:8080/categories', {
              method: 'POST',
              headers: { 'Content-Type': 'application/json' },
              body: JSON.stringify(category),
          })
          .then(response => {
              if (response.ok) {
                  alert('Category added successfully!');
                  loadCategories(); // Reload the category list after adding
              } else {
                  alert('Failed to add category.');
              }
          })
          .catch(error => console.error('Error:', error));
      }

      // Function to load categories into the table
      function loadCategories() {
          fetch('http://localhost:8080/categories')
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
                      `;
                      tableBody.appendChild(row);
                  });
              })
              .catch(error => console.error('Error loading categories:', error));
      }

      // Initial load of categories when the page is loaded
      window.onload = loadCategories;