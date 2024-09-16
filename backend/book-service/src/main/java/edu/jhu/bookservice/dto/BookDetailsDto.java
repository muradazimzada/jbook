package edu.jhu.bookservice.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class BookDetailsDto extends BookDto {
     private String description;

}
