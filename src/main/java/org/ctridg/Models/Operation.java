package org.ctridg.Models;

import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.*;

public class Operation {
    private String name;
    private String returnType;
    private String typeModifier;
    private List<Parameter> parameters;

    public Operation(Element element){
        this.name = element.getAttribute("Name");
        this.typeModifier = element.getAttribute("TypeModifier");
        Element returnTypeNode = (Element) element.getChildNodes().item(1).getChildNodes().item(1);
        this.returnType = returnTypeNode.getAttribute("Name");
        NodeList parameterNodeList = element.getChildNodes().item(3).getChildNodes();
        this.parameters = new ArrayList<Parameter>();
        for(int j=0;j<parameterNodeList.getLength();j++){
            Node paramNode = parameterNodeList.item(j);
            if(paramNode.getNodeType() == Node.ELEMENT_NODE){
                Element paramElement = (Element) paramNode;
                Parameter param = new Parameter(paramElement);
                this.parameters.add(param);
            }
        }
    }

    public String getName(){
        return name;
    }
    public String getReturnType(){
        return returnType;
    }
    public String getTypeModifier(){
        return typeModifier;
    }
    public List<Parameter> getParameters(){
        return parameters;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setReturnType(String returnType){
        this.returnType = returnType;
    }
    public void addParam(Parameter attr){
        this.parameters.add(attr);
    }
    public String toString(){
        String ret =  String.format("%s : %s%s", name, returnType, typeModifier);
        for(Parameter param : parameters){
            ret += ("\n" + param.toString());
        }
        ret.concat("\n");
        return ret;
    }
}
