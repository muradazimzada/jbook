package edu.jhu.bookservice.service;

import edu.jhu.bookservice.dto.BookCreateDto;
import edu.jhu.bookservice.dto.BookDetailsDto;
import edu.jhu.bookservice.dto.BookDto;
import edu.jhu.bookservice.dto.BookUpdateDto;

import java.util.List;

public interface BookService {
    List<BookDto> getAllBooks();

    BookDetailsDto getBookById(Long bookId);

    BookDto createBook(BookCreateDto bookCreateDto, Long userId);

    List<BookDto> searchBooks(String title, String author, String isbn);

    List<BookDto> getBooksByUserId(Long userId);

    BookDetailsDto updateBook(Long bookId, BookUpdateDto bookCreateDto);

    boolean isUserAuthorizedToEdit(Long userId, Long bookId) ;

    void deleteBook(Long bookId);
}
