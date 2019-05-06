package client.service;

import client.view.InputArea;
import client.view.MainFrame;
import ot.operation.Change;
import ot.operation.Operation;
import text.Text;

import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ClientService {
    private static MainFrame mainFrame;
    private static Text text;
    private static InputArea inputArea;
    private static int startIndex;
    private static List<Change> operationList = Collections.synchronizedList(new ArrayList<>());
    private static Queue<Change> changesToSendQueue = new ConcurrentLinkedQueue<>();
    private static boolean SelfOperation;

    public ClientService(int changeIndex) {
        this.text = new Text();
        mainFrame = new MainFrame();
        this.inputArea = mainFrame.getInputArea();
        this.startIndex = changeIndex;
        operationList = Collections.synchronizedList(new ArrayList<>());
        this.changesToSendQueue = new ConcurrentLinkedQueue<>();
        this.SelfOperation = true;
    }

    public static boolean hasNoChange(){
        return changesToSendQueue.isEmpty();
    }

    public static Change getNextChange(){
        return changesToSendQueue.poll();
    }

    public static boolean addNewChange(Change change, boolean isReceived){
        //客户端收到的第一条消息应该是服务器端维护的text
        //所以后面的消息序列号都应减一。
        change.setIndex(startIndex + operationList.size() );
        operationList.add(change);
        if(isReceived)
            return true;
        else
            return changesToSendQueue.offer(change);
    }

    public static void changeApply(Change change){
        if(change.apply(text))
            inputArea.setText(text.getTextBuffer());
    }

    public static boolean isSelfOperation() {
        return SelfOperation;
    }

    public static void setSelfOperation(boolean selfOperation) {
        SelfOperation = selfOperation;
    }

    public static Text getText() {
        return text;
    }

}
