import java.io.*;
import java.util.*;

public class Search {
    public final static Map<String, String> dependsAndName = new HashMap<>();
    public final static String pathRoot = "main_folder";

    public static void main(String[] args) {
        getFiles(new File(pathRoot), dependsAndName);
        checkDepens(dependsAndName);
        readAndWrite(sortedList(dependsAndName));

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
                try {
                    if (nameDepens.equals(nameFile)) {
                        throw new MyExeption1(map.getKey(),map.getValue());
                    }
                }catch (MyExeption1 e){
                    System.out.println("Exception: "+e);
                    return;
                }

                for (Map.Entry<String, String> mapAgain : dependsAndName.entrySet()) {
                    if (!Objects.equals(mapAgain.getValue(), "Без зависимости")) {
                        String nameDepensMapAgain = mapAgain.getValue().substring(mapAgain.getValue()
                                        .lastIndexOf("/") + 1,
                                mapAgain.getValue().indexOf("’"));
                        String nameFileMapAgain = mapAgain.getKey().substring(mapAgain.getKey()
                                .lastIndexOf("\\") + 1);
                        try {
                            if (nameFile.equals(nameDepensMapAgain) && nameFileMapAgain.equals(nameDepens)) {
                                throw new MyExeption2(map.getKey(),map.getValue());
                            }
                        } catch (MyExeption2 e) {
                            System.out.println("Exception: "+e);
                        }

                    }

                }
            }
        }
    }

    public static List<String> sortedList(Map<String, String> dependsAndName) {
        List<String> depens = new ArrayList<>(dependsAndName.values());
        List<String> name = new ArrayList<>(dependsAndName.keySet());
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
        return name;
    }

    public static void readAndWrite(List<String> arr) {
        for(String nameFile : arr){
            String nameFileWithSub = null;
            if(nameFile.startsWith("Зависимость")){
                nameFileWithSub = nameFile.substring(nameFile.indexOf("\\"));
            }else{
                nameFileWithSub = nameFile;
            }

            try(BufferedReader reader = new BufferedReader(new FileReader(pathRoot+"\\"
                    +nameFileWithSub));
            BufferedWriter writer = new BufferedWriter(new FileWriter("main_folder/mainFile",true))) {
                String lineR;
                writer.write(nameFileWithSub + "\n");
                writer.write("\n");
                while ((lineR = reader.readLine()) != null) {
                    writer.write(lineR + "\n");
                }
                writer.write("\n");
            }catch (IOException e){
                e.printStackTrace();
            }
        }

    }

}
