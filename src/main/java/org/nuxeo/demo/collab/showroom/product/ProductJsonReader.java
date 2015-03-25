package org.nuxeo.demo.collab.showroom.product;

import static org.nuxeo.ecm.core.io.registry.reflect.Instantiations.SINGLETON;

import java.io.IOException;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.nuxeo.ecm.core.io.marshallers.json.AbstractJsonReader;
import org.nuxeo.ecm.core.io.registry.reflect.Priorities;
import org.nuxeo.ecm.core.io.registry.reflect.Setup;

@Setup(mode = SINGLETON, priority = Priorities.REFERENCE)
public class ProductJsonReader extends AbstractJsonReader<Product> {

    @Override
    public Product read(JsonNode jn) throws IOException {
        return new ObjectMapper().readValue(jn, Product.class);
    }

}
