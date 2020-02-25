package search.gui.mapbuilder;

import search.grid.Cell;
import search.grid.Grid;
import search.gui.mapbuilder.legend.CellLegend;
import search.gui.mapbuilder.legend.TextFieldLegend;
import search.gui.selection.SelectionFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MapBuilderLegend extends JPanel implements MouseListener {

    /**
     * Width of the panel
     */
    public static final int WIDTH = 175;

    /**
     * Size of the colored squares that represent a cell that is shown on the legend (panel)
     */
    public static final int SQUARE_SIZE = 15;

    /**
     * X coordinate of the white square
     */
    public static final int WHITE_X = 5;

    /**
     * Y coordinate of the white square
     */
    public static final int WHITE_Y = 5;

    /**
     *
     */
    public static final int TEXT_FIELD_MAX_COLS = 3;

    /**
     *
     */
    public static final int TEXT_FIELD_WIDTH = 30;

    /**
     *
     */
    public static final int TEXT_FIELD_HEIGHT = 20;

    public static final int BUTTON_WIDTH = 100;
    public static final int BUTTON_HEIGHT = 25;

    public static final int STROKE_WIDTH = 3;
    public static final Color OUTLINE_COLOR = Color.YELLOW;

    private final int TEXT_Y = 4 * WHITE_Y;

    private CellLegend whiteCell;
    private CellLegend blackCell;
    private CellLegend blueCell;
    private CellLegend redCell;

    private TextFieldLegend rows;
    private TextFieldLegend cols;
    private TextFieldLegend mapName;

    private BufferedImage img;
    private Grid grid;

    private int row;
    private int col;

    private Rectangle selectedCell;

    public MapBuilderLegend(Grid grid, MapBuilderGrid mGrid, int width, int height, String fileName) {
        super();

        this.grid = grid;

        this.row = Grid.INIT_GRID_ROWS;
        this.col = Grid.INIT_GRID_COLS;

        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        int baseY = initLegends(fileName);
        initButtons(baseY, mGrid);

        // set the layout to null so that the JComponents (Buttons and Text Fields) can be positioned anywhere
        setLayout(null);

        addMouseListener(this);
        setPreferredSize(new Dimension(width, height));
    }

    @Override
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
        Rectangle mouseBounds = new Rectangle(e.getX(), e.getY(), 1, 1);

        if (mouseBounds.intersects(whiteCell.getCellBounds())) {
            selectedCell = whiteCell.getCellBounds();
            grid.setSelectedCellColor(whiteCell.getObject());
            repaint();
        } else if (mouseBounds.intersects(blackCell.getCellBounds())) {
            selectedCell = blackCell.getCellBounds();
            grid.setSelectedCellColor(blackCell.getObject());
            repaint();
        } else if (mouseBounds.intersects(blueCell.getCellBounds())) {
            selectedCell = blueCell.getCellBounds();
            grid.setSelectedCellColor(blueCell.getObject());
            repaint();
        } else if (mouseBounds.intersects(redCell.getCellBounds())) {
            selectedCell = redCell.getCellBounds();
            grid.setSelectedCellColor(redCell.getObject());
            repaint();
        }

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

    private int initLegends(String fileName) {
        /* Create UI for the legend so that the user knows how to create a map */

        /*
         * Start off by creating the legends for the cells
         * i.e what each color means
         * */
        int whiteCellY = TEXT_Y + 2 * SQUARE_SIZE;
        whiteCell = new CellLegend(Color.WHITE, "= searchable/clear cell", WHITE_X, whiteCellY);

        int blackY = whiteCellY + WHITE_Y + SQUARE_SIZE;
        blackCell = new CellLegend(Color.BLACK, "= unsearchable cell", WHITE_X, blackY);

        int blueY = blackY + SQUARE_SIZE + SQUARE_SIZE + WHITE_Y;
        blueCell = new CellLegend(Color.BLUE, "= start cell", WHITE_X, blueY);

        int redY = blueY + SQUARE_SIZE + WHITE_Y;
        redCell = new CellLegend(Color.RED, "= goal cell", WHITE_X, redY);

        /* create the text fields for entering the rows anc columns of the search.grid */
        int rowsY = redY + 4 * SQUARE_SIZE;
        rows = new TextFieldLegend(this, "Rows: ", new JTextField(TEXT_FIELD_MAX_COLS), WHITE_X, rowsY,
                TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT);

        int colsY = rowsY + TEXT_FIELD_HEIGHT;
        cols = new TextFieldLegend(this, "Cols: ", new JTextField(TEXT_FIELD_MAX_COLS), WHITE_X, colsY,
                TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT);

        int mnY = colsY + TEXT_FIELD_HEIGHT;
        mapName = new TextFieldLegend(this, "Name: ", new JTextField(TEXT_FIELD_MAX_COLS), WHITE_X, mnY,
                2 * TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT);

        if (fileName != null)
            mapName.getObject().setText(fileName);

        selectedCell = blackCell.getCellBounds();

        return mnY;
    }

    /**
     * Create the buttons for resizing the search.grid and for creating the map file.
     * Resizing depends on the rows and columns.
     *
     * @param baseY used to determine the <i>y</i>-position of the first button <code>(baseY + SQUARE_SIZE)</code>
     * @param mGrid the <code>MapBuilderGrid</code>
     */
    private void initButtons(int baseY, MapBuilderGrid mGrid) {
        int resizeY = baseY + SQUARE_SIZE;
        JButton resizeBtn = new JButton("Resize");
        resizeBtn.setBounds(WHITE_X, resizeY, BUTTON_WIDTH, BUTTON_HEIGHT);
        resizeBtn.addActionListener(e -> {
            System.out.println("Resizing");
            resize();
        });

        int smY = resizeY + BUTTON_HEIGHT;
        JButton saveMapBtn = new JButton("Save map");
        saveMapBtn.setBounds(WHITE_X, smY, BUTTON_WIDTH, BUTTON_HEIGHT);
        saveMapBtn.addActionListener(e -> {
            System.out.println("Saving map");
            saveMap();
        });

        int lmY = smY + BUTTON_HEIGHT;
        JButton loadMapBtn = new JButton("Load map");
        loadMapBtn.setBounds(WHITE_X, lmY, BUTTON_WIDTH, BUTTON_HEIGHT);
        loadMapBtn.addActionListener(e -> {
            System.out.println("Loading map");
            loadMap();
        });

        int clearY = lmY + BUTTON_HEIGHT;
        JButton clearBtn = new JButton("Clear grid");
        clearBtn.setBounds(WHITE_X, clearY, BUTTON_WIDTH, BUTTON_HEIGHT);
        clearBtn.addActionListener(e -> {
            this.grid.clear();
            mGrid.repaint();
        });

        int backY = clearY + BUTTON_HEIGHT;
        JButton backBtn = new JButton("Back");
        backBtn.setBounds(WHITE_X, backY, BUTTON_WIDTH, BUTTON_HEIGHT);
        backBtn.addActionListener(e -> {
            ((JFrame) getTopLevelAncestor()).dispose();
            new SelectionFrame("Select an option");
        });

        add(resizeBtn);
        add(saveMapBtn);
        add(loadMapBtn);
        add(clearBtn);
        add(backBtn);
    }

    /**
     * Renders the panel and its components such as the colored cells, the rows and cols text fields, and the buttons
     *
     * @param g the <code>Graphics</code> object
     */
    private void render(Graphics g) {
        Graphics2D imgGraphics = (Graphics2D) img.getGraphics();

        // draw panel skeleton
        drawPanelSkeleton(imgGraphics);

        imgGraphics.drawString("Select a cell color  w/ left click", WHITE_X, TEXT_Y);
        //imgGraphics.drawString("then type a letter: W, X, B, R", WHITE_X, g.getFontMetrics().getHeight() + TEXT_Y);

        // draw legend
        whiteCell.render(imgGraphics);
        //middleMouseWhiteCell.render(imgGraphics);
        blackCell.render(imgGraphics);
        //leftClickCell.render(imgGraphics);
        blueCell.render(imgGraphics);
        redCell.render(imgGraphics);

        Stroke s = imgGraphics.getStroke();
        if (selectedCell != null) {
            imgGraphics.setStroke(new BasicStroke(STROKE_WIDTH));
            imgGraphics.setColor(OUTLINE_COLOR);
            imgGraphics.drawRect(selectedCell.x, selectedCell.y, selectedCell.width, selectedCell.height);
        }

        imgGraphics.setStroke(s);

        // draw text fields
        rows.render(imgGraphics);
        cols.render(imgGraphics);
        mapName.render(imgGraphics);

        // draw image
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(img, 0, 0, this);
    }

    /**
     * Draws the skeleton of the panel (i.e the background and the border)
     *
     * @param g2d the <code>Graphics2D</code> object
     */
    private void drawPanelSkeleton(Graphics2D g2d) {
        // draw panel background
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        // draw panel outline
        g2d.setColor(Color.BLACK);
        g2d.drawRect(0, 0, getWidth(), getHeight());
    }

    private void resize() {
        try {
            row = Integer.parseInt(rows.getFieldText());
            col = Integer.parseInt(cols.getFieldText());
        } catch (NumberFormatException e) {
            return;
        }

        if (row != col)
            return;

        // only need to check bounds for row since row == col
        if (row < Grid.MIN_GRID_ROWS || row > Grid.MAX_GRID_ROWS)
            return;

        ((JFrame) getTopLevelAncestor()).dispose();
        new MapBuilder("Map Builder", row, col, null);
    }

    private void saveMap() {
        if (mapName.getFieldText().isEmpty())
            return;

        File map = new File("./maps/" + mapName.getFieldText() + ".txt");

        try {
            boolean created = map.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(map));

            writer.write(grid.getRows() + "");
            writer.write(" ");
            writer.write(grid.getCols() + "");

            writer.newLine();

            writer.write(grid.getStartCellExists() + "");
            writer.write(" ");
            writer.write(grid.getGoalCellExists() + "");

            writer.newLine();
            writer.newLine();

            for (int i = 0; i < grid.getRows(); i++) {
                for (int j = 0; j < grid.getCols(); j++) {
                    Cell c = grid.getCell(i, j);

                    //if (c.isSelected())
                        //c.setColor(Cell.CLEAR_CELL_COLOR);

                    writer.write(c.getColor().getRGB() + " ");
                }
                writer.write("\n");
            }

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ((JFrame) getTopLevelAncestor()).dispose();
        new SelectionFrame("Select an option");
    }

    private void loadMap() {
        JFileChooser fc = new JFileChooser();
        int retVal = fc.showOpenDialog(null);

        if (retVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();

            ((JFrame) getTopLevelAncestor()).dispose();
            new MapBuilder("Map Builder", row, col, file);
        }
    }

}


