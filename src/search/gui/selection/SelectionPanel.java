package search.gui.selection;

import search.grid.Grid;
import search.gui.mapbuilder.MapBuilder;
import search.gui.searcher.SearcherFrame;
import search.pathfinder.BreadthFirstSearcher;
import search.pathfinder.DepthFirstSearcher;
import search.pathfinder.Searcher;
import search.pathfinder.heuristic.AStarSearcher;
import search.pathfinder.heuristic.GreedyBestFirstSearcher;
import search.pathfinder.heuristic.HeuristicDepthFirstSearcher;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class SelectionPanel extends JPanel {

    public SelectionPanel(int width, int height) {
        super();

        setSize(width, height);

        JButton mBuilder = new JButton("Map builder");
        mBuilder.addActionListener(e -> {
            new MapBuilder("Map Builder", Grid.INIT_GRID_ROWS, Grid.INIT_GRID_COLS, null);
            ((JFrame) getTopLevelAncestor()).dispose();
        });

        JButton searcher = new JButton("Searcher");
        searcher.addActionListener(e -> {
            JFileChooser fc = new JFileChooser();
            int retVal = fc.showOpenDialog(null);

            if (retVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                ((JFrame) getTopLevelAncestor()).dispose();

                class Local {
                    private int numberChecked = 0;

                    private void action(JCheckBox cb) {
                        int MAX_CHECKED = 2;
                        if (cb.isSelected() && numberChecked >= MAX_CHECKED) {
                            cb.setSelected(false);
                        } else {
                            if (cb.isSelected())
                                numberChecked++;
                            else
                                numberChecked--;
                        }
                    }
                }

                JPanel chooseSearcherPanel = new JPanel();
                chooseSearcherPanel.setLayout(new BoxLayout(chooseSearcherPanel, BoxLayout.Y_AXIS));
                JCheckBox[] searcherCheckBoxes = {
                        new JCheckBox("A* Searcher"),
                        new JCheckBox("Greedy Best-First Searcher"),
                        new JCheckBox("Depth-First Searcher"),
                        new JCheckBox("Heuristic Depth-First Searcher"),
                        new JCheckBox("Breadth-First Searcher")
                };

                Local l = new Local();
                for (JCheckBox cb : searcherCheckBoxes) {
                    cb.addActionListener(e1 -> l.action(cb));
                    chooseSearcherPanel.add(cb);
                }

                Searcher[] searchers = {
                        new AStarSearcher(null, true),
                        new GreedyBestFirstSearcher(null, true),
                        new DepthFirstSearcher(null, true),
                        new HeuristicDepthFirstSearcher(null, true),
                        new BreadthFirstSearcher(null, true)
                };

                JButton okBtn = new JButton("OK");
                okBtn.addActionListener(e1 -> {
                    for (int i = 0; i < searcherCheckBoxes.length; i++) {
                        if (searcherCheckBoxes[i].isSelected())
                            new SearcherFrame(searcherCheckBoxes[i].getText(), file, searchers[i]);
                    }
                });

                chooseSearcherPanel.add(okBtn);

                JFrame chooseSearcherFrame = new JFrame("Choose searcher");
                chooseSearcherFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                chooseSearcherFrame.setResizable(false);
                chooseSearcherFrame.setFocusable(true);
                chooseSearcherFrame.setLocationRelativeTo(null);
                chooseSearcherFrame.add(chooseSearcherPanel);
                chooseSearcherFrame.pack();
                chooseSearcherFrame.setVisible(true);
            }
        });

        add(mBuilder);
        add(searcher);

        setBackground(Color.WHITE);
    }

}
