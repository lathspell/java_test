package wolky.web.model;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlogEntry implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @NotNull
    private String title;

    @NotNull
    private String content;

    @NotNull
    private List<BlogTag> tags;

    @NotNull
    private LocalDateTime created;

}
