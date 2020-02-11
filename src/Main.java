public class Main {
    public static void main(String[] args) {
        Maze myMaze = new MazeGenerator(3);

        myMaze.display();

        MazeSolver mazeSolver = new MazeSolver(myMaze.getMazeGrid());
        mazeSolver.solveMaze();
    }
}
