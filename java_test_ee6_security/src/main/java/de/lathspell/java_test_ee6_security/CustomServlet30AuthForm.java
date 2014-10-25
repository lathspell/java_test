package de.lathspell.java_test_ee6_security;

import java.io.IOException;
import java.io.Serializable;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Servlet 3.0 authentication.
 *
 * http://blog.triona.de/tag/servlet-3-0-login
 *
 * All ViewScoped and SessionScoped beans have to implement Serializable!
 */
@ManagedBean
@ViewScoped
public class CustomServlet30AuthForm implements Serializable {

    private static final Logger log = LoggerFactory.getLogger(CustomServlet30AuthForm.class);
    private String username;
    private String password;

    public void login() throws Exception {
        log.info("entering");

        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();

        try {
            // Credentials could be checked by the Application Server using:
            log.info("Trying login as " + username);
            request.login(username, password);

            // And return to where we came from
            String referer = getRedirectUrl();
            log.info("Redirecting to " + referer);
            externalContext.redirect(referer);
        } catch (ServletException e) {
            // Handle unknown username/password and stay on this login page
            context.addMessage(null, new FacesMessage("Unknown login: " + e.getMessage()));
        }
    }

    public void logout() throws IOException {
        log.info("logging out");
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        externalContext.invalidateSession();

        String contextPath = externalContext.getRequestContextPath();
        log.info("redirecting to context path: " + contextPath);
        externalContext.redirect(contextPath);
    }

    private String getRedirectUrl() throws Exception {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();

        // If the JSF security restricted access to a page, its location is in this field
        String originalURL = (String) externalContext.getRequestMap().get(RequestDispatcher.FORWARD_REQUEST_URI);
        if (originalURL != null) {
            // Maybe there was a "?query=path" appended to the original URL?
            String originalQuery = (String) externalContext.getRequestMap().get(RequestDispatcher.FORWARD_QUERY_STRING);
            if (originalQuery != null) {
                originalURL += "?" + originalQuery;
            }
        } else {
            // Else this form was probably called via the login button on the index page
            originalURL = externalContext.getRequestContextPath();
        }

        return originalURL;
    }

    // Getters/setters for username and password.
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
