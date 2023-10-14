import client.ClientGUI;
import server.Server;

public class Main {

    public static void main(String[] args) {
        Server server = new Server();
        int count = 4;
        for (int i = 0; i < count; i++) {
            new ClientGUI(server);
        }
    }

}