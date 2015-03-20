package org.nuxeo.demo.collab.showroom.api;

import static org.nuxeo.ecm.core.io.registry.reflect.Instantiations.SINGLETON;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.core.io.marshallers.json.enrichers.AbstractJsonEnricher;
import org.nuxeo.ecm.core.io.registry.reflect.Priorities;
import org.nuxeo.ecm.core.io.registry.reflect.Setup;

@Setup(mode = SINGLETON, priority = Priorities.REFERENCE)
public class ShowroomEntryUrlJsonEnricher extends AbstractJsonEnricher<DocumentModel> {

    private static final String NAME = "showroomEntryUrl";

    public ShowroomEntryUrlJsonEnricher() {
        super(NAME);
    }

    @Override
    public void write(JsonGenerator jg, DocumentModel doc) throws IOException {
        jg.writeObjectFieldStart(NAME);
        jg.writeStringField("url", ctx.getBaseUrl() + "showroom/entry/" + doc.getId());
        jg.writeEndObject();
    }

}
