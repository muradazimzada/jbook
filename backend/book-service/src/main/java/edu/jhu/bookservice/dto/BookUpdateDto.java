package edu.jhu.bookservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class BookUpdateDto {
    private Long id;
    private String title;
    private String author;
    private String isbn;
    private String description;
}
