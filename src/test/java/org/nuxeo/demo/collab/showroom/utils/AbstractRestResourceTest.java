package org.nuxeo.demo.collab.showroom.utils;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import javax.ws.rs.core.MediaType;

import org.apache.commons.io.IOUtils;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.WebResource.Builder;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.jersey.multipart.BodyPart;
import com.sun.jersey.multipart.FormDataMultiPart;
import com.sun.jersey.multipart.file.StreamDataBodyPart;

public class AbstractRestResourceTest {

    protected String getJsonResponse(String path) throws IOException {
        return getResponseContent(path, APPLICATION_JSON);
    }

    protected String getJsonResponse(String path, Map<String, String> parameters) throws IOException {
        return getResponseContent(path, APPLICATION_JSON);
    }

    protected String getHtmlResponse(String path) throws IOException {
        return getResponseContent(path, MediaType.TEXT_HTML);
    }

    protected String getResponseContent(String path, String mimetype) throws IOException {
        ClientResponse resp = getResponse(path, mimetype, null);
        return IOUtils.toString(resp.getEntityInputStream());
    }

    protected String getResponseContent(String path, String mimetype, Map<String, String> parameters)
            throws IOException {
        ClientResponse resp = getResponse(path, mimetype, parameters);
        return IOUtils.toString(resp.getEntityInputStream());
    }

    protected ClientResponse getResponse(String path, String mimetype, Map<String, String> parameters) {
        WebResource wr = getRemoteResource();
        if (parameters != null) {
            for (Map.Entry<String, String> parameter : parameters.entrySet()) {
                wr = wr.queryParam(parameter.getKey(), parameter.getValue());
            }
        }
        Builder builder = wr.path(path).accept(mimetype);
        ClientResponse resp = builder.get(ClientResponse.class);
        return resp;
    }

    protected ClientResponse postJson(String path, String json) throws IOException {
        WebResource wr = getRemoteResource();
        Builder builder = wr.path(path).accept(APPLICATION_JSON).type(APPLICATION_JSON);
        return builder.post(ClientResponse.class, json);
    }

    protected ClientResponse putFile(String path, InputStream is) throws IOException {
        FormDataMultiPart form = new FormDataMultiPart();
        BodyPart fdp = new StreamDataBodyPart("content", is);
        form.bodyPart(fdp);
        WebResource wr = getRemoteResource();
        Builder builder = wr.path(path).type(MediaType.MULTIPART_FORM_DATA_TYPE);
        ClientResponse resp = builder.put(ClientResponse.class, form);
        form.close();
        return resp;
    }

    private WebResource getRemoteResource() {
        ClientConfig config = new DefaultClientConfig();
        Client client = Client.create(config);
        client.setConnectTimeout(60000);
        client.setReadTimeout(60000);
        client.addFilter(new HTTPBasicAuthFilter("Administrator", "Administrator"));
        WebResource wr = client.resource("http://localhost:18090/");
        return wr;
    }

}
