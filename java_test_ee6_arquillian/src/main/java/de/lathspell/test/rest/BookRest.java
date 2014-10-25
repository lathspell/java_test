package de.lathspell.test.rest;

import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.enterprise.context.RequestScoped;

import de.lathspell.test.model.Book;

/**
 * REST Web Service
 */
@Path("book")
@RequestScoped
public class BookRest {

    /**
     * This does not work with Arquillian In-Container Tests!
     *
     * @Context
     * private UriInfo context;
     */
    private static final Book book = new Book("Jules Verne", "Michel Strogoff");

    @GET
    @Path("/getAuthor")
    @Produces("application/json")
    public String getAuthor() {
        System.out.println("#42#DEBUG# book=" + book);
        return book.getAuthor();
    }

}
