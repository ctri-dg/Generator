package Generator.Models;
import org.w3c.dom.*;

public class Field{
    protected String name;
    protected String type;
    protected String typeModifier;
    
    public Field(Element element){
        this.name = element.getAttribute("Name");
        Element innerElement = (Element ) element.getChildNodes().item(1).getChildNodes().item(1);
        this.type = innerElement.getAttribute("Name");
        this.typeModifier = element.getAttribute("TypeModifier");
    }

    public String getName(){
        return name;
    }
    public String getType(){
        return type;
    }
    public String getTypeModifier(){
        return typeModifier;
    }

    public void setName(String name){
        this.name = name;
    }
    public void setType(String type){
        this.type = type;
    }
    public void setTypeModifier(String typeModifier){
        this.typeModifier = typeModifier;
    }
}
