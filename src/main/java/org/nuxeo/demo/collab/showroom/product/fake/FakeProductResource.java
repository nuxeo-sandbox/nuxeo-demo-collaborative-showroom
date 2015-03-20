package org.nuxeo.demo.collab.showroom.product.fake;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.TEXT_HTML;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.nuxeo.demo.collab.showroom.product.Product;
import org.nuxeo.demo.collab.showroom.product.ProductService;
import org.nuxeo.ecm.webengine.model.Template;
import org.nuxeo.ecm.webengine.model.WebObject;
import org.nuxeo.ecm.webengine.model.impl.DefaultObject;
import org.nuxeo.runtime.api.Framework;

import com.sun.jersey.api.NotFoundException;

@WebObject(type = FakeProductResource.FAKE_PRODUCT_RESOURCE)
@Produces({ APPLICATION_JSON, TEXT_HTML })
public class FakeProductResource extends DefaultObject {

    public static final String FAKE_PRODUCT_RESOURCE = "FakeProductResource";

    @GET
    @Produces(APPLICATION_JSON)
    public List<Product> getProducts() {
        ProductService service = Framework.getService(ProductService.class);
        return service.getProducts();
    }

    @GET
    @Produces(TEXT_HTML)
    public Template getProductsPage() {
        ProductService service = Framework.getService(ProductService.class);
        List<Product> products = service.getProducts();
        return getView("products").arg("products", products);
    }

    @GET
    @Path("{reference}")
    @Produces(APPLICATION_JSON)
    public Product getProduct(@PathParam("reference") int reference) {
        ProductService service = Framework.getService(ProductService.class);
        Product product = service.getProduct(reference);
        if (product == null) {
            throw new NotFoundException();
        }
        return product;
    }

    @GET
    @Path("{reference}")
    @Produces(TEXT_HTML)
    public Template getProductPage(@PathParam("reference") int reference) {
        ProductService service = Framework.getService(ProductService.class);
        Product product = service.getProduct(reference);
        if (product == null) {
            throw new NotFoundException();
        }
        return getView("product").arg("product", product);
    }

}
