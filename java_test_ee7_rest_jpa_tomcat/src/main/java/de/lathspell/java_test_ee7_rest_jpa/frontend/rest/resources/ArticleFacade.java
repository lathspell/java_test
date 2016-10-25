package de.lathspell.java_test_ee7_rest_jpa.frontend.rest.resources;

import de.lathspell.java_test_ee7_rest_jpa.model.Article;
import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import lombok.extern.slf4j.Slf4j;

@RequestScoped
@Path("/article")
@Slf4j
public class ArticleFacade {


    private EntityManager em;

    public ArticleFacade() {
        // em = Persistence.createEntityManagerFactory("myPU").createEntityManager();
    }

    @POST
    @Consumes({"application/xml", "application/json"})
    public long create(Article entity) {
        //  log.info("#42# foo="+foo);
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
    @Path("/{id}")
    @Produces({"application/xml", "application/json"})
    public Article get(@PathParam("id") Long id) {
        log.info("get: " + id);

        Article a = em.createQuery("SELECT a FROM Article a WHERE a.id = :id", Article.class).setParameter("id", id).getSingleResult();
        log.info("get found: " + a);

        return a;
    }

}
