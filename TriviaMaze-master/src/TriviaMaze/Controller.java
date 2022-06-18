package TriviaMaze;
/*
 * Assignment: Course Project "Trivia Maze"
 *
 * Instructor: Tom Capaul
 *
 * */
import java.io.Serializable;
import java.sql.SQLException;
/**
 * This is a class called "Controller", that makes the updates on the trivia maze
 * and change the state of the rooms
 *
 * @author Bohan Yang, Ian Mclean, Qinyu Tao
 * @version June 1st, 2022
 */
public class Controller implements Serializable
{
    /** trivia maze instance*/
    private TriviaMaze myGameMaze;

    /**
     * Constructor controller by instantiating with a certain size
     *
     * @param theSize, the maze size, row and column
     * @throws SQLException
     * */
    public Controller(final int theSize) throws SQLException
    {
        this.myGameMaze = new TriviaMaze(theSize);
    }
    /**
     * Getter method to return the game maze
     *
     * @return the game maze
     * */
    public TriviaMaze getGameMaze()
    {
        return this.myGameMaze;
    }

    /**
     * getting a direction from the user to go either up down left and right
     *
     * @param theDirection,, String type direction
     * */
    public void askDirection(final String theDirection)
    {
        if (theDirection.equalsIgnoreCase("w"))
        {
            this.myGameMaze.changeDirection(this.myGameMaze.getMyY(), this.myGameMaze.getMyX() - 1);
        }
        else if (theDirection.equalsIgnoreCase("e"))
        {
            this.myGameMaze.changeDirection(this.myGameMaze.getMyY(), this.myGameMaze.getMyX() + 1);
        }
        else if (theDirection.equalsIgnoreCase("s"))
        {
            this.myGameMaze.changeDirection(this.myGameMaze.getMyY() + 1, this.myGameMaze.getMyX());
        }
        else if (theDirection.equalsIgnoreCase("n"))
        {
            this.myGameMaze.changeDirection(this.myGameMaze.getMyY() - 1, this.myGameMaze.getMyX());
        }
        System.out.println(this.myGameMaze);
    }

    /**
     * To get the room that is moving next to by getting the direction
     *
     * @param theDirection, the direction that is moving to
     * @return a room that is going to move to
     * */
    public Room findRoom(final String theDirection)
    {
        Room room = null;
        if (theDirection.equalsIgnoreCase("w"))
        {
            room = this.myGameMaze.getRoom(this.myGameMaze.getMyY(), this.myGameMaze.getMyX() - 1);
        }
        else if (theDirection.equalsIgnoreCase("e"))
        {
            room = this.myGameMaze.getRoom(this.myGameMaze.getMyY(), this.myGameMaze.getMyX() + 1);
        }
        else if (theDirection.equalsIgnoreCase("s"))
        {
            room = this.myGameMaze.getRoom(this.myGameMaze.getMyY() + 1, this.myGameMaze.getMyX());
        }
        else if (theDirection.equalsIgnoreCase("n"))
        {
            room = this.myGameMaze.getRoom(this.myGameMaze.getMyY() - 1, this.myGameMaze.getMyX());
        }
        return room;
    }
    /** Setter method to set the maze*/
    public void setMyGameMaze(TriviaMaze theMaze) {this.myGameMaze = theMaze;}
}