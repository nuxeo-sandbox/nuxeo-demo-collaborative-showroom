package org.nuxeo.demo.collab.showroom.product;

import static org.nuxeo.ecm.core.io.registry.reflect.Instantiations.SINGLETON;

import org.nuxeo.ecm.core.io.marshallers.json.DefaultListJsonWriter;
import org.nuxeo.ecm.core.io.registry.reflect.Priorities;
import org.nuxeo.ecm.core.io.registry.reflect.Setup;

@Setup(mode = SINGLETON, priority = Priorities.REFERENCE)
public class ProductListJsonWriter extends DefaultListJsonWriter<Product> {

    public ProductListJsonWriter() {
        super("products", Product.class);
    }

}
