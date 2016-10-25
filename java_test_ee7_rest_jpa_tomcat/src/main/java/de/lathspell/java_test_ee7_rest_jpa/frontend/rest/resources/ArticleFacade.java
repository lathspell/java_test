package de.lathspell.java_test_ee7_rest_jpa.frontend.rest.resources;

import de.lathspell.java_test_ee7_rest_jpa.model.Article;
import java.util.List;
import java.util.UUID;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.APPLICATION_XML;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import lombok.extern.slf4j.Slf4j;

@RequestScoped
@Path("/article")
@Slf4j
public class ArticleFacade {

    @PersistenceUnit(unitName = "myPU")
    private EntityManagerFactory emf;

    private EntityManager em;

    public ArticleFacade() {
        log.info("ctor");
    }

    @PostConstruct
    public void postConstruct() {
        log.info("postConstruct");
        if (emf == null) {
            log.info("postConstruct create EMF as @PersistenceUnit did not work!");
            emf = Persistence.createEntityManagerFactory("myPU");
        }
        em = emf.createEntityManager();
    }

    @POST
    @Consumes(APPLICATION_JSON)
    public Article create(Article article) {
        log.info("create: " + article);

        em.getTransaction().begin();
        em.persist(article);
        em.flush();
        em.refresh(article);
        em.getTransaction().commit();

        log.info("created: " + article);
        return article;
    }

    @PUT
    @Path("/{id}")
    @Consumes(APPLICATION_JSON)
    public void edit(@PathParam("id") Long id, Article entity) {
        log.info("edit: " + id);
    }

    @DELETE
    @Path("/{id}")
    public void remove(@PathParam("id") Long id) {
        log.info("remove: " + id);
    }

    @GET
    @Produces(APPLICATION_JSON)
    public List<Article> getAll() {
        log.info("getAll");
        List<Article> articles = em.createQuery("SELECT a FROM Article a", Article.class).getResultList();
        log.info("getAll found: " + articles.size());
        return articles;
    }

    @GET
    @Path("/{id: [0-9]+}")
    @Produces({APPLICATION_XML, APPLICATION_JSON})
    public Article get(@PathParam("id") Long id) {
        log.info("get: " + id);

        try {
            Article a = em.createQuery("SELECT a FROM Article a WHERE a.id = :id", Article.class).setParameter("id", id).getSingleResult();
            log.info("get found: " + a);
            return a;
        } catch (NoResultException e) {
            throw new WebApplicationException(NOT_FOUND);
        }
    }

    @GET
    @Path("/random")
    @Produces(APPLICATION_JSON)
    public Article generateRandom() {
        log.info("generateRandom");

        Article a = new Article();
        a.setText(UUID.randomUUID().toString());
        a.setTitle(a.getText().substring(0, 5));

        return create(a);
    }
}
