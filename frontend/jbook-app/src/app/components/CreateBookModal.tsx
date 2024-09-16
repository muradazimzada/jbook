"use client";
import React, { useState } from 'react';
import styles from '@/app/styles/bookDetailsModal.module.css'; // Reuse modal styles
import { createBook } from '@/app/api'; // Import create book API
import BookDetails from "@/app/interfaces/BookDetails";
import cookieUtil from "@/app/util/cookieUtil"; // Import the Book interface

interface CreateBookModalProps {
    isOpen: boolean;
    onClose: () => void;
    onBookCreated?: (newBook: BookDetails) => void; // Callback when a new book is created
}

const CreateBookModal: React.FC<CreateBookModalProps> = ({ isOpen, onClose, onBookCreated }) => {
    const [newBook, setNewBook] = useState<BookDetails>({
        title: '',
        author: '',
        isbn: '',
        description: '',
        createdByName: '',
        createdAt: new Date().toISOString(),
        id: 0,
    }); // Initialize with empty book details

    const [errorMessage, setErrorMessage] = useState<string | null>(null);
    const [isSaving, setIsSaving] = useState(false);
    const [successMessage, setSuccessMessage] = useState<string | null>(null); // Success message state

    // Handle changes in the form fields
    const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
        const { name, value } = e.target;
        setNewBook((prevBook) => ({
            ...prevBook,
            [name]: value,
        }));
    };

    // Handle saving the new book
    const handleSave = async () => {
        setIsSaving(true); // Disable the save button during the request
        const token = cookieUtil.getTokenFromCookies();

        if (token) {
            try {
                const createdBook = await createBook(newBook); // Call the create API
                if (onBookCreated) {
                    onBookCreated(createdBook); // Call the callback to update book list in parent component
                }
                setErrorMessage(null);
                setSuccessMessage('Book created successfully!'); // Show success message
                setTimeout(() => {
                    setSuccessMessage(null); // Hide success message after 3 seconds
                    onClose(); // Close the modal after showing success
                }, 3000);
            } catch (error) {
                console.error("Failed to create book:", error);
                setErrorMessage("An error occurred while creating the book.");
            } finally {
                setIsSaving(false);
            }
        } else {
            setErrorMessage("You must be logged in to create a book.");
            setIsSaving(false);
        }
    };

    // Don't render modal if it isn't open
    if (!isOpen) return null;

    return (
        <div className={styles.modalOverlay} onClick={onClose}>
            <div className={styles.modalContent} onClick={(e) => e.stopPropagation()}>
                <button className={styles.closeButton} onClick={onClose}>X</button>
                <h2>Create New Book</h2>
                <div className={styles.formGroup}>
                    <label htmlFor="title">Title</label>
                    <input
                        id="title"
                        name="title"
                        value={newBook.title}
                        onChange={handleChange}
                        className={styles.inputField}
                    />
                </div>
                <div className={styles.formGroup}>
                    <label htmlFor="author">Author</label>
                    <input
                        id="author"
                        name="author"
                        value={newBook.author}
                        onChange={handleChange}
                        className={styles.inputField}
                    />
                </div>
                <div className={styles.formGroup}>
                    <label htmlFor="isbn">ISBN</label>
                    <input
                        id="isbn"
                        name="isbn"
                        value={newBook.isbn}
                        onChange={handleChange}
                        className={styles.inputField}
                    />
                </div>
                <div className={styles.formGroup}>
                    <label htmlFor="description">Description</label>
                    <textarea
                        id="description"
                        name="description"
                        value={newBook.description}
                        onChange={handleChange}
                        className={styles.inputField}
                    />
                </div>
                <button
                    className={styles.saveButton}
                    onClick={handleSave}
                    disabled={isSaving} // Disable during saving
                >
                    {isSaving ? "Saving..." : "Create Book"}
                </button>
                {errorMessage && <p className={styles.errorMessage}>{errorMessage}</p>}
                {successMessage && <p className={styles.successMessage}>{successMessage}</p>} {/* Success Message */}
            </div>
        </div>
    );
};

export default CreateBookModal;
