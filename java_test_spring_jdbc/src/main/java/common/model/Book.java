package common.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    private Long id;
    private String name;
    private String isbn;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private LocalDate published;
}
