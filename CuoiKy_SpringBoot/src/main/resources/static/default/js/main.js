// Smooth scroll for anchor links
document.querySelectorAll('a[href^="#"]').forEach(anchor => {
    anchor.addEventListener('click', function (e) {
        e.preventDefault();
        document.querySelector(this.getAttribute('href')).scrollIntoView({
            behavior: 'smooth'
        });
    });
});

// Video play button functionality
document.addEventListener('DOMContentLoaded', function() {
    const videoBtn = document.querySelector('.video-btn');
    if (videoBtn) {
        videoBtn.addEventListener('click', function() {
            // Add your video play functionality here
            console.log('Video play button clicked');
        });
    }
});

// Navbar scroll effect
window.addEventListener('scroll', function() {
    const navbar = document.querySelector('.navbar');
    if (window.scrollY > 50) {
        navbar.classList.add('shadow-sm', 'bg-white');
    } else {
        navbar.classList.remove('shadow-sm', 'bg-white');
    }
});

// Initialize tooltips
var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'));
var tooltipList = tooltipTriggerList.map(function (tooltipTriggerEl) {
    return new bootstrap.Tooltip(tooltipTriggerEl);
});