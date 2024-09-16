"use client"
import React, { useState, useEffect } from 'react';
import {getBookById, getMyBooks} from '@/app/api';
import BookDetailsModal from '@/app/bookDetails/page';
import CreateBookModal from '@/app/components/CreateBookModal'; // Import the CreateBookModal
import styles from '@/app/styles/myBooks.module.css';
import BookDetails from '@/app/interfaces/BookDetails';
import cookieUtil from "@/app/util/cookieUtil";

const MyBooksPage: React.FC = () => {
    const [books, setBooks] = useState<BookDetails[]>([]);
    const [selectedBook, setSelectedBook] = useState<BookDetails | null>(null);
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [isCreateModalOpen, setIsCreateModalOpen] = useState(false);
    const [errorMessage, setErrorMessage] = useState<string | null>(null);

    // Fetch the token from localStorage after the component mounts (client-side only)

    // Fetch user's books
    useEffect(() => {
        const fetchMyBooks = async () => {
            try {
                if (cookieUtil.getTokenFromCookies()) {
                    const myBooks = await getMyBooks();
                    setBooks(myBooks);
                } else {
                    setErrorMessage("You need to be logged in to view your books.");
                }
            } catch (error) {
                console.error("Error fetching my books:", error);
                setErrorMessage("An error occurred while fetching your books.");
            }
        };

        fetchMyBooks().then(r => r);
    }, []);

    const handleBookClick = (book: BookDetails) => {
        // call api to get book details
        getBookById(book.id).then((bookDetails) => {
            setSelectedBook(bookDetails);
            setIsModalOpen(true);
        });
    };

    const handleModalClose = () => {
        setIsModalOpen(false);
        setSelectedBook(null);
    };

    const handleCreateModalClose = () => {

        setIsCreateModalOpen(false);
    };

    const handleBookCreated = (newBook: BookDetails) => {
        console.log('handleBookCreated called');
        setBooks((prevBooks) => [...prevBooks, newBook]); // Add the new book to the list
    };

    return (
        <div className={styles.myBooksContainer}>
            <h1 className={styles.heading}>My Books</h1>
            <button className={styles.createButton} onClick={() => {
                 setIsCreateModalOpen(true);
                 console.log("xxx")}}>

                Create New Book
            </button>
            {errorMessage && <p className={styles.errorMessage}>{errorMessage}</p>}
            <div className={styles.bookList}>
                {books.length > 0 ? (
                    books.map((book) => (
                        <div key={book.id} className={styles.bookItem} onClick={() => handleBookClick(book)}>
                            <h3>{book.title}</h3>
                            <p><strong>Author:</strong> {book.author}</p>
                            <p><strong>ISBN:</strong> {book.isbn}</p>
                        </div>
                    ))
                ) : (
                    <p>You havent added any books yet.</p>
                )}
            </div>

            {selectedBook && (
                <BookDetailsModal
                    book={selectedBook}
                    isOpen={isModalOpen}
                    onClose={handleModalClose}

                />
            )}

            {isCreateModalOpen && (
                <CreateBookModal
                    isOpen={isCreateModalOpen}
                    onClose={handleCreateModalClose}
                    onBookCreated={handleBookCreated}
                 />
            )}
        </div>
    );
};

export default MyBooksPage;
