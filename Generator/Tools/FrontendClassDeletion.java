package Generator.Tools;

import java.io.File;

public class FrontendClassDeletion {
    
    public void deleteClassFiles() {
        String classPath = "./output/client/src/screens/Model/"; // Path to the directory containing the class files
        
        File classDirectory = new File(classPath);
        if (classDirectory.exists() && classDirectory.isDirectory()) {
            File[] classFiles = classDirectory.listFiles();
            if (classFiles != null) {
                for (File classFile : classFiles) {
                    if (classFile.isFile() && classFile.getName().endsWith(".jsx")) {
                        if (classFile.delete()) {
                            System.out.println("Deleted file: " + classFile.getName());
                        } else {
                            System.out.println("Failed to delete file: " + classFile.getName());
                        }
                    }
                }
            }
        } else {
            System.out.println("Invalid class directory path: " + classPath);
        }
    }
}
