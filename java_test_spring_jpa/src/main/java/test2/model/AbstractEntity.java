package test2.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;
import static javax.persistence.TemporalType.TIMESTAMP;

@Setter
@Getter
@ToString
@MappedSuperclass
public abstract class AbstractEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(updatable = false)
    protected Long id;

    /**
     * An example of a property that usually gets set by a database trigger.
     */
    @Column(name= "created_at", nullable = false)
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TIMESTAMP)
    protected Date createdAt;
    
    @Version
    protected int version;
}
