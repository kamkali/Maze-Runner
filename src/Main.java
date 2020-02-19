public class Main {
    public static void main(String[] args) {

        Maze myMaze = new MazeGenerator(4);
        myMaze.display();

        MazeSolver mazeSolver = new MazeSolver(myMaze);
        mazeSolver.solveMaze();
        System.out.println();
        myMaze.display();
    }
}
