package org.ctridg.Tools;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import org.ctridg.Models.Attribute;
import org.ctridg.Models.Operation;
import org.ctridg.Models.Parameter;

public class BackendClassCreator {

    DocumentParser parser;
    Map<String, String> dataTypeMap;
    Map<String, String> primitiveClassMap;

    public BackendClassCreator(DocumentParser parser) {
        this.parser = parser;
        dataTypeMap = new HashMap<String, String>();
        dataTypeMap.put("string", "String");
        dataTypeMap.put("long", "long");
        dataTypeMap.put("int", "int");
        primitiveClassMap = new HashMap<String, String>();
        primitiveClassMap.put("string", "String");
        primitiveClassMap.put("long", "Long");
        primitiveClassMap.put("int", "Int");
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

    private void copyServerFiles() {
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

    private void createEntityFile() {
        String className = parser.getClassName();
        List<Attribute> attributes = parser.getAttributes();
        List<String> lines = new ArrayList<String>();

        // reading the template file
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

        // wiping the existing file in target
        File currentFile = new File(
                "./output/server/data-provider/src/main/java/com/example/dataprovider/models/Model.java");
        currentFile.delete();

        // writing the new file
        try {
            File file = new File(String.format(
                    "./output/server/data-provider/src/main/java/com/example/dataprovider/models/%s.java", className));
            FileWriter writer = new FileWriter(file);
            for (int i = 0; i < 9; i++) {
                writer.write(lines.get(i));
                writer.write("\n");
            }
            writer.write(String.format(lines.get(9), className.toLowerCase()));
            writer.write("\n");
            writer.write(String.format(lines.get(10), className));
            for (Attribute attribute : attributes) {
                if (attribute.getIsId().equals("true")) {
                    writer.write("\n\t@Id\n");
                }
                if (attribute.getIsAutogenerated().equals("true")) {
                    writer.write("\t@GeneratedValue\n");
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

    private void createRepositoryFile() {
        String className = parser.getClassName();
        List<Operation> operations = parser.getOperations();
        List<Attribute> attributes = parser.getAttributes();
        List<String> lines = new ArrayList<String>();

        // reading the template file
        try {
            BufferedReader reader = new BufferedReader(new FileReader(
                    "./template/server/data-provider/src/main/java/com/example/dataprovider/repositories/Repository.java"));
            String line = reader.readLine();
            while (line != null) {
                lines.add(line);
                line = reader.readLine();
            }
        } catch (Exception e) {
            System.out.println("error : " + e.getMessage());
        }

        // wiping the existing file in target
        File currentFile = new File(
                "./output/server/data-provider/src/main/java/com/example/dataprovider/repositories/Repository.java");
        currentFile.delete();

        // writing the new file
        try {
            File file = new File(String.format(
                    "./output/server/data-provider/src/main/java/com/example/dataprovider/repositories/%sRepository.java",
                    className));
            FileWriter writer = new FileWriter(file);
            for (int i = 0; i < 6; i++) {
                writer.write(lines.get(i));
                writer.write("\n");
            }
            Attribute idAttribute = null;
            for (Attribute attr : attributes) {
                if (attr.getIsId().equals("true")) {
                    idAttribute = attr;
                    break;
                }
            }
            writer.write(
                    String.format(lines.get(6), className, className, primitiveClassMap.get(idAttribute.getType())));
            writer.write("\n");

            for (Operation operation : operations) {
                String returnType = operation.getReturnType();
                if (operation.getTypeModifier().equals("[]")) {
                    returnType = String.format("List<%s>", operation.getReturnType());
                }

                String line = String.format("\t%s %s(", returnType, operation.getName());
                for (int i = 0; i < operation.getParameters().size(); i++) {
                    Parameter param = operation.getParameters().get(i);
                    if (i == operation.getParameters().size() - 1) {
                        line += String.format("%s %s", dataTypeMap.get(param.getType()), param.getName());
                    } else
                        line += String.format("%s %s, ", dataTypeMap.get(param.getType()), param.getName());
                }
                line += ");\n";
                writer.write(line);
            }
            writer.write("\n}\n");
            writer.close();
        } catch (Exception e) {
            System.out.println("error : " + e.getMessage());
        }
    }

    private void createRequestFile(List<Parameter> params) {
        List<String> lines = new ArrayList<String>();
        // reading the template file
        try {
            BufferedReader reader = new BufferedReader(new FileReader(
                    "./template/server/data-provider/src/main/java/com/example/dataprovider/requests/Request.java"));
            String line = reader.readLine();
            while (line != null) {
                lines.add(line);
                line = reader.readLine();
            }
        } catch (Exception e) {
            System.out.println("error : " + e.getMessage());
        }

        // wiping the existing file in target
        File currentFile = new File(
                "./output/server/data-provider/src/main/java/com/example/dataprovider/requests/Request.java");
        currentFile.delete();

        // writing the new file
        String reqName = "";
        for (Parameter param : params) {
            reqName += param.getName().substring(0, 1).toUpperCase() + param.getName().substring(1).toLowerCase();
        }
        try {
            File file = new File(String.format(
                    "./output/server/data-provider/src/main/java/com/example/dataprovider/requests/%sRequest.java",
                    reqName));
            FileWriter writer = new FileWriter(file);
            for (int i = 0; i < 13; i++) {
                writer.write(lines.get(i));
                writer.write("\n");
            }
            writer.write(String.format(lines.get(13), reqName));
            writer.write("\n");

            for (Parameter param : params) {
                writer.write(String.format("\t%s %s;\n", dataTypeMap.get(param.getType()), param.getName()));
            }

            writer.write("}\n");
            writer.close();
        } catch (Exception e) {
            System.out.println("error : " + e.getMessage());
        }
    }

    private void createRequestFiles() {
        List<Operation> operations = parser.getOperations();
        for (Operation operation : operations) {
            createRequestFile(operation.getParameters());
        }
    }

    private String toCamelCase(String a) {
        char ch[] = a.toCharArray();
        int n = a.length();
        ch[0] = Character.toUpperCase(ch[0]);
        return String.valueOf(ch, 0, n);
    }

    private void createControllerFile() {
        String className = parser.getClassName();
        List<Attribute> attributes = parser.getAttributes();
        List<String> lines = new ArrayList<String>();
        List<Operation> operations = parser.getOperations();
        // reading the template file
        try {
            BufferedReader reader = new BufferedReader(new FileReader(
                    "./template/server/data-provider/src/main/java/com/example/dataprovider/controllers/Controller.java"));
            String line = reader.readLine();
            while (line != null) {
                lines.add(line);
                line = reader.readLine();
            }
        } catch (Exception e) {
            System.out.println("error : " + e.getMessage());
        }

        // wiping the existing file in target
        File currentFile = new File(
                "./output/server/data-provider/src/main/java/com/example/dataprovider/controllers/Controller.java");
        currentFile.delete();

        // writing the new file

        try {
            File file = new File(String.format(
                    "./output/server/data-provider/src/main/java/com/example/dataprovider/controllers/%sController.java",
                    className));
            FileWriter writer = new FileWriter(file);
            for (int i = 0; i < 13; i++) {
                writer.write(lines.get(i));
                writer.write("\n");
            }
            writer.write(String.format(lines.get(13), className));
            writer.write("\n");
            writer.write("\n\t@Autowired\n");
            String reponame = "resourceRepository";
            String elementType = "";
            writer.write(String.format("\tprivate %sRepository %s;\n", className, reponame));
            writer.write("\n\t@GetMapping\n");
            String returnType = "";
            for (Operation operation : operations) {
                returnType = operation.getReturnType();
                if (operation.getTypeModifier().equals("[]")) {
                    elementType = operation.getReturnType();
                    returnType = String.format("List<%s>", elementType);
                    break;
                }
            }

            writer.write(String.format("\t\tprivate %s getAllBranches(){\n", returnType));
            writer.write(String.format("\t\t\treturn %s.findAll();\n}\n", reponame));

            writer.write(String.format("\n\t@PostMapping\n"));
            writer.write(String.format("\t\tprivate %s create%s(@RequestBody %s %s){\n", elementType, elementType,
                    elementType, elementType.toLowerCase()));
            writer.write(String.format("\t\t\treturn %s.save(%s);\n}\n", reponame, elementType.toLowerCase()));

            writer.write(String.format("\n\t@PutMapping\n"));
            writer.write(String.format("\t\tprivate %s update%s(@RequestBody %s %s){\n", elementType, elementType,
                    elementType, elementType.toLowerCase()));
            writer.write(String.format("\t\t\treturn %s.save(%s);\n}\n", reponame, elementType.toLowerCase()));

            for (Attribute attribute : attributes) {
                String aName = attribute.getName();
                String aType = attribute.getType();
                if (attribute.getIsId().equals("true")) {
                    writer.write(String.format("\n\t@DeleteMapping(\"/{%s}\")\n", aName.toLowerCase()));
                    writer.write(String.format("\t\tprivate void delete%sBy%s(@PathVariable %s %s){\n", elementType,
                            aName, aType, aName));
                    writer.write(String.format("\t\t\t%s.deleteById(%s);\n}\n", reponame, aName.toLowerCase()));

                    writer.write(String.format("\n\t@GetMapping(\"/{%s}\")\n", aName));
                    writer.write(String.format("\t\tprivate %s get%sBy%s(@PathVariable %s %s){\n", elementType,
                            elementType, toCamelCase(aName), aType, aName));
                    writer.write(String.format("\t\t\tOptional<%s> %s = %s.findById(%s);\n\n", elementType,
                            elementType.toLowerCase(), reponame, aName));
                    writer.write(String.format("\t\t\tif(%s.isEmpty()){\n", elementType.toLowerCase()));
                    writer.write(
                            String.format("\t\t\t\tthrow new ResourceNotFoundException(\"Invalid Branch Id\");\n}\n"));
                    writer.write(String.format("\t\t\treturn %s.get();\n}", elementType.toLowerCase()));
                }
            }

            for (Operation operation : operations) {
                 String concatParam="";
                for (int i = 0; i < operation.getParameters().size(); i++) {
                    Parameter param = operation.getParameters().get(i);
                    concatParam += toCamelCase(param.getName());
                }
                writer.write(String.format("\n\t@PostMapping(\"/%s\")\n",concatParam.toLowerCase()));
                writer.write(String.format("\t\tprivate %s get%sesBy%s(@RequestBody %sRequest %sRequest){\n",returnType,elementType,toCamelCase(concatParam),toCamelCase(concatParam),concatParam.toLowerCase()));
                String totalParam="";
                    for (int i = 0; i < operation.getParameters().size(); i++) {
                 Parameter param = operation.getParameters().get(i);
                 if(i==operation.getParameters().size()-1)
                 {
                 totalParam+=String.format("%sRequest.get%s()",concatParam.toLowerCase(),toCamelCase(param.getName()));   
                 }
                else
            {
            totalParam+=String.format("%sRequest.get%s() ,",concatParam.toLowerCase(),toCamelCase(param.getName()));  }
        }
                
                writer.write(String.format("\t\t\treturn %s.%s(%s);\n}", reponame,operation.getName(),totalParam));    
            }

            writer.write("\n}\n");
            writer.close();
        } catch (Exception e) {
            System.out.println("error : " + e.getMessage());
        }
    }

    public void create(){
        copyServerFiles();
        createEntityFile();
        createRepositoryFile();
        createRequestFiles();
        createControllerFile();
    }
}
