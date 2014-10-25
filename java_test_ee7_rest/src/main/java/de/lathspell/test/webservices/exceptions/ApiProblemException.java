package de.lathspell.test.webservices.exceptions;

import java.util.Map;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import static de.lathspell.test.webservices.exceptions.ApiProblem.APPLICATION_API_PROBLEM_JSON;
import static javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;

/**
 * Exception that generates HTTP header and body for a REST API problem.
 *
 * Conforming to https://tools.ietf.org/html/draft-nottingham-http-problem-04
 *
 * <code>
 * HTTP/1.1 403 Forbidden
 * Content-Type: application/api-problem+json
 * Content-Language: en
 *
 * {
 * "problemType": "http://example.com/probs/out-of-credit",
 * "title": "You do not have enough credit.",
 * "detail": "Your current balance is 30, but that costs 50.",
 * "problemInstance": "http://example.net/account/12345/msgs/abc",
 * "balance": 30,
 * "accounts": ["http://example.net/account/12345", "http://example.net/account/67890"]
 * }
 * </code>
 *
 * It is intended to be used as replacement for WebApplicationException:
 * <code>
 *      public String someRestMethod() {
 *          throw new ApiProblemException(BAD_REQUEST, "Whatever...");
 *      }
 * </code>
 */
public class ApiProblemException extends WebApplicationException {

    private ApiProblem ap;

    /**
     * @see ApiProblemJsonException(int statusCode, Throwable cause, String details, String instance, Map<String, String> extras)
     */
    public ApiProblemException(int statusCode, Throwable cause) {
        this(statusCode, cause, null, null, null);
    }

    /**
     * @see ApiProblemJsonException(int statusCode, Throwable cause, String details, String instance, Map<String, String> extras)
     */
    public ApiProblemException(Status status, String message) {
        this(status, new Exception(message), null, null, null);
    }

    /**
     * @see ApiProblemJsonException(int statusCode, Throwable cause, String details, String instance, Map<String, String> extras)
     */
    public ApiProblemException(Status status, String message, String details) {
        this(status, new Exception(message), details, null, null);
    }

    /**
     * @see ApiProblemJsonException(int statusCode, Throwable cause, String details, String instance, Map<String, String> extras)
     */
    public ApiProblemException(Status status, Throwable cause) {
        this(status, cause, null, null, null);
    }

    /**
     * @see ApiProblemJsonException(int statusCode, Throwable cause, String details, String instance, Map<String, String> extras)
     */
    public ApiProblemException(Status status, Throwable cause, String details) {
        this(status, cause, details, null, null);
    }

    /**
     * @see ApiProblemJsonException(int statusCode, Throwable cause, String details, String instance, Map<String, String> extras)
     */
    public ApiProblemException(WebApplicationException e) {
         // In Jersey 1.x (Java EE6) the entity is often NULL and some status values
         // like 405 do not yet have a phrase defined.
        this(e.getResponse().getStatus(), e);
    }

    /**
     * @see ApiProblemJsonException(int statusCode, Throwable cause, String details, String instance, Map<String, String> extras)
     */
    public ApiProblemException(Status status, Throwable cause, String detail, String instance, Map<String, String> extras) {
        this((status == null ? INTERNAL_SERVER_ERROR.getStatusCode() : status.getStatusCode()), cause, detail, instance, extras);
    }

    /**
     * Constructor for a WebApplicationException for REST problems.
     *
     * @param statusCode HTTP Status Code
     * @param cause Text or Exception (via getMessage) that describes the problem.
     * @param detail Further details.
     * @param instance E.g. the id of the database entity that caused the problem.
     * @param extras Whatever should be included in the JSON response.
     */
    public ApiProblemException(int statusCode, Throwable cause, String detail, String instance, Map<String, String> extras) {
        ap = new ApiProblem();
        ap.setProblemType("urn:" + cause.getClass().getCanonicalName());
        ap.setTitle(cause.getMessage());
        ap.setHttpStatus(statusCode);
        ap.setDetail(detail);
        ap.setProblemInstance(instance);
        ap.setExtras(extras);
    }

    @Override
    public Response getResponse() {
        // return createResponse(ap);
        return Response.status(ap.getHttpStatus()).type(APPLICATION_API_PROBLEM_JSON).entity(ap).build();
    }

}
