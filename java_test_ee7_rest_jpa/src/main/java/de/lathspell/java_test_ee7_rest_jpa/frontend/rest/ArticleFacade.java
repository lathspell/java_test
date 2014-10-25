package de.lathspell.java_test_ee7_rest_jpa.frontend.rest;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.lathspell.java_test_ee7_rest_jpa.config.AppConfig;
import de.lathspell.java_test_ee7_rest_jpa.config.PropertyValue;
import de.lathspell.java_test_ee7_rest_jpa.model.Article;

@RequestScoped
@Path("/rest/article")
public class ArticleFacade {
    private final static Logger log = LoggerFactory.getLogger(ArticleFacade.class);

    @Inject
    @de.lathspell.java_test_se_cdi.config.PropertyValue("greeter.name")
    private String name;
  //  @Inject @MyPersistentUnit
   // String foo;

    private EntityManager em;

    public ArticleFacade() {
       // em = Persistence.createEntityManagerFactory("myPU").createEntityManager();
    }

    @POST
    @Consumes({"application/xml", "application/json"})
    public long create(Article entity) {
      //  log.info("#42# foo="+foo);
        log.info("#42# prop="+name);
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
