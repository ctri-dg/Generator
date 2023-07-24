package org.ctridg.Models;

import java.util.List;

public class Entity {
    private List<Attribute> attributes;
    private List<Operation> operations;
    private Attribute idAttribute;
    private List<String> searchAttributes;
    private String className;

    public Entity(List<Attribute> attributes, List<Operation> operations, Attribute idAttribute, List<String> searchAttributes, String className) {
        this.attributes = attributes;
        this.operations = operations;
        this.idAttribute = idAttribute;
        this.searchAttributes = searchAttributes;
        this.className = className;
    }

    public Entity() {
    }

    @Override
    public String toString() {
        return "Entity{" +
                "attributes=" + attributes +
                ", operations=" + operations +
                ", idAttribute=" + idAttribute +
                ", searchAttributes=" + searchAttributes +
                ", className='" + className + '\'' +
                '}';
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<Attribute> attributes) {
        this.attributes = attributes;
    }

    public List<Operation> getOperations() {
        return operations;
    }

    public void setOperations(List<Operation> operations) {
        this.operations = operations;
    }

    public Attribute getIdAttribute() {
        return idAttribute;
    }

    public void setIdAttribute(Attribute idAttribute) {
        this.idAttribute = idAttribute;
    }

    public List<String> getSearchAttributes() {
        return searchAttributes;
    }

    public void setSearchAttributes(List<String> searchAttributes) {
        this.searchAttributes = searchAttributes;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
