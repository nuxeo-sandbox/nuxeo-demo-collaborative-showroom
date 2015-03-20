package org.nuxeo.demo.collab.showroom.product.fake;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.nuxeo.demo.collab.showroom.product.Product;
import org.nuxeo.demo.collab.showroom.product.ProductService;

public class FakeProductServiceImpl implements ProductService {

    public static final int NB_PRODUCT = 6;

    public static final String URL = "http://localhost:8080/nuxeo/site/showroom/products/";

    Map<Integer, Product> products = new HashMap<>();

    public FakeProductServiceImpl() {
        for (int id = 10000; id < 10000 + NB_PRODUCT; id++) {
            products.put(id, new Product(id, "Nuxeo Kitchen " + id, "A nice kitchen which id is " + id, URL + id));
        }
    }

    @Override
    public Product getProduct(int reference) {
        return products.get(reference);
    }

    @Override
    public List<Product> getProducts() {
        return new ArrayList<Product>(products.values());
    }

}
