package server.thread;

import ot.operation.Change;
import ot.operation.Operation;
import server.service.ServerService;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class SendThread implements Runnable {
    private ServerService serverService;
    private List<ReceiveThread> clientList;

    public SendThread(ServerService serverService, List<ReceiveThread> clientList) {
        this.serverService = serverService;
        this.clientList = clientList;
    }

    @Override
    public void run() {
        while(true) {
            while (!serverService.hasNoChange()) {
                Operation operation = serverService.getNextChange();

                for (ReceiveThread client : clientList) {
                    if (client.getClientIndex() != operation.getClientIndex()) {
                        Socket socket = client.getSocket();
                        Change change = operation.getChange();
                        if (socket.isClosed()) {
                            clientList.remove(client.getClientIndex() - 1);
                            continue;
                        }
                        try {
                            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                            objectOutputStream.writeObject(change);
                            objectOutputStream.flush();
                            System.out.println("send:" + change);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

    }
}
