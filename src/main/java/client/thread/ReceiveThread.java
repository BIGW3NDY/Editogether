package client.thread;

import client.service.ClientService;
import ot.operation.Change;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ReceiveThread implements Runnable {
    Socket socket;
    public ReceiveThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        InputStream inputStream = null;
        try {
            inputStream = socket.getInputStream();
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            Change change = (Change) objectInputStream.readObject();
            new ClientService(change.getIndex()-1);
            System.out.println("receive: " + change);
            ClientService.setSelfOperation(false);
            ClientService.changeApply(change);
            ClientService.setSelfOperation(true);
            ClientService.addNewChange(change, true);

            while(true){
                objectInputStream = new ObjectInputStream(inputStream);
                change = (Change) objectInputStream.readObject();
                ClientService.setSelfOperation(false);
                ClientService.changeApply(change);
                ClientService.setSelfOperation(true);
                ClientService.addNewChange(change, true);
                System.out.println("receive: " + change);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }
}
