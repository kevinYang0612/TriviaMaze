package GUI;
/*
 * Assignment: Course Project "Trivia Maze"
 *
 * Instructor: Tom Capaul
 *
 * */
import javax.swing.*;
import java.awt.*;
/**
 * This is a ImagePanel class that extends JComponent
 * ImagePanel will be painted as the background in the frame
 *
 * @author Bohan Yang, Ian Mclean, Qinyu Tao
 * @version June 9th 2022
 */
class ImagePanel extends JComponent
{
    /** a final Image instance that for background image*/
    private final Image myImage;

    /**
     * Constructs the ImagePanel by passing in an image
     * @param theImage, the background image
     * */
    public ImagePanel(Image theImage)
    {
        this.myImage = theImage;
    }

    /**
     * the background image will be got painted in Frame with a size
     *
     * @param theG, the graphic is used to draw the background image
     * */
    @Override
    protected void paintComponent(Graphics theG) {
        super.paintComponent(theG);
        Graphics2D graphics2D = (Graphics2D) theG;
        Image backGround = this.myImage.getScaledInstance(1525, 1030, Image.SCALE_DEFAULT);
        graphics2D.drawImage(backGround, 0, 0, this);
    }
}
