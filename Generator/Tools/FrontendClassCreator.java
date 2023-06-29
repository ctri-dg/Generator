package Generator.Tools;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import Generator.Models.Attribute;
import Generator.Models.Operation;
import Generator.Models.Parameter;


// import com.google.gson.JsonObject;

public class FrontendClassCreator {

    DocumentParser parser;
    Map<String, String> inputDataTypeMap;

    public FrontendClassCreator(DocumentParser parser) {
        this.parser = parser;
        inputDataTypeMap = new HashMap<String, String>();
        inputDataTypeMap.put("string", "text");
        inputDataTypeMap.put("long", "number");
        inputDataTypeMap.put("int", "number");

    }

    public void create(){
        copyClientFiles();
        createModel();
        createCreatePage();
        createUpdatePage();
        createDeletePage();
        createResultCard();
        createRetrieveFile();
    }

    private void copyDir(Path src, Path dest) throws IOException {
        Files.walk(src)
                .forEach(source -> {
                    try {
                        Files.copy(source, dest.resolve(src.relativize(source)),
                                StandardCopyOption.REPLACE_EXISTING);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
    }

    private void deleteFolder(File file) {
        for (File subFile : file.listFiles()) {
            if (subFile.isDirectory()) {
                deleteFolder(subFile);
            } else {
                subFile.delete();
            }
        }
        file.delete();
    }

    private void copyClientFiles() {
        File from = new File("./template/client");
        File to = new File("./output/client");
        // wiping the target clean
        deleteFolder(to);
        // copying the source to target
        try {
            copyDir(from.toPath(), to.toPath());
            System.out.println("Copied Client directory successfully.");
        } catch (IOException ex) {

            System.out.println(ex.getLocalizedMessage());
        }
    }

    private void createModel() {
        String className = parser.getClassName();
        className = className.substring(0, 1).toUpperCase() + className.substring(1).toLowerCase();
        List<Attribute> attributes = parser.getAttributes();
        List<String> lines = new ArrayList<String>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(
                    "./template/client/src/screens/Model/ModelDetailsClient.jsx"));
            String line = reader.readLine();
            while (line != null) {
                lines.add(line);
                line = reader.readLine();
            }
            reader.close();
        } catch (Exception e) {
            System.out.println("error 8: " + e.getMessage());
        }

        File currentFile = new File("./output/client/src/screens/Model/ModelDetailsClient.jsx");
        
        currentFile.delete();
        try{
            File file = new File("./output/client/src/screens/Model/ModelDetailsClient.jsx");
            FileWriter writer = new FileWriter(file);
            
            writer.write(String.format(lines.get(0),className ));
            writer.write("\n");
            for (Attribute attribute : attributes) {
                if (attribute.getIsId().equals("false"))
                    writer.write(
                            String.format(
                                    "\t %s;\n",
                                    attribute.getName()));
            }
            writer.write(String.format("}\n export default %s;", className));
            writer.close();
        } catch (Exception e) {
            System.out.println("error 9: " + e.getMessage());
        }
    }

    private void createCreatePage() {
        String className = parser.getClassName();
        className = className.substring(0, 1).toUpperCase() + className.substring(1).toLowerCase();
        List<Attribute> attributes = parser.getAttributes();
        List<String> lines = new ArrayList<String>();
        List<String> inputLines = new ArrayList<String>();
        List<String> submitLines = new ArrayList<>();

        try {
            BufferedReader updatReader = new BufferedReader(new FileReader(
                    "./template/client/src/screens/Create/Create.jsx"));
            String line = updatReader.readLine();
            while (line != null) {
                lines.add(line);
                line = updatReader.readLine();
            }
            updatReader.close();
        } catch (Exception e) {
            System.out.println("error 10: " + e.getMessage());
        }
        try {
            BufferedReader InputReader = new BufferedReader(new FileReader(
                    "./Generator/Tools/FrontEndElements/inputEntity.txt"));
            String line = InputReader.readLine();
            while (line != null) {
                inputLines.add(line);
                line = InputReader.readLine();
            }
            InputReader.close();
        } catch (Exception e) {
            System.out.println("error 11: " + e.getMessage());
        }
        try {
            BufferedReader submitReader = new BufferedReader(new FileReader(
                    "./Generator/Tools/FrontEndElements/submitEntity.txt"));
            String line = submitReader.readLine();
            while (line != null) {
                submitLines.add(line);
                line = submitReader.readLine();
            }
            submitReader.close();
        } catch (Exception e) {
            System.out.println("error : " + e.getMessage());
        }
        File currentFile = new File("./output/client/src/screens/Create/Create.jsx");
        currentFile.delete();

        try {
            File file = new File(String.format("./output/client/src/screens/Create/Create.jsx"));
            FileWriter writer = new FileWriter(file);
            for (int i = 0; i < 27; i++) {
                writer.write(lines.get(i));
                writer.write("\n");
            }
            writer.write(String.format(lines.get(27),"resource")+"\n");//setting the url here 
            for(int i = 28;i<54;i++)
            {
                writer.write(lines.get(i) + "\n");
            }
            writer.write(String.format(lines.get(54),className));
            writer.write("\n");
            for (Attribute attribute : attributes)
            {
                if(attribute.getIsId().equals("false"))
                {
                    createInputEntry(inputLines, writer, attribute,className,false);//writing input entries
                }
            }
            for (int i = 55; i < lines.size(); i++) {
                writer.write(lines.get(i));
                writer.write("\n");// Submit button and remaining things
            }
            writer.close();
        }catch(Exception e){
            System.out.println("Error in writing the file create");
            System.out.println(e.getMessage()); 
        }
    }  
    private void createUpdatePage()
    {
        String className = parser.getClassName();
        className = className.substring(0, 1).toUpperCase() + className.substring(1).toLowerCase();
        List<Attribute> attributes = parser.getAttributes();
        List<String> lines = new ArrayList<String>();
        List<String> inputLines = new ArrayList<String>();

        try {
            BufferedReader updateReader = new BufferedReader(new FileReader(
                    "./template/client/src/screens/Update/Update.jsx"));
            String line = updateReader.readLine();
            while (line != null) {
                lines.add(line);
                line = updateReader.readLine();
            }
            updateReader.close();
        } catch (Exception e) {
            System.out.println("error 10: " + e.getMessage());
        }
        try {
            BufferedReader inputReader = new BufferedReader(new FileReader(
                    "./Generator/Tools/FrontEndElements/inputEntity.txt"));
            String line = inputReader.readLine();
            while (line != null) {
                inputLines.add(line);
                line = inputReader.readLine();
            }
            inputReader.close();
        } catch (Exception e) {
            System.out.println("error 11: " + e.getMessage());
        }
        File currentFile = new File("./output/client/src/screens/Update/Update.jsx");
        currentFile.delete();

        try {
            File file = new File(String.format("./output/client/src/screens/Update/Update.jsx"));
            FileWriter writer = new FileWriter(file);
            for (int i = 0; i < 60; i++) {
                if(i == 34)
                {
                    writer.write(String.format(lines.get(i),"resource")+'\n');// used for setting the url
                    continue;
                }
                writer.write(lines.get(i));
                writer.write("\n");
            }
            writer.write(String.format(lines.get(60),className));
            writer.write("\n");
            for (Attribute attribute : attributes)
            {
                Boolean readOnly = attribute.getIsId().equals("true");
                createInputEntry(inputLines, writer, attribute,className,readOnly);//writing input entries
            }
            for (int i = 61; i < 67; i++) {
                writer.write(lines.get(i));
                writer.write("\n");// Submit button and remaining things
            }
            writer.write(String.format(lines.get(67),className) + "\n");
            for(int i = 68;i<lines.size();i++)
            {
                writer.write(lines.get(i) + '\n');
            }
            writer.close();
        }catch(Exception e){
            System.out.println("Error in writing the file create");
            System.out.println(e.getMessage()); 
        }

    }
    private void createInputEntry(List<String> inlines,FileWriter writer,Attribute attribute,String className,Boolean readOnly)
    {
        try {
            int offset = 0;
            writer.write(inlines.get(offset++));//1
            writer.write("\n");
            writer.write(inlines.get(offset++));//2
            writer.write("\n");
            writer.write(String.format(inlines.get(offset++), className, attribute.getName()));//3
            writer.write("\n");
            for (int i = 0; i < 3; i++) {
                writer.write(inlines.get(offset++));//4 5 6
                writer.write("\n");
            }
            writer.write(String.format(inlines.get(offset++), inputDataTypeMap.get(attribute.getType())));
            writer.write("\n");// setting type
            writer.write(String.format(inlines.get(offset++), attribute.getName()));
            writer.write("\n");//setting name
            writer.write(String.format(inlines.get(offset++), attribute.getName()));
            writer.write("\n");//setting value for specific input
            writer.write(String.format(inlines.get(offset++), className, attribute.getName()));
            writer.write("\n");//setting place holder
            if(!readOnly)
            writer.write(String.format(inlines.get(offset++), "\n"));// for extra attributes currently none
            else
            {
                writer.write(String.format(inlines.get(offset++),"readOnly\n"));// for read only attributes
            }
            while (offset != inlines.size()) {
                writer.write(inlines.get(offset++));
                writer.write("\n");
            }
            
        }catch(Exception e){
            System.out.println("Error in writing the file create");
            System.out.println(e.getMessage());
        }
    }
    private void createDeletePage() {
        String className = parser.getClassName();
        className = className.substring(0, 1).toUpperCase() + className.substring(1).toLowerCase();
        List<String> lines = new ArrayList<String>();
        try {
            BufferedReader aReader = new BufferedReader(new FileReader(
                    "./template/client/src/screens/Delete/Delete.jsx"));
            String line = aReader.readLine();
            while (line != null) {
                lines.add(line);
                line = aReader.readLine();
            }
            aReader.close();
        } catch (Exception e) {
            System.out.println("error : " + e.getMessage());
        }
        
        File currentFile = new File("./output/client/src/screens/Delete/Delete.jsx");
        currentFile.delete();

        try {
            File file = new File(String.format("./output/client/src/screens/Delete/Delete.jsx"));
            FileWriter writer = new FileWriter(file);
           for (int i = 0; i < lines.size(); i++) {
            writer.write(lines.get(i));
            writer.write("\n");
           } 
            writer.close();
        }catch(Exception e){
            System.out.println("Error in writing the file delete");
            System.out.println(e.getMessage()); 
        }
    }


    private void createResultCard() {
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

    private void createRetrieveFile(){
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
