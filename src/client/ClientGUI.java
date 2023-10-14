package client;

import repository.Authentication;
import server.Server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;

public class ClientGUI extends JFrame implements ClientView {
    private static final int WINDOW_WIDTH = 500;
    private static final int WINDOW_HEIGHT = 500;
    private static int WINDOW_COUNT = 0;
    private final Client client;
    String nameWindow = "Chat Client";
    String login;
    public int widthButtonSend = 80;
    public int windowOffset = 20; // Offset between windows
    Authentication auth;
    JTextArea textArea;
    JTextArea textFieldFromKeyboard;
    JPanel northPanel;


    public ClientGUI(Server server) {
        this.client = new Client(this, server);
        auth = new Authentication();

        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        screenLocator();
        setTitle(nameWindow);

        add(northButton(), BorderLayout.NORTH);
        add(textField(), BorderLayout.CENTER);
        add(sendTextField(), BorderLayout.SOUTH);
        setVisible(true);
    }

    private void screenLocator() {
        int x = (WINDOW_COUNT % 3) * (WINDOW_WIDTH + windowOffset);
        int y = (WINDOW_COUNT / 3) * (WINDOW_HEIGHT + windowOffset);
        setLocation(x, y);
        WINDOW_COUNT++;
    }

    @Override
    public void showMessage(String text) {
        appendLog(text);
    }

    @Override
    public void disconnectFromServer() {
        client.disconnect();
        northPanel.setVisible(true);
    }

    private void connectToServer() {
        if (client.connectToServer(login)) {
            northPanel.setVisible(false);
        }
    }

    public void sendMessageToClient() {
        client.sendMessage(textFieldFromKeyboard.getText());
    }

    private JPanel northButton() {
        northPanel = new JPanel(new BorderLayout());
        JButton btnAuth = new JButton("Authentication");
        JButton newUser = new JButton("New User");

        btnAuth.addActionListener(e -> {
            if (auth.findUser()) {
                login = auth.userName;
                showMessage("Приветствую тебя " + login);
                connectToServer();
            }
        });
        newUser.addActionListener(e -> {
            auth.newUser();
            login = auth.userName;
            showMessage("Приветствую тебя " + login);
            connectToServer();
        });

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
        buttonPanel.add(btnAuth);
        buttonPanel.add(newUser);

        northPanel.add(buttonPanel, BorderLayout.CENTER);

        return northPanel;
    }

    private JScrollPane textField() {
        textArea = new JTextArea();
        textArea.setLineWrap(true); // переносит текст если поле заполнено
        textArea.setEditable(false);  // неизменяемый текст
        textArea.setBackground(new Color(220, 220, 220));
        return new JScrollPane(textArea);
    }

    private void appendLog(String text) {
        textArea.append(text + System.lineSeparator());
    }

    private JPanel sendTextField() {
        JPanel panel = new JPanel(new BorderLayout());
        textFieldFromKeyboard = new JTextArea();
        textFieldFromKeyboard.setLineWrap(true); // переносит текст если поле заполнено
        textFieldFromKeyboard.setBackground(Color.orange);

        JButton btnSend = new JButton("Send");
        btnSend.addActionListener(e -> {
            sendMessageToClient();
            textFieldFromKeyboard.setText(""); // Очищаем поле
        });

        panel.add(textFieldFromKeyboard, BorderLayout.CENTER);
        panel.add(btnSend, BorderLayout.EAST);
        // Устанавливаем высоту кнопки, основываясь на высоте JTextArea в JScrollPane
        int preferredHeight = textFieldFromKeyboard.getPreferredSize().height;
        btnSend.setPreferredSize(new Dimension(widthButtonSend, preferredHeight));
        return panel;
    }

    @Override
    protected void processWindowEvent(WindowEvent e) {
        super.processWindowEvent(e);
        if (e.getID() == WindowEvent.WINDOW_CLOSING) {
            disconnectFromServer();
        }
    }

}