package org.ctridg.Tools;
import org.ctridg.Models.Association;
import org.ctridg.Models.Entity;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import org.ctridg.Models.Attribute;
import org.ctridg.Models.Operation;

import javax.xml.parsers.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DocumentParser {
    private DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    
    private List<Attribute> attributes;
    private List<Operation> operations;
    private Attribute idAttribute;
    private List<String> searchAttributes;
    private String className;

    private List<Entity> entities;
    private List<Association> associations;

    public DocumentParser(String filename){
        try{
            parse(filename);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    private void parseClass(Element classNode){
        if(classNode == null) return;
        className = classNode.getAttribute("Name");

        NodeList attributeList = classNode.getChildNodes().item(1).getChildNodes();

        this.attributes = new ArrayList<Attribute>();
        this.operations = new ArrayList<Operation>();

        for(int i=0;i<attributeList.getLength();i++){
            Node node = attributeList.item(i);
            if(node.getNodeType() == Node.ELEMENT_NODE){
                Element element = (Element) node;
                if(element.getTagName().equals("Attribute")){
                    Attribute attribute = new Attribute(element);
                    this.attributes.add(attribute);
                }
                else if(element.getTagName().equals("Operation")){
                    Operation operation = new Operation(element);
                    this.operations.add(operation);
                }
            }
        }

        for(Attribute attribute : this.attributes){
            if(attribute.getIsId().equals("true")){
                idAttribute = attribute;
                break;
            }
        }
        this.searchAttributes = new ArrayList<String>();
        for(Operation operation : this.operations){
            if(operation.getParameters().size() != 1){
                continue;
            }
            searchAttributes.add(operation.getParameters().get(0).getName());
        }
        entities.add(
                new Entity(
                        attributes,
                        operations,
                        idAttribute,
                        searchAttributes,
                        className
                )
        );
    }

    private void parseAssociation(Element associationNode){
        Association association = new Association();
        association.setName(associationNode.getAttribute("Name"));
        Element fromEnd = (Element) associationNode.getChildNodes().item(1).getChildNodes().item(1);
        Element toEnd = (Element) associationNode.getChildNodes().item(3).getChildNodes().item(1);

        association.setFromMultiplicity(fromEnd.getAttribute("Multiplicity"));
        association.setFrom(((Element)fromEnd.getChildNodes().item(3).getChildNodes().item(1)).getAttribute("Name"));
        association.setToMultiplicity(toEnd.getAttribute("Multiplicity"));
        association.setTo(((Element)toEnd.getChildNodes().item(3).getChildNodes().item(1)).getAttribute("Name"));

        associations.add(association);
    }

    private void parse(String filename) throws ParserConfigurationException, SAXException, IOException{
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new File(filename));
        NodeList modelList = document.getDocumentElement().getChildNodes().item(3).getChildNodes();

        List<Element> classNodes = new ArrayList<Element>();
        NodeList associationNodes = null;

        for(int i=0;i<modelList.getLength();i++){
            Node node = modelList.item(i);
            if(node.getNodeType() == Node.ELEMENT_NODE){
                Element element = (Element) node;
                if(element.getNodeName().equals("Class")){
                    classNodes.add(element);
                } else if(element.getNodeName().equals("ModelRelationshipContainer")){
                    associationNodes = element.getChildNodes().item(1).getChildNodes().item(1).getChildNodes().item(1).getChildNodes();
                }
            }
        }

        entities = new ArrayList<Entity>();
        associations = new ArrayList<Association>();

        for(Element classNode : classNodes){
            parseClass(classNode);
        }
        if(associationNodes == null){
            return;
        }
        for(int i=0;i<associationNodes.getLength();i++){
            Node associationNode = associationNodes.item(i);
            if(associationNode.getNodeType() == Node.ELEMENT_NODE){
                Element element = (Element) associationNode;
                if(element.getNodeName().equals("Association")){
                    parseAssociation(element);
                }
            }
        }
    }

    public List<Attribute> getAttributes(){
        return attributes;
    }
    public List<Operation> getOperations(){
        return operations;
    }
    public String getClassName(){
        return className;
    }
    public Attribute getIdAttribute(){
        return idAttribute;
    }
    public List<String> getSearchAttributes(){
        return searchAttributes;
    }

    public List<Entity> getEntities() {
        return entities;
    }
}
