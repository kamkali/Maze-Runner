# Maze-Runner

This is a repository for the Maze Runner project. It was implemented in Java. The main goal was to practice Java, data structures and algorithms.
## Compile'n'Run

Example compilation and run:
```
javac -d bin src/*.java
java -classpath bin Main
```
## Description
The project consists of a couple of components:
1. Random maze generator – the maze is represented as a grid (two-dimensional array of Cell objects). The **Depth-First Search algorithm** implemented as recursive backtracker is used to go through a grid and generate passes and walls.
2. **Breadth-First Search algorithm** to find all neighboring vertices with the path cost for every graph node to represent maze as a graph
3. **Dijkstra's Shortest Path First algorithm** to solve a graph and find a path from entry to exit

## Usage
When run, the user can generate a random maze. The app will ask for a dimension of a maze. The valid dimension range is <2,65>.
<img width="438" alt="Zrzut ekranu 2020-02-19 o 20 40 21" src="https://user-images.githubusercontent.com/50112357/74869398-2cad3e00-5358-11ea-82a1-bbf46b02bd11.png">

The generated maze can be solved using the next option. The path will be marked on the maze and the shortest path from entry to exit through graph vertices will be displayed.
<img width="1064" alt="Zrzut ekranu 2020-02-19 o 20 40 30" src="https://user-images.githubusercontent.com/50112357/74870085-6599e280-5359-11ea-9e05-2ffaebb31547.png">

## Author

* **Kamil Kaliś**  - [kamkali](https://github.com/kamkali)
