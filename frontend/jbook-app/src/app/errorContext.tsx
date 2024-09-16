// src/context/ErrorContext.tsx
"use client";

import React, { createContext, useState, ReactNode } from 'react';

// Define types for the context value
interface ErrorContextType {
    errorMessage: string | null;
    handleError: (message: string) => void;
    clearError: () => void;
}

// Create Error Context with default values
export const ErrorContext = createContext<ErrorContextType>({
    errorMessage: null,
    handleError: () => {},
    clearError: () => {},
});

// Error Provider Component Props
interface ErrorProviderProps {
    children: ReactNode;
}

// Error Provider Component
export const ErrorProvider: React.FC<ErrorProviderProps> = ({ children }) => {
    const [errorMessage, setErrorMessage] = useState<string | null>(null);

    const handleError = (message: string) => {
        setErrorMessage(message);
    };

    const clearError = () => {
        setErrorMessage(null);
    };

    return (
        <ErrorContext.Provider value={{ errorMessage, handleError, clearError }}>
            {children}
        </ErrorContext.Provider>
    );
};
