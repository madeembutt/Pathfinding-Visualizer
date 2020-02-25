package search.pathfinder;

import search.grid.Cell;
import search.grid.Grid;

import java.awt.*;
import java.util.*;

public class BreadthFirstSearcher extends Searcher {

    private Queue<Cell> open;
    private ArrayList<Cell> closed;

    public BreadthFirstSearcher(Grid grid, boolean showMultipleColors) {
        super(grid, showMultipleColors);

        open = new LinkedList<>();
        closed = new ArrayList<>();
    }

    @Override
    public Cell search() {
        Color first = new Color(245, 11, 255);
        Color second = new Color(80, 242, 255);
        Color third = new Color(255, 144, 4);
        Color fourth = new Color(255, 61, 146);
        Color fifth = new Color(159, 2, 255);
        Color[] seenColors = {first, second, third, fourth, fifth};

        open.add(grid.getStartCell());

        while (!open.isEmpty()) {
            try {
                Thread.sleep(SEARCHER_DELAY);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Cell c = open.poll();

            if (c != null) {
                c.setAlongPath(true);

                if (c.isGoal())
                    return c;

                // cycle pruning
                if (closed.indexOf(c) >= 0) {
                    if (showMultipleColors) {
                        if (!c.isStart() && !c.isGoal()) {
                            if (c.getColor().equals(seenColors[c.getReachedCount()]))
                                c.setReachedCount(c.getReachedCount() + 1);

                            c.setColor(seenColors[c.getReachedCount()]);
                        }
                    }

                    continue;
                }

                closed.add(c);

                if (!c.isStart() && !c.isGoal())
                    c.setColor(new Color(157, 255, 26));

                ArrayList<Cell> neighbors = c.getNeighbors();
                for (Cell n : neighbors) {
                    if (closed.indexOf(n) < 0) {
                        if (!n.isAlongPath() && (!n.isStart() && !n.isGoal()))
                            n.setColor(Color.YELLOW);

                        open.add(n);
                        n.setParent(c);
                        n.setAlongPath(true);
                    }
                }
            }
        }

        return null;
    }

}
