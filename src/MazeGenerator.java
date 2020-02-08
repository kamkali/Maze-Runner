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
