package client.thread;

import client.service.ClientService;
import ot.operation.Change;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SendThread implements Runnable {
    Socket socket;

    public SendThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        while(true){
            while(!ClientService.hasNoChange()){
                Change change = ClientService.getNextChange();

                try {
                    //通过对象流向服务端传输数据
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                    objectOutputStream.writeObject(change);
                    objectOutputStream.flush();
                    System.out.println("send:" + change);
                } catch (IOException e){
                    e.printStackTrace();
                }
                //console log
                System.out.println(change);
            }
        }
    }
}
