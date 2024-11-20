// Toggle the submenu visibility when clicking on a menu item
document.querySelectorAll('.menu-section > a').forEach(item => {
    item.addEventListener('click', function (e) {
        e.preventDefault(); // Prevent default link behavior
        const menuSection = this.parentNode;
        menuSection.classList.toggle('active'); // Toggle the 'active' class to show/hide submenu
    });
});