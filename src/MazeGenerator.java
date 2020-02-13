import java.util.ArrayDeque;
import java.util.Deque;

public class MazeGenerator implements Maze {

    private Cell[][] mazeGrid;
    private final int rows;
    private final int cols;
    private Deque<Cell> cellStack = new ArrayDeque<>();


    public MazeGenerator(int dim) throws InterruptedException {
        this.cols = dim;
        this.rows = dim;
        this.generate();
    }

    private void generate() throws InterruptedException {
        mazeGrid = new Cell[cols][rows];

        for (int i = 0; i < mazeGrid.length; i++) {
            for (int j = 0; j < mazeGrid[i].length; j++) {
                mazeGrid[i][j] = new Cell(i, j);
            }
        }

        // initial cell
        Cell currentCell = mazeGrid[0][0];
        // exit cell
        mazeGrid[rows - 1][cols - 1].getWalls()[Cell.Wall.BOTTOM.getWall()] = false;

        // open an entry wall
        currentCell.getWalls()[Cell.Wall.TOP.getWall()] = false;
        currentCell.setVisited(true);
        currentCell.setPathValue(0);

        findCell(currentCell);
    }


    private Cell findCell(Cell currentCell) throws InterruptedException {
        Cell nextCell = currentCell.checkNeighbors(mazeGrid, currentCell.getRow(), currentCell.getCol());

        if (nextCell != null){
            nextCell.setVisited(true);
            cellStack.push(currentCell);

            removeWalls(currentCell, nextCell);
            
            currentCell = nextCell;
            return findCell(currentCell);

        } else if (!cellStack.isEmpty()) {
            currentCell = cellStack.pop();
            return findCell(currentCell);
        }
        return null;
    }


    private void removeWalls(Cell currentCell, Cell nextCell) {
        int x = currentCell.getRow() - nextCell.getRow();
        if (x == 1){
            currentCell.getWalls()[Cell.Wall.LEFT.getWall()] = false;
            nextCell.getWalls()[Cell.Wall.RIGHT.getWall()] = false;
        } else if (x == -1){
            currentCell.getWalls()[Cell.Wall.RIGHT.getWall()] = false;
            nextCell.getWalls()[Cell.Wall.LEFT.getWall()] = false;
        }
        int y = currentCell.getCol() - nextCell.getCol();
        if (y == 1){
            currentCell.getWalls()[Cell.Wall.TOP.getWall()] = false;
            nextCell.getWalls()[Cell.Wall.BOTTOM.getWall()] = false;
        } else if (y == -1){
            currentCell.getWalls()[Cell.Wall.BOTTOM.getWall()] = false;
            nextCell.getWalls()[Cell.Wall.TOP.getWall()] = false;
        }
    }


    @Override
    public void display() {
        for (int i = 0; i < this.rows; i++) {
            // draw the top edge
            for (int j = 0; j < this.rows; j++) {
                System.out.print((mazeGrid[j][i].getWalls()[Cell.Wall.TOP.getWall()]) ? "+---" : "+   ");
            }
            System.out.println("+");
            // draw the left edge
            for (int j = 0; j < this.rows; j++) {
                if (mazeGrid[j][i].isOnPath()) {
                    System.out.print((mazeGrid[j][i].getWalls()[Cell.Wall.LEFT.getWall()]) ? "|///" : "////");
                } else {
                    System.out.print((mazeGrid[j][i].getWalls()[Cell.Wall.LEFT.getWall()]) ? "|   " : "    ");
                }
            }
            System.out.println("|");
        }
        // draw the bottom line
        for (int j = 0; j < this.rows - 1; j++) {
            System.out.print("+---");
        }
        // draw an exit
        System.out.print("+   ");
        System.out.println("+");
    }


    @Override
    public Cell[][] getMazeGrid() {
        return mazeGrid;
    }
}
