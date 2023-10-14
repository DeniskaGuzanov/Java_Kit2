package repository;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class FindUser {
    public String filePath;
    private String name;
    private String password;
    public boolean findUser;

    public FindUser(String filePath, String nameToCheck, String passwordToCheck) {
        this.filePath = filePath;
        this.name = nameToCheck;
        this.password = passwordToCheck;

        readTxtFile();
    }

    public void readTxtFile() {
        File file = new File(filePath);
        if (file.exists() && file.isFile()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (checkName(line)) {
                        break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            new SaveToFile(filePath).appendToFile("");
        }
    }

    public boolean checkName(String nameLine) {
        String[] divideStr = nameLine.split(" ");
        if (name.equals(divideStr[0]) && password.equals(divideStr[1])) {
            name = divideStr[0];
            password = divideStr[1];
            findUser = true;
            return true;
        }
        return false;
    }

    public String getName() {
        return name;
    }

}