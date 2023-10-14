package repository;

import java.io.FileWriter;

public class SaveToFile {
    public String path;


    public SaveToFile(String path) {
        this.path = path;
    }

    public void appendToFile(String text) {
        try (FileWriter writer = new FileWriter(path, true)) {
            writer.write(text);
            writer.write(System.lineSeparator());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}