package org.nuxeo.demo.collab.showroom.product;

import static org.nuxeo.ecm.core.io.registry.reflect.Instantiations.SINGLETON;

import org.nuxeo.ecm.core.io.marshallers.json.DefaultListJsonReader;
import org.nuxeo.ecm.core.io.registry.reflect.Priorities;
import org.nuxeo.ecm.core.io.registry.reflect.Setup;

@Setup(mode = SINGLETON, priority = Priorities.REFERENCE)
public class ProductListJsonReader extends DefaultListJsonReader<Product> {

    public ProductListJsonReader() {
        super("products", Product.class);
    }

}
