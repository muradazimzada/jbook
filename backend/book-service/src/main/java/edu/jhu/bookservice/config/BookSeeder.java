package edu.jhu.bookservice.config;

import edu.jhu.bookservice.entity.Book;
import edu.jhu.bookservice.repository.BookRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Arrays;
@Component
@RequiredArgsConstructor
public class BookSeeder {

    private final BookRepository bookRepository;

    @PostConstruct
    public void seedBooks() {
        if (bookRepository.count() == 0) {
            Book book1 = new Book("Book One", "Author A", "ISBN0001", 1L, "Description 1");
            Book book2 = new Book("Book Two", "Author B", "ISBN0002", 2L, "Description 2");
            Book book3 = new Book("Book Three", "Author C", "ISBN0003", 3L, "Description 3");
            Book book4 = new Book("Book Four", "Author D", "ISBN0004", 4L, "Description 4");
            Book book5 = new Book("Book Five", "Author E", "ISBN0005", 5L, "Description 5");
            Book book6 = new Book("Book Six", "Author F", "ISBN0006", 1L, "Description 6");
            Book book7 = new Book("Book Seven", "Author G", "ISBN0007", 2L, "Description 7");
            Book book8 = new Book("Book Eight", "Author H", "ISBN0008", 3L, "Description 8");
            Book book9 = new Book("Book Nine", "Author I", "ISBN0009", 4L, "Description 9");
            Book book10 = new Book("Book Ten", "Author J", "ISBN0010", 5L, "Description 10");
            Book book11 = new Book("Book Eleven", "Author K", "ISBN0011", 1L, "Description 11");
            Book book12 = new Book("Book Twelve", "Author L", "ISBN0012", 2L, "Description 12");
            Book book13 = new Book("Book Thirteen", "Author M", "ISBN0013", 3L, "Description 13");
            Book book14 = new Book("Book Fourteen", "Author N", "ISBN0014", 4L, "Description 14");
            Book book15 = new Book("Book Fifteen", "Author O", "ISBN0015", 5L, "Description 15");

            bookRepository.saveAll(Arrays.asList(
                    book1, book2, book3, book4, book5, book6, book7, book8, book9, book10,
                    book11, book12, book13, book14, book15
            ));
            System.out.println("Books seeded");
        } else {
            System.out.println("Books already exist, skipping seeding.");
        }
    }
}
