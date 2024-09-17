// Create DeleteConfirmationModal.tsx in components directory
import React from 'react';
import styles from '@/app/styles/deleteConfirmationModal.module.css';

interface DeleteConfirmationModalProps {
    isOpen: boolean;
    onClose: () => void;
    onConfirm: () => void;
}

const DeleteConfirmationModal: React.FC<DeleteConfirmationModalProps> = ({ isOpen, onClose, onConfirm }) => {
    if (!isOpen) return null;

    return (
        <div className={styles.modalOverlay}>
            <div className={styles.modalContent}>
                <h3>Are you sure you want to delete this book?</h3>
                <div className={styles.buttonContainer}>
                    <button onClick={onConfirm} className={styles.confirmButton}>Yes</button>
                    <button onClick={onClose} className={styles.cancelButton}>No</button>
                </div>
            </div>
        </div>
    );
};

export default DeleteConfirmationModal;
