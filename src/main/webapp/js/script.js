// Main JavaScript file for Game Testing Platform

// Flag to indicate script has loaded successfully
window.scriptLoaded = true;

console.log('Game Testing Platform JS loaded successfully');

document.addEventListener('DOMContentLoaded', function() {
    console.log('DOM fully loaded and parsed');

    // Smooth scrolling for anchor links
    document.querySelectorAll('a[href^="#"]').forEach(anchor => {
        anchor.addEventListener('click', function(e) {
            // e.preventDefault(); // Prevent default only if target exists
            const targetId = this.getAttribute('href');

            if (targetId === '#') return; // Do nothing if it's just "#"

            const targetElement = document.querySelector(targetId);

            if (targetElement) {
                e.preventDefault(); // Prevent default jump only if we found the element
                window.scrollTo({
                    top: targetElement.offsetTop - 80, // Adjusted for fixed header (e.g., 70-80px)
                    behavior: 'smooth'
                });
            }
            // If targetElement is not found, let the default anchor behavior happen (or do nothing)
        });
    });

    // Password strength meter
    const passwordInput = document.getElementById('password');
    const passwordStrength = document.querySelector('.password-strength');

    if (passwordInput && passwordStrength) {
        passwordInput.addEventListener('input', function() {
            const password = this.value;
            let strength = 0;

            if (password.length >= 8) strength += 1;
            if (password.length >= 12) strength += 1;
            if (/[A-Z]/.test(password)) strength += 1;
            if (/[a-z]/.test(password)) strength += 1;
            if (/[0-9]/.test(password)) strength += 1;
            if (/[^A-Za-z0-9]/.test(password)) strength += 1;

            let statusText = '';
            let statusClass = '';

            switch (true) {
                case (strength <= 2):
                    statusText = 'Weak';
                    statusClass = 'weak';
                    break;
                case (strength <= 4):
                    statusText = 'Moderate';
                    statusClass = 'moderate';
                    break;
                default:
                    statusText = 'Strong';
                    statusClass = 'strong';
                    break;
            }
            passwordStrength.textContent = statusText;
            passwordStrength.className = 'password-strength ' + statusClass;
        });
    }

    // Animation for register options
    const registerOptions = document.querySelectorAll('.register-option');
    if (registerOptions.length) {
        registerOptions.forEach((option, index) => {
            option.style.animationDelay = (index * 0.2) + 's';
            option.classList.add('fade-in-up');
        });
    }

    // Helper function for email validation
    function isValidEmail(email) {
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        return emailRegex.test(email);
    }

    // Newsletter form validation
    const newsletterForm = document.querySelector('.newsletter-form');
    if (newsletterForm) {
        newsletterForm.addEventListener('submit', function(event) {
            const emailInput = this.querySelector('input[type="email"]');
            if (!emailInput.value.trim() || !isValidEmail(emailInput.value)) {
                event.preventDefault();
                alert('Please enter a valid email address for the newsletter.'); // More specific alert
            }
        });
    }

    // Tab functionality for dashboard pages
    const tabButtons = document.querySelectorAll('.tab-button');
    const tabContents = document.querySelectorAll('.tab-content');
    if (tabButtons.length && tabContents.length) {
        tabButtons.forEach(button => {
            button.addEventListener('click', function() {
                tabButtons.forEach(btn => btn.classList.remove('active'));
                tabContents.forEach(content => content.classList.remove('active'));
                this.classList.add('active');
                const targetContent = document.querySelector(this.dataset.target);
                if (targetContent) {
                    targetContent.classList.add('active');
                }
            });
        });
    }

    // Initialize tooltips
    const tooltips = document.querySelectorAll('[data-tooltip]');
    tooltips.forEach(tooltipHost => { // Renamed variable for clarity
        let tooltipEl = null; // Keep track of the created tooltip element

        tooltipHost.addEventListener('mouseenter', function() {
            const tooltipText = this.dataset.tooltip;
            tooltipEl = document.createElement('div');
            tooltipEl.className = 'tooltip';
            tooltipEl.textContent = tooltipText;
            document.body.appendChild(tooltipEl);

            const rect = this.getBoundingClientRect();
            // Position tooltip above the element, centered
            tooltipEl.style.left = (rect.left + (rect.width / 2) - (tooltipEl.offsetWidth / 2)) + 'px';
            tooltipEl.style.top = (rect.top - tooltipEl.offsetHeight - 10) + 'px'; // 10px gap
            tooltipEl.classList.add('visible');
        });

        tooltipHost.addEventListener('mouseleave', function() {
            if (tooltipEl && document.body.contains(tooltipEl)) {
                document.body.removeChild(tooltipEl);
                tooltipEl = null; // Reset for next time
            }
        });
    });


    // Initialize modal functionality
    const modalTriggers = document.querySelectorAll('[data-modal]');
    const modals = document.querySelectorAll('.modal'); // All modal elements
    const modalCloseButtons = document.querySelectorAll('.modal-close');

    modalTriggers.forEach(trigger => {
        trigger.addEventListener('click', function(e) {
            e.preventDefault();
            const modalId = this.dataset.modal;
            const modal = document.getElementById(modalId);
            if (modal) {
                modal.classList.add('show');
                document.body.classList.add('modal-open'); // Prevent body scroll
            }
        });
    });

    modalCloseButtons.forEach(button => {
        button.addEventListener('click', function() {
            const modal = this.closest('.modal');
            if (modal) {
                modal.classList.remove('show');
                if (!document.querySelector('.modal.show')) { // Only remove if no other modals are open
                    document.body.classList.remove('modal-open');
                }
            }
        });
    });

    modals.forEach(modal => {
        modal.addEventListener('click', function(e) {
            // Close if clicked on the modal backdrop itself (not its content)
            if (e.target === this) {
                this.classList.remove('show');
                if (!document.querySelector('.modal.show')) {
                    document.body.classList.remove('modal-open');
                }
            }
        });
    });

    // Add main content wrapper (Optional, consider if really needed, can affect layout)
    // const main = document.querySelector('main');
    // if (main && !main.querySelector('.main-content')) {
    //     console.log('Wrapping main content...');
    //     const mainContent = document.createElement('div');
    //     mainContent.className = 'main-content';
    //     // Move all children of main into mainContent
    //     while (main.firstChild) {
    //         mainContent.appendChild(main.firstChild);
    //     }
    //     main.appendChild(mainContent);
    // }

    // --- Functions from the second DOMContentLoaded block ---

    // Mobile menu toggle functionality
    const setupMobileMenu = () => {
        const mobileMenuButton = document.querySelector('.mobile-menu-toggle');
        const navMenu = document.querySelector('header nav ul'); // Be more specific for the nav menu

        if (mobileMenuButton && navMenu) {
            mobileMenuButton.addEventListener('click', () => {
                navMenu.classList.toggle('active');
                mobileMenuButton.classList.toggle('active'); // Toggle active class on button too
            });
        } else {
            // console.warn('Mobile menu button or nav menu not found.');
        }
    };

    // Add active class to navigation items based on current page
    const setActiveNavItem = () => {
        const currentPath = window.location.pathname;
        const navLinks = document.querySelectorAll('header nav ul li a'); // Be more specific

        navLinks.forEach(link => {
            link.classList.remove('active'); // Remove active from all first
            const linkPath = link.getAttribute('href');
            // Basic matching, might need refinement for complex URLs or query params
            if (linkPath === currentPath || (linkPath !== '/' && currentPath.startsWith(linkPath))) {
                link.classList.add('active');
            }
        });
        // Handle root path ("/") separately if needed, e.g., ensure only exact match
        if (currentPath === '/') {
            const homeLink = document.querySelector('header nav ul li a[href="/"]');
            if (homeLink) homeLink.classList.add('active');
        }
    };

    // Form validation
    const setupFormValidation = () => {
        const forms = document.querySelectorAll('form[data-validate="true"]');

        forms.forEach(form => {
            form.addEventListener('submit', function(e) {
                let isValid = true;
                // Clear previous errors
                form.querySelectorAll('.input-error').forEach(el => el.classList.remove('input-error'));
                form.querySelectorAll('.error-message-text').forEach(el => el.remove());


                const requiredFields = form.querySelectorAll('[required]');
                requiredFields.forEach(field => {
                    if (!field.value.trim()) {
                        isValid = false;
                        displayFieldError(field, `${field.dataset.name || 'This field'} is required.`);
                    }
                });

                const emailFields = form.querySelectorAll('input[type="email"]');
                emailFields.forEach(field => {
                    if (field.value.trim() && !isValidEmail(field.value.trim())) {
                        isValid = false;
                        displayFieldError(field, 'Please enter a valid email address.');
                    }
                });

                const passwordField = form.querySelector('input[type="password"][data-validate-password="true"]');
                if (passwordField && passwordField.value.trim()) {
                    if (passwordField.value.length < 8) {
                        isValid = false;
                        displayFieldError(passwordField, 'Password must be at least 8 characters long.');
                    }

                    const confirmPasswordField = form.querySelector('input[type="password"][data-password-confirm="true"]'); // Ensure type="password"
                    if (confirmPasswordField && confirmPasswordField.value.trim() &&
                        passwordField.value !== confirmPasswordField.value) {
                        isValid = false;
                        displayFieldError(confirmPasswordField, 'Passwords do not match.');
                    }
                }


                if (!isValid) {
                    e.preventDefault();
                }
            });

            // Clear errors on input
            const inputs = form.querySelectorAll('input, textarea, select');
            inputs.forEach(input => {
                input.addEventListener('input', function() {
                    clearFieldError(this);
                });
                input.addEventListener('change', function() { // Also on change for select, etc.
                    clearFieldError(this);
                });
            });
        });
    };

    function displayFieldError(field, message) {
        field.classList.add('input-error');
        let errorMsgElement = field.parentNode.querySelector(`.error-message-text[data-for="${field.id || field.name}"]`);
        if (!errorMsgElement) {
            errorMsgElement = document.createElement('div');
            errorMsgElement.classList.add('error-message-text'); // Use a different class to avoid conflict with general .error-message
            errorMsgElement.dataset.for = field.id || field.name;
            // Insert after the field or its label
            const parent = field.parentNode;
            if (parent.tagName.toLowerCase() === 'label') {
                parent.parentNode.insertBefore(errorMsgElement, parent.nextSibling);
            } else {
                parent.insertBefore(errorMsgElement, field.nextSibling);
            }
        }
        errorMsgElement.textContent = message;
        errorMsgElement.style.display = 'block';
    }

    function clearFieldError(field) {
        field.classList.remove('input-error');
        const errorMsgElement = field.parentNode.querySelector(`.error-message-text[data-for="${field.id || field.name}"]`);
        if (errorMsgElement) {
            errorMsgElement.style.display = 'none';
            // errorMsgElement.remove(); // Or remove it completely
        }
    }


    // File upload preview
    const setupFileUploadPreview = () => {
        const fileInputs = document.querySelectorAll('input[type="file"][data-preview="true"]');
        fileInputs.forEach(input => {
            const previewContainerId = input.dataset.previewContainer;
            const previewContainer = document.querySelector(previewContainerId);

            if (previewContainer) {
                input.addEventListener('change', function() {
                    previewContainer.innerHTML = ''; // Clear previous previews
                    if (this.files && this.files.length > 0) {
                        Array.from(this.files).forEach(file => {
                            if (file.type.startsWith('image/')) {
                                const reader = new FileReader();
                                reader.onload = function(e) {
                                    const img = document.createElement('img');
                                    img.src = e.target.result;
                                    img.classList.add('file-preview');
                                    img.style.maxWidth = '100px'; // Add some basic styling
                                    img.style.maxHeight = '100px';
                                    img.style.margin = '5px';
                                    previewContainer.appendChild(img);
                                };
                                reader.readAsDataURL(file);
                            } else {
                                const fileInfo = document.createElement('div');
                                fileInfo.classList.add('file-info');
                                fileInfo.textContent = `${file.name} (${(file.size / 1024).toFixed(2)} KB)`;
                                previewContainer.appendChild(fileInfo);
                            }
                        });
                    }
                });
            } else {
                // console.warn(`Preview container not found for input: ${previewContainerId}`);
            }
        });
    };

    // Add fade-in animation to elements with fade-in class
    const animateFadeIn = () => {
        const fadeElements = document.querySelectorAll('.fade-in');
        if (!('IntersectionObserver' in window)) {
            console.log('IntersectionObserver not supported, showing all .fade-in elements immediately.');
            fadeElements.forEach(el => el.classList.add('visible'));
            return;
        }

        const fadeInObserver = new IntersectionObserver((entries, observer) => {
            entries.forEach(entry => {
                if (entry.isIntersecting) {
                    entry.target.classList.add('visible');
                    observer.unobserve(entry.target); // Stop observing once visible
                }
            });
        }, { threshold: 0.1 }); // Trigger when 10% of the element is visible

        fadeElements.forEach(element => {
            fadeInObserver.observe(element);
        });
    };
/**
 * PreGame Testing Platform
 * Main JavaScript file for client-side functionality
 */

document.addEventListener('DOMContentLoaded', function() {
    // Initialize tooltips if present
    initTooltips();
    
    // Initialize game filter form if present
    initGameFilter();
    
    // Enable responsive navigation menu
    initResponsiveNav();
    
    // Animations for page elements
    animatePageElements();
});

/**
 * Initialize tooltip elements
 */
function initTooltips() {
    const tooltips = document.querySelectorAll('[data-tooltip]');
    
    tooltips.forEach(tooltip => {
        tooltip.addEventListener('mouseenter', function() {
            const tooltipText = this.getAttribute('data-tooltip');
            
            const tooltipElement = document.createElement('div');
            tooltipElement.className = 'tooltip';
            tooltipElement.textContent = tooltipText;
            
            document.body.appendChild(tooltipElement);
            
            const rect = this.getBoundingClientRect();
            tooltipElement.style.top = (rect.top - tooltipElement.offsetHeight - 5) + 'px';
            tooltipElement.style.left = (rect.left + (rect.width / 2) - (tooltipElement.offsetWidth / 2)) + 'px';
            
            setTimeout(() => tooltipElement.classList.add('visible'), 10);
        });
        
        tooltip.addEventListener('mouseleave', function() {
            const tooltipElement = document.querySelector('.tooltip');
            if (tooltipElement) {
                tooltipElement.classList.remove('visible');
                setTimeout(() => tooltipElement.remove(), 300);
            }
        });
    });
}

/**
 * Initialize game filter functionality
 */
function initGameFilter() {
    const filterForm = document.querySelector('.game-filter form');
    
    if (filterForm) {
        // Get URL parameters to set initial filter values
        const urlParams = new URLSearchParams(window.location.search);
        const gameTypeParam = urlParams.get('gameType');
        const esrbRatingParam = urlParams.get('esrbRating');
        
        // Set initial values if present in URL
        if (gameTypeParam) {
            const gameTypeSelect = filterForm.querySelector('#gameType');
            gameTypeSelect.value = gameTypeParam;
        }
        
        if (esrbRatingParam) {
            const esrbRatingSelect = filterForm.querySelector('#esrbRating');
            esrbRatingSelect.value = esrbRatingParam;
        }
    }
}

/**
 * Initialize responsive navigation menu
 */
function initResponsiveNav() {
    const header = document.querySelector('header');
    
    if (header) {
        // Create mobile menu toggle button if it doesn't exist
        if (!document.querySelector('.mobile-menu-toggle')) {
            const mobileMenuToggle = document.createElement('button');
            mobileMenuToggle.className = 'mobile-menu-toggle';
            mobileMenuToggle.innerHTML = '<span></span><span></span><span></span>';
            header.querySelector('.header-container').appendChild(mobileMenuToggle);
            
            // Toggle mobile menu on click
            mobileMenuToggle.addEventListener('click', function() {
                header.classList.toggle('mobile-menu-open');
            });
        }
    }
}

/**
 * Add animation to page elements
 */
function animatePageElements() {
    // Fade in game cards with staggered delay
    const gameCards = document.querySelectorAll('.game-card');
    
    gameCards.forEach((card, index) => {
        card.style.opacity = '0';
        card.style.transform = 'translateY(20px)';
        card.style.transition = 'opacity 0.5s ease, transform 0.5s ease';
        
        setTimeout(() => {
            card.style.opacity = '1';
            card.style.transform = 'translateY(0)';
        }, index * 100);
    });
}

/**
 * Confirm deletion of a game
 * @param {number} gameId - ID of the game to delete
 */
function confirmDelete(gameId) {
    if (confirm("Are you sure you want to delete this game? This action cannot be undone.")) {
        const form = document.createElement('form');
        form.method = 'POST';
        form.action = contextPath + '/games/delete';
        
        const idInput = document.createElement('input');
        idInput.type = 'hidden';
        idInput.name = 'id';
        idInput.value = gameId;
        
        form.appendChild(idInput);
        document.body.appendChild(form);
        form.submit();
    }
}

/**
 * Show a notification message
 * @param {string} message - The message to display
 * @param {string} type - Message type (success, error, info)
 */
function showNotification(message, type = 'info') {
    const notification = document.createElement('div');
    notification.className = `notification ${type}`;
    notification.textContent = message;
    
    document.body.appendChild(notification);
    
    setTimeout(() => {
        notification.classList.add('visible');
    }, 10);
    
    setTimeout(() => {
        notification.classList.remove('visible');
        setTimeout(() => {
            notification.remove();
        }, 300);
    }, 5000);
}

/**
 * Set the context path for use in JavaScript
 * This should be set in the JSP that includes this script
 * @example <script>const contextPath = '${pageContext.request.contextPath}';</script>
 */
if (typeof contextPath === 'undefined') {
    const contextPath = '';
}
    // Initialize all setup functions
    setupMobileMenu();
    setActiveNavItem();
    setupFormValidation();
    setupFileUploadPreview();
    animateFadeIn(); // Call animateFadeIn

    console.log('All event listeners and initializations are set up.');
});