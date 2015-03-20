package org.nuxeo.demo.collab.showroom.product;

import static org.junit.Assert.assertTrue;
import static org.nuxeo.demo.collab.showroom.product.fake.FakeProductServiceImpl.NB_PRODUCT;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.nuxeo.demo.collab.showroom.product.fake.FakeProductServiceImpl;
import org.nuxeo.demo.collab.showroom.utils.AbstractRestResourceTest;
import org.nuxeo.demo.collab.showroom.utils.ServerFeature;
import org.nuxeo.ecm.core.io.marshallers.json.JsonAssert;
import org.nuxeo.ecm.core.test.DefaultRepositoryInit;
import org.nuxeo.ecm.core.test.annotations.Granularity;
import org.nuxeo.ecm.core.test.annotations.RepositoryConfig;
import org.nuxeo.runtime.api.Framework;
import org.nuxeo.runtime.test.runner.Features;
import org.nuxeo.runtime.test.runner.FeaturesRunner;
import org.nuxeo.runtime.test.runner.Jetty;

@RunWith(FeaturesRunner.class)
@Features(ServerFeature.class)
@Jetty(port = 18090)
@RepositoryConfig(cleanup = Granularity.METHOD, init = DefaultRepositoryInit.class)
public class FakeProductRestResourceTest extends AbstractRestResourceTest {

    @Test
    public void getProducts() throws Exception {
        JsonAssert json = JsonAssert.on(getJsonResponse("/showroom/products/"));
        json.isObject().properties(2);
        json.has("entity-type").isEquals("products");
        json = json.has("entries").isArray();
        json.length(NB_PRODUCT);
        for (int i = 0; i < NB_PRODUCT; i++) {
            JsonAssert child = json.has(i).isObject();
            child.isObject().properties(4);
            child.has("reference").isInt();
            child.has("title").isText();
            child.has("description").isText();
            child = child.has("url").isText();
            assertTrue(child.toString().startsWith("\"http"));
        }
    }

    @Test
    public void getProductsPage() throws Exception {
        String html = getHtmlResponse("/showroom/products/");
        ProductService service = Framework.getService(ProductService.class);
        for (Product product : service.getProducts()) {
            assertTrue(html.contains(Integer.toString(product.getReference())));
        }
    }

    @Test
    public void getProduct() throws Exception {
        JsonAssert json = JsonAssert.on(getJsonResponse("/showroom/products/10005"));
        json.isObject().properties(4);
        json.has("reference").isEquals(10005);
        json.has("title").isEquals("Nuxeo Kitchen 10005");
        json.has("description").isEquals("A nice kitchen which id is 10005");
        json.has("url").isEquals(FakeProductServiceImpl.URL + 10005);
    }

    @Test
    public void getProductPage() throws Exception {
        String html = getHtmlResponse("/showroom/products/10005");
        assertTrue(html.contains("10005"));
    }

}
