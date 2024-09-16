"use client";
import React, { useState, useEffect } from 'react';
import { getBookById, updateBook } from '@/app/api';
import { useRouter } from 'next/router'; // For navigation
import styles from '@/app/styles/bookEdit.module.css';
import BookDetails from "@/app/interfaces/BookDetails";
import cookieUtil from "@/app/util/cookieUtil";

// Define a default book object
const defaultBook: BookDetails = {
    id: 0,
    title: '',
    author: '',
    isbn: '',
    description: '',
    createdByName: ''
};

const BookEditPage: React.FC = () => {
    // eslint-disable-next-line @typescript-eslint/no-unused-vars
    const [book, setBook] = useState<BookDetails>(defaultBook); // Original book state
    const [editedBook, setEditedBook] = useState<BookDetails>(defaultBook); // Edited book state
    const [errorMessage, setErrorMessage] = useState<string | null>(null);
    const [successMessage, setSuccessMessage] = useState<string | null>(null); // New state for success message
    const router = useRouter();
    const { id } = router.query;

    useEffect(() => {
        const fetchBook = async () => {
            try {
                const bookData = await getBookById(Number(id));
                setBook(bookData); // Store the original book
                setEditedBook(bookData); // Initialize edited book
            } catch (error) {
                console.error("Error loading book:", error);
                setErrorMessage("Failed to load book data.");
            }
        };

        if (id) {
            fetchBook().then(r => r);
        }
    }, [id]);

    const handleSave = async () => {
        try {
            const token = cookieUtil.getTokenFromCookies();
            if (!token) {
                await router.push('/');
                return;
            }

            const updatedBook = await updateBook(Number(id), editedBook);
            setBook(updatedBook);
            setSuccessMessage("Book updated successfully!");
            setErrorMessage(null); // Clear any error messages if successful

            setTimeout(() => {
                setSuccessMessage(null); // Clear the success message
                router.push(`/book/${id}`); // Redirect to the book's detail page
            }, 3000); // 3-second delay before redirect

        } catch (error) {
            console.error("Failed to update book:", error);
            setErrorMessage("An error occurred while saving the changes.");
            setSuccessMessage(null); // Clear success message in case of failure
        }
    };

    const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
        const { name, value } = e.target;
        setEditedBook((prevBook) => ({
            ...prevBook,
            [name]: value,
        }));
    };

    return (
        <div className={styles.bookEditContainer}>
            <h1>Edit Book</h1>

            {/* Form fields to edit the book */}
            <div className={styles.formGroup}>
                <label>Title</label>
                <input
                    name="title"
                    value={editedBook.title}
                    onChange={handleChange}
                    className={styles.inputField}
                />
            </div>

            {/* Save Button */}
            <button onClick={handleSave} className={styles.saveButton}>Save</button>

            {/* Display error or success messages just below the Save button */}
            {errorMessage && <p className={styles.errorMessage}>{errorMessage}</p>}
            {successMessage && <p className={styles.successMessage}>{successMessage}</p>}
        </div>
    );
};

export default BookEditPage;
