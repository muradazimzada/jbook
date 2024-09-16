"use client";
import Link from 'next/link';
import styles from '../styles/header.module.css';
import { useRouter, usePathname } from "next/navigation";
import { useState, useEffect } from 'react';
import cookieUtil from "@/app/util/cookieUtil";

const Header = () => {
    const router = useRouter();
    const pathname = usePathname();
    const [isAuthenticated, setIsAuthenticated] = useState(false);

    // Check authentication status based on the token from cookies
    const checkAuthentication = () => {
        const token = cookieUtil.getTokenFromCookies();
        if (token) {
            setIsAuthenticated(true);
        } else {
            setIsAuthenticated(false);
        }
    };

    useEffect(() => {
        // Initial authentication check on component mount
        checkAuthentication();

        // Set an interval to check for token changes in cookies
        const interval = setInterval(() => {
            checkAuthentication();
        }, 1000); // Check every second

        return () => {
            clearInterval(interval); // Clear interval when the component unmounts
        };
    }, []);

    const handleLogout = () => {
        // Delete token from cookies
        cookieUtil.deleteTokenFromCookies();

        // Update state and redirect
        setIsAuthenticated(false);
        router.push('/login');
    };

    return (
        <header className={styles.header}>
            <nav className={styles.nav}>
                <ul className={styles.navList}>
                    {pathname !== '/' && (
                        <li>
                            <Link href="/" className={styles.navItem}>
                                Home
                            </Link>
                        </li>
                    )}
                </ul>

                {/* Right-aligned items */}
                <ul className={styles.navRight}>
                    {!isAuthenticated ? (
                        <>
                            <li>
                                <Link href="/login" className={styles.navItem}>
                                    Login
                                </Link>
                            </li>
                            <li>
                                <Link href="/register" className={styles.navItem}>
                                    Register
                                </Link>
                            </li>
                        </>
                    ) : (
                        <>
                            <li>
                                <Link href="/myBooks" className={styles.navItem}>
                                    My Books
                                </Link>
                            </li>
                            <li>
                                <Link href="/profile" className={styles.navItem}>
                                    Profile
                                </Link>
                            </li>
                            <li>
                                <a onClick={handleLogout} className={styles.navItem} style={{ cursor: 'pointer' }}>
                                    Logout
                                </a>
                            </li>
                        </>
                    )}
                </ul>
            </nav>
        </header>
    );
};

export default Header;
