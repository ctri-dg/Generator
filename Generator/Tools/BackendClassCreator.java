package Generator.Tools;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import Generator.Models.Attribute;

public class BackendClassCreator {
    
    DocumentParser parser;

    public BackendClassCreator(String filename){
        parser = new DocumentParser(filename);
    }
    
    public void createEntityFile(){
        String className = parser.getClassName();
        List<Attribute> attributes = parser.getAttributes();
        List<String> lines = new ArrayList<String>();
        try{
            BufferedReader reader = new BufferedReader(new FileReader("./template/server/data-provider/src/main/java/com/example/dataprovider/models/Branch.java"));
            String line = reader.readLine();
            while(line != null){
                lines.add(line);
                System.out.println(line);
                line = reader.readLine();
            }
        }catch(Exception e){
            System.out.println("error : " + e.getMessage());
        }

        try{
            File file = new File(String.format("./output/%s.java", className));
            FileWriter writer = new FileWriter(file);
            for(int i=0;i<8;i++){
                writer.write(lines.get(i));
                writer.write("\n");
            }
            writer.write(String.format(lines.get(9), className.toLowerCase()));
            writer.write("\n");
            writer.write(String.format(lines.get(10), className));
            for(Attribute attribute : attributes){
                if(attribute.getIsId().equals("true")){
                    writer.write("@Id\n@GeneratedValue\n");
                }
                writer.write(
                    String.format(
                        "private %s %s;\n"
                        
                    )
                );
            }
            writer.write("\n}\n");
            writer.close();
        }catch(Exception e){
            System.out.println("error : " + e.getMessage());
        }
    }
}
