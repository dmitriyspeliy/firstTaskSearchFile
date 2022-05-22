import java.io.*;
import java.util.*;

public class Search {
    public final static Map<String, String> dependsAndName = new HashMap<>();
    public final static String pathRoot = "main_folder";

    public static void main(String[] args) {
        getFiles(new File(pathRoot), dependsAndName);
        checkDepens(dependsAndName);
        sortedList(dependsAndName);

    }

    public static void getFiles(File rootFile, Map<String, String> dependsAndName) {
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
                                        dependsAndName.put("Зависимость файла номер " + depens + file.getAbsolutePath().
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

    public static void sortForDepens(Map<String, String> dependsAndName) {

    }

    public static void checkDepens(Map<String, String> dependsAndName) {
        String nameFile;
        String nameDepens;
        for (Map.Entry<String, String> map : dependsAndName.entrySet()) {
            if (!Objects.equals(map.getValue(), "Без зависимости")) {
                nameDepens = map.getValue().substring(map.getValue()
                                .lastIndexOf("/") + 1,
                        map.getValue().indexOf("’"));
                nameFile = map.getKey().substring(map.getKey()
                        .lastIndexOf("\\") + 1);
                if (nameDepens.equals(nameFile)) {
                    System.out.println("Ключ и значение одинаковы, зависимость ссылается на файл, где прописана," +
                            " будет циклическая зависимость.\nИмя файла : " + map.getKey() + ",зависимость : " + map.getValue());
                    break;
                }
                for (Map.Entry<String, String> mapAgain : dependsAndName.entrySet()) {
                    if (!Objects.equals(mapAgain.getValue(), "Без зависимости")) {
                        String nameDepensMapAgain = mapAgain.getValue().substring(mapAgain.getValue()
                                        .lastIndexOf("/") + 1,
                                mapAgain.getValue().indexOf("’"));
                        String nameFileMapAgain = mapAgain.getKey().substring(mapAgain.getKey()
                                .lastIndexOf("\\") + 1);
                        if (nameFile.equals(nameDepensMapAgain) && nameFileMapAgain.equals(nameDepens)) {
                            System.out.println("Файлы " + map.getKey() + " ссылаются друг на друга " + map.getValue());
                        }
                    }

                }
            }
        }
    }

    public static void sortedList(Map<String, String> dependsAndName) {
        List<Map<String, String>> sortedList = new ArrayList<>();
        for (Map.Entry<String, String> map : dependsAndName.entrySet()) {
            sortedList.add(Map.of(map.getKey(), map.getValue()));
        }
        List<String> depens = new ArrayList<>(dependsAndName.values());
        List<String> name = new ArrayList<>(dependsAndName.keySet());
        System.out.println(name);
        System.out.println(depens);

        System.out.println("<----------------------------------------------------------------->");
        for (int i = 0; i < name.size(); i++) {
            for (int j = 0; j < depens.size(); j++) {
                String nameFile = name.get(i).substring(name.get(i)
                        .lastIndexOf("\\") + 1);
                String depensN = depens.get(j).substring(depens.get(j)
                                .lastIndexOf("/") + 1,
                        depens.get(j).lastIndexOf("t") + 1);
                if(depensN.equals(nameFile) && j<i){
                    String tmp1 = name.get(j);
                    String tmp2 = name.get(i);
                    String dp1 = depens.get(j);
                    String dp2 = depens.get(i);
                    name.set(i,tmp1);
                    name.set(j,tmp2);
                    depens.set(i,dp1);
                    depens.set(j,dp2);
                }
            }
        }

        System.out.println(name);
    }

}
