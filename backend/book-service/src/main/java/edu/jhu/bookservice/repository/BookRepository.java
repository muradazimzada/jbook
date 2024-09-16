
package edu.jhu.bookservice.repository;

import edu.jhu.bookservice.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    // Find all books by the user who created them
    List<Book> findByCreatedById(Long createdById);

    List<Book> findAllByOrderByIdAsc();
    @Query(value = "SELECT * FROM books b " +
            "WHERE (:title IS NULL OR LOWER(b.title) LIKE LOWER(CONCAT('%', :title, '%')))" +
            " AND (:author IS NULL OR LOWER(b.author) LIKE LOWER(CONCAT('%', :author, '%')))" +
            " AND (:isbn IS NULL OR LOWER(b.isbn) LIKE LOWER(CONCAT('%', :isbn, '%'))) " +
            "order by b.id"
            , nativeQuery = true)
    List<Book> findByTitleOrAuthorOrIsbnContaining(String title, String author, String isbn);

}

