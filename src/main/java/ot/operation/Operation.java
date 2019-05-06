package ot.operation;

import server.service.ServerService;
import text.Text;

public class Operation {
    Change change;

    int clientIndex;
    int index;

    public Operation(Change change, int clientIndex) {
        this.change = change;
        this.clientIndex = clientIndex;
    }

    @Override
    public String toString() {
        return "Operation{" +
                "change=" + change +
                ", clientIndex=" + clientIndex +
                '}';
    }

    public void setIndex(int index){
        this.index = index;
    }

    public int getIndex(){
        return index;
    }



    public void applyOperation(Text text){
        change.apply(text);
    }

    public Change getChange() {
        return change;
    }


    public int getClientIndex() {
        return clientIndex;
    }
}
