package org.factcast.server.rest.resources.cors;

import java.io.IOException;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;

import lombok.NonNull;

/**
 * this filter provides static headers to enable request from the browser
 * 
 * @author joerg_adler
 *
 */
@Priority(Priorities.HEADER_DECORATOR)
public class CorsFilter implements ContainerResponseFilter {

    @Override
    public void filter(@NonNull ContainerRequestContext requestContext,
            @NonNull ContainerResponseContext responseContext) throws IOException {

        responseContext.getHeaders().add("Access-Control-Allow-Origin", "*");
        responseContext.getHeaders().add("Access-Control-Allow-Headers",
                "origin, content-type, accept");
        responseContext.getHeaders().add("Access-Control-Allow-Credentials", "false");
        responseContext.getHeaders().add("Access-Control-Allow-Methods",
                "GET, POST, PUT, DELETE, OPTIONS, HEAD");
        responseContext.getHeaders().add("Access-Control-Max-Age", "1209600");

    }
}
