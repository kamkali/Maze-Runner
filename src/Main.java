public class Main {
    public static void main(String[] args) {

        Maze myMaze = new MazeGenerator(10);
        myMaze.display();

        MazeSolver mazeSolver = new MazeSolver(myMaze);
        mazeSolver.solveMaze();
    }
}
