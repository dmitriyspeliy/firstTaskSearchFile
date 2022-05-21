import java.io.*;
import java.util.*;

public class Search {
    public final static Map<String, String> dependsAndName = new HashMap<>();
    public final static String pathRoot = "main_folder";

    public static void main(String[] args) {
        getFiles(new File(pathRoot), dependsAndName);
        for (Map.Entry<String, String> arr : dependsAndName.entrySet()) {
            System.out.println(arr.toString());
        }
    }

    private static void getFiles(File rootFile, Map<String, String> dependsAndName) {
        if (rootFile.isDirectory()) {
            File[] directoryFiles = rootFile.listFiles();
            if (directoryFiles != null) {
                for (File file : directoryFiles) {
                    if (file.isDirectory()) {
                        getFiles(file, dependsAndName);
                    } else {
                        if (file.getName().toLowerCase().endsWith(".txt")) {
                            int depens = 1;
                            try (BufferedReader obj = new BufferedReader(new FileReader(file.getAbsolutePath()))) {
                                ArrayList<String> arr = new ArrayList<>();
                                while (obj.ready()) {
                                    String fileString = obj.readLine();
                                    if (fileString.contains("require")) {
                                        dependsAndName.put("Зависимость файла номер "+depens+file.getAbsolutePath().
                                                substring(file.getAbsolutePath()
                                                        .indexOf(pathRoot) + pathRoot.length()), fileString);
                                        arr.add(fileString);
                                        depens++;
                                    }
                                }
                                if (arr.size() == 0) {
                                    dependsAndName.put(file.getAbsolutePath().
                                                    substring(file.getAbsolutePath()
                                                            .indexOf(pathRoot) + pathRoot.length()),
                                            "Без зависимости");
                                }
                                arr.clear();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }

                        }
                    }
                }
            }
        }
    }
}
