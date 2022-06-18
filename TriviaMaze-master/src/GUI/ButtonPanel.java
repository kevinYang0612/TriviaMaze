package GUI;
/*
 * Assignment: Course Project "Trivia Maze"
 *
 * Instructor: Tom Capaul
 *
 * */
import TriviaMaze.Cell;
import TriviaMaze.Room;

import javax.swing.*;

import java.awt.*;
import java.io.Serializable;

import static GUI.Frame.*;
/**
 * This is a subclass called "ButtonPanel" extended JPanel class implement Serializable
 * The ButtonPanel has the buttons and labels that indicate information.
 *
 * @author Bohan Yang, Ian Mclean, Qinyu Tao
 * @version June 1st 2022
 */
public class ButtonPanel extends JPanel implements Serializable
{
    /**
     * Constructs a ButtonPanel that has JLabel to tell us information
     * and has buttons that allows player to do up, down, left, right, and enter the answer
     * */
    public ButtonPanel()
    {
        JLabel title = new JLabel("Please choose a direction:");
        title.setForeground(Color.BLUE);
        title.setFont(new Font("Tahoma",Font.BOLD,14));
        JButton up = upButton();
        JButton down = downButton();
        JButton right = rightButton();
        JButton left = leftButton();
        JLabel hint = new JLabel("Hint: You can go back to any White Room without answering questions.");
        hint.setForeground(Color.BLUE);
        hint.setFont(new Font("Tahoma",Font.BOLD,14));
        this.add(title);
        this.add(up);
        this.add(down);
        this.add(left);
        this.add(right);
        this.add(hint);
        this.setOpaque(false);
    }
    /**
     * private helper to constructs the left Bottom
     *
     * @return JButton, left Button
     * */
    private JButton leftButton()
    {
        JButton left = new JButton("Left");
        styleButtons(left);
        left.addActionListener(e ->
        {
            myTextBoxes.clearText();
            Room r = Frame.myController.findRoom("w");
            if (r.getMyQuestion() != null && r.getMyStatus() == Cell.RoomStatus.LOCKED)
            {
                myTextBoxes.addQuestionText(r.getMyQuestion().getQuestion());
                myTextBoxes.myCurrentAnswer = r.getMyQuestion().getCorrectAnswer();
                myCur = "w";
            }
            else if (r.getMyStatus() == Cell.RoomStatus.UNLOCKED)
            {
                myController.askDirection("w");
                Frame.myMazeView.decrementX();
                Frame.myMazeView.repaint();
            } else
            {
                myTextBoxes.addResultText("This door is sealed.");
                myCur = null;
            }
        });
        return left;
    }

    /**
     * private helper to constructs the right Bottom
     *
     * @return JButton, right Button
     * */
    private JButton rightButton() {
        JButton right = new JButton("Right");
        styleButtons(right);
        right.addActionListener(e ->
        {
            myTextBoxes.clearText();
            Room r = Frame.myController.findRoom("e");
            if (r.getMyQuestion() != null && r.getMyStatus() == Cell.RoomStatus.LOCKED)
            {
                myTextBoxes.addQuestionText(r.getMyQuestion().getQuestion());
                myTextBoxes.myCurrentAnswer = r.getMyQuestion().getCorrectAnswer();
                myCur = "e";
            }
            else if (r.getMyStatus() == Cell.RoomStatus.UNLOCKED)
            {
                myController.askDirection("e");
                Frame.myMazeView.incrementX();
                Frame.myMazeView.repaint();
            }
            else
            {
                myTextBoxes.addResultText("This door is sealed.");
                myCur = null;
            }
        });
        return right;
    }
    /**
     * private helper to constructs the down Bottom
     *
     * @return JButton, down Button
     * */
    private JButton downButton()
    {
        JButton down = new JButton("Down");
        styleButtons(down);
        down.addActionListener(e ->
        {
            myTextBoxes.clearText();
            Room r = Frame.myController.findRoom("s");
            if (r.getMyQuestion() != null && r.getMyStatus() == Cell.RoomStatus.LOCKED)
            {
                myTextBoxes.addQuestionText(r.getMyQuestion().getQuestion());
                myTextBoxes.myCurrentAnswer = r.getMyQuestion().getCorrectAnswer();
                myCur = "s";
            }
            else if (r.getMyStatus() == Cell.RoomStatus.UNLOCKED)
            {
                myController.askDirection("s");
                Frame.myMazeView.incrementY();
                Frame.myMazeView.repaint();
            }
            else
            {
                myTextBoxes.addResultText("This door is sealed.");
                myCur = null;
            }
        });
        return down;
    }
    /**
     * private helper to constructs the up Bottom
     *
     * @return JButton, up Button
     * */
    private JButton upButton() {
        JButton up = new JButton("Up");
        styleButtons(up);
        up.addActionListener(e ->
        {
            myTextBoxes.clearText();
            Room r = Frame.myController.findRoom("n");
            if (r.getMyQuestion() != null && r.getMyStatus() == Cell.RoomStatus.LOCKED)
            {
                myTextBoxes.addQuestionText(r.getMyQuestion().getQuestion());
                myTextBoxes.myCurrentAnswer = r.getMyQuestion().getCorrectAnswer();
                myCur = "n";
            }
            else if (r.getMyStatus() == Cell.RoomStatus.UNLOCKED)
            {
                myController.askDirection("n");
                myMazeView.decrementY();
                myMazeView.repaint();
            }
            else
            {
                myTextBoxes.addResultText("This door is sealed.");
                myCur = null;
            }
        });
        return up;
    }
    /**
     * private helper to make the user-friendly style of the buttons
     *
     * @param theButton, the buttons are getting styles
     * */
    private static void styleButtons(final JButton theButton)
    {
        theButton.setBackground(new Color(131, 39, 145));
        theButton.setForeground(Color.BLACK);
        theButton.setFont(new Font("Tahoma", Font.BOLD, 12));
    }
}