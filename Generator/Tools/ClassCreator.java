package Generator.Tools;

import java.io.*;
import java.util.List;

import Generator.Models.Attribute;

public class ClassCreator {
    private String filename;
    DocumentParser parser ;

    public ClassCreator(String filename){
        this.filename = filename;
        parser = new DocumentParser(filename);
    }

    public void createEntityFile(){
        String className = parser.getClassName();
        List<Attribute> attributes = parser.getAttributes();
        FileWriter fileWriter = null;
        File file = null;
        try{
            file = new File(String.format("%s.java"));
            fileWriter = new FileWriter(file);
            fileWriter.write("Hello");
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}
