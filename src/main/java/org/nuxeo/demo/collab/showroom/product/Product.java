package org.nuxeo.demo.collab.showroom.product;

public class Product {

    int reference;

    String title;

    String description;

    String url;

    public Product(int reference, String title, String description, String url) {
        super();
        this.reference = reference;
        this.title = title;
        this.description = description;
        this.url = url;
    }

    public int getReference() {
        return reference;
    }

    public void setReference(int reference) {
        this.reference = reference;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
