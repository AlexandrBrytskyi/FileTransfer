package model;

import controller.ClientController;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.file.Files;

/**
 * User: huyti
 * Date: 20.10.15
 */
public class Client {
    private ObjectOutputStream os;
    private FileInputStream is;
    private ClientController cont = null;
    private String IP;
    private String port;
    private Socket socket;
    String filePath;


    public Client(String IP, String port, ClientController cont) throws UnknownHostException {
        this.cont = cont;
        this.IP = IP;
        this.port = port;
        try {
            socket = new Socket(InetAddress.getByName(IP), Integer.valueOf(port));
            sendMessageToUI("its ok, i`ve connected");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sentOnServer() {
        try {
            os = new ObjectOutputStream(socket.getOutputStream());
            is = new FileInputStream(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        File file = new File(filePath);
        sendMessageToUI("I`ll try now to push file on server");
        try {
            os.writeObject(new ObjectToBeTrans(file.getName(), takeBytes(file)));
            sendMessageToUI("Success! I pushed file on server");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private byte[] takeBytes(File file) throws IOException {
        return Files.readAllBytes(file.toPath());
    }

    private void sendMessageToUI(String message) {
        cont.sendMessageToUI(message);
    }

    public ClientController getCont() {
        return cont;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
        sendMessageToUI("File set as: " + filePath);
    }


}
