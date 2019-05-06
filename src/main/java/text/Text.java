package text;

public class Text {
    private StringBuffer textBuffer;

    public Text(){
        this.textBuffer = new StringBuffer();
    }

    public Text(StringBuffer textBuffer) {
        this.textBuffer = textBuffer;
    }

    public void insert(int offset, String newContent){
        textBuffer.insert(offset,newContent);
    }

    public void delete(int offset, int length){
        textBuffer.delete(offset, offset + length);
    }

    public String getTextBuffer() {
        return textBuffer.toString();
    }
}
