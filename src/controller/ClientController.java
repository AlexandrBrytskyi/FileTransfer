package controller;

import model.Client;
import view.UI;

import java.net.UnknownHostException;

/**
 * User: huyti
 * Date: 20.10.15
 */
public class ClientController {
    private UI ui;
    private Client client;


    public ClientController(UI ui) {
        this.ui = ui;
    }

    public void createNewClient(String ip, String port) throws UnknownHostException {
        this.client = new Client(ip, port, this);
    }

    public void setFilePath(String filePath) {
        client.setFilePath(filePath);
    }

    public void sendMessageToUI(String message) {
        ui.printMessage(message);
    }

    public void pushFileOnServer() {
        client.sentOnServer();
    }
}
