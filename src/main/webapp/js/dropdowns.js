// Add event listener for dropdown toggle
document.addEventListener('DOMContentLoaded', function() {
    const dropdownBtn = document.querySelector('.user-dropdown-btn');
    const dropdownContent = document.querySelector('.user-dropdown-content');

    if (dropdownBtn && dropdownContent) {
        // Toggle dropdown when button is clicked
        dropdownBtn.addEventListener('click', function(e) {
            e.preventDefault();
            dropdownContent.classList.toggle('show');

            // Update aria-expanded attribute for accessibility
            const isExpanded = dropdownContent.classList.contains('show');
            dropdownBtn.setAttribute('aria-expanded', isExpanded);
        });

        // Close dropdown when clicking outside
        document.addEventListener('click', function(e) {
            if (!dropdownBtn.contains(e.target)) {
                dropdownContent.classList.remove('show');
                dropdownBtn.setAttribute('aria-expanded', 'false');
            }
        });
    }
});

