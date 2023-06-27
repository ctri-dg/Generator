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


import com.google.gson.JsonObject;

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

    // Existing code for copyDir and deleteFolder methods...

    public void createReadPage() {
        String className = parser.getClassName();
        className = className.substring(0, 1).toUpperCase() + className.substring(1).toLowerCase();
        List<Attribute> attributes = parser.getAttributes();
        List<String> lines = new ArrayList<String>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(
                    "./template/client/src/screens/Read/Read.jsx"));
            String line = reader.readLine();
            while (line != null) {
                lines.add(line);
                line = reader.readLine();
            }
            reader.close();
        } catch (Exception e) {
            System.out.println("error 12: " + e.getMessage());
        }

        File currentFile = new File("./output/client/src/screens/Read/Read.jsx");
        currentFile.delete();

        try {
            File file = new File(String.format("./output/client/src/screens/Read/Read.jsx"));
            FileWriter writer = new FileWriter(file);
            for (int i = 0; i < 32; i++) {
                writer.write(lines.get(i));
                writer.write("\n");
            }
            writer.write(String.format(lines.get(32), className));
            writer.write("\n");

            for (Attribute attribute : attributes) {
                writer.write(String.format(lines.get(33), attribute.getName()));
                writer.write("\n");
                writer.write(String.format(lines.get(34), attribute.getName()));
                writer.write("\n");
            }

            for (int i = 35; i < lines.size(); i++) {
                writer.write(lines.get(i));
                writer.write("\n");
            }
            writer.close();
        } catch (Exception e) {
            System.out.println("Error in writing the file read");
            System.out.println(e.getMessage());
        }
    }

    // Existing code for createModel, createCreatePage, and createUpdatePage methods...
}
