package org.ctridg;

import org.ctridg.Models.Entity;
import org.ctridg.Tools.*;

public class Main {
    public static void main(String args[]){
        DocumentParser parser = new DocumentParser("personcat.xml");
        for(Entity entity : parser.getEntities()){
            System.out.println(entity);
        }
        BackendClassCreator backendClassCreator = new BackendClassCreator(parser);
        backendClassCreator.create();
        FrontendClassCreator frontendClassCreator= new FrontendClassCreator(parser);
        frontendClassCreator.create();
    }
}
