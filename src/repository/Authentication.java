package repository;

import client.User;

import javax.swing.*;

public class Authentication {
    private final String filePathAuth = "repository/Users.txt";
    public String userName;


    public void newUser() {
        User user = enterLoginPassword();
        new WriteUser(filePathAuth, user);
    }

    public boolean findUser() {
        int attempt = 2;
        while (attempt > 0) {
            User user = enterLoginPassword();
            if (checkLoginPassw(user)) {
                return true;
            } else {
                String attemptsLeft = String.valueOf(attempt-1);
                String infoStr = "Неверно имя пользователя или пароль" + System.lineSeparator()
                        + "У вас осталось: " + attemptsLeft + " попыток";
                JOptionPane.showMessageDialog(null, infoStr);
            }
            attempt--;
        }
        return false;
    }

    private boolean checkLoginPassw(User user) {
        if (user == null)return false;
        String name = user.getName();
        String password = user.getPassword();
        FindUser checkUser = new FindUser(filePathAuth, name, password);
        boolean check = checkUser.findUser;
        if (check){
            userName = checkUser.getName();
        }
        return check;
    }

    private User enterLoginPassword() {
        JTextField loginField = new JTextField();
        JPasswordField passwordField = new JPasswordField();

        Object[] message = {"Login:", loginField, "Password:", passwordField};

        int option = JOptionPane.showOptionDialog(null, message,
                "Enter Login Password", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.INFORMATION_MESSAGE, null, null, null);
        if (option == JOptionPane.OK_OPTION) {
            userName = loginField.getText();
            String password = new String(passwordField.getPassword());
            return new User(userName, password);
        } else {
            JOptionPane.showMessageDialog(null, "Неверно имя пользователя или пароль");
        }
        return null;
    }

}