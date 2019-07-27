package wolky.web.model;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogTag implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @NotNull
    private String tag;
}
