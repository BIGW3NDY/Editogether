package server.service;

import ot.operation.Operation;
import ot.transform.Transform;
import text.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ServerService {
    private Text text;

    //维护修改记录是为了在收到新的Operation的时候可以用于同步更新
    private List<Operation> operationList = Collections.synchronizedList(new ArrayList<>());

    private Queue<Operation> changesToSendQueue = new ConcurrentLinkedQueue<>();

    public ServerService(){
        text = new Text();
    }

    public void addOperation(Operation op){
        //同步更新Operation
        //由于Operation在客户端产生到发送至服务器需要一定时间
        //在这段时间可能有其它客户端发生的更改信息传到了服务器
        //因此我们需要根据这些更改信息修改Operation的发生位置
        op.setIndex(operationList.size());
        System.out.println("need to transform ? changeIndex:" + op.getChange().getIndex() + " listIndex:" + operationList.size());
        for(int i = op.getChange().getIndex(); i < operationList.size(); i++){
            Operation operation =  operationList.get(i);
            Transform.transform(op, operation);
        }
        //TODO


        //修改在服务器端维护的text
        op.applyOperation(text);
        //加入修改记录
        operationList.add(op);
        //加入发送队列
        changesToSendQueue.offer(op);
    }

    public boolean hasNoChange(){
        return changesToSendQueue.isEmpty();
    }

    public Operation getNextChange(){
        return changesToSendQueue.poll();
    }

    public Text getText() {
        return text;
    }

    public int getListIndex(){
        return operationList.size();
    }
}
