package repository;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class ReadLog {
    public String filePath;

    public ReadLog(String filePath) {
        this.filePath = filePath;
    }

    public String readTxtFile() {
        StringBuilder csvString = new StringBuilder();
        File file = new File(filePath);
        if (file.exists() && file.isFile()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    csvString.append(line).append(System.lineSeparator());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            new SaveToFile(filePath).appendToFile("");
        }
        return csvString.toString();
    }

}