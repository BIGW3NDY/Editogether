package ot.operation;

import client.service.ClientService;
import text.Text;

public class Insert extends Change {
    int offset;
    String newContent;

    /**
     *
     * @param offset 插入点在字符串中的索引位置
     * @param newContent 插入的字符串
     */
    public Insert(int offset, String newContent){
        this.offset = offset;
        this.newContent = newContent;

    }

    public boolean apply(Text text) {
        if(newContent==null || newContent.isEmpty())
            return false;
        text.insert(offset, newContent);
        return true;
    }

    @Override
    public String toString() {
        return "Insert{" +
                "offset=" + offset +
                ", newContent='" + newContent + '\'' +
                '}';
    }

    public int getOffset() {
        return offset;
    }

    public String getNewContent() {
        return newContent;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public void setNewContent(String newContent) {
        this.newContent = newContent;
    }
}
