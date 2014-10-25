package de.lathspell.stack1.frontend.rest.exceptions;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.Provider;

import org.codehaus.jackson.map.ObjectMapper;

import static de.lathspell.stack1.frontend.rest.exceptions.ApiProblem.APPLICATION_API_PROBLEM_JSON;

@Consumes(APPLICATION_API_PROBLEM_JSON)
@Provider
public class ApiProblemJsonMessageBodyReader implements MessageBodyReader<ApiProblem> {

    @Override
    public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return true;
    }

    @Override
    public ApiProblem readFrom(Class<ApiProblem> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, String> httpHeaders, InputStream entityStream) throws IOException, WebApplicationException {
        Map<String, Object> m = new ObjectMapper().readValue(entityStream, Map.class);
        ApiProblem ap = new ApiProblem();
        for (String k : m.keySet()) {
            switch (k) {
                case "problemType":
                    ap.setProblemType((String) m.get(k));
                    break;
                case "title":
                    ap.setTitle((String) m.get(k));
                    break;
                case "httpStatus":
                    ap.setHttpStatus(Integer.valueOf((String) m.get(k)));
                    break;
                case "detail":
                    ap.setDetail((String) m.get(k));
                    break;
                case "problemInstance":
                    ap.setProblemInstance((String) m.get(k));
                    break;
                default:
                    ap.getExtras().put(k, (String) m.get(k));
            }
        }

        return ap;
    }

}
