//package edu.jhu.bookservice.service;
//
//import edu.jhu.bookservice.client.UserServiceClient;
//import edu.jhu.bookservice.dto.BookDto;
//import edu.jhu.bookservice.dto.UserDto;
//import edu.jhu.bookservice.entity.Book;
//import edu.jhu.bookservice.proxy.UserServiceProxy;
//import edu.jhu.bookservice.repository.BookRepository;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//
//@Service
//public class BookService {
//
//    private final BookRepository bookRepository;
//    private final UserServiceProxy userServiceProxy;
//
//    public BookService(BookRepository bookRepository, UserServiceProxy userServiceProxy) {
//        this.bookRepository = bookRepository;
//        this.userServiceProxy = userServiceProxy;
//    }
//
//    public List<BookDto> getAllBooks() {
//        // Fetch all books
//        List<Book> books = bookRepository.findAll();
//
//        if(books.isEmpty()){
//            return List.of();
//        }
//
//        // Extract createdByIds from books
//        List<Long> createdByIds = books.stream()
//                .map(Book::getCreatedById)
//                .collect(Collectors.toList());
//
//        // Call user-service to get user details by IDs
//        Map<Long, UserDto> userMap = userServiceProxy.getUsersByIds(createdByIds);
//
//        // Build the BookDto list with user full names
//        return books.stream()
//                .map(book -> BookDto.builder()
//                        .id(book.getId())
//                        .title(book.getTitle())
//                        .author(book.getAuthor())
//                        .createdByName(userMap.get(book.getCreatedById()).getFullName())
//                        .build())
//                .collect(Collectors.toList());
//    }
//}


package edu.jhu.bookservice.service;

import edu.jhu.bookservice.dto.*;
import edu.jhu.bookservice.proxy.UserServiceProxy;
import edu.jhu.bookservice.entity.Book;
import edu.jhu.bookservice.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final UserServiceProxy userServiceProxy;

    public BookServiceImpl(BookRepository bookRepository, UserServiceProxy userServiceProxy) {
        this.bookRepository = bookRepository;
        this.userServiceProxy = userServiceProxy;
    }

    // Fetch all books and map user details
    public List<BookDto> getAllBooks() {
        List<Book> books = bookRepository.findAllByOrderByIdAsc();
        return mapBooksWithUserDetails(books);
    }

    // Get book by ID and map the user details
    public BookDetailsDto getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));
        return mapBookDetailsWithUserDetails(book);
    }

    // Create a new book and map the user details
    public BookDto createBook(BookCreateDto bookCreateDto, Long userId) {
        Book book = new Book();
        book.setTitle(bookCreateDto.getTitle());
        book.setAuthor(bookCreateDto.getAuthor());
        book.setIsbn(bookCreateDto.getIsbn());
        book.setCreatedById(userId);
        book.setDescription(bookCreateDto.getDescription());

        Book savedBook = bookRepository.save(book);
        return mapBookWithUserDetails(savedBook);
    }

    // Search books by title, author, isbn, and map user details
    public List<BookDto> searchBooks(String title, String author, String isbn) {
        List<Book> books = bookRepository.findByTitleOrAuthorOrIsbnContaining(title, author, isbn);
        return mapBooksWithUserDetails(books);
    }

    // Get books by user ID and map user details
    public List<BookDto> getBooksByUserId(Long userId) {
        List<Book> books = bookRepository.findByCreatedById(userId);
        return mapBooksWithUserDetails(books);
    }

    // Mapping Method: Map books to BookDto and fetch user details
    private List<BookDto> mapBooksWithUserDetails(List<Book> books)

     { if (books.isEmpty()) {
            return List.of();
        }

        List<Long> createdByIds = books.stream()
                .map(Book::getCreatedById)
                .collect(Collectors.toList());

        // Fetch user details from user-service for all createdByIds
        Map<Long, UserDto> userMap = userServiceProxy.getUsersByIds(createdByIds);

        // Convert each book to BookDto with the user’s full name
        return books.stream()
                .map(book -> BookDto.builder()
                        .id(book.getId())
                        .title(book.getTitle())
                        .author(book.getAuthor())
                        .isbn(book.getIsbn())
                        .createdByName(userMap.get(book.getCreatedById()).getFullName()) // Map user's full name
                        .build())
                .collect(Collectors.toList());
    }

    // Mapping Method: Map a single book to BookDto and fetch user details
    private BookDto mapBookWithUserDetails(Book book) {
        UserDto userDTO = userServiceProxy.getUserById(book.getCreatedById());

        return BookDto.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .isbn(book.getIsbn())
                .createdByName(userDTO.getFullName())  // Set user's full name
                .build();
    }

    private BookDetailsDto mapBookDetailsWithUserDetails(Book book) {
        UserDto userDTO = userServiceProxy.getUserById(book.getCreatedById());

        return  BookDetailsDto.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .isbn(book.getIsbn())
                .createdByName(userDTO.getFullName())  // Set user's full name
                .createdAt(book.getCreatedAt())
                .description(book.getDescription())
                .build();
    }

    public BookDetailsDto updateBook(Long id, BookUpdateDto bookUpdateDto) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        // Check if the user is the creator of the book

        book.setTitle(bookUpdateDto.getTitle());
        book.setAuthor(bookUpdateDto.getAuthor());
        book.setIsbn(bookUpdateDto.getIsbn());
        book.setDescription(bookUpdateDto.getDescription());

        bookRepository.save(book);

        return mapBookDetailsWithUserDetails(book);
    }
    public boolean isUserAuthorizedToEdit(Long userId, Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        Long createdById = book.getCreatedById();

        return userId.equals(createdById);
    }
}
