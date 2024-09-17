
import Book from '../interfaces/Book';
import api from '../interceptor/auth-interceptor';
import BookDetails from "@/app/interfaces/BookDetails";

// Authentication APIs
export const registerUser = async (email: string, password: string, fullName: string) => {
    const response =  await api.post(`/auth/register`, {
        email,
        password,
        fullName,
    });
    return response;

};

export const loginUser = async (email: string, password: string) => {
    const response = await api.post(`/auth/login`, {
        email,
        password,
    });

    const token: string = response.data.token;
    console.log("Login successful, token:", token);
    // Store the token in localStorage
    localStorage.setItem('token', token);
    document.cookie = `token=${token}; path=/; SameSite=Strict;`;
    return token;
};

// User APIs
export const getAuthenticatedUser = async () => {
    const response = await api.get(`/users/me`);
    return response.data;
};

export const getAllUsers = async () => {
    const response = await api.get(`/users/`);
    return response.data;
};

// Book APIs
export const getAllBooks = async () => {
    try {
        const response = await api.get(`/books/all`);
        return response.data;
    } catch (error) {
        console.error('Error fetching books:', error);
        throw error; // Re-throw the error after logging it
    }
};

export const getBookById = async (id: number): Promise<BookDetails> => {
    const response = await api.get(`/books/${id}`);
    return response.data as BookDetails;
};

export const createBook = async (bookData: BookDetails) => {
    const response = await api.post(`/books/create`, bookData);
    return response.data;
};

export const searchBooks = async (title?: string, author?: string, isbn?: string) => {
    const response = await api.get(`/books/search`, {
        params: { title, author, isbn },
    });
    return response.data;
};

export const getMyBooks = async () => {
    const response = await api.get(`/books/my-books`);
    return response.data;
};

// return status code
export const updateBook = async (id: number, bookData: Book) => {
    const response = await api.put(`/books/${id}`, bookData);
    return response.data;
};

export const deleteBook = async (id: number) => {
    const response = await api.delete(`/books/${id}`);
    return response.data;
}