package org.nuxeo.demo.collab.showroom.api;

import java.util.Set;

import org.nuxeo.ecm.webengine.app.WebEngineModule;
import org.nuxeo.ecm.webengine.jaxrs.coreiodelegate.JsonCoreIODelegate;

import com.google.common.collect.Sets;

public class ShowroomApp extends WebEngineModule {

    @Override
    public Set<Object> getSingletons() {
        return Sets.newHashSet(new JsonCoreIODelegate());
    }

}
