import java.util.concurrent.ThreadLocalRandom;

class Maze {
    private int[][] arr2D;

    Maze(int x, int y) {
        this.arr2D = new int[x][y];

        for(int i = 0; i < arr2D.length; i++){
            for (int j = 0; j < arr2D[i].length; j++){
                if (i == 0 || i == arr2D.length - 1) {
                    int border = 1;
                    arr2D[i][j] = border;
                } else {
                    int randomNumber = ThreadLocalRandom.current().nextInt(0, 2);
                    arr2D[i][j] = randomNumber;
                }
            }
        }
    }

    void draw(){
        for (int[] vector : this.arr2D){
            for (int el : vector){
                if (el == 1)
                    System.out.print("\u2588\u2588");
                else
                    System.out.print("  ");
            }
            System.out.println();
        }
    }

}
