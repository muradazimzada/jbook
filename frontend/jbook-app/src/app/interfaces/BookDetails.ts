import Book from "@/app/interfaces/Book";

interface BookDetails extends Book {
    createdByName: string;
    description: string;
    createdAt?: string; // Optional field
}

export default BookDetails;