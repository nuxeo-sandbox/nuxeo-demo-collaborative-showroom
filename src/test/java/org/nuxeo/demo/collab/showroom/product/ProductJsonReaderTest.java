package org.nuxeo.demo.collab.showroom.product;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.ByteArrayInputStream;

import org.junit.Test;
import org.nuxeo.ecm.core.io.marshallers.json.AbstractJsonWriterTest;
import org.nuxeo.ecm.core.io.registry.context.RenderingContext.CtxBuilder;

public class ProductJsonReaderTest extends AbstractJsonWriterTest.Local<ProductJsonWriter, Product> {

    public ProductJsonReaderTest() {
        super(ProductJsonWriter.class, Product.class);
    }

    @Test
    public void test() throws Exception {
        Product product = new Product(123, "a title", "a description", "http://product");
        String json = asJson(product).toString();
        ProductJsonReader reader = registry.getInstance(CtxBuilder.get(), ProductJsonReader.class);
        ByteArrayInputStream in = new ByteArrayInputStream(json.getBytes());
        Product result = reader.read(Product.class, Product.class, APPLICATION_JSON_TYPE, in);
        assertNotNull(result);
        assertEquals(product.getReference(), result.getReference());
        assertEquals(product.getTitle(), result.getTitle());
        assertEquals(product.getDescription(), result.getDescription());
        assertEquals(product.getUrl(), result.getUrl());
    }
}
