package Generator;

import Generator.Models.Attribute;
import Generator.Models.Operation;
import Generator.Tools.BackendClassCreator;
import Generator.Tools.DocumentParser;

public class Main {
    public static void main(String args[]){
        BackendClassCreator creator = new BackendClassCreator("project.xml");
        creator.createEntityFile();
        // DocumentParser parser = new DocumentParser("project.xml");
        // System.out.println(parser.getClassName());
        // System.out.println("Attributes : ");
        // for(Attribute attr : parser.getAttributes()){
        //     System.out.println(attr);
        // }
        // System.out.println("Operations : ");
        // for(Operation operation : parser.getOperations()){
        //     System.out.println(operation);
        // }
    }
}
