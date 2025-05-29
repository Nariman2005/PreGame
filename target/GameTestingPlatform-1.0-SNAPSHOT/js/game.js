/**
 * game.js - JavaScript functionality for Game detail pages
 * For PreGame Testing Platform
 */

document.addEventListener('DOMContentLoaded', function() {
    // Game image zoom functionality
    initGameImageZoom();

    // Initialize stat item hover effects
    initStatItemEffects();

    // Add smooth scrolling for navigation within the game page
    initSmoothScrolling();
});

/**
 * Adds zoom effect for the game cover image
 */
function initGameImageZoom() {
    const gameImage = document.querySelector('.game-image img');
    if (!gameImage) return;

    // Add mouse event listeners for zoom effect
    gameImage.addEventListener('mouseover', function() {
        this.style.transform = 'scale(1.05)';
        this.style.transition = 'transform 0.3s ease';
        this.style.cursor = 'pointer';
    });

    gameImage.addEventListener('mouseout', function() {
        this.style.transform = 'scale(1)';
    });

    // Open larger image in modal/lightbox when clicked
    gameImage.addEventListener('click', function() {
        const modal = createImageModal(this.src, this.alt);
        document.body.appendChild(modal);

        // Fade in effect
        setTimeout(() => {
            modal.style.opacity = '1';
        }, 10);

        // Close modal when clicking outside the image
        modal.addEventListener('click', function(e) {
            if (e.target === modal) {
                modal.style.opacity = '0';
                setTimeout(() => {
                    document.body.removeChild(modal);
                }, 300);
            }
        });
    });
}

/**
 * Creates a modal/lightbox element for the game image
 */
function createImageModal(imageSrc, imageAlt) {
    const modal = document.createElement('div');
    modal.style.position = 'fixed';
    modal.style.top = '0';
    modal.style.left = '0';
    modal.style.width = '100%';
    modal.style.height = '100%';
    modal.style.backgroundColor = 'rgba(0, 0, 0, 0.85)';
    modal.style.display = 'flex';
    modal.style.justifyContent = 'center';
    modal.style.alignItems = 'center';
    modal.style.zIndex = '1050';
    modal.style.cursor = 'pointer';
    modal.style.opacity = '0';
    modal.style.transition = 'opacity 0.3s ease';

    const img = document.createElement('img');
    img.src = imageSrc;
    img.alt = imageAlt;
    img.style.maxWidth = '90%';
    img.style.maxHeight = '90%';
    img.style.boxShadow = '0 5px 25px rgba(0, 0, 0, 0.5)';
    img.style.cursor = 'default';

    modal.appendChild(img);

    return modal;
}

/**
 * Adds interactive effects to game stat items
 */
function initStatItemEffects() {
    const statItems = document.querySelectorAll('.stat-item');
    if (statItems.length === 0) return;

    statItems.forEach(item => {
        // Add hover effect
        item.addEventListener('mouseenter', function() {
            this.style.transform = 'translateY(-5px)';
            this.style.boxShadow = '0 5px 15px rgba(0, 0, 0, 0.1)';

            // Change icon color on hover
            const icon = this.querySelector('.stat-icon');
            if (icon) {
                icon.style.color = getComputedStyle(document.documentElement)
                    .getPropertyValue('--primary-dark');
            }
        });

        item.addEventListener('mouseleave', function() {
            this.style.transform = '';
            this.style.boxShadow = '';

            // Reset icon color
            const icon = this.querySelector('.stat-icon');
            if (icon) {
                icon.style.color = getComputedStyle(document.documentElement)
                    .getPropertyValue('--primary');
            }
        });
    });
}

/**
 * Implements smooth scrolling for in-page navigation
 */
function initSmoothScrolling() {
    document.querySelectorAll('a[href^="#"]').forEach(anchor => {
        anchor.addEventListener('click', function(e) {
            e.preventDefault();

            const targetId = this.getAttribute('href');
            if (targetId === '#') return;

            const targetElement = document.querySelector(targetId);
            if (!targetElement) return;

            window.scrollTo({
                top: targetElement.offsetTop - 80, // Offset for fixed header
                behavior: 'smooth'
            });
        });
    });
}

/**
 * Initializes description expansion/collapse if it's too long
 * Add this function if you want to implement a "Read more" feature
 */
function initDescriptionToggle() {
    const descriptionContent = document.querySelector('.description-content');
    if (!descriptionContent || descriptionContent.textContent.length < 300) return;

    const fullText = descriptionContent.textContent;
    const truncatedText = fullText.substring(0, 300) + '...';

    descriptionContent.textContent = truncatedText;
    descriptionContent.classList.add('truncated');

    // Create "Read more" button
    const readMoreBtn = document.createElement('button');
    readMoreBtn.textContent = 'Read More';
    readMoreBtn.className = 'btn btn-outline-primary btn-sm mt-2';
    readMoreBtn.addEventListener('click', function() {
        if (descriptionContent.classList.contains('truncated')) {
            descriptionContent.textContent = fullText;
            descriptionContent.classList.remove('truncated');
            this.textContent = 'Read Less';
        } else {
            descriptionContent.textContent = truncatedText;
            descriptionContent.classList.add('truncated');
            this.textContent = 'Read More';
        }
    });

    descriptionContent.parentNode.appendChild(readMoreBtn);
}
