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

public class FrontendClassCreator {

    DocumentParser parser;
    Map<String, String> inputDataTypeMap;

    public FrontendClassCreator(String filename) {
        parser = new DocumentParser(filename);
        inputDataTypeMap = new HashMap<String, String>();
        inputDataTypeMap.put("string", "text");
        inputDataTypeMap.put("long", "number");
        inputDataTypeMap.put("int", "number");

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

    public void copyClientFiles() {
        File from = new File("./template/client");
        File to = new File("./output/client");
        // wiping the target clean
        deleteFolder(to);
        // copying the source to target
        try {
            copyDir(from.toPath(), to.toPath());
            System.out.println("Copied whole directory successfully.");
        } catch (IOException ex) {

            System.out.println(ex.getLocalizedMessage());
        } finally {
            System.out.println("Yes here is the problem ");
        }
    }

    public void createModel() {
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
        } catch (Exception e) {
            System.out.println("error 8: " + e.getMessage());
        }

        File currentFile = new File("./output/client/src/screens/Model/ModelDetailsClient.jsx");
        File currentFile = new File("./output/client/src/screens/Model/ModelDetailsClient.jsx");
        currentFile.delete();

        try {
            File file = new File(String.format("./output/client/src/screens/Model/ModelDetailsClient.jsx"));
        try{
            File file = new File("./output/client/src/screens/Model/ModelDetailsClient.jsx");
            FileWriter writer = new FileWriter(file);
            writer.write(String.format(lines.get(0), className));
            
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

    public void createCreatePage() {
        String className = parser.getClassName();
        className = className.substring(0, 1).toUpperCase() + className.substring(1).toLowerCase();
        List<Attribute> attributes = parser.getAttributes();
        List<String> lines = new ArrayList<String>();
        List<String> inputLines = new ArrayList<String>();
        List<String> submitLines = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(
                    "./template/client/src/screens/Create/create.jsx"));
            String line = reader.readLine();
            while (line != null) {
                lines.add(line);
                line = reader.readLine();
            }
        } catch (Exception e) {
            System.out.println("error 10: " + e.getMessage());
        }
        try {
            BufferedReader reader = new BufferedReader(new FileReader(
                    "./template/client/src/screens/Model/inputEntity.txt"));
            String line = reader.readLine();
            while (line != null) {
                inputLines.add(line);
                line = reader.readLine();
            }
        } catch (Exception e) {
            System.out.println("error 11: " + e.getMessage());
        }
        try {
            BufferedReader reader = new BufferedReader(new FileReader(
                    "./template/client/src/screens/Model/submitEntity.txt"));
            String line = reader.readLine();
            while (line != null) {
                submitLines.add(line);
                line = reader.readLine();
            }
        } catch (Exception e) {
            System.out.println("error : " + e.getMessage());
        }
        File currentFile = new File("./output/client/src/screens/Create/create.jsx");
        currentFile.delete();

        try {
            File file = new File(String.format("./output/client/src/screens/Create/create.jsx"));
            FileWriter writer = new FileWriter(file);
            for (int i = 0; i < 54; i++) {
                writer.write(lines.get(i));
                writer.write("\n");
            }
            write.write(String.format(lines.get(54),className));
            for (Attribute attribute : attributes)
            {
                if(attribute.getIsId().equals("false"))
                {
                    createInputEntry(inputLines, writer, attribute,className);
                }
            }
            for (int i = 54; i < lines.size(); i++) {
                writer.write(lines.get(i));
            }
            writer.close();
        }catch(Exception e){
            System.out.println("Error in writing the file create");
            System.out.println(e.getMessage()); 
        }
    }  

    private void createInputEntry(List<String> inlines,FileWriter writer,Attribute attribute,String className)
    {
        try {
            int offset = 0;
            writer.write(inlines.get(offset++));//1
            writer.write(inlines.get(offset++));//2
            writer.write(String.format(inlines.get(offset++), className, attribute.getName()));//3
            for (int i = 0; i < 3; i++) {
                writer.write(inlines.get(offset++));//4 5 6
            }
            writer.write(String.format(inlines.get(offset++), inputDataTypeMap.get(attribute.getType())));// setting type
            writer.write(String.format(inlines.get(offset++), attribute.getName()));//setting name
            writer.write(String.format(inlines.get(offset++), attribute.getName()));//setting value for specific input
            writer.write(String.format(inlines.get(offset++), className, attribute.getName()));
            while (offset != inlines.size()) {
                writer.write(inlines.get(offset++));
            }
            for(int i = 54; i<lines.size();i++)
            {
                writer.write(lines.get(i));
            }
            writer.close();
            
            
        }catch(Exception e){
            System.out.println("Error in writing the file create");
            System.out.println(e.getMessage());
        }
    }  


}
