package search.gui.mapbuilder.legend;

import search.gui.mapbuilder.MapBuilderLegend;

import java.awt.*;

public abstract class ObjectLegend<T> {

    protected final int SQUARE_SIZE = MapBuilderLegend.SQUARE_SIZE;
    protected final int TEXT_OFFSET = 20;

    protected final Font DEFAULT_FONT = new Font("Calibri", Font.PLAIN, 12);

    protected String text;
    protected T object;

    protected int x;
    protected int y;

    protected abstract void render(Graphics2D g2d);
    protected abstract void drawText(Graphics2D g2d, int x, int y);

    public T getObject() {
        return object;
    }

}
