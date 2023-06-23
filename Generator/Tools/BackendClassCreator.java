package Generator.Tools;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import Generator.Models.Attribute;

public class BackendClassCreator {

    DocumentParser parser;
    Map<String, String> dataTypeMap;

    public BackendClassCreator(String filename) {
        parser = new DocumentParser(filename);
        dataTypeMap = new HashMap<String, String>();
        dataTypeMap.put("string", "string");
        dataTypeMap.put("long", "long");
        dataTypeMap.put("int", "int");
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

    public void copyServerFiles() {
        File from = new File("./template/server");
        File to = new File("./output/server");
        // wiping the target clean
        deleteFolder(to);
        // copying the source to target
        try {
            copyDir(from.toPath(), to.toPath());
            System.out.println("Copied whole directory successfully.");
        } catch (IOException ex) {
            System.out.println(ex.getLocalizedMessage());
        }
    }

    public void createEntityFile() {
        String className = parser.getClassName();
        List<Attribute> attributes = parser.getAttributes();
        List<String> lines = new ArrayList<String>();

        //reading the template file
        try {
            BufferedReader reader = new BufferedReader(new FileReader(
                    "./template/server/data-provider/src/main/java/com/example/dataprovider/models/Model.java"));
            String line = reader.readLine();
            while (line != null) {
                lines.add(line);
                line = reader.readLine();
            }
        } catch (Exception e) {
            System.out.println("error : " + e.getMessage());
        }

        //wiping the existing file in target
        File currentFile = new File("./output/server/data-provider/src/main/java/com/example/dataprovider/models/Model.java");
        currentFile.delete();

        //writing the new file
        try {
            File file = new File(String.format("./output/server/data-provider/src/main/java/com/example/dataprovider/models/%s.java", className));
            FileWriter writer = new FileWriter(file);
            for (int i = 0; i < 8; i++) {
                writer.write(lines.get(i));
                writer.write("\n");
            }
            writer.write(String.format(lines.get(9), className.toLowerCase()));
            writer.write("\n");
            writer.write(String.format(lines.get(10), className));
            for (Attribute attribute : attributes) {
                if (attribute.getIsId().equals("true")) {
                    writer.write("\n\t@Id\n\t@GeneratedValue\n");
                }
                writer.write(
                        String.format(
                                "\tprivate %s %s;\n",
                                dataTypeMap.get(attribute.getType()),
                                attribute.getName()));
            }
            writer.write("\n}\n");
            writer.close();
        } catch (Exception e) {
            System.out.println("error : " + e.getMessage());
        }
    }
}
