package com.hkr;

import org.json.JSONStringer;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

@Provider
public class AuthMiddleware implements ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        if (!(requestContext.getUriInfo().getPath().equals("user/authenticate") ||
                requestContext.getUriInfo().getPath().equals("callback"))) {
            if (Auth.id() == null) {
                String j = new JSONStringer()
                        .object()
                            .key("description")
                            .value("User has not been authenticated.")
                        .endObject()
                        .toString();
                requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).entity(j).build());
            }
        }
    }
}
