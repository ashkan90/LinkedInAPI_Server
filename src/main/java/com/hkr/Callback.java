package com.hkr;


import org.json.JSONObject;
import org.json.JSONStringer;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.*;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

@Path("/callback")
public class Callback {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(
            @QueryParam("state") String state,
            @QueryParam("code") String code
            ) throws InterruptedException, ExecutionException, IOException {



        try {
            LinkedIn.setOAuthToken(code);
        } catch (Exception ex) {
            return Response.status(200).entity(ex.getMessage()).build();
        }

        (new LinkedInRepository()).profile();

//        String a = new JSONStringer()
//                .object()
//                    .key("state")
//                    .value(state)
//                    .key("code")
//                    .value(code)
//                .endObject()
//                .toString();

        return Response.status(Response.Status.SEE_OTHER)
                .header(HttpHeaders.LOCATION, "http://localhost:8080/#/listen/ok")
                .build();
    }
}
