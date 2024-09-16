import axios from 'axios';

import  cookieUtil from '@/app/util/cookieUtil';
const api = axios.create({
    baseURL: 'http://localhost:8085/api', // Update this if needed
});
// Add an interceptor to attach the Authorization header
api.interceptors.request.use(
    (config) => {
        const token = cookieUtil.getTokenFromCookies();
        if (token) {
            config.headers['Authorization'] = `Bearer ${token}`;
        }
        return config;
    },
    (error) => {
        return Promise.reject(error);
    }
);

// Optional: Add a response interceptor for handling errors globally (e.g., token expiration)
api.interceptors.response.use(
    (response) => response,
    (error) => {
        // if (error.response && error.response.status === 401) {
        //     // Handle token expiration, redirect to login, etc.
        //     console.error('Token expired, logging out...');
        //     localStorage.removeItem('token');
        //     window.location.href = '/login'; // Redirect to login page
        // }
        // return Promise.reject(error);
        console.log('Error:', error);
    }
);

export default api;
