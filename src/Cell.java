public class Cell {
    private int row;
    private int col;
    private boolean[] walls;
    private boolean visited;

    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
        /*
        Simple cell view:
                top
                ---
            left| |right
                ---
                bottom
         */
        // clock-wise order
        this.walls = new boolean[]{true, true, true, true}; // means that at the beggining every 'wall' is set
        this.visited = false;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}
