
document.addEventListener('DOMContentLoaded', function() {
	let currentPage = 0;
	let pageSize = 10;

	$('#filterCreatedDate').datepicker({
		dateFormat: 'yy-mm-dd'
	});

	loadStorages();

	function loadStorages(filters = {}, page = 0) {
		fetch(`/storage/list?page=${page}&size=${pageSize}`)
			.then(response => {
				if (!response.ok) {
					throw new Error('Network response was not ok ' + response.statusText);
				}
				return response.json();
			})
			.then(storages => {
				const tbody = document.getElementById('storageTable').querySelector('tbody');
				tbody.innerHTML = '';
				const productPromises = storages.map(storage =>
					fetch(`/product/view/${storage.productID}`)
						.then(response => {
							if (!response.ok) {
								throw new Error('Network response was not ok ' + response.statusText);
							}
							return response.json();
						})
						.then(product => ({ ...storage, productName: product.product }))
				);

				Promise.all(productPromises)
					.then(storagesWithProducts => {
						if (filters.createdDate) {
							storagesWithProducts = storagesWithProducts.filter(storage => storage.createdDate === filters.createdDate);
						}
						if (filters.productName) {
							storagesWithProducts = storagesWithProducts.filter(storage => storage.productName.toLowerCase().includes(filters.productName.toLowerCase()));
						}
						if (filters.measurement) {
							storagesWithProducts = storagesWithProducts.filter(storage => storage.measurement.toLowerCase().includes(filters.measurement.toLowerCase()));
						}

						storagesWithProducts.forEach(storage => {
							const row = document.createElement('tr');
							row.innerHTML = `
	                                <td>${storage.storageID}</td>
	                                <td>${storage.productName}</td>
	                                <td>${storage.quantity.toFixed(2)}</td>
	                                <td>${storage.measurement}</td>
	                                <td>${storage.createdDate}</td>
	                                <td>
	                                    <button class="edit-btn" onclick="editStorage(${storage.storageID})">Edit</button>
	                                    <button class="delete-btn" onclick="deleteStorage(${storage.storageID})">Delete</button>
	                                </td>
	                            `;
							tbody.appendChild(row);
						});

						updatePaginationControls(page, storages.length < pageSize);
					})
					.catch(error => console.error('Error loading products:', error));
			})
			.catch(error => console.error('Error loading storages:', error));
	}

	function updatePaginationControls(page, isLastPage) {
		const paginationControls = document.getElementById('paginationControls');
		paginationControls.innerHTML = `
	            <button class="edit-btn" onclick="loadStorages({}, ${page - 1})" ${page === 0 ? 'disabled' : ''}>Previous</button>
	            <span>${page + 1}</span>
	            <button class="edit-btn" onclick="loadStorages({}, ${page + 1})" ${isLastPage ? 'disabled' : ''}>Next</button>
	        `;
	}

	window.loadStorages = loadStorages;

	window.updatePageSize = function() {
		pageSize = parseInt(document.getElementById('pageSize').value, 10);
		loadStorages({}, 0);
	};

	window.applyFilters = function() {
		const filters = {
			createdDate: $('#filterCreatedDate').val(),
			productName: $('#filterProductName').val(),
			measurement: $('#filterMeasurement').val()
		};
		loadStorages(filters, 0);
	};

	window.createStorage = function() {
		localStorage.setItem('storage', JSON.stringify({ createdDate: new Date().toISOString() }));
		window.location.href = '/storage-form';
	}

	window.editStorage = function(id) {
		fetch(`/storage/view/${id}`)
			.then(response => {
				if (!response.ok) {
					throw new Error('Network response was not ok ' + response.statusText);
				}
				return response.json();
			})
			.then(storage => {
				localStorage.setItem('storage', JSON.stringify(storage));
				window.location.href = '/storage-form';
			})
			.catch(error => console.error('Error fetching storage:', error));
	}

	window.deleteStorage = function(id) {
		fetch(`/storage/delete/${id}`, {
			method: 'DELETE'
		})
			.then(response => {
				if (!response.ok) {
					throw new Error('Network response was not ok ' + response.statusText);
				}
				loadStorages();
			})
			.catch(error => console.error('Error deleting storage:', error));
	}

	window.toggleFilter = function() {
		const filterSection = document.querySelector('.filter-section');
		filterSection.style.display = filterSection.style.display === 'none' ? 'block' : 'none';
	}

	window.exportData = function() {
		const filters = {
			createdDate: $('#filterCreatedDate').val(),
			productName: $('#filterProductName').val(),
			measurement: $('#filterMeasurement').val()
		};
		const queryString = new URLSearchParams(filters).toString();
		const page = currentPage;
		const size = pageSize;

		fetch(`/storage/export?${queryString}&page=${page}&size=${size}`)
			.then(response => {
				if (!response.ok) {
					throw new Error('Network response was not ok ' + response.statusText);
				}
				return response.blob();
			})
			.then(blob => {
				const url = window.URL.createObjectURL(blob);
				const a = document.createElement('a');
				a.href = url;
				a.download = 'storages.csv';
				document.body.appendChild(a);
				a.click();
				window.URL.revokeObjectURL(url);
			})
			.catch(error => console.error('Error exporting data:', error));
	};

	window.importData = function() {
		document.getElementById('importFile').click();
	};

	document.getElementById('importFile').addEventListener('change', function(event) {
		const file = event.target.files[0];
		if (file) {
			const formData = new FormData();
			formData.append('file', file);

			fetch('/storage/import', {
				method: 'POST',
				body: formData
			})
				.then(response => {
					if (!response.ok) {
						throw new Error('Network response was not ok ' + response.statusText);
					}
					// Check if the response has content
					if (response.status === 204 || response.headers.get('content-length') === '0') {
						return null;
					}
					return response.json();
				})
				.then(data => {
					alert('Import successful');
					loadStorages();
				})
				.catch(error => {
					console.error('Error importing data:', error);
					alert('Import failed');
				});
		}
	});
});
