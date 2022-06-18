package TriviaMaze;
/*
 * Assignment: Course Project "Trivia Maze"
 *
 * Instructor: Tom Capaul
 *
 * */

import java.io.Serializable;

import static TriviaMaze.Cell.RoomStatus.*;

/**
 * This is a class called "TriviaMaze". It is part of the model
 * for the trivia maze game. It includes the Rooms at 2-D array
 * current location with a character in the spot.
 *
 * @author Bohan Yang, Ian Mclean, Qinyu Tao
 * @version June 1st 2022
 */

public class TriviaMaze implements Serializable
{
    /** GenerateQuestion class instance here for a room to generate questions from database */
    private final GenerateQuestion myGenerator;

    /** a 2-D array of rooms to form the maze */
    private final Room[][] myMaze;

    private final int mySize;

    /** the current location x to keep tracking */
    private int myX;

    /** the current location y to keep tracking */
    private int myY;

    /** a boolean value to record if the game is over yet */
    private boolean myGameOver = false;


    /**
     * Construct the trivia maze by accepting the size that is passing in
     * and get ready to generate the questions
     *
     * @param theSize, the size that represents the row and column
     * */
    public TriviaMaze(final int theSize) // for developing purposes I am auto-filling rooms
    {
        this.mySize = theSize;
        this.myMaze = new Room[mySize][mySize];
        this.myGenerator = new GenerateQuestion();
        generateMaze();
    }

    /**
     * Generating the 2-D maze by iterating each row and column that is filled with
     * an individual room, each room is instantiated with a question pulled randomly
     * from database and instantiate the route, the starting point.
     * */
    private void generateMaze()
    {
        for (int row = 0; row < this.myMaze.length; row++)
        {
            for (int col = 0; col < this.myMaze.length; col++)
            {
                this.myMaze[row][col] = new Room(this.myGenerator.generateRandomQuestion());
                if (row == 0 || col == 0 ||
                        row == this.myMaze.length - 1 || col == this.myMaze.length - 1 ||
                        row - col == 0 || row - col == 1)
                {
                    this.myMaze[row][col].setStatus(LOCKED);
                }
                else
                {
                    this.myMaze[row][col].setStatus(SEALED);
                }
            }
        }
        this.myMaze[0][0].unlock();
        this.myX = 0;
        this.myY = 0;
        this.myMaze[0][0].setStatus(UNLOCKED);
    }

    /**
     * The new location that we are moving to
     *
     * @param theRow, the new row that we are moving to
     * @param theCol, the new column that we are moving to
     * */
    void changeDirection(final int theRow, final int theCol)
    {
        if (validMove(theRow, theCol))
        {
            this.myX = theCol;                           // the direction we want to move in
            this.myY = theRow;
            this.myMaze[this.myY][this.myX].setStatus(UNLOCKED);
        }
        if (this.myX == mySize-1 && this.myY == mySize-1)
        {
            this.myGameOver = true;
        }
    }
    /**
     * It checks if there is still a route to end point from current location
     *
     * @return boolean value if the game ends or not yet
     * */
    public boolean hasRoute()
    {
        int[][] maze = getMaze();
        return gameOverHelper(maze, this.myX, this.myY);
    }

    /**
     * private helper method to take a transformed int maze and current position
     * to check entire int maze
     *
     * @param theMaze, the int maze that sealed cells are 0, otherwise 1
     * @param theX, the current x coordinate
     * @param theY, the current y coordinate
     * @return boolean type if the game ends or not yet
     * */
    private boolean gameOverHelper(final int[][] theMaze, final int theX, final int theY)
    {
        if (!((theX >= 0 && theX < theMaze.length) && (theY >= 0 && theY < theMaze.length)) || theMaze[theX][theY] == 0)
        {
            return false;
        }
        if (theX == this.myMaze.length - 1 && theY == this.myMaze.length - 1)
        {
            return true;
        }
        theMaze[theX][theY] = 0;
        return gameOverHelper(theMaze, theX + 1, theY) || gameOverHelper(theMaze, theX - 1, theY)
                || gameOverHelper(theMaze, theX, theY + 1) || gameOverHelper(theMaze, theX, theY - 1);
    }

    /**
     * The maze get transformed to be a int maze, Sealed cell is 0, otherwise 1
     *
     * @return int[][], an int 2-D array
     * */
    int[][] getMaze()
    {
        int[][] maze = new int[TriviaMaze.this.myMaze.length][TriviaMaze.this.myMaze.length];
        for (int i = 0; i < maze.length; i++)
        {
            for (int j = 0; j < maze.length; j++)
            {
                switch (getStatus(i, j))
                {
                    case UNLOCKED, LOCKED -> maze[j][i] = 1;
                    case SEALED -> maze[j][i] = 0;
                }
            }
        }
        return maze;
    }
    /**
     * Helper method to check if this is a valid move, which means contain the movement
     * with in a range and do not run into a locked or sealed room.
     *
     * @param theRow, the row that is current located
     * @param theCol, the column that is current located
     *
     * @return a boolean type to indicate a possible move or not
     * */
    private boolean validMove(final int theRow, final int theCol)
    {
        return theRow >= 0 && theRow < this.myMaze.length //checking if this is in bounds
                && theCol >= 0 && theCol < this.myMaze[theRow].length
                && this.myMaze[theRow][theCol].getMyStatus() != SEALED;
    }


    /**
     * a getter method to get information if the game is over
     * @return a boolean type to indicate if the game is over
     * */
    public boolean isGameOver()
    {
        return myGameOver;
    }

    /**
     * a getter method to get the current row;
     * @return the current row.
     * */
    public int getMyX() {
        return myX;
    }
    /**
     * a getter method to get the current column;
     * @return the current column.
     * */
    public int getMyY() {
        return myY;
    }
    /**
     * a getter method to get the total rows of this maze
     * @return the row size
     * */
    public int row()
    {
        return this.myMaze.length;
    }
    /**
     * a getter method to get the total column of this maze
     * @return the column size
     * */
    public int column()
    {
        return this.myMaze.length;
    }

    /**
     * a getter method to get the status of the room of a certain row and column in the maze
     *
     * @param theRow, the row
     * @param theCol, the column
     * @return the status of the room in the maze
     * */
    public Cell.RoomStatus getStatus(final int theRow, final int theCol)
    {
        return this.myMaze[theRow][theCol].getMyStatus();
    }

    /**
     * a getter method to get a room by its row and column in the maze
     *
     * @param theRow, the row
     * @param theCol, the column
     * @return the room in the maze location
     * */
    protected Room getRoom(final int theRow, final int theCol) {
        return this.myMaze[theRow][theCol];
    }

    public int getMySize() {
        return this.mySize;
    }
}