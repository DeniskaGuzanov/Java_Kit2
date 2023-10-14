package repository;

public class Storage implements Repository {
    public String filePathLog = "repository/Log.txt";


    @Override
    public String getHistory() {
        return new ReadLog(filePathLog).readTxtFile();
    }

    @Override
    public void setHistory(String text) {
        new SaveToFile(filePathLog).appendToFile(text);
    }

}