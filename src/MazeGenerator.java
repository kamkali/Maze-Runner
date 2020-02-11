import java.util.ArrayDeque;
import java.util.Deque;

public class MazeGenerator implements Maze {

    private Cell[][] mazeGrid;
    private final int rows;
    private final int cols;
    private Deque<Cell> cellStack = new ArrayDeque<>();

    @Override
    public Cell[][] getMazeGrid() {
        return mazeGrid;
    }

    public MazeGenerator(int dim) {
        this.cols = dim;
        this.rows = dim;
        this.generate();
    }

    private void generate() {
        mazeGrid = new Cell[rows][cols];

        for (int i = 0; i < mazeGrid.length; i++) {
            for (int j = 0; j < mazeGrid[i].length; j++) {
                mazeGrid[i][j] = new Cell(i, j);
            }
        }
        Cell currentCell = mazeGrid[0][0];
        currentCell.walls[Cell.Wall.TOP.getWall()] = false;
        currentCell.setVisited(true);

        findCell(currentCell);
    }

    private Cell findCell(Cell currentCell){
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
            currentCell.walls[Cell.Wall.LEFT.getWall()] = false;
            nextCell.walls[Cell.Wall.RIGHT.getWall()] = false;
        } else if (x == -1){
            currentCell.walls[Cell.Wall.RIGHT.getWall()] = false;
            nextCell.walls[Cell.Wall.LEFT.getWall()] = false;
        }
        int y = currentCell.getCol() - nextCell.getCol();
        if (y == 1){
            currentCell.walls[Cell.Wall.TOP.getWall()] = false;
            nextCell.walls[Cell.Wall.BOTTOM.getWall()] = false;
        } else if (y == -1){
            currentCell.walls[Cell.Wall.BOTTOM.getWall()] = false;
            nextCell.walls[Cell.Wall.TOP.getWall()] = false;
        }
    }


    @Override
    public void display() {
        for (int i = 0; i < this.rows; i++) {
            // draw the top edge
            for (int j = 0; j < this.rows; j++) {
                System.out.print((mazeGrid[j][i].walls[Cell.Wall.TOP.getWall()]) ? "+---" : "+   ");
            }
            System.out.println("+");
            // draw the left edge
            for (int j = 0; j < this.rows; j++) {
                System.out.print((mazeGrid[j][i].walls[Cell.Wall.LEFT.getWall()]) ? "|   " : "    ");
            }
            System.out.println("|");
        }
        // draw the bottom line
        for (int j = 0; j < this.rows - 1; j++) {
            System.out.print("+---");
        }
        System.out.print("+   ");
        System.out.println("+");
    }
}
