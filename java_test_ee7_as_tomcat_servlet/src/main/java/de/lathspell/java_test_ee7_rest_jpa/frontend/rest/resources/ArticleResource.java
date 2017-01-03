package de.lathspell.java_test_ee7_rest_jpa.frontend.rest.resources;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
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
import javax.ws.rs.core.Response;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;

import com.fasterxml.jackson.databind.ObjectMapper;
import static com.fasterxml.jackson.databind.SerializationFeature.INDENT_OUTPUT;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import de.lathspell.java_test_ee7_rest_jpa.backend.sql.ArticleDAO;
import de.lathspell.java_test_ee7_rest_jpa.model.Article;

@ApplicationScoped
@Path("/article")
@Slf4j
public class ArticleResource implements Serializable {

    @Inject
    private ArticleDAO articleDAO;

    public ArticleResource() {
        log.info("ctor");
    }

    
    @PostConstruct
    public void postConstruct() {
        log.info("postConstruct");
        log.info(""+ articleDAO);
    }
    
    @POST
    @Consumes(APPLICATION_JSON)
    public Article create(Article article) {
        log.info("create: " + article);
        article = articleDAO.save(article);
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
    @SneakyThrows
    public Response getAll() {
        log.info("getAll");
        List<Article> articles = articleDAO.getAll();
        log.info("getAll found: " + articles.size());
        return Response.ok(new ObjectMapper().enable(INDENT_OUTPUT).writeValueAsString(articles)).build();
    }

    @GET
    @Path("/{id: [0-9]+}")
    @Produces({APPLICATION_XML, APPLICATION_JSON})
    public Article get(@PathParam("id") Long id) {
        log.info("get: " + id);

        Article a = articleDAO.getOneById(id);
        log.info("get found: " + a);
        if (a == null) {
            throw new WebApplicationException(NOT_FOUND);
        }
        return a;
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
