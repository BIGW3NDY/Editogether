package client.view;

import client.service.ClientService;
import ot.operation.Change;
import ot.operation.Delete;
import ot.operation.Insert;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import java.lang.ref.Cleaner;


public class InputArea extends JTextArea {

    public InputArea(){
        Document document = this.getDocument();
        this.setLineWrap(true);
        this.setWrapStyleWord(true);

        this.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                if(!ClientService.isSelfOperation()){
                    return;
                }
                int offset = e.getOffset();
                int length = e.getLength();
                String content = "";
                try {
                    content = document.getText(offset, length);
                } catch (BadLocationException e1) {
                    e1.printStackTrace();
                }

                //向发送队列添加新Insert
                Change change = new Insert(offset, content);
                change.apply(ClientService.getText());
                ClientService.addNewChange(change, false);
                System.out.println("成功添加到发送队列"+change);
                //console log
                //System.out.print(e.getOffset() + "; ");
                //System.out.print(content+"\n");
            }

            public void removeUpdate(DocumentEvent e) {
                if(!ClientService.isSelfOperation()){
                    return;
                }
                int offset = e.getOffset();
                int length = e.getLength();

                //向发送队列添加新Insert
                Change change = new Delete(offset, length);
                change.apply(ClientService.getText());
                ClientService.addNewChange(change, false);

                //console log
                //System.out.println(offset + " " + length);
            }

            public void changedUpdate(DocumentEvent e) {

            }
        });
    }
}
