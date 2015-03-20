package org.nuxeo.demo.collab.showroom.product;

import java.util.List;

public interface ProductService {

    public abstract Product getProduct(int reference);

    public abstract List<Product> getProducts();

}