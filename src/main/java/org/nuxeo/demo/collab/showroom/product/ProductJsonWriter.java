package org.nuxeo.demo.collab.showroom.product;

import static org.nuxeo.ecm.core.io.registry.reflect.Instantiations.SINGLETON;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.nuxeo.ecm.core.io.marshallers.json.AbstractJsonWriter;
import org.nuxeo.ecm.core.io.registry.reflect.Priorities;
import org.nuxeo.ecm.core.io.registry.reflect.Setup;

@Setup(mode = SINGLETON, priority = Priorities.REFERENCE)
public class ProductJsonWriter extends AbstractJsonWriter<Product> {

    @Override
    public void write(Product product, JsonGenerator jg) throws IOException {
        jg.writeObject(product);
    }

}
