package Generator.Models;
import org.w3c.dom.*;


public class Parameter extends Field{
    public Parameter(Element element){
        super(element);
    }
    @Override
    public String toString(){
        return String.format("%s : %s%s", name, type, typeModifier);
    }
}
