package server;

import server.thread.SendThread;
import server.service.ServerService;
import server.thread.ReceiveThread;
import text.Text;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class server {
    static ServerService serverService = new ServerService();
    public static void main(String[] args) {
        // 存储连接上的客户端线程
        List<ReceiveThread> clientThreadList = new ArrayList<>();
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        // 使用线程池
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 10, 10, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(10), threadFactory);
        SendThread broadcastThread = new SendThread(serverService,clientThreadList);
        threadPoolExecutor.execute(broadcastThread);
        threadPoolExecutor.execute(() -> {
            try {
                ServerSocket serverSocket = new ServerSocket(9999);
                int clientIndex = 0;
                //循环等待每一个客户端的链接
                while (true) {
                    System.out.println("waiting!");
                    Socket socket = serverSocket.accept();
                    System.out.println("connected!");
                    ReceiveThread clientThread = new ReceiveThread(socket, ++clientIndex, serverService);
                    clientThreadList.add(clientThread);
                    threadPoolExecutor.execute(clientThread);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
