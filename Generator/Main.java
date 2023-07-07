package Generator;

import Generator.Tools.*;

public class Main {
    public static void main(String args[]){
        DocumentParser parser = new DocumentParser("project.xml");
        BackendClassCreator backendClassCreator = new BackendClassCreator(parser);
        backendClassCreator.create();
        FrontendClassCreator frontendClassCreator= new FrontendClassCreator(parser);
        frontendClassCreator.create();
    }
}
