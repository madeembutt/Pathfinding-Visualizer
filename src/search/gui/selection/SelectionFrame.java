package search.gui.selection;

import javax.swing.*;

public class SelectionFrame extends JFrame {

    public static final int WIDTH = 300;
    public static final int HEIGHT = 75;

    public SelectionFrame(String title) {
        super(title);

        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setFocusable(true);
        setLocationRelativeTo(null);

        SelectionPanel sPanel = new SelectionPanel(WIDTH, HEIGHT);

        add(sPanel);

        setVisible(true);
    }


}
