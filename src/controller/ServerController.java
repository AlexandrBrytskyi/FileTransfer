package controller;

import model.Server;
import view.UI;

/**
 * User: huyti
 * Date: 20.10.15
 */
public class ServerController {
    private Server server;
    private UI ui;

    public ServerController(UI ui) {
        this.ui = ui;
    }

    public void createServer(String port) {
        server = new Server(port, this);
    }

    public void sendMessageToUI(String message) {
        ui.printMessage(message);
    }

    public void setDirectory(String directory) {
        server.setDirectoryToSaveFiles(directory);
    }
}
