package GUI;
/*
 * Assignment: Course Project "Trivia Maze"
 *
 * Instructor: Tom Capaul
 *
 * */
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
/**
 * This is a Music class that allows us to get a music and playing in the background
 *
 * @author Bohan Yang, Ian Mclean, Qinyu Tao
 * @version June 5th 2022
 */
class Music
{
    /** music clip*/
    private static Clip myClip;
    
    /**
     * Constructs the Music class that accepting a file path to initialize myClip
     *  
     * @param thePath, the music we are going to use for the background
     */
    public Music(URL thePath)
    {
        try
        {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(thePath);
            myClip = AudioSystem.getClip();
            myClip.open(audioStream);
        }
        catch (IOException | UnsupportedAudioFileException | LineUnavailableException e)
        {
            e.printStackTrace();
        }
    }
    /**
     * this method allows us user to change the background music volumn
     * 
     * @param theNewVolume, the volumn that user wants to set up
     * @throw IllegalAccessException, now allow the volumn is less 0 or greater than 100
     */
    public static void changeVolume(int theNewVolume) throws IllegalAccessException
    {
        if (theNewVolume < 0 || theNewVolume >100)
        {
            throw new IllegalAccessException();
        }
        FloatControl fC=(FloatControl) myClip.getControl(FloatControl.Type.MASTER_GAIN);
        fC.setValue(20.0f * (float) Math.log10(theNewVolume /100.0));
    }
    
    /**
     * this method allows the background music to repeatedly play after ends
     */
    public void loop()
    {
        myClip.setFramePosition(0);
        myClip.loop(Clip.LOOP_CONTINUOUSLY);
    }
}
