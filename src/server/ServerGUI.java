package server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;


public class ServerGUI extends JFrame implements ServerView {
    public static final int WINDOW_WIDTH = 500;
    public static final int WINDOW_HEIGHT = 500;
    public Server server;
    public String nameWindow = "Chat server";
    JTextArea textArea;


    public ServerGUI(Server server) {
        this.server = server;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        ScreenLocator();
        setTitle(nameWindow);
        setResizable(false);

        add(textField(), BorderLayout.CENTER);
        add(southPanel(), BorderLayout.SOUTH);

        setVisible(true);
    }


    private void ScreenLocator() {
        int screenWidth = getToolkit().getScreenSize().width;
        int screenHeight = getToolkit().getScreenSize().height;
        // find coordinate window to set
        int x = (screenWidth - getWidth()) / 2;
        int y = (screenHeight - getHeight()) / 2;
        setLocation(x, y);
    }

    private JScrollPane textField() {
        textArea = new JTextArea();
        textArea.setLineWrap(true); // переносит текст если поле заполнено
        textArea.setEditable(false);  // неизменяемый текст
        JScrollPane scrollPane = new JScrollPane(textArea);
        textArea.setBackground(new Color(220, 220, 220));
        return scrollPane;
    }

    private JPanel southPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JButton btnStart = new JButton("Start");
        btnStart.addActionListener(e -> {
            if (server.isServerWorking()) {
                appendLog("Сервер уже был запущен");
            } else {
                server.setServerWorking(true);
                appendLog("Сервер запущен!");
            }
        });
        JButton btnStop = new JButton("Stop");
        btnStop.addActionListener(e -> {
            if (!server.isServerWorking()) {
                appendLog("Сервер уже был остановлен");
            } else {
                server.setServerWorking(false);
                appendLog("Сервер остановлен!");
                disconnectUsers();
            }
        });
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
        buttonPanel.add(btnStart);
        buttonPanel.add(btnStop);

        panel.add(buttonPanel, BorderLayout.CENTER);
        return panel;
    }

    @Override
    public void appendLog(String text) {
        textArea.append(text + System.lineSeparator());
    }

    @Override
    protected void processWindowEvent(WindowEvent e) {
        super.processWindowEvent(e);
        if (e.getID() == WindowEvent.WINDOW_CLOSING)
            disconnectUsers();
    }

    public void disconnectUsers() {
        server.disconnectAllUsers();
    }

}