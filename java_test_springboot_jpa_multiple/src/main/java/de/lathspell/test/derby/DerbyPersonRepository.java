package de.lathspell.test.derby;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DerbyPersonRepository extends JpaRepository<DerbyPerson, Long> {
}
