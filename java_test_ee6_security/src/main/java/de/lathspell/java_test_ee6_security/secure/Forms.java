package de.lathspell.java_test_ee6_security.secure;

// import javax.annotation.security.DenyAll;
// import javax.annotation.security.RolesAllowed;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/**
 * The actions here are only available to certain roles.
 *
 * The RolesAllowed annotation does not seem to work with ManagedBeans, only
 * with EJB. The proposed solution seems to handle this in the view part i.e.
 * the XHTML pages. JSF checks the condition after request again so this seems
 * to be secured against hand crafted HTTP requests.
 */
@ManagedBean
@RequestScoped
public class Forms {

    // Does not work here: @RolesAllowed({"dept_a_role"})
    public String onlyForDeptA() throws Exception {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();

        if (!externalContext.isUserInRole("dept_a_role")) {
            throw new Exception("Oups? Access should have been denied?!");
        }

        context.addMessage(null, new FacesMessage("Access granted to onlyForDeptA() to " + request.getRemoteUser()));
        return "";
    }

    // Does not work here: @RolesAllowed("dept_b_role")
    public String onlyForDeptB() throws Exception {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();

        context.addMessage(null, new FacesMessage("Access granted to onlyForDeptB() to " + request.getRemoteUser()));
        return "";
    }
}
