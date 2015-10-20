package view;

import controller.ClientController;
import controller.ServerController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.UnknownHostException;

/**
 * User: huyti
 * Date: 20.10.15
 */
public class UI extends JFrame {
    private JRadioButton serverRadioButton;
    private JRadioButton clientRadioButton;
    private JTextField ipField;
    private JTextField portField;
    private JButton runButton;
    private JLabel infoField;
    private JTextField fileField;
    private JTextField directoryField;
    private JButton pushButton;
    private JPanel mainPanel;
    private JLabel portLabel;
    private JLabel ipLabel;
    private JScrollPane scroll;
    private JLabel infoLabel;
    private JLabel directoryLabel;
    private JLabel fileLabel;
    private ServerController servContr = null;
    private ClientController clientContr = null;

    public UI() throws HeadlessException {
        super();
        setTitle("Socket file transfer");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(new Dimension(600, 400));
        setVisible(true);
        add(mainPanel);
        ButtonGroup bg = new ButtonGroup();
        serverRadioButton.setSelected(true);
        ipField.setEnabled(false);
        pushButton.setEnabled(false);
        fileField.setEnabled(false);
        bg.add(serverRadioButton);
        bg.add(clientRadioButton);
        serverRadioButton.addActionListener(radiobuttonListener);
        clientRadioButton.addActionListener(radiobuttonListener);
        runButton.addActionListener(runButtonActionListener);
        fileField.addActionListener(textFieldActionListener);
        directoryField.addActionListener(textFieldActionListener);
        infoField.setText("<html>");
        pushButton.addActionListener(pushButtonListener);
    }

    private ActionListener pushButtonListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            clientContr.pushFileOnServer();
        }
    };

    private ActionListener textFieldActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource().equals(directoryField)) servContr.setDirectory(directoryField.getText());
            if (e.getSource().equals(fileField)) clientContr.setFilePath(fileField.getText());
        }
    };

    private ActionListener runButtonActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (serverRadioButton.isSelected()) {
                setController("server");
                servContr.createServer(portField.getText());
                runButton.setEnabled(false);
                serverRadioButton.setEnabled(false);
                clientRadioButton.setEnabled(false);
            }
            if (clientRadioButton.isSelected()) {
                setController("client");
                try {
                    clientContr.createNewClient(ipField.getText(), portField.getText());
                    runButton.setEnabled(false);
                    serverRadioButton.setEnabled(false);
                    clientRadioButton.setEnabled(false);
                } catch (UnknownHostException e1) {
                    printMessage(e1.getMessage());
                }
            }
        }
    };

    private void setController(String type) {
        if (type.equals("server")) servContr = new ServerController(this);
        if (type.equals("client")) clientContr = new ClientController(this);
    }

    private ActionListener radiobuttonListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (serverRadioButton.isSelected()) {
                ipField.setEnabled(false);
                pushButton.setEnabled(false);
                fileField.setEnabled(false);
                directoryField.setEnabled(true);
            }
            if (clientRadioButton.isSelected()) {
                ipField.setEnabled(true);
                pushButton.setEnabled(true);
                fileField.setEnabled(true);
                directoryField.setEnabled(false);
            }
        }
    };

    public void printMessage(String message) {
        infoField.setText(infoField.getText() + "<br>" + message);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new UI();
            }
        });
    }
}
