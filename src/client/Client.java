package client;

import server.Server;

public class Client {

    private String name;
    private final ClientGUI clientView;
    private final Server server;
    private boolean connected;

    public Client(ClientGUI clientView, Server server) {
        this.clientView = clientView;
        this.server = server;
    }

    public boolean connectToServer(String name) {
        this.name = name;
        if (server.connectUser(this)) {
            printText("Вы успешно подключились!" + System.lineSeparator());
            connected = true;
            String log = server.getHistory();
            if (log != null) {
                printText(log);
            }
            return true;
        } else {
            printText("Подключение не удалось");
            return false;
        }
    }

    public void sendMessage(String message) {
        if (connected) {
            if (!message.isEmpty()) {
                server.sendMessage(name + ": " + message);
            }
        } else {
            printText("Нет подключения к серверу");
        }
    }

    public void disconnect() {
        if (connected) {
            connected = false;
            clientView.disconnectFromServer();
            server.disconnectUser(this);
            printText("Вы были отключены от сервера!");
        }
    }

    public void printText(String text) {
        clientView.showMessage(text);
    }

}