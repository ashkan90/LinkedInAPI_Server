package com.hkr;

import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Verb;
import org.json.JSONObject;
import org.json.JSONStringer;
import org.json.JSONWriter;

import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class LinkedInRepository extends LinkedIn {


    public Response profile() throws InterruptedException, ExecutionException, IOException {
        String uri = "https://api.linkedin.com/v2/me";
        String body = LinkedInRepository.request(uri);

        String authId = new JSONObject(body)
                .getJSONObject("body")
                    .get("id")
                .toString();

        Auth.id(authId);

        return Response.status(200).entity(body).build();

    }

    public Response email() throws InterruptedException, ExecutionException, IOException {
        String uri = "https://api.linkedin.com/v2/emailAddress?q=members&projection=(elements*(handle~))";

        return Response.status(200).entity(LinkedInRepository.request(uri)).build();
    }


//    public Response post(Map<String, Object> parameters) throws InterruptedException, ExecutionException, IOException {
//        String uri = "https://api.linkedin.com/v2/ugcPosts";
//
//        Map<String, Map<String, Object>> optHeaders = new HashMap<>();
//        optHeaders.put("headers", new HashMap<String, Object>() {
//            { put("X-Restli-Protocol-Version", "2.0.0"); }
//        });
//        optHeaders.put("parameters", parameters);
//
//
//        String body = LinkedInRepository.request(Verb.POST, uri, optHeaders);
//
//        return Response.status(200).entity(body).build();
//
//    }


}
