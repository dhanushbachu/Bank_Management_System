// API Base URL - Update this to match your backend
const API_BASE = 'http://localhost:8080/api';

// Common utility functions
class BankUtils {

    // Show loading spinner
    static showLoading(button) {
        const originalText = button.innerHTML;
        button.innerHTML = '<div class="loading"></div>';
        button.disabled = true;
        return originalText;
    }

    // Hide loading spinner
    static hideLoading(button, originalText) {
        button.innerHTML = originalText;
        button.disabled = false;
    }

    // Show alert message
    static showAlert(message, type = 'success', container = null) {
        const alertDiv = document.createElement('div');
        alertDiv.className = `alert alert-${type}`;
        alertDiv.textContent = message;

        if (container) {
            container.insertBefore(alertDiv, container.firstChild);
        } else {
            document.body.insertBefore(alertDiv, document.body.firstChild);
        }

        setTimeout(() => {
            alertDiv.remove();
        }, 5000);
    }

    // Format currency
    static formatCurrency(amount) {
        return new Intl.NumberFormat('en-IN', {
            style: 'currency',
            currency: 'INR'
        }).format(amount);
    }

    // Format date
    static formatDate(dateString) {
        return new Date(dateString).toLocaleDateString('en-IN');
    }

    // Format date time
    static formatDateTime(dateString) {
        return new Date(dateString).toLocaleString('en-IN');
    }

    // Get stored user data
    static getUserData() {
        return JSON.parse(localStorage.getItem('bankUser') || '{}');
    }

    // Check if user is logged in
    static isLoggedIn() {
        const user = this.getUserData();
        return user && user.token;
    }

    // Redirect to login if not authenticated
    static requireAuth() {
        if (!this.isLoggedIn()) {
            window.location.href = 'index.html';
            return false;
        }
        return true;
    }

    // Make API request with authentication
    static async apiCall(endpoint, options = {}) {
        const user = this.getUserData();
        const url = `${API_BASE}${endpoint}`;

        const defaultOptions = {
            headers: {
                'Content-Type': 'application/json',
            }
        };

        // Add authorization header if user is logged in
        if (user && user.token) {
            defaultOptions.headers['Authorization'] = `Bearer ${user.token}`;
        }

        const finalOptions = { ...defaultOptions, ...options };

        try {
            const response = await fetch(url, finalOptions);
            const data = await response.json();

            if (!response.ok) {
                throw new Error(data.message || `HTTP error! status: ${response.status}`);
            }

            return data;
        } catch (error) {
            console.error('API Call failed:', error);
            throw error;
        }
    }

    // Logout user
    static logout() {
        localStorage.removeItem('bankUser');
        window.location.href = 'index.html';
    }
}

// Initialize common functionality
document.addEventListener('DOMContentLoaded', function() {
    // Add logout button to all pages except login
    if (!window.location.pathname.includes('index.html') && BankUtils.isLoggedIn()) {
        const user = BankUtils.getUserData();

        // Update user info in navbar if exists
        const userInfoElement = document.getElementById('userInfo');
        if (userInfoElement) {
            userInfoElement.textContent = `Welcome, ${user.fullName || user.username} (${user.role})`;
        }

        // Add logout functionality
        const logoutBtn = document.getElementById('logoutBtn');
        if (logoutBtn) {
            logoutBtn.addEventListener('click', BankUtils.logout);
        }
    }
});