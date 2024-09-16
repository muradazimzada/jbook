"use client";
import React, { useState, useEffect } from 'react';
import styles from '@/app/styles/bookDetailsModal.module.css';
import { updateBook } from '@/app/api';
import BookDetails from "@/app/interfaces/BookDetails";
import { AxiosError } from 'axios';

interface BookDetailsModalProps {
    book: BookDetails;
    isOpen: boolean;
    onClose: () => void;
}

const BookDetailsModal: React.FC<BookDetailsModalProps> = ({ book, isOpen, onClose }) => {
    const [isEditing, setIsEditing] = useState(false);
    const [editedBook, setEditedBook] = useState<BookDetails>(book);
    const [errorMessage, setErrorMessage] = useState<string | null>(null);
    const [isSaving, setIsSaving] = useState(false);
    const [successMessage, setSuccessMessage] = useState<string | null>(null); // For success message

    // Reset editedBook data when the modal opens or the book changes
    useEffect(() => {
        if (isOpen) {
            setEditedBook(book);
        }
    }, [isOpen, book]);

    // Handle ESC key close
    useEffect(() => {
        const handleEscClose = (event: KeyboardEvent) => {
            if (event.key === 'Escape') {
                onClose();
            }
        };
        if (isOpen) {
            window.addEventListener('keydown', handleEscClose);
        }
        return () => {
            window.removeEventListener('keydown', handleEscClose);
        };
    }, [isOpen, onClose]);

    const handleEdit = () => {
        setIsEditing(true);
    };

    const handleSave = async () => {
        setIsSaving(true); // Disable the save button during the request
        const token = localStorage.getItem('token');

        if (!token) {
            setErrorMessage("You must be logged in to edit the book.");
            setIsSaving(false);
            return;
        }

        try {
            const updatedBook = await updateBook(book.id, editedBook);
            setEditedBook(updatedBook);
            setSuccessMessage("Book updated successfully!"); // Show success message
            setErrorMessage(null);
            setIsEditing(false);

            // Delay closing the modal to allow the user to see the success message
            setTimeout(() => {
                setSuccessMessage(null); // Clear success message after a few seconds
                onClose(); // Close modal
            }, 3000); // 3-second delay before closing the modal

        } catch (error: unknown | AxiosError) {
            if (error instanceof AxiosError && error.response && error.response.status === 403) {
                setErrorMessage("You cannot edit this book because you are only allowed to update the ones you created.");
            } else {
                console.error("Failed to update book:", error);
                setErrorMessage("An error occurred while saving the changes.");
            }
        } finally {
            setIsSaving(false);
        }
    };

    // Reset the editedBook state to its original state if editing is cancelled or modal is closed
    const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
        const { name, value } = e.target;
        setEditedBook((prevBook) => ({
            ...prevBook,
            [name]: value
        }));
    };

    if (!isOpen) return null;

    return (
        <div className={styles.modalOverlay} onClick={onClose}>
            <div className={styles.modalContent} onClick={(e) => e.stopPropagation()}>
                <button className={styles.closeButton} onClick={onClose}>X</button>

                {isEditing ? (
                    <>
                        <div className={styles.formGroup}>
                            <label htmlFor="title">Title</label>
                            <input
                                id="title"
                                name="title"
                                value={editedBook.title}
                                onChange={handleChange}
                                className={styles.inputField}
                            />
                        </div>

                        <div className={styles.formGroup}>
                            <label htmlFor="author">Author</label>
                            <input
                                id="author"
                                name="author"
                                value={editedBook.author}
                                onChange={handleChange}
                                className={styles.inputField}
                            />
                        </div>

                        <div className={styles.formGroup}>
                            <label htmlFor="isbn">ISBN</label>
                            <input
                                id="isbn"
                                name="isbn"
                                value={editedBook.isbn}
                                onChange={handleChange}
                                className={styles.inputField}
                            />
                        </div>

                        <div className={styles.formGroup}>
                            <label htmlFor="description">Description</label>
                            <textarea
                                id="description"
                                name="description"
                                value={editedBook.description}
                                onChange={handleChange}
                                className={styles.inputField}
                            />
                        </div>
                    </>
                ) : (
                    <>
                        <h2>{book.title}</h2>
                        <p><strong>Author:</strong> {book.author}</p>
                        <p><strong>ISBN:</strong> {book.isbn}</p>
                        <p><strong>Description:</strong> {book.description}</p>
                    </>
                )}

                <p><strong>Created by:</strong> {book.createdByName}</p>

                {!isEditing && (
                    <button className={styles.editButton} onClick={handleEdit}>Edit</button>
                )}

                {isEditing && (
                    <>
                        <button
                            className={styles.saveButton}
                            onClick={handleSave}
                            disabled={isSaving} // Disable during saving
                        >
                            {isSaving ? "Saving..." : "Save"}
                        </button>
                    </>
                )}

                {errorMessage && (
                    <p className={styles.errorMessage}>{errorMessage}</p>
                )}

                {successMessage && (
                    <p className={styles.successMessage}>{successMessage}</p> // Show success message
                )}
            </div>
        </div>
    );
};

export default BookDetailsModal;
