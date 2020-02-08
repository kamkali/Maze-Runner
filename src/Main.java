public class Main {
    public static void main(String[] args) {
        Maze myMaze = new MazeGenerator();

        myMaze.generate(10, 10);

        myMaze.draw();

    }
}
