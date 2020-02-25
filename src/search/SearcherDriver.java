package search;

import search.gui.selection.SelectionFrame;

public class SearcherDriver {

    public static void main(String[] args) {
        new SelectionFrame("Select an option");

        //MapBuilderFrame mbf = new MapBuilderFrame("Map Builder");


        /*SwingUtilities.invokeLater(() -> {

            try {
                SearcherFrame frame = new SearcherFrame("A* Test", "grid1.txt");
            } catch (GridSizeException e) {
                e.printStackTrace();
            }

        });*/

    }


}
