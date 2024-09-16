import React, { useContext } from 'react';
import { ErrorContext } from '@/app/errorContext';

const ErrorDisplay: React.FC = () => {
    const { errorMessage, clearError } = useContext(ErrorContext);

    return (
        errorMessage && (
            <div className="error-message">
                <p>{errorMessage}</p>
                <button onClick={clearError}>Clear Error</button>
            </div>
        )
    );
};

export default ErrorDisplay;