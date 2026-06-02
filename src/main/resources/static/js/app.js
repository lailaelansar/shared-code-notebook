// Main application logic with routing
class App {
    constructor() {
        this.currentPage = null;
        this.user = this.getStoredUser();
        this.setupRouting();
    }

    getStoredUser() {
        const stored = localStorage.getItem('user');
        return stored ? JSON.parse(stored) : null;
    }

    setupRouting() {
        window.addEventListener('hashchange', () => this.handleRoute());
        this.handleRoute();
    }

    handleRoute() {
        const hash = window.location.hash || '#/login';
        const [path, ...params] = hash.slice(2).split('/');

        // Check authentication
        if (!this.user && path !== 'login' && path !== 'register') {
            window.location.hash = '#/login';
            return;
        }

        const app = document.getElementById('app');

        switch (path) {
            case 'login':
                this.renderLogin(app);
                break;
            case 'register':
                this.renderRegister(app);
                break;
            case 'dashboard':
                this.renderDashboard(app);
                break;
            case 'projects':
                if (params.length > 0 && params[0] === 'new') {
                    this.renderProjectForm(app);
                } else if (params.length > 0 && params[0] === 'edit') {
                    const id = params[1];
                    this.renderProjectForm(app, id);
                } else {
                    this.renderProjects(app);
                }
                break;
            case 'users':
                if (params.length > 0 && params[0] === 'edit') {
                    const id = params[1];
                    this.renderUserForm(app, id);
                } else {
                    this.renderUsers(app);
                }
                break;
            default:
                window.location.hash = '#/dashboard';
        }
    }

    renderLogin(container) {
        const template = document.getElementById('login-template').content.cloneNode(true);
        container.innerHTML = '';
        container.appendChild(template);

        const form = document.getElementById('login-form');
        form.addEventListener('submit', async (e) => {
            e.preventDefault();
            const email = document.getElementById('login-email').value;
            const password = document.getElementById('login-password').value;

            try {
                await api.login(email, password);
                this.user = this.getStoredUser();
                window.location.hash = '#/dashboard';
            } catch (error) {
                alert('Login failed: ' + error.message);
            }
        });
    }

    renderRegister(container) {
        const template = document.getElementById('register-template').content.cloneNode(true);
        container.innerHTML = '';
        container.appendChild(template);

        const form = document.getElementById('register-form');
        form.addEventListener('submit', async (e) => {
            e.preventDefault();
            const firstName = document.getElementById('register-firstname').value;
            const lastName = document.getElementById('register-lastname').value;
            const email = document.getElementById('register-email').value;
            const password = document.getElementById('register-password').value;
            const confirm = document.getElementById('register-confirm').value;

            if (password !== confirm) {
                alert('Passwords do not match');
                return;
            }

            try {
                await api.register(firstName, lastName, email, password);
                this.user = this.getStoredUser();
                window.location.hash = '#/dashboard';
            } catch (error) {
                alert('Registration failed: ' + error.message);
            }
        });
    }

    renderDashboard(container) {
        const template = document.getElementById('dashboard-template').content.cloneNode(true);
        container.innerHTML = '';
        container.appendChild(template);

        this.setupNavigation(template);

        // Show/hide admin links
        const userLinks = document.querySelectorAll('#users-link');
        if (this.user.role === 'admin') {
            userLinks.forEach(link => link.style.display = 'inline-block');
        }

        // Update user name
        document.getElementById('user-name').textContent = this.user.firstName;

        // Load stats
        api.getProjects().then(projects => {
            document.getElementById('total-projects').textContent = projects.length;
        }).catch(error => console.error('Failed to load projects:', error));
    }

    renderProjects(container) {
        const template = document.getElementById('projects-template').content.cloneNode(true);
        container.innerHTML = '';
        container.appendChild(template);

        this.setupNavigation(template);

        // Show/hide admin links
        const userLinks = document.querySelectorAll('#users-link2');
        if (this.user.role === 'admin') {
            userLinks.forEach(link => link.style.display = 'inline-block');
        }

        // Load projects
        api.getProjects().then(projects => {
            const tbody = document.getElementById('projects-list');
            tbody.innerHTML = '';
            projects.forEach(project => {
                const row = document.createElement('tr');
                row.innerHTML = `
                    <td>${this.escapeHtml(project.name)}</td>
                    <td>${this.escapeHtml(project.description)}</td>
                    <td>${this.escapeHtml(project.createdBy)}</td>
                    <td>
                        <div class="action-buttons">
                            <a href="#/projects/edit/${project.id}" class="btn-edit">Edit</a>
                            ${this.user.role === 'admin' ? `<button class="btn-delete" onclick="app.deleteProject(${project.id})">Delete</button>` : ''}
                        </div>
                    </td>
                `;
                tbody.appendChild(row);
            });
        }).catch(error => console.error('Failed to load projects:', error));
    }

    renderProjectForm(container, id = null) {
        const template = document.getElementById('project-form-template').content.cloneNode(true);
        container.innerHTML = '';
        container.appendChild(template);

        this.setupNavigation(template);

        // Show/hide admin links
        const userLinks = document.querySelectorAll('#users-link3');
        if (this.user.role === 'admin') {
            userLinks.forEach(link => link.style.display = 'inline-block');
        }

        // Load languages
        api.getLanguages().then(languages => {
            const container = document.getElementById('languages-container');
            languages.forEach(lang => {
                const label = document.createElement('label');
                label.style.marginRight = '15px';
                label.style.marginBottom = '10px';
                label.innerHTML = `
                    <input type="checkbox" name="languages" value="${lang.id}"> ${this.escapeHtml(lang.name)}
                `;
                container.appendChild(label);
            });
        });

        const form = document.getElementById('project-form');
        const titleEl = document.getElementById('form-title');

        if (id) {
            titleEl.textContent = 'Edit Project';
            api.getProject(id).then(project => {
                document.getElementById('project-name').value = project.name;
                document.getElementById('project-description').value = project.description;
                
                const checkboxes = document.querySelectorAll('input[name="languages"]');
                project.languages.forEach(lang => {
                    checkboxes.forEach(cb => {
                        if (cb.value === lang.id.toString()) {
                            cb.checked = true;
                        }
                    });
                });
            });
        }

        form.addEventListener('submit', async (e) => {
            e.preventDefault();
            const name = document.getElementById('project-name').value;
            const description = document.getElementById('project-description').value;
            const checkboxes = document.querySelectorAll('input[name="languages"]:checked');
            const languages = Array.from(checkboxes).map(cb => parseInt(cb.value));

            try {
                if (id) {
                    await api.updateProject(id, name, description, languages);
                } else {
                    await api.createProject(name, description, languages);
                }
                window.location.hash = '#/projects';
            } catch (error) {
                alert('Failed to save project: ' + error.message);
            }
        });
    }

    renderUsers(container) {
        const template = document.getElementById('users-template').content.cloneNode(true);
        container.innerHTML = '';
        container.appendChild(template);

        this.setupNavigation(template);

        // Load users
        api.getUsers().then(users => {
            const tbody = document.getElementById('users-list');
            tbody.innerHTML = '';
            users.forEach(user => {
                const row = document.createElement('tr');
                row.innerHTML = `
                    <td>${this.escapeHtml(user.firstName)} ${this.escapeHtml(user.lastName)}</td>
                    <td>${this.escapeHtml(user.email)}</td>
                    <td>${this.escapeHtml(user.role)}</td>
                    <td>
                        <div class="action-buttons">
                            <a href="#/users/edit/${user.id}" class="btn-edit">Edit</a>
                            <button class="btn-delete" onclick="app.deleteUser(${user.id})">Delete</button>
                        </div>
                    </td>
                `;
                tbody.appendChild(row);
            });
        }).catch(error => console.error('Failed to load users:', error));
    }

    renderUserForm(container, id) {
        const template = document.getElementById('project-form-template').content.cloneNode(true);
        container.innerHTML = '';

        // Create a simplified user edit form
        const content = document.createElement('div');
        content.className = 'container';
        content.innerHTML = `
            <nav class="navbar">
                <div class="nav-brand">Shared Code Notebook</div>
                <div class="nav-links">
                    <a href="#/dashboard">Dashboard</a>
                    <a href="#/projects">Projects</a>
                    <a href="#/users">Users</a>
                    <button id="logout-btn5" class="btn-logout">Logout</button>
                </div>
            </nav>
            <div class="content">
                <h1>Edit User</h1>
                <form id="user-form">
                    <div class="form-group">
                        <label for="user-firstname">First Name:</label>
                        <input type="text" id="user-firstname" name="firstName" required>
                    </div>
                    <div class="form-group">
                        <label for="user-lastname">Last Name:</label>
                        <input type="text" id="user-lastname" name="lastName" required>
                    </div>
                    <div class="form-group">
                        <label for="user-email">Email:</label>
                        <input type="email" id="user-email" name="email" required>
                    </div>
                    <div class="form-group">
                        <label for="user-role">Role:</label>
                        <select id="user-role" name="role" required>
                            <option value="user">User</option>
                            <option value="admin">Admin</option>
                        </select>
                    </div>
                    <button type="submit" class="btn">Save User</button>
                    <a href="#/users" class="btn-secondary">Cancel</a>
                </form>
            </div>
        `;
        container.appendChild(content);

        this.setupLogoutButtons();

        // Load user data
        api.getUser(id).then(user => {
            document.getElementById('user-firstname').value = user.firstName;
            document.getElementById('user-lastname').value = user.lastName;
            document.getElementById('user-email').value = user.email;
            document.getElementById('user-role').value = user.role;
        });

        const form = document.getElementById('user-form');
        form.addEventListener('submit', async (e) => {
            e.preventDefault();
            const firstName = document.getElementById('user-firstname').value;
            const lastName = document.getElementById('user-lastname').value;
            const email = document.getElementById('user-email').value;
            const role = document.getElementById('user-role').value;

            try {
                await api.updateUser(id, firstName, lastName, email, role);
                window.location.hash = '#/users';
            } catch (error) {
                alert('Failed to update user: ' + error.message);
            }
        });
    }

    setupNavigation(template) {
        let logoutButtons = [];
        try {
            logoutButtons = template.querySelectorAll('.btn-logout');
        } catch (e) {
            logoutButtons = [];
        }

        // If the template fragment is empty after being appended, fall back to the document
        if (!logoutButtons || logoutButtons.length === 0) {
            logoutButtons = document.querySelectorAll('.btn-logout');
        }

        logoutButtons.forEach(btn => {
            btn.addEventListener('click', async () => {
                await api.logout();
                this.user = null;
                window.location.hash = '#/login';
            });
        });
    }

    setupLogoutButtons() {
        const logoutButtons = document.querySelectorAll('.btn-logout');
        logoutButtons.forEach(btn => {
            btn.addEventListener('click', async () => {
                await api.logout();
                this.user = null;
                window.location.hash = '#/login';
            });
        });
    }

    deleteProject(id) {
        if (confirm('Are you sure you want to delete this project?')) {
            api.deleteProject(id).then(() => {
                window.location.reload();
            }).catch(error => alert('Failed to delete project: ' + error.message));
        }
    }

    deleteUser(id) {
        if (confirm('Are you sure you want to delete this user?')) {
            api.deleteUser(id).then(() => {
                window.location.reload();
            }).catch(error => alert('Failed to delete user: ' + error.message));
        }
    }

    escapeHtml(text) {
        const div = document.createElement('div');
        div.textContent = text;
        return div.innerHTML;
    }
}

// Initialize app
const app = new App();
