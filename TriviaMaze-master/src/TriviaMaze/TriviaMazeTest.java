package TriviaMaze;
/*
 * Assignment: Course Project "Trivia Maze"
 *
 * Instructor: Tom Capaul
 *
 * */
import GUI.Frame;
import org.junit.*;
import java.sql.*;
import static org.junit.Assert.*;
/**
 * This is a class called "TriviaMaze". It is part of the model
 * for the trivia maze game. It includes the Rooms at 2-D array
 * current location with a character in the spot.
 *
 * @author Bohan Yang, Ian Mclean, Qinyu Tao
 * @version June 1st 2022
 */

public class TriviaMazeTest
{
    /** The controller with a maze size will get unit test on*/
    private final Controller controller = new Controller(Frame.MAZE_SIZE);

    /**
     * a default constructor, for unit test only
     * */
    public TriviaMazeTest() throws SQLException {
    }

    /**
     * test if method GenerateMaze works correctly
     * */
    @Test
    public void testGenerateMaze()
    {   //this should not be deprecated, we  are assuring that our arrays are
        //initializing properly
        assertEquals(new TriviaMaze(8).getMaze(),
                new int[][]{{1, 1, 1, 1, 1, 1, 1, 1},
                            {1, 1, 1, 0, 0, 0, 0, 1},
                            {1, 0, 1, 1, 0, 0, 0, 1},
                            {1, 0, 0, 1, 1, 0, 0, 1},
                            {1, 0, 0, 0, 1, 1, 0, 1},
                            {1, 0, 0, 0, 0, 1, 1, 1},
                            {1, 0, 0, 0, 0, 0, 1, 1},
                            {1, 1, 1, 1, 1, 1, 1, 1}});
    }

    /**
     * check if each room's status is good
     * */
    @Test
    public void testStatus()
    {
        assertEquals(Cell.RoomStatus.UNLOCKED, controller.getGameMaze().getStatus(0, 0));
        assertEquals(Cell.RoomStatus.LOCKED, controller.getGameMaze().getStatus(2, 1));
        assertEquals(Cell.RoomStatus.LOCKED, controller.getGameMaze().getStatus(5, 7));
        assertEquals(Cell.RoomStatus.SEALED, controller.getGameMaze().getStatus(6, 1));
        assertEquals(Cell.RoomStatus.SEALED, controller.getGameMaze().getStatus(4, 6));
    }
    /**
     * Test our SQLite table is not null
     * */
    @Test
    public void testRoomQuestion() throws SQLException
    {
        Database db = new Database();
        Connection myConn= DriverManager.getConnection("jdbc:sqlite::resource:trivia.db");
        Statement myStmt = myConn.createStatement();
        ResultSet myRs = myStmt.executeQuery("SELECT * FROM truefalse ;");
        assertNotEquals(null, myRs);
    }

    /**
     * Test if the movement in maze is correct
     * */
    @Test
    public void testMovementInMaze()
    {
        TriviaMaze t = new TriviaMaze(Frame.MAZE_SIZE);
        t.changeDirection(3,3);
        assertEquals(3,t.getMyX());
        assertEquals(3,t.getMyY());
    }

    /**
     * Test if the game over method works correctly
     * */
    @Test
    public void testGameOver()
    {
        TriviaMaze t = new TriviaMaze(Frame.MAZE_SIZE);
        t.changeDirection(t.getMySize()-1,t.getMySize()-1);
        assertTrue(t.isGameOver());
    }

    /**
     * Test if the maze is going get out of bound
     * */
    @Test
    public void testInvalidMove() {
        TriviaMaze t = new TriviaMaze(Frame.MAZE_SIZE);
        t.changeDirection(-1,-2);//the TriviaMaze should interpret this as bad input
        assertEquals(0,t.getMyX());//coordinates should not have changed
        assertEquals(0,t.getMyY());
    }
}
