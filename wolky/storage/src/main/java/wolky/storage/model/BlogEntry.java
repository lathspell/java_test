package wolky.storage.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "entries")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlogEntry implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @NotNull
    private String title;

    @NotNull
    private String content;

    @ManyToMany(cascade = {})
    @JoinTable(name = "entry_to_tag",
            joinColumns = @JoinColumn(name = "entry_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    @NotNull
    private List<wolky.storage.storage.model.BlogTag> tags;

    @NotNull
    private LocalDateTime created;

}
