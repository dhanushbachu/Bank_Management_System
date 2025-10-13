class AuthManager {
    constructor() {
        this.currentRole = 'CUSTOMER';
        this.initEventListeners();
    }

    initEventListeners() {
        // Role selection
        const roleButtons = document.querySelectorAll('.role-btn');
        roleButtons.forEach(btn => {
            btn.addEventListener('click', (e) => {
                this.selectRole(e.target.dataset.role);
            });
        });

        // Login form submission
        const loginForm = document.getElementById('loginForm');
        if (loginForm) {
            loginForm.addEventListener('submit', (e) => {
                e.preventDefault();
                this.handleLogin();
            });
        }
    }

    selectRole(role) {
        this.currentRole = role;

        // Update UI
        document.querySelectorAll('.role-btn').forEach(btn => {
            btn.classList.remove('active');
        });
        document.querySelector(`[data-role="${role}"]`).classList.add('active');

        // Update form placeholder based on role
        const usernameInput = document.getElementById('username');
        if (usernameInput) {
            switch(role) {
                case 'MANAGER':
                    usernameInput.placeholder = 'Enter manager username';
                    break;
                case 'CASHIER':
                    usernameInput.placeholder = 'Enter cashier username';
                    break;
                case 'CUSTOMER':
                    usernameInput.placeholder = 'Enter customer username';
                    break;
            }
        }
    }

    async handleLogin() {
        const username = document.getElementById('username').value;
        const password = document.getElementById('password').value;
        const loginBtn = document.getElementById('loginBtn');

        if (!username || !password) {
            BankUtils.showAlert('Please enter both username and password', 'danger');
            return;
        }

        const originalText = BankUtils.showLoading(loginBtn);

        try {
            const loginData = {
                username: username,
                password: password,
                role: this.currentRole
            };

            const response = await BankUtils.apiCall('/auth/login', {
                method: 'POST',
                body: JSON.stringify(loginData)
            });

            // Store user data
            localStorage.setItem('bankUser', JSON.stringify({
                token: response.token,
                username: response.user.username,
                fullName: response.user.fullName,
                role: response.user.role,
                userId: response.user.userId
            }));

            BankUtils.showAlert('Login successful!', 'success');

            // Redirect based on role
            setTimeout(() => {
                this.redirectToDashboard(response.user.role);
            }, 1000);

        } catch (error) {
            BankUtils.showAlert(error.message || 'Login failed. Please check your credentials.', 'danger');
        } finally {
            BankUtils.hideLoading(loginBtn, originalText);
        }
    }

    redirectToDashboard(role) {
        switch(role) {
            case 'MANAGER':
                window.location.href = 'manager-dashboard.html';
                break;
            case 'CASHIER':
                window.location.href = 'cashier-dashboard.html';
                break;
            case 'CUSTOMER':
                window.location.href = 'customer-dashboard.html';
                break;
            default:
                window.location.href = 'dashboard.html';
        }
    }
}

// Initialize auth manager when DOM is loaded
document.addEventListener('DOMContentLoaded', function() {
    new AuthManager();
});