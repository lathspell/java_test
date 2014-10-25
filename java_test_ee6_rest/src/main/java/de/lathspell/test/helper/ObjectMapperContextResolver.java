package de.lathspell.test.helper;

import javax.ws.rs.ext.ContextResolver;
// import javax.ws.rs.ext.Provider;

import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;

/**
 * Attention: There can only be one class per Java instance!
 *
 * Therefore Serialization and Deserialization both has to be configured here.
 * @see http://stackoverflow.com/questions/16380065/how-to-extract-objectmapper-from-jax-rs-client/16384686#16384686
 *
 * To activate add: @Provider
 */
public class ObjectMapperContextResolver implements ContextResolver<ObjectMapper> {

    /**
     * Configures certain serialization features.
     *
     * - INDENT_OUTPUT looks nice but newlines are ugly in junit tests.
     * - WRAP_ROOT_VALUE was supposed to permit returning primitive values
     *   but that did not work.
     * @see MyRestResource.getInteger()
     */
    private ObjectMapper mapper = new ObjectMapper()
            .configure(SerializationConfig.Feature.WRAP_ROOT_VALUE, true)
            .configure(DeserializationConfig.Feature.UNWRAP_ROOT_VALUE, true);

    @Override
    public ObjectMapper getContext(Class<?> type) {
        return mapper;
    }
}