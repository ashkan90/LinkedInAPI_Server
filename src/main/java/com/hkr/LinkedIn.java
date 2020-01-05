package com.hkr;

import com.github.scribejava.apis.LinkedInApi20;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;
import org.json.JSONObject;
import org.json.JSONStringer;
import org.json.JSONWriter;


import java.io.IOException;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutionException;


public class LinkedIn {

    protected static OAuth2AccessToken token;

    protected static OAuth20Service service;

    protected static Map<String, String> credentials;

    public static void credentials(Map<String, String> info) {
        LinkedIn.credentials = info;
    }

    public static String authenticate() {
        service = new ServiceBuilder(LinkedIn.credentials.get("clientId"))
                .apiSecret(LinkedIn.credentials.get("clientSecret"))
                .defaultScope("r_liteprofile r_emailaddress")
                .callback("http://localhost:8000/exa_war_exploded/rest/callback")
                .build(LinkedInApi20.instance());


        // Obtain the Authorization URL
        final String secretState = "secret" + new Random().nextInt(999_999);

        return new JSONStringer()
            .object()
                .key("redirect")
                .value(service.getAuthorizationUrl(secretState))
            .endObject()
            .toString();
    }

    public static void setOAuthToken(String tok) throws InterruptedException, ExecutionException, IOException {
        token = service.getAccessToken(tok);
    }

    public static String request(String uri) throws InterruptedException, ExecutionException, IOException {
        final OAuthRequest request = LinkedIn.prepareRequest(uri);


        service.signRequest(token, request);

        JSONWriter a = new JSONStringer()
                .object();

        String json = "";

        try (com.github.scribejava.core.model.Response resp = service.execute(request)) {
            json = a
                    .key("code")
                    .value(resp.getCode())
                    .key("body")
                    .value(new JSONObject(resp.getBody()))
                    .endObject()
                    .toString();
        }
        return json;
    }

    public static String request(Verb verb, String uri, Map<String, Map<String, Object>> options) throws InterruptedException, ExecutionException, IOException {
        final OAuthRequest request = LinkedIn.prepareRequest(verb, uri, options);

        service.signRequest(token, request);

        JSONWriter a = new JSONStringer()
                .object();

        String json = "";

        try (com.github.scribejava.core.model.Response resp = service.execute(request)) {
            json = a
                    .key("code")
                    .value(resp.getCode())
                    .key("body")
                    .value(new JSONObject(resp.getBody()))
                    .endObject()
                    .toString();
        }
        return json;
    }


    protected static OAuthRequest prepareRequest(String uri) {
        final OAuthRequest request = new OAuthRequest(Verb.GET, uri);

        request.addHeader("x-li-format", "json");
        request.addHeader("Accept-Language", "tr-TR");

        return request;
    }

    protected static OAuthRequest prepareRequest(Verb verb, String uri, Map<String, Map<String, Object>> options) {
        final OAuthRequest request = new OAuthRequest(verb, uri);

        request.addHeader("x-li-format", "json");
        request.addHeader("Accept-Language", "tr-TR");

        options.get("headers").forEach((k, v) -> {
            request.addHeader(k, v.toString());
        });
        request.setPayload(JSONStringer.valueToString(options.get("parameters")));

        return request;
    }

}
