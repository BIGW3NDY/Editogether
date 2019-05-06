package ot.operation;

import text.Text;

public class Delete extends Change {
    int offset;
    int length;

    /**
     *
     * @param offset 删除字符在字符串中的索引位置
     * @param length 删除长度
     */
    public Delete(int offset, int length) {
        this.offset = offset;
        this.length = length;
    }

    @Override
    public boolean apply(Text text) {
        text.delete(offset, length);
        return true;
    }

    @Override
    public String toString() {
        return "Delete{" +
                "offset=" + offset +
                ", length=" + length +
                '}';
    }

    public int getOffset() {
        return offset;
    }

    public int getLength() {
        return length;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public void setLength(int length) {
        this.length = length;
    }
}
