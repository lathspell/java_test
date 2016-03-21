package de.lathspell.test.sql.hibernate;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.NamedNativeQuery;
import javax.persistence.ColumnResult;

@Entity()
@Table(name="Food")
// @Table(name="Food", uniqueConstraints = {@UniqueConstraint(columnNames={"fav"})})
@SqlResultSetMapping(name="scalar",columns=@ColumnResult(name="max_id"))
@NamedNativeQuery(name="Food.named1",query="SELECT max(id) as max_id FROM Food",resultSetMapping="scalar")
public class Food implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
        
    private String fav;
    
    public void setFav(String fav) {
        this.fav = fav;
    }
    
    public String getFav() {
        return fav;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
