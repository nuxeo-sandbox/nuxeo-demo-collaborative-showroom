Collaborative Showroom App
--------------------------

An example application about Nuxeo Contents Validation and Pluggable JSON.

Please note that the authentication is not yet managed in this example application. You have to authenticate with Administrator/Administrator.

Build it and use it
-------------------

- $ git clone https://github.com/nuxeo-sandbox/nuxeo-demo-collaborative-showroom.git
- $ cd nuxeo-demo-collaborative-showroom
- $ mvn clean install
- $ cp target/*.jar path/to/nuxeo/nxserver/bundles/
- $ path/to/nuxeo/bin/nuxeoctl console # starts Nuxeo
- => Go to http://localhost:8080/nuxeo/site/showroom/products (that's the fake e-commerce site)
- => Go to http://localhost:8080/nuxeo/nxpath/default/default-domain/workspaces/showrooms@view_documents to view the shared pictures from Nuxeo

To understand how it works, start with the MANIFEST.MF file and the corresponding contribs.

Contact:
--------

nc[at]nuxeo.com : email, jabber/hangout

Links:
------

- A blog post about validation: http://www.nuxeo.com/blog/automatic-data-validation-example-and-code-too/
- A blog post about batch upload: http://www.nuxeo.com/blog/qa-friday-upload-files-documents-rest-api/
- Documentation, Field Constraints and Validation:http://doc.nuxeo.com/x/yIBkAQ
- Documentation, How to Customize Document Validation: http://doc.nuxeo.com/x/rINkAQ
