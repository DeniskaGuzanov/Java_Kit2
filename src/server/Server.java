package server;

import client.Client;
import repository.Authentication;
import repository.Storage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Server {
    private final Storage storage = new Storage();
    public ServerGUI serverGUI;
    Authentication auth;
    private final List<Client> clientList;
    public boolean isServerWorking;


    public Server() {
        clientList = new ArrayList<>();
        serverGUI = new ServerGUI(this);
        auth = new Authentication();
    }

    public boolean connectUser(Client client) {
        if (!isServerWorking) {
            return false;
        }
        clientList.add(client);
        return true;
    }

    public void disconnectUser(Client client) {
        if (client != null) {
            client.disconnect();
        }
    }

    public void disconnectAllUsers() {
        Iterator<Client> iterator = clientList.iterator();
        while (iterator.hasNext()) {
            Client client = iterator.next();
            disconnectUser(client);
            iterator.remove();
        }
    }

    public void sendMessage(String text) {
        if (isServerWorking) {
            answerAll(text);
            storage.setHistory(text);
        }
    }

    private void answerAll(String text) {
        for (Client client : clientList) {
            client.printText(text);
        }
    }

    public String getHistory() {
        return storage.getHistory();
    }

    public void setServerWorking(boolean serverWorking) {
        isServerWorking = serverWorking;
    }

    public boolean isServerWorking() {
        return isServerWorking;
    }

}