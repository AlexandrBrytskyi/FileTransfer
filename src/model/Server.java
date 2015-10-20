package model;

import controller.ServerController;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * User: huyti
 * Date: 20.10.15
 */
public class Server {

    private ServerSocket serverSocket;
    //    private ArrayList<Connection> sockets;
    private String port;
    private String directoryToSaveFiles;
    private ServerController cont;

    public Server(String port, ServerController cont) {
        this.cont = cont;
        this.port = port;
        try {
            serverSocket = new ServerSocket(Integer.valueOf(this.port));
            sendMessageToUI("server created");
            Thread thread = new Thread() {
                @Override
                public void run() {
                    while (true) {
                        try {
                            sendMessageToUI("waiting for client");
                            Socket socket = serverSocket.accept();
                            Connection con = new Connection(socket);
                            con.run();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            };
            thread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class Connection implements Runnable {
        ObjectInputStream input;
        FileOutputStream output;
        Socket socket;

        private Connection(Socket socket) throws IOException {
            this.socket = socket;


        }

        @Override
        public void run() {
            sendMessageToUI("Connected " + socket.toString());
            Thread tr = new Thread() {
                @Override
                public void run() {
                    while (true) {
                        try {
                            input = new ObjectInputStream(socket.getInputStream());
                            ObjectToBeTrans obj = (ObjectToBeTrans) input.readObject();
                            output = new FileOutputStream(directoryToSaveFiles + "/" + obj.getName());
                            sendMessageToUI(socket.toString() + " sent file " + obj.getName());
                            sendMessageToUI("trying to write file on " + directoryToSaveFiles);
                            output.write(obj.getBytes());
                            sendMessageToUI("success");

                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                }
            };
            tr.start();
        }
    }

    public void setDirectoryToSaveFiles(String directoryToSaveFiles) {
        this.directoryToSaveFiles = directoryToSaveFiles;
        sendMessageToUI("Directory was set as: " + directoryToSaveFiles);
    }

    public void setCont(ServerController cont) {
        this.cont = cont;
    }

    private void sendMessageToUI(String message) {
        cont.sendMessageToUI(message);
    }
}
