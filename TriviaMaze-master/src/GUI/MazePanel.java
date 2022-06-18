package GUI;
/*
 * Assignment: Course Project "Trivia Maze"
 *
 * Instructor: Tom Capaul
 *
 * */
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import TriviaMaze.Cell;
import TriviaMaze.TriviaMaze;
/**
 * This is a MazePanel class that extends JPanel
 * MazePanel is mainly displaying the Maze as GUI, showing the locked room, 
 * available path, and current character
 *
 * @author Bohan Yang, Ian Mclean, Qinyu Tao
 * @version June 9th 2022
 */

public class MazePanel extends JPanel implements Serializable
{
    /** the trivia maze that from the model */
    private final TriviaMaze myMaze;
    
    /** an image that present the door */
    private final BufferedImage myDoor;
    
    /** an image icon to present the current location*/
    private final BufferedImage myIcon;

    /** the player character */
    private Character myCharacter;

    /** the x coordinate*/
    private int myXCoord = Frame.myController.getGameMaze().getMyX() * 55;
    
    /** the y coordinate*/
    private int myYCoord = Frame.myController.getGameMaze().getMyY() * 55;

    /**
     * Constructs the MazePanel with a certain size,
     * an image icon to present the current location, an image to present the end maze
     * it will be size by size maze where the size can be decided. Usually is 8 by 8
     * 
     * @param theMaze, theMaze that is the model, trivia maze.
     * @throw IOException, that will be the exception of file input exception
     */
    MazePanel(TriviaMaze theMaze) throws IOException
    {
        this.myMaze = theMaze;
        this.setSize(Frame.getMazeSize()*55, Frame.getMazeSize()*55);
        this.myIcon = ImageIO.read(getClass().getResource("/GUIPictures/Icon.png"));
        this.myDoor = ImageIO.read(getClass().getResource("/GUIPictures/door.jpeg"));
        new TexturePaint(this.myIcon, new Rectangle(0, 0, 50, 50));
        new TexturePaint(this.myDoor, new Rectangle(0, 0, 50, 50));
    }
    
    /**
     * draw the maze by painting each block one by one, white is unlocked,
     * blue is locked but needed to answer question corrected to unlocked it,
     * black is sealed, or answer question wrong will get sealed too
     * 
     * @param theG, the graphic that we are going to paint on.
     */
    private void drawMaze(Graphics theG)
    {
        this.myXCoord = Frame.myController.getGameMaze().getMyX() * 55;
        this.myYCoord = Frame.myController.getGameMaze().getMyY() * 55;
        Graphics2D graphics2D = (Graphics2D) theG;

        Image resizedDoor = null;
        for (int i = 0; i < Frame.myController.getGameMaze().row(); i++)
        {
            for (int j = 0; j < Frame.myController.getGameMaze().column(); j++)
            {
                Rectangle2D rectangle =
                        new Rectangle2D.Double(i * 55, j * 55, 55, 55);
                if (Frame.myController.getGameMaze().getStatus(j, i) == Cell.RoomStatus.UNLOCKED)
                {
                    graphics2D.setColor(Color.WHITE);
                }
                else if (Frame.myController.getGameMaze().getStatus(j, i) == Cell.RoomStatus.SEALED)
                {
                    graphics2D.setColor(Color.BLACK);
                }
                else
                    graphics2D.setColor(Color.BLUE);
                graphics2D.fill(rectangle);
                graphics2D.setPaint(Color.ORANGE);
                graphics2D.draw(rectangle);
               resizedDoor = this.myDoor.getScaledInstance(55, 55, Image.SCALE_DEFAULT);
                
                Image resizeIcon = this.myIcon.getScaledInstance(50, 50, Image.SCALE_DEFAULT);
                graphics2D.drawImage(resizeIcon, this.myXCoord, this.myYCoord, null);
            }
        }
        graphics2D.fillRect(55 * (Frame.myController.getGameMaze().row() - 1),
                55 * (Frame.myController.getGameMaze().column() - 1), 55, 55);
        graphics2D.drawImage(resizedDoor, (Frame.getMazeSize()-1)*55, (Frame.getMazeSize()-1)*55, null);
    }
    
    /**
     * Paint each component on the frame and panel
     * 
     * @param theGraphics, the graphic that we are going to paint on.
     */
    public void paintComponent(Graphics theGraphics)
    {
        super.paintComponent(theGraphics);
        this.drawMaze(theGraphics);
    }
    
    /**
     * increment x coordinate from the updates in Frame controller
     */
    public void incrementX() {
        this.myXCoord = Frame.myController.getGameMaze().getMyX() * 55;
    }
    /**
     * decrement x coordinate from the updates in Frame controller
     */
    public void decrementX() {
        this.myXCoord = Frame.myController.getGameMaze().getMyX() * 55;
    }
    /**
     * increment y coordinate from the updates in Frame controller
     */
    public void incrementY() {
        this.myYCoord =Frame.myController.getGameMaze().getMyY() * 55;
    }
    /**
     * decrement y coordinate from the updates in Frame controller
     */
    public void decrementY() {
        this.myYCoord=Frame.myController.getGameMaze().getMyY() * 55;
    }

}
