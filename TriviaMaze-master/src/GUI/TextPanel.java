package GUI;
/*
 * Assignment: Course Project "Trivia Maze"
 *
 * Instructor: Tom Capaul
 *
 * */
import TriviaMaze.Cell;
import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import java.util.Objects;
import static GUI.Frame.*;
/**
 * This is a subclass TextPanel that extends JPanel
 * This panel mainly will display the question and the correctness of the question
 *
 * @author Bohan Yang, Ian Mclean, Qinyu Tao
 * @version June 9th 2022
 */

public class TextPanel extends JPanel implements Serializable
{
    /** the literal "question" to indicate a question goes here*/
    private final JTextField myQuestionText;

    /** The field that user should user put the answer */
    private final JTextField myInputText;

    /** the literal "Result" to indicate the answer result*/
    private final JTextField myResultText;

    /** The question body*/
    private final JLabel myQuestion;

    /** the literal "Please type your answer" to indicate the answer goes here*/
    private final JLabel myAnswer;

    /** the result text label will display the correctness of the result*/
    private final JLabel myResult;

    /** the correct answer from the database that will compare against with user answer*/
    protected String myCurrentAnswer;

    /**
     * Construct the TextPanel for user to read and type
     * */
    public TextPanel()
    {
        this.myQuestion = new JLabel("Question:");
        this.myAnswer=new JLabel("Please type your answer:");
        this.myResult = new JLabel("Result:");
        Font font=new Font("Tahoma",Font.BOLD,14);
        myAnswer.setForeground(Color.BLUE);
        myAnswer.setFont(font);
        myQuestion.setForeground(Color.BLUE);
        myQuestion.setFont(font);
        myResult.setForeground(Color.BLUE);
        myResult.setFont(font);
        this.myQuestionText = new JTextField(55);
        this.myInputText = new JTextField(50);
        JButton myButton = new JButton("Enter");
        this.myResultText = new JTextField(50);
        this.add(this.myQuestion);
        this.add(this.myQuestionText);
        this.add(this.myAnswer);
        this.add(this.myInputText);
        this.add(myButton);
        this.add(this.myResult);
        this.add(this.myResultText);
        makeButtonListener(myButton);
        this.myQuestionText.setEnabled(true);
        this.myInputText.setEnabled(true);
        this.myResultText.setEnabled(true);
        this.setBackground(Color.LIGHT_GRAY);
        this.setBackground(new Color(0.0f, 0.0f, 0.0f, 0.0f));
    }

    /**
     * Add question text, the literal "Question: " is added
     *
     * @param theText, the "Question" literal
     * */
    public void addQuestionText(final String theText)
    {
        this.myQuestionText.setText(theText);
    }

    /**
     * Add result text, the literal "Result: " is added
     *
     * @param theText, the "Result: " literal
     * */
    public void addResultText(final String theText) {
        this.myResultText.setText(theText);
    }

    /**
     * Reset the text label to none, clear the text label
     * */
    public void clearText()
    {
        this.myInputText.setText("");
        this.myResultText.setText("");
    }

    /**
     * ActionListener implemented here that to check if the answer is correct
     * or not, then display the proper text to the user, display the information
     * like user can't go painted black cell will display certain information
     *
     * @param theButton, the button that will be left, right, up, and down
     * */
    public void makeButtonListener(final JButton theButton)
    {
        theButton.addActionListener(e ->
        {
            if (this.myInputText.getText().equalsIgnoreCase(this.myCurrentAnswer))
            {
                switch (Frame.myCur)
                {
                    case "n" ->
                            {
                                addResultText("Correct!");
                                myController.askDirection("n");
                                Frame.myMazeView.decrementY();
                            }
                    case "e" ->
                            {
                                addResultText("Correct!");
                                myController.askDirection("e");
                                Frame.myMazeView.incrementX();
                            }
                    case "s" ->
                            {
                                addResultText("Correct!");
                                myController.askDirection("s");
                                Frame.myMazeView.incrementY();
                            }
                    case "w" ->
                            {
                                addResultText("Correct!");
                                myController.askDirection("w");
                                Frame.myMazeView.decrementX();
                            }
                }
            }
            else if (myCur == null)
            {
                addResultText("You can't go this way!");
            }
            else
            {
                addResultText("Wrong! This door is locked!");
                Frame.myController.findRoom(myCur).setStatus(Cell.RoomStatus.SEALED);
                endGameLost();
            }
            endGameWon();
            Frame.myMazeView.validate();
            Frame.myMazeView.repaint();
            Frame.myCur = null;
        });
    }

    /**
     * when the game is won, will display "You win" with a picture.
     * */
    private void endGameWon()
    {
        if (myController.getGameMaze().isGameOver())
        {
            this.myResultText.setText("You Win!");
            validate();
            repaint();
            final ImageIcon winImage = new ImageIcon
                    (new ImageIcon(Objects.requireNonNull(getClass().getResource("/GUIPictures/win.png")))
                            .getImage().getScaledInstance(300,300,Image.SCALE_DEFAULT));
            JOptionPane.showMessageDialog(null,"",
                    "You Win!",JOptionPane.PLAIN_MESSAGE,winImage);
        }
    }

    /**
     * When lost, will display "you lost" with a picture
     * */
    private void endGameLost()
    {
        if (!myController.getGameMaze().hasRoute())
        {
            this.myResultText.setText("You Lose!");
            validate();
            repaint();
            final ImageIcon loseImage = new ImageIcon
                    (new ImageIcon(Objects.requireNonNull(getClass().getResource("/GUIPictures/lose.png")))
                            .getImage().getScaledInstance(300,300,Image.SCALE_DEFAULT));
            JOptionPane.showMessageDialog(null,"",
                    "You Lose!",JOptionPane.PLAIN_MESSAGE,loseImage);
        }
    }
}