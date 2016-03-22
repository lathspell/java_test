package de.lathspell.test.dao.personteam;

import javax.inject.Named;

import org.springframework.data.repository.CrudRepository;

import de.lathspell.test.model.personteam.Team;

@Named
public interface TeamRepository extends CrudRepository<Team, Long> {
}
