"use client"; // Since this will involve interactive elements

import { useEffect, useState } from 'react';
import Link from 'next/link';
import { getAllBooks, getBookById, searchBooks } from "@/app/api";
import styles from '../app/styles/home.module.css';
import bookStyles from '@/app/styles/bookContainer.module.css';
import Book from "@/app/interfaces/Book";
import BookDetailsModal from "@/app/bookDetails/page"; // Correct import for modal
import BookDetails from "@/app/interfaces/BookDetails";
import SearchBooks from "@/app/components/SearchBooks";
import { useRouter } from "next/navigation";
import cookieUtil from "@/app/util/cookieUtil";

export default function HomePage() {
    const [isAuthenticated, setIsAuthenticated] = useState<boolean | null>(null); // Start with null
    const [books, setBooks] = useState<Book[]>([]);
    const [selectedBook, setSelectedBook] = useState<BookDetails | null>(null);
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [loading, setLoading] = useState(false);
    const router = useRouter();

    useEffect(() => {
        const token = cookieUtil.getTokenFromCookies();
        if (token) {
            setIsAuthenticated(true);
            getAllBooks().then(setBooks).catch(console.error);
        } else {
            setIsAuthenticated(false);
            router.push('/');
        }
    }, [router]);

    // Handle the book search
    const handleSearch = async (title: string, author: string, isbn: string) => {
        try {
            const result = await searchBooks(title, author, isbn); // Correctly pass token to API
            setBooks(result);
        } catch (error) {
            console.error('Search failed:', error);
        }
    };

    // Open the book details modal and fetch the book by ID
    const openModal = async (bookId: number) => {
        setLoading(true); // Set loading state to true when starting the API call

        try {
            const bookDetails = await getBookById(bookId); // Correctly pass token to API
            setSelectedBook(bookDetails);
            setIsModalOpen(true);
        } catch (error) {
            console.error("Failed to fetch book details: ", error);
        } finally {
            setLoading(false); // Always set loading state to false when API call is done
        }
    };

    const closeModal = () => {
        setIsModalOpen(false);
        setSelectedBook(null);
    };

    if (isAuthenticated === null) {
        // While checking authentication, render nothing or a loading screen
        return <div>Loading...</div>;
    }

    if (!isAuthenticated) {
        return (
            <div className={styles.container}>
                <h1 className={styles.title}>Welcome to JBook App</h1>
                <p className={styles.subtitle}>
                    Your gateway to managing and discovering books effortlessly.
                </p>
                <div className={styles.buttons}>
                    <Link href="/login">
                        <button className={styles.loginButton}>Login</button>
                    </Link>
                    <Link href="/register">
                        <button className={styles.registerButton}>Sign Up</button>
                    </Link>
                </div>
            </div>
        );
    }

    return (
        <div>
            <h1>Book List</h1>
            <SearchBooks onSearch={handleSearch} />
            <ul>
                {books.map((book) => (
                    <li
                        key={book.id}
                        className={bookStyles.bookContainer}
                        onClick={() => openModal(book.id)} // Pass the book's ID when opening the modal
                    >
                        <strong className={bookStyles.bookTitle}>{book.title}</strong>
                        <p className={bookStyles.bookAuthor}>by {book.author}</p>
                        <p className={bookStyles.bookInfo}>ISBN: {book.isbn}, Created by: {book.createdByName}</p>
                    </li>
                ))}
            </ul>

            {loading && <p>Loading...</p>} {/* Show loading state if data is being fetched */}

            {selectedBook && (
                <BookDetailsModal
                    book={selectedBook}
                    isOpen={isModalOpen}
                    onClose={closeModal}
                />
            )}
        </div>
    );
}
