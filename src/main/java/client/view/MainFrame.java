package client.view;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private InputPanel inputPanel = new InputPanel();
    private EditorMenu editorMenu = new EditorMenu();
    public MainFrame(){
        this.setTitle("Editogether --写作编辑");
        this.add(inputPanel);
        this.setJMenuBar(editorMenu);
        this.setVisible(true);
        this.setSize(500,500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public InputArea getInputArea() {
        return inputPanel.getInputArea();
    }
}
