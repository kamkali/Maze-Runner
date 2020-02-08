import java.util.concurrent.TimeUnit;

public class MazeGenerator implements Maze {

    private Cell[][] arr2D;
    private Cell currentCell;
    int[][] arr = new int[10][10];


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

        findCell(currentCell);
    }

    public Cell findCell(Cell currentCell){
        Cell nextCell = currentCell.checkNeighbors(arr2D, currentCell.getRow(), currentCell.getCol());
        if (nextCell != null){
            nextCell.setVisited(true);
            drawPath(nextCell.getRow(), nextCell.getCol());
            currentCell = nextCell;
            return findCell(currentCell);
        } else
            return null;
    }

    public void drawPath(int row,int col){
        arr[0][0] = 1;
        arr[row][col] = 1;
        for (int i = 0; i < arr.length; i++){
            for (int j = 0 ; j < arr[0].length; j++){
                if (arr[i][j] == 1)
                    System.out.print("* ");
                else
                    System.out.print(arr[i][j] + " ");
            }
            System.out.println();
        }
//        System.out.print("*");
        System.out.println();
        System.out.println();
    }


    @Override
    public void draw() throws InterruptedException {
        for (Cell[] vec : this.arr2D){
            for(Cell el : vec){
                if (el.isVisited()) {
                    System.out.print("*");
                }
                System.out.print("(" + el.getRow() + "," + el.getCol() + ") ");
//                TimeUnit.SECONDS.sleep(1);
            }
            System.out.println();
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


}
