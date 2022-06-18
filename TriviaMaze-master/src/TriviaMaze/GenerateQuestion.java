package TriviaMaze;
/*
 * Assignment: Course Project "Trivia Maze"
 *
 * Instructor: Tom Capaul
 *
 * */
import TriviaMaze.Question.Question;
import java.io.Serializable;
import java.util.Random;
/**
 * This is a class called "GenerateQuestion", that connects to
 * database and randomly pull question from either true or false question or
 * short answer question
 *
 * @author Bohan Yang, Ian Mclean, Qinyu Tao
 * @version June 1st 2022
 */
public class GenerateQuestion implements Serializable
{
    /** database instance */
    private static final Database myDatabase = new Database();

    /** a random instance to randomize the question type */
    private static Random myRandom;

    /** Constructs the generateQuestion class by instantiates random */
    protected GenerateQuestion()
    {
        myRandom = new Random();
    }

    /**
     * generate a random type of question
     *
     * @return a question
     * */
    protected Question generateRandomQuestion()
    {
        return switch (myRandom.nextInt(2))
                {
                    case 0 -> createQuestion("tf");
                    case 1 -> createQuestion("sa");
                    default -> null;
                };
    }

    /**
     * a private helper method that will help to generate a question by passing in type
     *
     * @param theType, the type is either by the true or false question or short answer
     *
     * @return Question, the question that is pulled from database
     * */
    private Question createQuestion(final String theType)
    {
        return switch (theType) {
            case "tf" -> myDatabase.getTrueFalseQuestion();
            case "sa" -> myDatabase.getShortAnswerQuestion();
            default -> null;
        };
    }
}