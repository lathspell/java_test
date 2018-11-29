package de.lathspell.test.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.lathspell.test.model.Team;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
}
