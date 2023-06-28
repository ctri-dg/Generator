package Generator.Tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Generator.Models.Attribute;

public class FrontendClassCreator2 {
    DocumentParser parser;

    public FrontendClassCreator2(DocumentParser parser) {
        this.parser = parser;
    }

    public void createResultCard() {
        List<Attribute> attributes = parser.getAttributes();
        Attribute idAttribute = parser.getIdAttribute();

        List<String> lines = new ArrayList<String>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(
                    "./template/client/src/screens/Retrieve/ResultCard.jsx"));
            String line = reader.readLine();
            while (line != null) {
                lines.add(line);
                line = reader.readLine();
            }
            reader.close();
        } catch (Exception e) {
            System.out.println("error 1:" + e.getMessage());
        }

        File currentFile = new File("./output/client/src/screens/Retrieve/ResultCard.jsx");
        currentFile.delete();

        try {
            File file = new File("./output/client/src/screens/Retrieve/ResultCard.jsx");
            FileWriter writer = new FileWriter(file);

            int i = 0;
            int n = lines.size();
           
            i = writeTillBreak(i, n, lines, writer);
         
            String parameterLine = "\t";
            for (Attribute attribute : attributes) {
                parameterLine += String.format("%s, ", attribute.getName());
            }
            parameterLine += "\n";
            writer.write(parameterLine);

            i = writeTillBreak(i, n, lines, writer);

            writer.write(String.format(lines.get(i++), idAttribute.getName()));
            writer.write("\n");
            for(Attribute attribute : attributes){
                if(attribute.getName().equals(idAttribute.getName())){
                    continue;
                }
                writer.write(String.format(lines.get(i) + "\n", attribute.getName()));
            }
            i++;
            i = writeTillBreak(i, n, lines, writer);
            for(Attribute attribute : attributes){
                writer.write(String.format("\t\t\t\t%s : %s,\n", attribute.getName(), attribute.getName()));
            }
            i = writeTillBreak(i, n, lines, writer);
            writer.write(String.format(lines.get(i++)+"\n", idAttribute.getName()));
            i = writeTillBreak(i, n, lines, writer);
            writer.close();
        } catch (Exception e) {
            System.out.println("error 2: " + e.getMessage());
        }
    }

    public void createRetrieveFile(){
        String className = parser.getClassName();
        List<Attribute> attributes = parser.getAttributes();
        Attribute idAttribute = parser.getIdAttribute();

        List<String> lines = new ArrayList<String>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(
                    "./template/client/src/screens/Retrieve/Retrieve.jsx"));
            String line = reader.readLine();
            while (line != null) {
                lines.add(line);
                line = reader.readLine();
            }
            reader.close();
        } catch (Exception e) {
            System.out.println("error 3:" + e.getMessage());
        }

        File currentFile = new File("./output/client/src/screens/Retrieve/Retrieve.jsx");
        currentFile.delete();

        try {
            File file = new File("./output/client/src/screens/Retrieve/Retrieve.jsx");
            FileWriter writer = new FileWriter(file);

            int i = 0;
            int n = lines.size();
            
            i = writeTillBreak(i, n, lines, writer);
            writer.write(String.format(lines.get(i++) + "\n", idAttribute.getName()));
            i = writeTillBreak(i, n, lines, writer);
            writer.write(String.format(lines.get(i++) + "\n", idAttribute.getName()));
            i = writeTillBreak(i, n, lines, writer);
            writer.write(String.format(lines.get(i++) + "\n", idAttribute.getName(), idAttribute.getName()));
            for(String attr : parser.getSearchAttributes()){
                writer.write(String.format(lines.get(i) + "\n", attr, attr));
            }
            i++;
            i = writeTillBreak(i, n, lines, writer);
            for(Attribute attribute : attributes){
                writer.write(String.format(lines.get(i) + "\n", attribute.getName()));
            }
            i++;
            i = writeTillBreak(i, n, lines, writer);
            writer.write(String.format(lines.get(i++) + "\n", idAttribute.getName()));
            for(Attribute attribute : attributes){
                writer.write(String.format(lines.get(i) + "\n", attribute.getName(), attribute.getName()));
            }
            i++;
            i = writeTillBreak(i, n, lines, writer);
            writer.close();
        } catch (Exception e) {
            System.out.println("error 4: " + e.getMessage());
        }
    }

    private int writeTillBreak(int i, int n, List<String> lines, FileWriter writer) throws IOException{
        while (i < n && !lines.get(i).startsWith("//break")) {
            writer.write(lines.get(i++));
            writer.write("\n");
        }
        return ++i;
    }
}
