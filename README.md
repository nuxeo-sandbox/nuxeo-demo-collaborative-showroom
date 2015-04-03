Collaborative Showroom App
--------------------------

An example application about Nuxeo Contents Validation and Pluggable JSON.

Build it and use it
-------------------

- $ git clone https://github.com/nuxeo-sandbox/nuxeo-demo-collaborative-showroom.git
- $ cd nuxeo-demo-collaborative-showroom
- $ mvn clean install
- $ cp target/*.jar path/to/nuxeo/nxserver/bundles/
- => Start Nuxeo and go to http://localhost:8080/nuxeo/site/showroom/products

To understand how it works, start with the MANIFEST.MF file and the corresponding contribs.

Contact:
--------

nc@nuxeo.com: email, jabber/hangout

Links:
------

- A blog post about validation: http://www.nuxeo.com/blog/automatic-data-validation-example-and-code-too/
- A blog post about batch upload: http://www.nuxeo.com/blog/qa-friday-upload-files-documents-rest-api/
- Documentation, Field Constraints and Validation:http://doc.nuxeo.com/x/yIBkAQ
- Documentation, How to Customize Document Validation: http://doc.nuxeo.com/x/rINkAQ
