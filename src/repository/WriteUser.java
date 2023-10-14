package repository;

import client.User;

public class WriteUser {
    public String path;
    User user;

    public WriteUser(String path, User user) {
        this.path = path;
        this.user = user;
        createTextToSave();
    }

    private void createTextToSave() {
        String name = user.getName();
        String password = user.getPassword();
        String result = name + " " + password;
        new SaveToFile(path).appendToFile(result);
    }

}