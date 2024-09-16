// SearchBooks.tsx
import React, { useState } from 'react';
import styles from '@/app/styles/searchBooks.module.css'; // We'll create this CSS module for styling

interface SearchBooksProps {
    onSearch: (title: string, author: string, isbn: string) => void;
}

const SearchBooks: React.FC<SearchBooksProps> = ({ onSearch }) => {
    const [title, setTitle] = useState('');
    const [author, setAuthor] = useState('');
    const [isbn, setIsbn] = useState('');

    const handleSearch = () => {
        onSearch(title, author, isbn);
    };

    return (
        <div className={styles.searchContainer}>
            <h2 className={styles.title}>Search Books</h2>
            <div className={styles.searchFields}>
                <input
                    type="text"
                    placeholder="Search by Title"
                    value={title}
                    onChange={(e) => setTitle(e.target.value)}
                    className={styles.inputField}
                />
                <input
                    type="text"
                    placeholder="Search by Author"
                    value={author}
                    onChange={(e) => setAuthor(e.target.value)}
                    className={styles.inputField}
                />
                <input
                    type="text"
                    placeholder="Search by ISBN"
                    value={isbn}
                    onChange={(e) => setIsbn(e.target.value)}
                    className={styles.inputField}
                />
            </div>
            <button onClick={handleSearch} className={styles.searchButton}>
                Search
            </button>
        </div>
    );
};

export default SearchBooks;
