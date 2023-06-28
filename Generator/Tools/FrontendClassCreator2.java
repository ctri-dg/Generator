package Generator.Tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
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
        String className = parser.getClassName();
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
        
        try {
            File file = new File("./output/client/src/screens/Model/ModelDetailsClient.jsx");
            FileWriter writer = new FileWriter(file);

            writer.write(String.format(lines.get(0), className));
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

}
