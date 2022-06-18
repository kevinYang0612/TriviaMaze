package TriviaMaze;
/*
 * Assignment: Course Project "Trivia Maze"
 *
 * Instructor: Tom Capaul
 *
 * */
import TriviaMaze.Question.Question;
import TriviaMaze.Question.ShortAnswer;
import TriviaMaze.Question.TrueFalseType;
import org.sqlite.SQLiteDataSource;
import java.io.Serializable;
import java.sql.*;

/**
 * This is a class called "Database", that connects to the SQLite database that
 * allows necessary classes to pull question.
 *
 * @author Bohan Yang, Ian Mclean, Qinyu Tao
 * @version June 1st 2022
 */
public class Database implements Serializable
{
    /** The connection instance to connect "jdbc:sqlite:trivia.db" */
    private Connection myConn = null;

    /** The statement instance to create a statement by executing a query */
    private Statement myStmt = null;

    /** The ResultSet instance to save the result after executing the query */
    private ResultSet myRs = null;

    /** The SQLiteDataSource the class we connect the SQLite database */
    private final SQLiteDataSource myDataSource = new SQLiteDataSource();

    /**
     * Constructs the Database instance by calling connect method
     * */
    protected Database()
    {
        try
        {
            connect();
        }
        catch (SQLException theE)
        {
            theE.printStackTrace();
        }
    }
    /**
     * a helper method to connect the SQLite our trivia.db file
     *
     * @throws SQLException
     * */
    private void connect() throws SQLException
    {
        //this.myDataSource.setUrl("jdbc:sqlite::resource:trivia.db");
        this.myConn = DriverManager.getConnection("jdbc:sqlite::resource:trivia.db");
        this.myStmt = this.myConn.createStatement();

        //this.myConn = this.myDataSource.getConnection();
    }

    /**
     * Selecting a True or False question from the database
     *
     * @return Question a true or false question instance
     * */
    protected Question getTrueFalseQuestion()
    {
        String qBody = null;
        String answer = null;
        try
        {
            this.myStmt = this.myConn.createStatement();
            this.myRs = this.myStmt.executeQuery("SELECT * FROM truefalse ORDER BY RANDOM() LIMIT 1;");
             qBody = this.myRs.getString("question");
             answer = this.myRs.getString("correctbool");
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return new TrueFalseType(qBody, answer);
    }

    /**
     * Selecting a Short answer question from the database
     *
     * @return Question, a short question instance
     * */
    protected Question getShortAnswerQuestion()
    {
        String qBody = null;
        String answer = null;
        try
        {
            this.myStmt = this.myConn.createStatement();
            this.myRs = this.myStmt.executeQuery("SELECT * FROM shortanswer ORDER BY RANDOM() LIMIT 1;");
            qBody = this.myRs.getString("question");
            answer = this.myRs.getString("correct");
        }
        catch (SQLException theE)
        {
            theE.printStackTrace();
        }
        return new ShortAnswer(qBody, answer);
    }
}
