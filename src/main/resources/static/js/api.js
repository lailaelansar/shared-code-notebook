// API Client for communicating with backend
const API_BASE = '/api';

class ApiClient {
    constructor() {
        this.token = localStorage.getItem('token');
    }

    async request(endpoint, options = {}) {
        const url = `${API_BASE}${endpoint}`;
        const headers = {
            'Content-Type': 'application/json',
            ...options.headers,
        };

        if (this.token) {
            headers['Authorization'] = `Bearer ${this.token}`;
        }

        const response = await fetch(url, {
            ...options,
            headers,
        });

        if (response.status === 401) {
            // Unauthorized - redirect to login
            localStorage.removeItem('token');
            localStorage.removeItem('user');
            window.location.hash = '#/login';
            throw new Error('Unauthorized');
        }

        if (!response.ok) {
            throw new Error(`API Error: ${response.statusText}`);
        }

        return response.json();
    }

    // Auth endpoints
    async login(email, password) {
        const data = await this.request('/auth/login', {
            method: 'POST',
            body: JSON.stringify({ email, password }),
        });
        this.token = data.token;
        localStorage.setItem('token', this.token);
        localStorage.setItem('user', JSON.stringify(data.user));
        return data;
    }

    async register(firstName, lastName, email, password) {
        const data = await this.request('/auth/register', {
            method: 'POST',
            body: JSON.stringify({ firstName, lastName, email, password }),
        });
        this.token = data.token;
        localStorage.setItem('token', this.token);
        localStorage.setItem('user', JSON.stringify(data.user));
        return data;
    }

    async logout() {
        try {
            // Attempt to notify backend about logout; don't fail if network error
            await this.request('/auth/logout', { method: 'POST' });
            console.log('Logout request sent to server');
        } catch (e) {
            console.warn('Logout request failed or not required:', e.message);
        }

        // Clear local auth state
        localStorage.removeItem('token');
        localStorage.removeItem('user');
        this.token = null;
    }

    // Project endpoints
    async getProjects() {
        return this.request('/projects');
    }

    async getProject(id) {
        return this.request(`/projects/${id}`);
    }

    async createProject(name, description, languages) {
        return this.request('/projects', {
            method: 'POST',
            body: JSON.stringify({ name, description, languages }),
        });
    }

    async updateProject(id, name, description, languages) {
        return this.request(`/projects/${id}`, {
            method: 'PUT',
            body: JSON.stringify({ name, description, languages }),
        });
    }

    async deleteProject(id) {
        return this.request(`/projects/${id}`, {
            method: 'DELETE',
        });
    }

    // User endpoints
    async getUsers() {
        return this.request('/users');
    }

    async getUser(id) {
        return this.request(`/users/${id}`);
    }

    async updateUser(id, firstName, lastName, email, role) {
        return this.request(`/users/${id}`, {
            method: 'PUT',
            body: JSON.stringify({ firstName, lastName, email, role }),
        });
    }

    async deleteUser(id) {
        return this.request(`/users/${id}`, {
            method: 'DELETE',
        });
    }

    // Language endpoints
    async getLanguages() {
        return this.request('/languages');
    }
}

const api = new ApiClient();
