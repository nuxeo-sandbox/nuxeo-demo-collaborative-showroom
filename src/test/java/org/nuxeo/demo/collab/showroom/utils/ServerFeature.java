package org.nuxeo.demo.collab.showroom.utils;

import org.nuxeo.ecm.core.test.DetectThreadDeadlocksFeature;
import org.nuxeo.ecm.core.test.TransactionalFeature;
import org.nuxeo.ecm.webengine.test.WebEngineFeature;
import org.nuxeo.runtime.test.runner.Deploy;
import org.nuxeo.runtime.test.runner.Features;
import org.nuxeo.runtime.test.runner.SimpleFeature;

@Deploy({ "org.nuxeo.demo.collab.showroom", "org.nuxeo.ecm.automation.core", "org.nuxeo.ecm.automation.io",
        "org.nuxeo.ecm.automation.server", "org.nuxeo.ecm.platform.restapi.server", "org.nuxeo.ecm.default.config",
        "org.nuxeo.ecm.platform.ui", "org.nuxeo.ecm.platform.preview", "org.nuxeo.ecm.platform.url.core" })
@Features({ TransactionalFeature.class, WebEngineFeature.class })
@DetectThreadDeadlocksFeature.Config(dumpAtTearDown = true)
public class ServerFeature extends SimpleFeature {

}
