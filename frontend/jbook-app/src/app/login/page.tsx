"use client";  // Add this line
import { useState} from 'react';
import {useRouter} from "next/navigation"
import {loginUser} from '@/app/api';
import styles from "../../app/styles/login.module.css";

export default function LoginPage() {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');
    const router = useRouter();

    // if user is already logged in, redirect to home page


    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        setError('');
        try {
            await loginUser( email, password);
            router.push('/')

        } catch (err) {
            console.log(err);
            setError('Error during login ' + err);
        }
    };

    return (
        <div className={styles.container}>
            <h1>Login</h1>
            {error && <p className={styles.error}>{error}</p>}
            <form onSubmit={handleSubmit} className={styles.form}>
                <input
                    type="email"
                    placeholder="Email"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                    required
                    className={styles.input}
                />
                <input
                    type="password"
                    placeholder="Password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    required
                    className={styles.input}
                />
                <button type="submit" className={styles.submitButton}>
                    Login
                </button>
            </form>
        </div>
    );
}

