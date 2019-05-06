package client.view;

import javax.swing.*;
import java.awt.*;

public class InputPanel extends JPanel {
    private InputArea inputArea;
    public InputPanel(){
        inputArea = new InputArea();
        this.setLayout(new BorderLayout());
        this.add(new JScrollPane(inputArea));
    }

    public InputArea getInputArea() {
        return inputArea;
    }
}
