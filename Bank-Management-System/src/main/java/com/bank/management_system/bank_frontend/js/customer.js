class CustomerDashboard {
    constructor() {
        this.userData = BankUtils.getUserData();
        this.customerId = null;
        this.accounts = [];
        this.init();
    }

    async init() {
        if (!BankUtils.requireAuth()) return;

        // Update user info
        document.getElementById('userInfo').textContent =
            `Welcome, ${this.userData.fullName || this.userData.username} (Customer)`;

        // Initialize tabs
        this.initTabs();

        // Load customer data
        await this.loadCustomerData();

        // Load accounts
        await this.loadAccounts();

        // Update quick stats
        this.updateQuickStats();
    }

    initTabs() {
        const tabButtons = document.querySelectorAll('.tab-btn');
        const tabPanels = document.querySelectorAll('.tab-panel');

        tabButtons.forEach(btn => {
            btn.addEventListener('click', () => {
                const targetTab = btn.dataset.tab;

                // Update active tab button
                tabButtons.forEach(b => b.classList.remove('active'));
                btn.classList.add('active');

                // Show target tab panel
                tabPanels.forEach(panel => {
                    panel.classList.remove('active');
                    if (panel.id === `${targetTab}-tab`) {
                        panel.classList.add('active');
                    }
                });

                // Load tab-specific data
                this.loadTabData(targetTab);
            });
        });
    }

    async loadCustomerData() {
        try {
            // Since we don't have a direct endpoint, we'll get customer ID from user data
            // In a real app, you'd have an endpoint like /api/customers/me
            const user = await BankUtils.apiCall(`/users/username/${this.userData.username}`);
            if (user.customer) {
                this.customerId = user.customer.customerId;
            }
        } catch (error) {
            console.error('Failed to load customer data:', error);
            BankUtils.showAlert('Failed to load customer information', 'danger');
        }
    }

    async loadAccounts() {
        if (!this.customerId) return;

        try {
            this.accounts = await BankUtils.apiCall(`/accounts/customer/${this.customerId}`);
            this.displayAccounts();
            this.populateAccountDropdowns();
        } catch (error) {
            console.error('Failed to load accounts:', error);
            document.getElementById('accountsList').innerHTML =
                '<p class="alert alert-danger">Failed to load accounts</p>';
        }
    }

    displayAccounts() {
        const container = document.getElementById('accountsList');

        if (this.accounts.length === 0) {
            container.innerHTML = '<p class="alert alert-warning">No accounts found</p>';
            return;
        }

        const accountsHTML = this.accounts.map(account => `
            <div class="card" style="margin-bottom: 15px;">
                <div style="display: flex; justify-content: between; align-items: center;">
                    <div>
                        <h3>${account.accountNumber}</h3>
                        <p><strong>Type:</strong> ${account.accountType} |
                           <strong>Status:</strong> ${account.status}</p>
                    </div>
                    <div style="text-align: right;">
                        <div style="font-size: 24px; font-weight: bold; color: #28a745;">
                            ${BankUtils.formatCurrency(account.balance)}
                        </div>
                        <small>Created: ${BankUtils.formatDate(account.createdDate)}</small>
                    </div>
                </div>
            </div>
        `).join('');

        container.innerHTML = accountsHTML;
    }

    populateAccountDropdowns() {
        const fromAccountSelect = document.getElementById('fromAccount');
        const transactionAccountSelect = document.getElementById('transactionAccount');

        // Clear existing options
        fromAccountSelect.innerHTML = '<option value="">Select your account</option>';
        transactionAccountSelect.innerHTML = '<option value="">Select account</option>';

        // Add account options
        this.accounts.forEach(account => {
            if (account.status === 'ACTIVE') {
                const optionText = `${account.accountNumber} - ${BankUtils.formatCurrency(account.balance)}`;

                const fromOption = new Option(optionText, account.accountNumber);
                const transOption = new Option(optionText, account.accountId);

                fromAccountSelect.add(fromOption);
                transactionAccountSelect.add(transOption);
            }
        });

        // Add event listener for transaction account selection
        transactionAccountSelect.addEventListener('change', () => {
            this.loadTransactions();
        });
    }

    updateQuickStats() {
        const totalBalance = this.accounts.reduce((sum, account) => sum + account.balance, 0);
        const accountCount = this.accounts.length;

        document.getElementById('totalBalance').textContent = BankUtils.formatCurrency(totalBalance);
        document.getElementById('accountCount').textContent = accountCount;
    }

    async loadTabData(tabName) {
        switch(tabName) {
            case 'transactions':
                await this.loadTransactions();
                break;
            case 'feedback':
                await this.loadFeedbackHistory();
                break;
        }
    }

    async loadTransactions() {
        const accountSelect = document.getElementById('transactionAccount');
        const accountId = accountSelect.value;

        if (!accountId) {
            document.getElementById('transactionsList').innerHTML =
                '<p class="alert alert-info">Please select an account to view transactions</p>';
            return;
        }

        try {
            const transactions = await BankUtils.apiCall(`/transactions/account/${accountId}`);
            this.displayTransactions(transactions);
        } catch (error) {
            console.error('Failed to load transactions:', error);
            document.getElementById('transactionsList').innerHTML =
                '<p class="alert alert-danger">Failed to load transactions</p>';
        }
    }

    displayTransactions(transactions) {
        const container = document.getElementById('transactionsList');

        if (transactions.length === 0) {
            container.innerHTML = '<p class="alert alert-info">No transactions found</p>';
            return;
        }

        const transactionsHTML = `
            <table class="table">
                <thead>
                    <tr>
                        <th>Date</th>
                        <th>Description</th>
                        <th>Type</th>
                        <th>Amount</th>
                        <th>From/To</th>
                    </tr>
                </thead>
                <tbody>
                    ${transactions.map(transaction => `
                        <tr>
                            <td>${BankUtils.formatDateTime(transaction.timestamp)}</td>
                            <td>${transaction.description || 'N/A'}</td>
                            <td>
                                <span class="badge ${
                                    transaction.type === 'DEPOSIT' ? 'badge-success' :
                                    transaction.type === 'WITHDRAWAL' ? 'badge-warning' : 'badge-info'
                                }">
                                    ${transaction.type}
                                </span>
                            </td>
                            <td style="color: ${
                                transaction.type === 'DEPOSIT' ? '#28a745' : '#dc3545'
                            };">
                                ${transaction.type === 'DEPOSIT' ? '+' : '-'}
                                ${BankUtils.formatCurrency(transaction.amount)}
                            </td>
                            <td>
                                ${transaction.fromAccount ? `From: ${transaction.fromAccount.accountNumber}` : ''}
                                ${transaction.toAccount ? `To: ${transaction.toAccount.accountNumber}` : ''}
                            </td>
                        </tr>
                    `).join('')}
                </tbody>
            </table>
        `;

        container.innerHTML = transactionsHTML;

        // Update recent transactions count
        document.getElementById('recentTransactions').textContent = transactions.length;
    }

    // Transfer Money
    initTransferForm() {
        const form = document.getElementById('transferForm');
        form.addEventListener('submit', async (e) => {
            e.preventDefault();
            await this.handleTransfer();
        });
    }

    async handleTransfer() {
        const fromAccount = document.getElementById('fromAccount').value;
        const toAccount = document.getElementById('toAccount').value;
        const amount = parseFloat(document.getElementById('transferAmount').value);
        const description = document.getElementById('transferDescription').value;
        const transferBtn = document.getElementById('transferBtn');

        if (!fromAccount || !toAccount || !amount) {
            BankUtils.showAlert('Please fill all required fields', 'danger');
            return;
        }

        if (amount <= 0) {
            BankUtils.showAlert('Amount must be greater than 0', 'danger');
            return;
        }

        const originalText = BankUtils.showLoading(transferBtn);

        try {
            await BankUtils.apiCall('/transactions/transfer', {
                method: 'POST',
                body: JSON.stringify({
                    fromAccountNumber: fromAccount,
                    toAccountNumber: toAccount,
                    amount: amount,
                    description: description
                })
            });

            BankUtils.showAlert('Transfer successful!', 'success');
            document.getElementById('transferForm').reset();

            // Reload accounts to update balances
            await this.loadAccounts();
            this.updateQuickStats();

        } catch (error) {
            BankUtils.showAlert(error.message || 'Transfer failed', 'danger');
        } finally {
            BankUtils.hideLoading(transferBtn, originalText);
        }
    }

    // Feedback
    initFeedbackForm() {
        const form = document.getElementById('feedbackForm');
        form.addEventListener('submit', async (e) => {
            e.preventDefault();
            await this.handleFeedback();
        });
    }

    async handleFeedback() {
        const message = document.getElementById('feedbackMessage').value;
        const feedbackBtn = document.getElementById('feedbackBtn');

        if (!message.trim()) {
            BankUtils.showAlert('Please enter your feedback message', 'danger');
            return;
        }

        const originalText = BankUtils.showLoading(feedbackBtn);

        try {
            // Note: You'll need to create a feedback endpoint in your backend
            // For now, we'll simulate the API call
            BankUtils.showAlert('Feedback submitted successfully! The manager will review it soon.', 'success');
            document.getElementById('feedbackForm').reset();

            // Reload feedback history
            await this.loadFeedbackHistory();

        } catch (error) {
            BankUtils.showAlert('Failed to submit feedback', 'danger');
        } finally {
            BankUtils.hideLoading(feedbackBtn, originalText);
        }
    }

    async loadFeedbackHistory() {
        // Note: You'll need to implement this endpoint in your backend
        // For now, we'll show a placeholder
        document.getElementById('feedbackHistory').innerHTML = `
            <p class="alert alert-info">Feedback history feature will be available soon</p>
        `;
    }
}

// Add badge styles to CSS
const style = document.createElement('style');
style.textContent = `
    .badge {
        padding: 4px 8px;
        border-radius: 4px;
        font-size: 12px;
        font-weight: bold;
    }
    .badge-success { background: #d4edda; color: #155724; }
    .badge-warning { background: #fff3cd; color: #856404; }
    .badge-info { background: #d1ecf1; color: #0c5460; }
`;
document.head.appendChild(style);

// Initialize dashboard when DOM is loaded
document.addEventListener('DOMContentLoaded', function() {
    new CustomerDashboard();
});