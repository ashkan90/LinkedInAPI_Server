package com.hkr;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.linkedin.ugc.ShareContent;
import org.json.JSONObject;
import org.json.JSONStringer;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Path("/user")
public class LinkedInController extends LinkedInRepository {

    @GET
    @Path("/authenticate")
    @Produces(MediaType.APPLICATION_JSON)
    public Response auth(
            @QueryParam("id") String clientId,
            @QueryParam("secret") String clientSecret
    ) {
        LinkedInRepository.credentials(new HashMap<>() {{
            put("clientId", clientId);
            put("clientSecret", clientSecret);
        }});
        return Response.status(200).entity(LinkedInRepository.authenticate()).build();
    }


    @GET
    @Path("/profile")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProfile() throws InterruptedException, ExecutionException, IOException {
        return this.profile();
    }

    @GET
    @Path("/email")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEmail() throws InterruptedException, ExecutionException, IOException {
        return this.email();
    }

//    @GET
//    @Path("/add/post")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response addPost() throws InterruptedException, ExecutionException, IOException {
//        Map<String, Object> form = new HashMap<>();
//        String g = new Gson().toJson(new ShareContent("Hello World! This is my first Share on LinkedIn!"));
//
//        String specificContent = new JSONStringer()
//                .object()
//                    .key("com.linkedin.ugc.ShareContent")
//                    .value(new JSONObject(g))
//                .endObject()
//                .toString();
//
//
//
//        form.put("author", Auth.id());
//        form.put("lifecycleState", "PUBLISHED");
//        form.put("specificContent", new JSONObject(specificContent));
//        form.put("visibility", "CONNECTIONS");
//
//        return this.post(form);
//    }
}
