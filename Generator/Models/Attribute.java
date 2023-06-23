package Generator.Models;
import org.w3c.dom.*;

public class Attribute extends Field{
    private String visibility;
    private String isId;

    public Attribute(Element element){
        super(element);
        this.visibility = element.getAttribute("Visibility");
        this.isId = element.getAttribute("IsID");
    }
    public String getVisibility(){
        return visibility;
    }
    public String getIsId(){
        return isId;
    }
    public void setVisibility(String visibility){
        this.visibility = visibility;
    }
    public void setIsId(String isId){
        this.isId = isId;
    }
    @Override
    public String toString(){
        return String.format("%s %s : %s%s %s",visibility, name, type, typeModifier, isId);
    }
}
