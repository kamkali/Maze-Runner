import java.util.Stack;

public class MazeGenerator implements Maze {

    private Cell[][] arr2D;
    private Cell currentCell;
    private final int rows, cols;
    private Stack<Cell> cellStack = new Stack<>();

    public MazeGenerator(int dim) {
        this.cols = dim;
        this.rows = dim;
        this.generate();
    }

    private void generate() {
        arr2D = new Cell[rows][cols];

        for (int i = 0; i < arr2D.length; i++) {
            for (int j = 0; j < arr2D[i].length; j++) {
                arr2D[i][j] = new Cell(i, j);
            }
        }
        this.currentCell = arr2D[0][0];
        this.currentCell.walls[Cell.Wall.TOP.getWall()] = false;
        this.currentCell.setVisited(true);

        findCell(this.currentCell);
    }

    private Cell findCell(Cell currentCell){
        Cell nextCell = currentCell.checkNeighbors(arr2D, currentCell.getRow(), currentCell.getCol());

        if (nextCell != null){
            nextCell.setVisited(true);
            cellStack.push(currentCell);

            removeWalls(currentCell, nextCell);
            
            currentCell = nextCell;
            return findCell(currentCell);

        } else if (!cellStack.empty()) {
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
                System.out.print((arr2D[j][i].walls[Cell.Wall.TOP.getWall()]) ? "+---" : "+   ");
            }
            System.out.println("+");
            // draw the left edge
            for (int j = 0; j < this.rows; j++) {
                System.out.print((arr2D[j][i].walls[Cell.Wall.LEFT.getWall()]) ? "|   " : "    ");
            }
            System.out.println("|");
        }
        // draw the bottom line
        for (int j = 0; j < this.rows; j++) {
            System.out.print("+---");
        }
        System.out.println("+");
    }
}
