package search.gui.mapbuilder;

import search.grid.Cell;
import search.grid.Grid;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public class MapBuilderGrid extends JPanel implements MouseMotionListener, MouseListener {

    private BufferedImage img;
    private Grid grid;

    private int width;
    private int height;

    public MapBuilderGrid(int rows, int cols, int[][] colors, boolean startCellExists, boolean goalCellExists) {
        super();

        int cellSize = Grid.GRID_SIZE / rows;
        this.width = cellSize * rows;
        this.height = cellSize * cols;

        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        grid = new Grid(rows, cols, colors, false);
        grid.setStartCellExists(startCellExists);
        grid.setGoalCellExists(goalCellExists);

        System.out.println("width: " + width + " height: " + height);
        setPreferredSize(new Dimension(width, height));

        addMouseListener(this);
        addMouseMotionListener(this);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        render(g);
    }

    /* Mouse input methods */

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        mouseDragged(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    /* Mouse motion methods */

    @Override
    public void mouseDragged(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e))
            colorCell(e.getX(), e.getY());
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Grid getGrid() {
        return grid;
    }

    private void render(Graphics g) {
        Graphics2D imgGraphics = (Graphics2D) img.getGraphics();

        // draw panel outline
        imgGraphics.setColor(Color.BLACK);
        imgGraphics.drawRect(0, 0, getWidth(), getHeight());

        grid.render(imgGraphics);

        // draw image
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(img, 0, 0, this);
    }

    private void colorCell(int x, int y) {
        Cell cell = null;
        try {
            cell = grid.getCell(y / grid.getHeight(), x / grid.getWidth());
        } catch (ArrayIndexOutOfBoundsException e) {

        }

        Color c = grid.getSelectedCellColor();

        if (c != null) {
            if (cell != null) {
                Color cellColor = cell.getColor();
                if (cellColor.equals(Cell.START_CELL_COLOR))
                    grid.setStartCellExists(false);
                else if (cellColor.equals(Cell.GOAL_CELL_COLOR))
                    grid.setGoalCellExists(false);
                if (c.equals(Cell.START_CELL_COLOR)) {
                    if (!grid.getStartCellExists()) {
                        cell.setColor(c);
                        grid.setStartCellExists(true);
                    }
                } else if (c.equals(Cell.GOAL_CELL_COLOR)) {
                    if (!grid.getGoalCellExists()) {
                        cell.setColor(c);
                        grid.setGoalCellExists(true);
                    }
                } else {
                    cell.setColor(c);
                }

                repaint();
            }
        }
    }

}
