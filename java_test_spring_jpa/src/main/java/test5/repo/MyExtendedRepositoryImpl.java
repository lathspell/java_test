package test5.repo;

import java.io.Serializable;

import javax.persistence.EntityManager;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.transaction.annotation.Transactional;
import test5.model.Author;

//@Component
@Slf4j
public class MyExtendedRepositoryImpl<T, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements MyExtendedRepository<T, ID> {

    private EntityManager em;

    public MyExtendedRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager em) {
        super(entityInformation, em);
        this.em = em;
    }

    @Transactional
    @Override
    public void saveWithGivenId(ID id, T a) {
        log.info("*** saving with id {} ***", id);
        ((Author) a).setId((Long) id);
        em.persist(a);
        em.flush();
        em.refresh(a);
        log.info("*** saved with id {} ***", ((Author) a).getId());
    }
}
