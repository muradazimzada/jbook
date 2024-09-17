package edu.jhu.bookservice.controller;

import edu.jhu.bookservice.dto.BookCreateDto;
import edu.jhu.bookservice.dto.BookDetailsDto;
import edu.jhu.bookservice.dto.BookDto;
import edu.jhu.bookservice.dto.BookUpdateDto;
import edu.jhu.bookservice.service.BookService;
import edu.jhu.bookservice.util.TokenUtility;
import edu.jhu.bookservice.util.TokenUtilityImpl;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;
    private final TokenUtility tokenUtility;

    public BookController(BookService bookService, TokenUtility tokenUtility) {
        this.bookService = bookService;
        this.tokenUtility = tokenUtility;
    }

    // Get all books (for authenticated users)
    @GetMapping("/all")
    public ResponseEntity<List<BookDto>> getAllBooks() {
        List<BookDto> books = bookService.getAllBooks();
        return ResponseEntity.ok(books);
    }

    // Get book by ID
    @GetMapping("/{id}")
    public ResponseEntity<BookDetailsDto> getBookById(@PathVariable Long id) {
        BookDetailsDto book = bookService.getBookById(id);
        return ResponseEntity.ok(book);
    }

    // Create a new book (with the current authenticated user)
    @PostMapping("/create")
    public ResponseEntity<BookDto> createBook(@RequestBody BookCreateDto bookCreateDto,
                                              @RequestHeader("Authorization") String token) {
        // Now use the user's ID to create the book
        Long userId = tokenUtility.getUserFromToken(token).getId();
        // Create the book using the BookService
        BookDto book = bookService.createBook(bookCreateDto, userId);

        return ResponseEntity.ok(book);
    }


    // Search for books by title, author, and ISBN
    @GetMapping("/search")
    public ResponseEntity<List<BookDto>> searchBooks(@RequestParam(required = false) String title,
                                                     @RequestParam(required = false) String author,
                                                     @RequestParam(required = false) String isbn) {
        List<BookDto> books = bookService.searchBooks(title, author, isbn);
        return ResponseEntity.ok(books);
    }

    // Get books created by the authenticated user
    @GetMapping("/my-books")
    public ResponseEntity<List<BookDto>> getBooksByUser(@RequestHeader("Authorization") String token) {
        Long userId = tokenUtility.getUserFromToken(token).getId();
        List<BookDto> books = bookService.getBooksByUserId(userId);
        return ResponseEntity.ok(books);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookDto> updateBook(@RequestHeader("Authorization") String authorizationHeader, @PathVariable Long id, @RequestBody BookUpdateDto bookUpdateDto) {
        Long userId = tokenUtility.getUserFromToken(authorizationHeader).getId();
        if(bookService.isUserAuthorizedToEdit( userId, id)) {
            var updatedBook = bookService.updateBook(id, bookUpdateDto);
            return ResponseEntity.ok(updatedBook);
        } else {
            return ResponseEntity.status(403).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@RequestHeader("Authorization") String authorizationHeader, @PathVariable Long id) {
        Long userId = tokenUtility.getUserFromToken(authorizationHeader).getId();
        if(bookService.isUserAuthorizedToEdit( userId, id)) {
            bookService.deleteBook(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(403).build();
        }
    }
}
