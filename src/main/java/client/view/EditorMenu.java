package client.view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditorMenu extends JMenuBar implements ActionListener {
    private JMenu newFile = new JMenu("新建");
    private JMenu saveFile = new JMenu("保存");
    private JMenu about = new JMenu("关于");

    public EditorMenu(){
        this.add(newFile);
        this.add(saveFile);
        this.add(about);
        newFile.addActionListener(this);
        saveFile.addActionListener(this);
        about.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == newFile){
            //TODO
        }else if(e.getSource() == saveFile){
            //TODO
        }else if(e.getSource() == about){
            JOptionPane.showMessageDialog(null,"实现了协同编辑的简单小Demo");
            //JOptionPane.showMessageDialog(null, "关于这个项目", "实现了协同编辑的简单小Demo", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
