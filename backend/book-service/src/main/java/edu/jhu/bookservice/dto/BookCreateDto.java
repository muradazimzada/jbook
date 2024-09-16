package edu.jhu.bookservice.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookCreateDto {
    private String title;
    private String author;
    private String isbn;
    private String description;
}