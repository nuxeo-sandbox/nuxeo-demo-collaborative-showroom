package org.nuxeo.demo.collab.showroom.api;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.nuxeo.ecm.core.api.CoreSession;
import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.core.api.validation.DocumentValidationService;
import org.nuxeo.ecm.core.test.CoreFeature;
import org.nuxeo.ecm.core.test.annotations.Granularity;
import org.nuxeo.ecm.core.test.annotations.RepositoryConfig;
import org.nuxeo.runtime.test.runner.Deploy;
import org.nuxeo.runtime.test.runner.Features;
import org.nuxeo.runtime.test.runner.FeaturesRunner;

@RunWith(FeaturesRunner.class)
@Features(CoreFeature.class)
@Deploy("org.nuxeo.demo.collab.showroom")
@RepositoryConfig(cleanup = Granularity.METHOD)
public class ShowroomTypesTest {

    @Inject
    CoreSession session;

    @Inject
    DocumentValidationService validator;

    @Test
    public void testDocType() throws Exception {
        DocumentModel doc = session.createDocumentModel("/", "test1", "ShowroomEntry");
        assertTrue(doc.hasSchema("dublincore"));
        assertTrue(doc.hasSchema("location"));
        assertTrue(doc.hasSchema("productReference"));
        assertTrue(doc.hasSchema("file"));
    }

    @Test
    public void testLocationLatitude() throws Exception {
        assertFalse(validator.validate("loc:latitude", 90.0).hasError());
        assertFalse(validator.validate("loc:latitude", -90.0).hasError());
        assertFalse(validator.validate("loc:latitude", 0.0).hasError());
        assertFalse(validator.validate("loc:latitude", 45.0).hasError());
        assertFalse(validator.validate("loc:latitude", -45.0).hasError());
        assertTrue(validator.validate("loc:latitude", 90.1).hasError());
        assertTrue(validator.validate("loc:latitude", -90.1).hasError());
        assertTrue(validator.validate("loc:latitude", null).hasError());
    }

    @Test
    public void testLocationLongitude() throws Exception {
        assertFalse(validator.validate("loc:longitude", 180.0).hasError());
        assertFalse(validator.validate("loc:longitude", -180.0).hasError());
        assertFalse(validator.validate("loc:longitude", 0.0).hasError());
        assertFalse(validator.validate("loc:longitude", 45.0).hasError());
        assertFalse(validator.validate("loc:longitude", -45.0).hasError());
        assertTrue(validator.validate("loc:longitude", 180.1).hasError());
        assertTrue(validator.validate("loc:longitude", -180.1).hasError());
        assertTrue(validator.validate("loc:longitude", null).hasError());
    }

    @Test
    public void testProductReference() throws Exception {
        assertFalse(validator.validate("pdt:product", 10005).hasError());
        assertTrue(validator.validate("pdt:product", 12345).hasError());
        assertTrue(validator.validate("pdt:product", null).hasError());
    }

}
