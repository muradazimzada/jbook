"use client";  // Add this line

import React, { useState } from 'react';
import styles from '../../app/styles/login.module.css'; // External CSS for styles
import {registerUser} from '@/app/api';
import {useRouter} from "next/navigation";

export default function RegisterPage() {
    const [fullName, setFullName] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');

    const router = useRouter();
    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        setError('');

        try {
            const response = await registerUser(
                email,
                password,
                fullName
            );
            console.log("log from try: " + response); // Handle the response
             router.push('/login'); // Redirect to login page after successful registration
        } catch (err) {
            console.log(err);
                setError('An error occurred while registering');
        }
    };

    return (
        <div className={styles.container}>
            <h1>Register</h1>
            {error && <p className={styles.error}>{error}</p>}
            <form onSubmit={handleSubmit} className={styles.form}>
                <input
                    type="text"
                    placeholder="Full Name"
                    value={fullName}
                    onChange={(e) => setFullName(e.target.value)}
                    required
                    className={styles.input}
                />
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
                    Register
                </button>
            </form>
        </div>
    )


}
