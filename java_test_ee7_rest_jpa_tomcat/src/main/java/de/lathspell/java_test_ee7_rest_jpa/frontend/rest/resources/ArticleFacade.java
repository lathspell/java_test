package de.lathspell.java_test_ee7_rest_jpa.frontend.rest.resources;

import de.lathspell.java_test_ee7_rest_jpa.model.Article;
import java.util.List;
import java.util.UUID;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
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
        em = emf.createEntityManager();
    }

    @POST
    @Consumes({"application/xml", "application/json"})
    public long create(Article entity) {
        log.info("create: " + entity);

        em.getTransaction().begin();
        em.persist(entity);
        em.flush();
        em.refresh(entity);
        em.getTransaction().commit();

        log.info("created: " + entity);
        return entity.getId();
    }

    @PUT
    @Path("/{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") Long id, Article entity) {
        log.info("edit: " + id);
    }

    @DELETE
    @Path("/{id}")
    public void remove(@PathParam("id") Long id) {
        log.info("remove: " + id);
    }

    @GET
    @Produces({"application/xml", "application/json"})
    public List<Article> getAll() {
        log.info("getAll");
        List<Article> articles = em.createQuery("SELECT a FROM Article a", Article.class).getResultList();
        log.info("getAll found: " + articles.size());
        return articles;
    }

    @GET
    @Path("/{id: [0-9]+}")
    @Produces({"application/xml", "application/json"})
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
    public void generateRandom() {
        log.info("generateRandom");

        Article a = new Article();
        a.setText(UUID.randomUUID().toString());
        a.setTitle(a.getText().substring(0, 5));

        create(a);
    }
}
