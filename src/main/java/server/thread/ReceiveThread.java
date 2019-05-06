package server.thread;

import ot.operation.Change;
import ot.operation.Insert;
import ot.operation.Operation;
import server.service.ServerService;
import text.Text;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;

public class ReceiveThread implements Runnable {
    Socket socket;
    int clientIndex;
    ServerService serverService;
    public ReceiveThread(Socket socket, int index, ServerService serverService) {
        this.socket = socket;
        this.clientIndex = index;
        this.serverService = serverService;
    }

    @Override
    public void run() {
        //首先将服务器端维护的文档内容text同步到连接上的客户端上
        //并且维护消息的index
        try {
            Change change = new Insert(0, serverService.getText().getTextBuffer());
            change.setIndex(serverService.getListIndex());
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(change);
            objectOutputStream.flush();
        }catch (IOException e){
            e.printStackTrace();
        }

        //不断接受客户端发来的消息
        InputStream inputStream = null;
        try {
            inputStream = socket.getInputStream();
            while(true){
                ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
                Change change = (Change) objectInputStream.readObject();
                Operation operation = new Operation(change, clientIndex);
                System.out.println("receive: " + operation);
                serverService.addOperation(operation);

            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public Socket getSocket() {
        return socket;
    }

    public int getClientIndex() {
        return clientIndex;
    }
}
