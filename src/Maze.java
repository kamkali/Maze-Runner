// TODO: Generate proper maze for ex. with recursive backtracker

public interface Maze {
    void draw() throws InterruptedException;

    void generate(int rows, int cols);

}
