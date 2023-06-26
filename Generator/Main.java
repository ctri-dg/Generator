package Generator;

import Generator.Tools.BackendClassCreator;

public class Main {
    public static void main(String args[]){
        BackendClassCreator creator = new BackendClassCreator("project.xml");
        creator.copyServerFiles();
        creator.createEntityFile();
        creator.createRepositoryFile();
        creator.createRequestFiles();
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
