package TriviaMaze.Question;
/*
 * Assignment: Course Project "Trivia Maze"
 *
 * Instructor: Tom Capaul
 *
 * */
import java.io.Serializable;
/**
 * This is a superclass called "Question", implement Serializable
 * this class will contain different type of question's body and answer.
 *
 * @author Bohan Yang, Ian Mclean, Qinyu Tao
 * @version June 1st 2022
 */
public abstract class Question implements Serializable
{
    /** A String type of question body */
    protected String myQuestionBody;

    /** A String type of question answer */
    protected String myCorrectAnswer;

    /**
     * Constructs the question by instantiate the question body and question answer
     *
     * @param theQuestionBody, the question body
     * @param theAnswer, the correct answer of that question
     * */
    public Question(String theQuestionBody, String theAnswer)
    {
        this.myQuestionBody = theQuestionBody;
        this.myCorrectAnswer = theAnswer;
    }

    /**
     * a getter method that is abstract that subclass will implement the body
     *
     * @return String type of the question body
     * */
    public abstract String getQuestion();

    /**
     * a getter method that is abstract that subclass will implement the body
     *
     * @return String type of correct answer
     * */
    public abstract String getCorrectAnswer();
}
