package edu.jhu.bookservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "books")
public class Book {
    // Getters and Setters
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String author;

    private String isbn;

    // The ID of the user who created the book (links to user-service)
    private Long createdById;

    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    private String description;

    // Constructors
    public Book() {}

    public Book(String title, String author, String isbn, Long createdById, String description) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.createdById = createdById;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.description = description;
    }

}
