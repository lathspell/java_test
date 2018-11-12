package test5.repo;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface MyExtendedRepository<T, ID extends Serializable> extends JpaRepository<T, ID> {

    public void saveWithGivenId(ID id, T a);
}
