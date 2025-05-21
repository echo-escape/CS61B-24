package gameoflife;

import edu.princeton.cs.algs4.StdDraw;
import tileengine.TERenderer;
import tileengine.TETile;
import tileengine.Tileset;
import utils.FileUtils;

import java.awt.event.KeyEvent;
import java.util.Random;

import static utils.FileUtils.*;

/**
 * Am implementation of Conway's Game of Life using StdDraw.
 * Credits to Erik Nelson, Jasmine Lin and Elana Ho for
 * creating the assignment.
 */
public class GameOfLife {

    private static final int DEFAULT_WIDTH = 50;
    private static final int DEFAULT_HEIGHT = 50;
    private static final String SAVE_FILE = "src/save.txt";
    private long prevFrameTimestep;
    private TERenderer ter;
    private Random random;
    private TETile[][] currentState;
    private int width;
    private int height;

    /**
     * Initializes our world.
     * @param seed
     */
    public GameOfLife(long seed) {
        width = DEFAULT_WIDTH;
        height = DEFAULT_HEIGHT;
        ter = new TERenderer();
        ter.initialize(width, height);
        random = new Random(seed);
        TETile[][] randomTiles = new TETile[width][height];
        fillWithRandomTiles(randomTiles);
        currentState = randomTiles;
    }

    /**
     * Constructor for loading in the state of the game from the
     * given filename and initializing it.
     * @param filename
     */
    public GameOfLife(String filename) {
        this.currentState = loadBoard(filename);
        ter = new TERenderer();
        ter.initialize(width, height);
    }

    /**
     * Constructor for loading in the state of the game from the
     * given filename and initializing it. For testing purposes only, so
     * do not modify.
     * @param filename
     */
    public GameOfLife(String filename, boolean test) {
        this.currentState = loadBoard(filename);
    }

    /**
     * Initializes our world without using StdDraw. For testing purposes only,
     * so do not modify.
     * @param seed
     */
    public GameOfLife(long seed, boolean test) {
        width = DEFAULT_WIDTH;
        height = DEFAULT_HEIGHT;
        random = new Random(seed);
        TETile[][] randomTiles = new TETile[width][height];
        fillWithRandomTiles(randomTiles);
        currentState = randomTiles;
    }

    /**
     * Initializes our world with a given TETile[][] without using StdDraw.
     * For testing purposes only, so do not modify.
     * @param tiles
     * @param test
     */
    public GameOfLife(TETile[][] tiles, boolean test) {
        TETile[][] transposeState = transpose(tiles);
        this.currentState = flip(transposeState);
        this.width = tiles[0].length;
        this.height = tiles.length;
    }

    /**
     * Flips the matrix along the x-axis.
     * @param tiles
     * @return
     */
    private TETile[][] flip(TETile[][] tiles) {
        int w = tiles.length;
        int h = tiles[0].length;

        TETile[][] rotateMatrix = new TETile[w][h];
        int y = h - 1;
        for (int j = 0; j < h; j++) {
            for (int i = 0; i < w; i++) {
                rotateMatrix[i][y] = tiles[i][j];
            }
            y--;
        }
        return rotateMatrix;
    }

    /**
     * Transposes the tiles.
     * @param tiles
     * @return
     */
    private TETile[][] transpose(TETile[][] tiles) {
        int w = tiles[0].length;
        int h = tiles.length;

        TETile[][] transposeState = new TETile[w][h];
        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                transposeState[x][y] = tiles[y][x];
            }
        }
        return transposeState;
    }

    /**
     * Runs the game. You don't have to worry about how this method works.
     * DO NOT MODIFY THIS METHOD!
     */
    public void runGame() {
        boolean paused = false;
        long evoTimestamp = System.currentTimeMillis();
        long pausedTimestamp = System.currentTimeMillis();
        long clickTimestamp = System.currentTimeMillis();
        while (true) {
            if (!paused && System.currentTimeMillis() - evoTimestamp > 250) {
                evoTimestamp = System.currentTimeMillis();
                currentState = nextGeneration(currentState);
            }
            if (System.currentTimeMillis() - prevFrameTimestep > 17) {
                prevFrameTimestep = System.currentTimeMillis();

                double mouseX = StdDraw.mouseX();
                double mouseY = StdDraw.mouseY();
                int tileX = (int) mouseX;
                int tileY = (int) mouseY;

                TETile currTile = currentState[tileX % width][tileY % height];

                if (StdDraw.isMousePressed() && System.currentTimeMillis() - clickTimestamp > 250) {
                    clickTimestamp = System.currentTimeMillis();
                    if (currTile == Tileset.CELL) {
                        currentState[tileX][tileY] = Tileset.NOTHING;
                    } else {
                        currentState[tileX][tileY] = Tileset.CELL;
                    }
                }
                if (StdDraw.isKeyPressed(KeyEvent.VK_SPACE) && System.currentTimeMillis() - pausedTimestamp > 500) {
                    pausedTimestamp = System.currentTimeMillis();
                    paused = !paused;
                }
                if (StdDraw.isKeyPressed(KeyEvent.VK_S)) {
                    saveBoard();
                    System.exit(0);
                }
                ter.renderFrame(currentState);
            }
        }
    }


    /**
     * Fills the given 2D array of tiles with RANDOM tiles.
     * @param tiles
     */
    public void fillWithRandomTiles(TETile[][] tiles) {
        int height = tiles[0].length;
        int width = tiles.length;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                tiles[x][y] = randomTile();
            }
        }
    }

    /**
     * Fills the 2D array of tiles with NOTHING tiles.
     * @param tiles
     */
    public void fillWithNothing(TETile[][] tiles) {
        int height = tiles[0].length;
        int width = tiles.length;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                tiles[x][y] = Tileset.NOTHING;
            }
        }
    }

    /**
     * Selects a random tile, with a 50% change of it being a CELL
     * and a 50% change of being NOTHING.
     */
    private TETile randomTile() {
        // The following call to nextInt() uses a bound of 3 (this is not a seed!) so
        // the result is bounded between 0, inclusive, and 3, exclusive. (0, 1, or 2)
        int tileNum = random.nextInt(2);
        return switch (tileNum) {
            case 0 -> Tileset.CELL;
            default -> Tileset.NOTHING;
        };
    }

    /**
     * Returns the current state of the board.
     * @return
     */
    public TETile[][] returnCurrentState() {
        return currentState;
    }

    /**
     * At each timestep, the transitions will occur based on the following rules:
     *  1.Any live cell with fewer than two live neighbors dies, as if by underpopulation.
     *  2.Any live cell with two or three neighbors lives on to the next generation.
     *  3.Any live cell with more than three neighbors dies, as if by overpopulation,
     *  4.Any dead cell with exactly three live neighbors becomes a live cell, as if by reproduction.
     * @param tiles
     * @return
     */
    public TETile[][] nextGeneration(TETile[][] tiles) {
        TETile[][] nextGen = new TETile[width][height];
        // The board is filled with Tileset.NOTHING
        fillWithNothing(nextGen);


        // TODO: Implement this method so that the described transitions occur.
        // TODO: The current state is represented by TETiles[][] tiles and the next
        // TODO: state/evolution should be returned in TETile[][] nextGen.


        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int countsForLiveCell = getCountsForLiveCell(tiles, x, y);
                if (tiles[x][y] == Tileset.CELL) {
                    if (countsForLiveCell < 2) {
                        nextGen[x][y] = Tileset.NOTHING;
                    }
                    else if (countsForLiveCell == 2 || countsForLiveCell == 3) {
                        nextGen[x][y] = Tileset.CELL;
                    }
                    else {
                        nextGen[x][y] = Tileset.NOTHING;
                    }
                }
                else {
                    if (countsForLiveCell == 3) {
                        nextGen[x][y] = Tileset.CELL;
                    }
                }


            }
        }
        return nextGen;
    }

    private int getCountsForLiveCell(TETile[][] tiles, int x, int y) {
        int countsForLiveCell = 0;
        for (int xOffset = -1 ; xOffset <= 1 ; xOffset++) {
            for (int yOffset = -1 ; yOffset <= 1 ; yOffset++) {
                int tileX = x + xOffset;
                int tileY = y + yOffset;
                if (tileX == x && tileY == y) {
                    continue;
                }
                else if (tileX < 0 || tileX >= width || tileY < 0 || tileY >= height) {
                    continue;
                }
                else if (tiles[tileX][tileY] == Tileset.CELL) {
                    countsForLiveCell++;
                }

                }
            }
        return countsForLiveCell;
    }

    /**
     * Helper method for saveBoard without rendering and running the game.
     * @param tiles
     */
    public void saveBoard(TETile[][] tiles) {
        TETile[][] transposeState = transpose(tiles);
        this.currentState = flip(transposeState);
        this.width = tiles[0].length;
        this.height = tiles.length;
        saveBoard();
    }

    /**
     * Saves the state of the current state of the board into the
     * save.txt file (make sure it's saved into this specific file).
     * 0 represents NOTHING, 1 represents a CELL.
     */
    public void saveBoard() {
        // TODO: Save the dimensions of the board into the first line of the file.
        // TODO: The width and height should be separated by a space, and end with "\n".
        TETile[][] transposeState = transpose(currentState);
        TETile[][] result = transposeState;
        int height = result.length;
        int width = result[0].length;
        StringBuilder lines = new StringBuilder();
        lines.append(width).append(" ").append(height).append("\n");



        // TODO: Save the current state of the board into save.txt. You should
        // TODO: use the provided FileUtils functions to help you. Make sure
        // TODO: the orientation is correct! Each line in the board should
        // TODO: end with a new line character.
        for (int x = height - 1; x >= 0; x--) {
            for (int y = 0; y < width; y++) {
                if (result[x][y] == Tileset.CELL) {
                    lines.append("1");
                }
                else {
                    lines.append("0");
                }
            }
            lines.append("\n");
        }
        writeFile(SAVE_FILE, lines.toString());





    }

    /**
     * Loads the board from filename and returns it in a 2D TETile array.
     * 0 represents NOTHING, 1 represents a CELL.
     */
    public TETile[][] loadBoard(String filename) {
        // TODO: Read in the file.
        String contents = "";
        if (fileExists(filename)) {
            contents = readFile(filename);
        }

        // TODO: Split the file based on the new line character.
        String[] lines = contents.split("\n");

        // TODO: Grab and set the dimensions from the first line.
        String[] firstLine = lines[0].split(" ");

        // TODO: Create a TETile[][] to load the board from the file into
        // TODO: and any additional variables that you think might help.
        int width = Integer.parseInt(firstLine[0]);
        int height = Integer.parseInt(firstLine[1]);
        TETile[][] tiles = new TETile[width][height];

        // TODO: Load the state of the board from the given filename. You can
        // TODO: use the provided builder variable to help you and FileUtils
        // TODO: functions. Make sure the orientation is correct!
        for (int x = 0; x < height; x++) {
            char[] currentLine = lines[x + 1].toCharArray();
            for (int y = 0; y < width; y++) {
                if (currentLine[y] == '1') {
                    tiles[x][y] = Tileset.CELL;
                }
                else {
                    tiles[x][y] = Tileset.NOTHING;
                }
            }
        }



        // TODO: Return the board you loaded. Replace/delete this line.
        return tiles;
    }

    /**
     * This is where we run the program. DO NOT MODIFY THIS METHOD!
     * @param args
     */
    public static void main(String[] args) {
        if (args.length == 2) {
            // Read in the board from a file.
            if (args[0].equals("-l")) {
                GameOfLife g = new GameOfLife(args[1]);
                g.runGame();
            }
            System.out.println("Verify your program arguments!");
            System.exit(0);
        } else {
            long seed = args.length > 0 ? Long.parseLong(args[0]) : (new Random()).nextLong();
            GameOfLife g = new GameOfLife(seed);
            g.runGame();
        }
    }
}
