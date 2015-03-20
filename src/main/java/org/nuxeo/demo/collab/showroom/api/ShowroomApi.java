package org.nuxeo.demo.collab.showroom.api;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;

import org.nuxeo.demo.collab.showroom.product.fake.FakeProductResource;
import org.nuxeo.ecm.core.api.CoreSession;
import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.core.api.DocumentModelList;
import org.nuxeo.ecm.core.api.IdRef;
import org.nuxeo.ecm.core.io.registry.context.DepthValues;
import org.nuxeo.ecm.core.io.registry.context.RenderingContextImpl.RenderingContextBuilder;
import org.nuxeo.ecm.webengine.jaxrs.coreiodelegate.RenderingContextWebUtils;
import org.nuxeo.ecm.webengine.model.Resource;
import org.nuxeo.ecm.webengine.model.WebObject;
import org.nuxeo.ecm.webengine.model.impl.ModuleRoot;

@Path("/showroom")
@WebObject(type = "ShowroomApi")
@Produces({ APPLICATION_JSON })
public class ShowroomApi extends ModuleRoot {

    private static final String CTX_KEY = "_STORED_GENERATED_RENDERING_CONTEXT";

    @GET
    @Path("/ping")
    @Produces(APPLICATION_JSON)
    public String ping() {
        return "ok";
    }

    @GET
    @Path("entry/{uid}")
    @Produces(APPLICATION_JSON)
    public DocumentModel getEntryById(@PathParam("uid") String uid, @Context HttpServletRequest request) {
        CoreSession session = ctx.getCoreSession();
        DocumentModel doc = session.getDocument(new IdRef(uid));
        configureOutput(request);
        return doc;
    }

    @GET
    @Path("entries/forProduct/{productReference}")
    @Produces(APPLICATION_JSON)
    public List<DocumentModel> getEntriesForProduct(@PathParam("productReference") int productReference,
            @Context HttpServletRequest request) {
        CoreSession session = ctx.getCoreSession();
        String query = "SELECT * FROM Document";
        query += " WHERE pdt:product = " + productReference;
        query += " ORDER BY dc:modified desc";
        DocumentModelList list = session.query(query);
        configureOutput(request);
        return list;
    }

    @GET
    @Path("entries/forProducts")
    @Produces(APPLICATION_JSON)
    public Map<Integer, List<DocumentModel>> getEntriesForProduct(
            @QueryParam("references") List<Integer> productReferences, @Context HttpServletRequest request) {
        CoreSession session = ctx.getCoreSession();
        Map<Integer, List<DocumentModel>> result = new HashMap<>();
        for (Integer ref : productReferences) {
            String query = "SELECT * FROM Document";
            query += " WHERE pdt:product = " + ref.intValue();
            query += " ORDER BY dc:modified desc";
            DocumentModelList list = session.query(query);
            result.put(ref, list);
        }
        configureOutput(request);
        return result;
    }

    private void configureOutput(HttpServletRequest request) {
        RenderingContextBuilder builder = RenderingContextWebUtils.getBuilder(request);
        // load the required schema
        builder.properties("dublincore", "file", "location", "productReference");
        // load the data referenced by some field values
        builder.fetchInDoc("dc:creator", "dc:subjects", "dc:nature", "pdt:product");
        // enrich the returned json with additional related data
        builder.enrichDoc("preview", "showroomEntryUrl");
        // for dc:subjects, load the complete directory entry hierarchy
        builder.fetch("directoryEntry", "parent");
        // for dc:nature, translate the label key which contains a message key
        builder.translate("directoryEntry", "label");
        // set the aggregatation depth to max (root, children, max)
        // to be able to load dc:subjects parent
        builder.depth(DepthValues.max);
        request.setAttribute(CTX_KEY, builder.get());
    }

    @Path("/products")
    public Resource fakeProductSite() {
        return newObject(FakeProductResource.FAKE_PRODUCT_RESOURCE);
    }

}
