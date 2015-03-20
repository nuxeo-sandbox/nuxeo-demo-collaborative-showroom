package org.nuxeo.demo.collab.showroom.product;

import org.junit.Test;
import org.nuxeo.demo.collab.showroom.product.Product;
import org.nuxeo.demo.collab.showroom.product.ProductJsonWriter;
import org.nuxeo.ecm.core.io.marshallers.json.AbstractJsonWriterTest;
import org.nuxeo.ecm.core.io.marshallers.json.JsonAssert;

public class ProductJsonWriterTest extends AbstractJsonWriterTest.Local<ProductJsonWriter, Product> {

    public ProductJsonWriterTest() {
        super(ProductJsonWriter.class, Product.class);
    }

    @Test
    public void test() throws Exception {
        Product product = new Product(123, "a title", "a description", "http://product");
        JsonAssert json = jsonAssert(product);
        json.isObject().properties(4);
        json.has("reference").isEquals(product.getReference());
        json.has("title").isEquals(product.getTitle());
        json.has("description").isEquals(product.getDescription());
        json.has("url").isEquals(product.getUrl());
    }
}
