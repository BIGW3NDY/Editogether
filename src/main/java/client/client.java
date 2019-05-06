package client;

import client.service.ClientService;
import client.thread.ReceiveThread;
import client.thread.SendThread;
import client.view.MainFrame;

import javax.swing.*;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ThreadPoolExecutor;

public class client {
    public static void main(String[] args){
        try {
            Socket socket = new Socket("127.0.0.1", 9999);
            Thread sendThread = new Thread(new SendThread(socket));
            Thread receiveThred = new Thread(new ReceiveThread(socket));
            sendThread.start();
            receiveThred.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
