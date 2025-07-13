package com.newsletter.dto;


import java.time.LocalDate;
import java.util.List;

public record RatingDatailsDTO(long version, LocalDate dataLancamento,
                               double average, List<UserCommentDTO> comments) {
}
