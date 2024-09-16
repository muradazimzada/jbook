"use client";  // Add this line
import React, { useState, useEffect } from 'react';
import { getAuthenticatedUser } from '@/app/api';
import { useRouter } from "next/navigation"; // Assuming you have this function in your api/index.ts
import styles from '../styles/profile.module.css';
import cookieUtil from "@/app/util/cookieUtil"; // Import the new CSS module

const ProfilePage = () => {
    const [user, setUser] = useState<{ email: string; fullName: string } | null>(null);
    const [error, setError] = useState('');

    const router = useRouter();
    useEffect(() => {
        const fetchProfile = async () => {
            try {
                // Assuming you are storing the JWT token in localStorage or cookies
                const token = cookieUtil.getTokenFromCookies();
                if (!token) {
                    router.push('/login');
                }
                const userData = await getAuthenticatedUser();
                setUser(userData);
            } catch (err) {
                setError('Failed to load profile.');
            }
        };

        fetchProfile();
    }, []);

    if (error) {
        return <p>{error}</p>;
    }

    if (!user) {
        return <p>Loading...</p>;
    }

    return (
        <div className={styles.profileContainer}>
            <li className={styles.listStyle}>
                <ul >
                    <div>
                        <h1 className={styles.title}>Your Profile Information</h1>
                    </div>
                </ul>
                <ul>
                    <div className={styles.profileCard}>
                        <p><strong>Email:</strong> {user.email}</p>
                        <p><strong>Full Name:</strong> {user.fullName}</p>
                    </div>
                </ul>
            </li>

</div>
)
    ;
};

export default ProfilePage;
