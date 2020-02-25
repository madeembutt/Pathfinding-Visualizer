package search.gui.mapbuilder.legend;

import java.awt.*;

public class CellLegend extends ObjectLegend<Color> {

    public CellLegend(Color object, String text, int x, int y) {
        this.object = object;
        this.text = text;

        this.x = x;
        this.y = y;
    }

    public void render(Graphics2D g2d) {
        drawSquare(g2d, x, y);
        drawText(g2d, x + TEXT_OFFSET, y + SQUARE_SIZE);
    }

    private void drawSquare(Graphics2D g2d, int x, int y) {
        g2d.setColor(object);
        g2d.fillRect(x, y, SQUARE_SIZE, SQUARE_SIZE);

        g2d.setColor(Color.BLACK);
        g2d.drawRect(x, y, SQUARE_SIZE, SQUARE_SIZE);
    }

    protected void drawText(Graphics2D g2d, int x, int y) {
        if (object.equals(Color.WHITE))
            g2d.setColor(Color.BLACK);
        else
            g2d.setColor(object);

        g2d.setFont(DEFAULT_FONT);
        g2d.drawString(text, x, y);
    }

    public Rectangle getCellBounds() {
        return new Rectangle(x, y, SQUARE_SIZE, SQUARE_SIZE);
    }

}
