package org.nuxeo.demo.collab.showroom.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.InputStream;

import javax.inject.Inject;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.nuxeo.demo.collab.showroom.utils.AbstractRestResourceTest;
import org.nuxeo.demo.collab.showroom.utils.ServerFeature;
import org.nuxeo.ecm.core.api.CoreSession;
import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.core.api.PathRef;
import org.nuxeo.ecm.core.io.marshallers.json.JsonAssert;
import org.nuxeo.ecm.core.test.DefaultRepositoryInit;
import org.nuxeo.ecm.core.test.annotations.Granularity;
import org.nuxeo.ecm.core.test.annotations.RepositoryConfig;
import org.nuxeo.runtime.test.runner.Features;
import org.nuxeo.runtime.test.runner.FeaturesRunner;
import org.nuxeo.runtime.test.runner.Jetty;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.core.util.MultivaluedMapImpl;

@RunWith(FeaturesRunner.class)
@Features(ServerFeature.class)
@Jetty(port = 18090)
@RepositoryConfig(cleanup = Granularity.METHOD, init = DefaultRepositoryInit.class)
public class ShowroomApiTest extends AbstractRestResourceTest {

    @Inject
    CoreSession session;

    @Test
    public void pingShowroom() throws Exception {
        assertEquals("ok", getJsonResponse("/showroom/ping"));
    }

    @Test
    public void pingRestApi() throws Exception {
        assertEquals(200, getResponse("/api/v1/path/", MediaType.APPLICATION_JSON, null).getStatus());
    }

    @Test
    public void createEntry() throws Exception {
        ClientResponse resp = createEntry(10005, "showroom_entry");
        assertEquals(201, resp.getStatus());
    }

    @Test
    public void postPicture() throws Exception {
        String name = "showroom_entry";
        createEntry(10005, name);
        ClientResponse resp = postPicture(name);
        assertEquals(200, resp.getStatus());
    }

    @Test
    public void getEntryById() throws Exception {
        String name = "showroom_entry";
        createEntry(10005, name);
        postPicture(name);
        DocumentModel doc = session.getDocument(new PathRef("/default-domain/workspaces/showrooms/" + name));
        String entryJson = getJsonResponse("/showroom/entry/" + doc.getId());
        JsonAssert json = JsonAssert.on(entryJson);
        json.isObject();
        json.has("entity-type").isEquals("document");
        json.has("uid").isEquals(doc.getId());
        json.has("title").isEquals("Nuxeo Kitchen 10005");
        json.has("type").isEquals("ShowroomEntry");
        // properties checking
        JsonAssert properties = json.has("properties").isObject();
        // properties location
        properties.has("loc:latitude").isEquals("50.608313");
        properties.has("loc:longitude").isEquals("3.200336");
        // properties dc:subjects and dc:nature
        properties.has("dc:nature").isObject();
        properties.has("dc:subjects").isArray().length(1);
        properties.has("dc:subjects[0].properties.parent").isObject();
        // properties product
        JsonAssert product = properties.has("pdt:product").isObject();
        product.has("reference").isEquals(10005);
        // properties file
        JsonAssert file = properties.has("file:content").isObject();
        String url = file.has("data").isText().getNode().getTextValue();
        assertTrue(url.startsWith("http"));
        // url provided
        url = json.has("contextParameters.preview.url").isText().getNode().getTextValue();
        assertTrue(url.startsWith("http"));
        String expected = "http://localhost:18090/showroom/entry/" + doc.getId();
        json.has("contextParameters.showroomEntryUrl.url").isEquals(expected);
    }

    @Test
    public void getEntriesForProduct() throws Exception {
        createEntry(10005, "10005-1");
        postPicture("10005-1");
        createEntry(10005, "10005-2");
        postPicture("10005-2");
        createEntry(10005, "10005-3");
        postPicture("10005-3");
        createEntry(10004, "10004-1");
        postPicture("10004-1");
        createEntry(10002, "10002-1");
        postPicture("10002-1");
        createEntry(10000, "10000-1");
        postPicture("10000-1");
        String entryJson = getJsonResponse("/showroom/entries/forProduct/10005");
        JsonAssert json = JsonAssert.on(entryJson);
        json.isObject();
        json.properties(2);
        json.has("entity-type").isEquals("documents");
        json = json.has("entries").isArray().length(3);
        json.childrenContains("entity-type", "document", "document", "document");
        json.childrenContains("properties.pdt:product.reference", "10005", "10005", "10005");
        json = json.has(0).isObject();
        json.has("entity-type").isEquals("document");
        json.has("title").isEquals("Nuxeo Kitchen 10005");
        json.has("type").isEquals("ShowroomEntry");
        // properties checking
        JsonAssert properties = json.has("properties").isObject();
        // properties location
        properties.has("loc:latitude").isEquals("50.608313");
        properties.has("loc:longitude").isEquals("3.200336");
        // properties dc:subjects and dc:nature
        properties.has("dc:nature").isObject();
        properties.has("dc:subjects").isArray().length(1);
        properties.has("dc:subjects[0].properties.parent").isObject();
        // properties product
        JsonAssert product = properties.has("pdt:product").isObject();
        product.has("reference").isEquals(10005);
        // properties file
        JsonAssert file = properties.has("file:content").isObject();
        String url = file.has("data").isText().getNode().getTextValue();
        assertTrue(url.startsWith("http"));
        // url provided
        url = json.has("contextParameters.preview.url").isText().getNode().getTextValue();
        assertTrue(url.startsWith("http"));
        url = json.has("contextParameters.showroomEntryUrl.url").isText().getNode().getTextValue();
        assertTrue(url.startsWith("http"));
    }

    @Test
    public void getEntriesForProducts() throws Exception {
        createEntry(10005, "10005-1");
        postPicture("10005-1");
        createEntry(10005, "10005-2");
        postPicture("10005-2");
        createEntry(10005, "10005-3");
        postPicture("10005-3");
        createEntry(10004, "10004-1");
        postPicture("10004-1");
        createEntry(10002, "10002-1");
        postPicture("10002-1");
        createEntry(10000, "10000-1");
        postPicture("10000-1");
        MultivaluedMap<String, String> parameters = new MultivaluedMapImpl();
        parameters.add("references", "10000");
        parameters.add("references", "10001");
        parameters.add("references", "10002");
        parameters.add("references", "10003");
        parameters.add("references", "10004");
        parameters.add("references", "10005");
        String entryJson = getJsonResponse("/showroom/entries/forProducts", parameters);
        JsonAssert json = JsonAssert.on(entryJson);
        json.properties(4);
        json.has("10000").isObject().has("entries").length(1);
        json.has("10002").isObject().has("entries").length(1);
        json.has("10004").isObject().has("entries").length(1);
        json.has("10005").isObject().has("entries").length(3);
    }

    private ClientResponse createEntry(int id, String name) throws IOException {
        String jsonDoc = "{\"entity-type\": \"document\",\"name\": \"" + name
                + "\", \"type\": \"ShowroomEntry\", \"properties\": {" + "\"dc:title\":\"Nuxeo Kitchen " + id
                + "\",  \"dc:nature\":\"article\",  \"dc:subjects\":[\"art/photography\"],"
                + "\"loc:latitude\":50.608313,  \"loc:longitude\":3.200336,  \"pdt:product\":" + id + "}}";
        return postJson("/api/v1/path/default-domain/workspaces/showrooms", jsonDoc);
    }

    private ClientResponse postPicture(String name) throws IOException {
        InputStream stream = ShowroomApiTest.class.getClassLoader().getResourceAsStream("files/kitchen_test.jpg");
        return putFile("/api/v1/path/default-domain/workspaces/showrooms/" + name + "/@blob/file:content", stream);
    }

}
