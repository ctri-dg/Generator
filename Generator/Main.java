package Generator;

import Generator.Tools.BackendClassCreator;
import Generator.Tools.FrontendClassCreator;

public class Main {
    public static void main(String args[]){
        BackendClassCreator creator = new BackendClassCreator("project.xml");
        creator.copyServerFiles();
        creator.createEntityFile();
        creator.createRepositoryFile();
        creator.createRequestFiles();
        creator.createControllerFile();
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
        FrontendClassCreator creator2 = new FrontendClassCreator("project.xml");
        creator2.copyClientFiles();
        creator2.createModel();
        creator2.createCreatePage();
    }
}
