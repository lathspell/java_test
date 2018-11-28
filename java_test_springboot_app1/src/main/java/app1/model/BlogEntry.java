package app1.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import static javax.persistence.GenerationType.IDENTITY;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @OneToMany(targetEntity = BlogTag.class)
    @NotNull
    private String tag;

    @NotNull
    private LocalDateTime created;

}
