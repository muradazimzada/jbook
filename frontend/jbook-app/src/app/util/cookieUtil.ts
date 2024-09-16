// Helper functions to get and delete the token from cookies
const cookieUtil = {
    getTokenFromCookies: () => {
        const cookies = document.cookie.split('; ');
        const tokenCookie = cookies.find(cookie => cookie.startsWith('token='));
        if (tokenCookie) {
            return tokenCookie.split('=')[1]; // Return the token value
        }
        return null; // Return null if token is not found
    },

    deleteTokenFromCookies: () => {
        document.cookie = `token=; path=/; expires=Thu, 01 Jan 1970 00:00:00 GMT;`;
    }
};

export default cookieUtil;
