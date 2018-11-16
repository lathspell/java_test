package de.lathspell.test.h2;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface H2PersonRepository extends JpaRepository<H2Person, Long> {
}
