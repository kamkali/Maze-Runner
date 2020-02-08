import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class MazeGenerator implements Maze {

    private Cell[][] arr2D;

    private Cell currentCell;

    @Override
    public void generate(int rows, int cols) {
        arr2D = new Cell[rows][cols];

        for (int i = 0; i < arr2D.length; i++) {
            for (int j = 0; j < arr2D[i].length; j++) {
                arr2D[i][j] = new Cell(i, j);
            }
        }
        currentCell = arr2D[0][0];
        currentCell.setVisited(true);
    }

    public boolean checkIndex(int row, int col){
        return !(row < 0 || row > this.arr2D.length || col < 0 || col > this.arr2D[0].length);
    }

    public Cell checkNeighbors(Cell[][] grid, int row, int col){
        List<Cell> unvisitedNeighbors = new ArrayList<>();
        Cell top;
        Cell right;
        Cell bottom;
        Cell left;


        if (checkIndex(row - 1, col) || !grid[row - 1][col].isVisited()) {
            top = grid[row - 1][col];
            unvisitedNeighbors.add(top);
        }
        if (checkIndex(row, col + 1) || !grid[row][col + 1].isVisited()) {
            right = grid[row][col + 1];
            unvisitedNeighbors.add(right);
        }
        if (checkIndex(row + 1, col) || !grid[row + 1][col].isVisited()) {
            bottom = grid[row + 1][col];
            unvisitedNeighbors.add(bottom);
        }
        if (checkIndex(row, col - 1) || !grid[row][col - 1].isVisited()) {
            left = grid[row][col - 1];
            unvisitedNeighbors.add(left);
        }

        if (!unvisitedNeighbors.isEmpty()){
            int randomNum = ThreadLocalRandom.current().nextInt(0, unvisitedNeighbors.size() + 1);
            return unvisitedNeighbors.get(randomNum);
        } else {
            return null;
        }
    }

//    @Override
//    public void draw() {
//        for (Cell[] vector : this.arr2D){
//            for (Cell el : vector){
//                if (el == 1)
//                    System.out.print("\u2588\u2588");
//                else
//                    System.out.print("  ");
//            }
//            System.out.println();
//        }
//    }


    @Override
    public void draw() {
        for (Cell[] vec : this.arr2D){
            for(Cell el : vec){
                if (el.isVisited()) {
                    System.out.print("*");
                }
                System.out.print("(" + el.getRow() + "," + el.getCol() + ") ");
            }
            System.out.println();
        }
    }
}
