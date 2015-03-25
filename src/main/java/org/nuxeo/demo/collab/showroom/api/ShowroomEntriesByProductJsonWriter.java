package org.nuxeo.demo.collab.showroom.api;

import static org.nuxeo.ecm.core.io.registry.reflect.Instantiations.SINGLETON;

import java.io.IOException;
import java.util.Map;

import org.codehaus.jackson.JsonGenerator;
import org.nuxeo.ecm.core.api.DocumentModelList;
import org.nuxeo.ecm.core.io.marshallers.json.AbstractJsonWriter;
import org.nuxeo.ecm.core.io.registry.reflect.Priorities;
import org.nuxeo.ecm.core.io.registry.reflect.Setup;

@Setup(mode = SINGLETON, priority = Priorities.REFERENCE)
public class ShowroomEntriesByProductJsonWriter extends AbstractJsonWriter<Map<Integer, DocumentModelList>> {

    @Override
    public void write(Map<Integer, DocumentModelList> map, JsonGenerator jg) throws IOException {
        jg.writeStartObject();
        for (Map.Entry<Integer, DocumentModelList> entry : map.entrySet()) {
            jg.writeFieldName(entry.getKey().toString());
            writeEntity(entry.getValue(), jg);
        }
        jg.writeEndObject();
    }

}
