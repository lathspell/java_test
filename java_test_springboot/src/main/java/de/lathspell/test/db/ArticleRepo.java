package de.lathspell.test.db;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import de.lathspell.test.model.Article;

@Repository
public interface ArticleRepo extends JpaRepository<Article, Long> {

    public Page<Article> findOrderByCreatedAt(Pageable pageable);
    
    @Query("SELECT a FROM Article a ORDER BY createdAt desc")
    public Page<Article> myFindFirstOrderByCreatedAtDesc(PageRequest page);
}
