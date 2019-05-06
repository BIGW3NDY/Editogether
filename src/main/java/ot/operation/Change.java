package ot.operation;

import text.Text;

import java.io.Serializable;

public abstract class Change implements Serializable {
    private int index;

    public Change() { }

    public abstract boolean apply(Text text);

    public void setIndex(int index) {
        this.index = index;
    }

    public int getIndex(){
        return index;
    }
}
