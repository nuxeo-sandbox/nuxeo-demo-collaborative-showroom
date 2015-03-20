package org.nuxeo.demo.collab.showroom.product;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.nuxeo.ecm.core.schema.types.resolver.ObjectResolver;
import org.nuxeo.runtime.api.Framework;

public class ProductResolver implements ObjectResolver {

    private static final String NAME = "productResolver";

    @Override
    public void configure(Map<String, String> parameters) throws IllegalArgumentException, IllegalArgumentException {
    }

    @Override
    public List<Class<?>> getManagedClasses() {
        return Arrays.asList(Product.class);
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public Map<String, Serializable> getParameters() {
        return Collections.emptyMap();
    }

    @Override
    public boolean validate(Object value) {
        return fetch(value) != null;
    }

    @Override
    public Object fetch(Object value) {
        ProductService service = Framework.getService(ProductService.class);
        if (value != null && value instanceof Number) {
            int reference = ((Number) value).intValue();
            return service.getProduct(reference);
        }
        return null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T fetch(Class<T> type, Object value) {
        if (Product.class.isAssignableFrom(type)) {
            return (T) fetch(value);
        }
        return null;
    }

    @Override
    public Serializable getReference(Object object) {
        if (object != null && object instanceof Product) {
            return ((Product) object).getReference();
        }
        return null;
    }

    @Override
    public String getConstraintErrorMessage(Object invalidValue, Locale locale) {
        return Helper.getConstraintErrorMessage(this, invalidValue, locale);
    }

}
