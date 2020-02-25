package search.gui.searcher;

import search.pathfinder.Searcher;
import search.grid.Cell;
import search.grid.Grid;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class SearcherPanel extends JPanel {

    public static final int PAINT_DELAY = 100;

    private Grid grid;
    private BufferedImage img;

    private Searcher searcher;

    public SearcherPanel(Grid grid, int width, int height, Searcher searcher) {
        super();

        setPreferredSize(new Dimension(width, height));

        this.grid = grid;
        this.searcher = searcher;

        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        Thread searcherThread = new Thread(() -> {
            if (grid != null) {
                Cell pathCell = this.searcher.search();

                JFrame parentFrame = (JFrame) getTopLevelAncestor();
                parentFrame.setTitle(parentFrame.getTitle() + ": " + this.searcher.path(pathCell)[1]);
            }
        });

        searcherThread.setName(searcher.getClass() + " thread");
        searcherThread.start();

        startPaintLoop();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        render(g);
    }

    private void render(Graphics g) {
        // draw onto image
        Graphics2D imgGraphics = (Graphics2D) img.getGraphics();
        grid.render(imgGraphics);

        // draw image onto panel
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(img, 0, 0, this);
    }

    private void startPaintLoop() {
        Timer timer = new Timer(PAINT_DELAY, null);

        timer.addActionListener(e -> {
            if (searcher.isDone())
                timer.stop();

            repaint();
        });

        timer.start();
    }

    public Grid getGrid() {
        return grid;
    }

}
