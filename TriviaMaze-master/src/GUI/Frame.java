package GUI;
/*
 * Assignment: Course Project "Trivia Maze"
 *
 * Instructor: Tom Capaul
 *
 * */
import TriviaMaze.Controller;
import TriviaMaze.TriviaMaze;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.SQLException;
import java.util.Objects;
/**
 * This is a subclass called "Frame" extended JFrame class implement Serializable
 * The Frame of the whole GUI, that will contain the Maze Panel, Text label, Menus, Buttons
 * and any other useful utilities
 *
 * @author Bohan Yang, Ian Mclean, Qinyu Tao
 * @version June 1st 2022
 */
public class Frame extends JFrame implements Serializable
{

    /** The maze width and height of the trivia maze*/
    public final static int MAZE_SIZE = 8;

    /** The text panel that allows user be able to put answer in the text box */
    protected static TextPanel myTextBoxes;

    /**
     * Making the controller be static and allow the manipulation.
     * */
    static Controller myController;
    static
    {
        try
        {
            myController = new Controller(MAZE_SIZE);
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }
    /**
     * Making the Maze Panel of the entire trivia maze to be static
     * */
    static MazePanel myMazeView;
    static
    {
        try
        {
            myMazeView = new MazePanel(myController.getGameMaze());
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
    /** The current direction you are going */
    static String myCur;

    /** the new game menu item */
    private JMenuItem myNewMenuItem;

    /** the save menu item */
    private JMenuItem mySaveMenuItem;

    /** the load menu item */
    private JMenuItem myLoadMenuItem;

    /** the exit menu item */
    private JMenuItem myExitMenuItem;

    /** the about menu item*/
    private JMenuItem myAboutMenuItem;

    /** the instruction menu item*/
    private JMenuItem myInstructionMenuItem;

    /** volume slider minimum */
    private static final int VOLUME_MINIMUM = 0;

    /** volume slider maximum */
    private static final int VOLUME_MAXIMUM = 100;

    /** volume slider major spacing */
    private static final int MAJOR_SPACING = 25;

    /** volume slider minor spacing */
    private static final int MINOR_SPACING = 5;

    /** volume slider initial value position. */
    private static final int VOLUME_INITIAL = 50;

    /** background music */
    private Music backgroundMusic = new Music(getClass().getResource("/SoundSource/hobbits.wav"));



    /**
     * frame GUI constructor
     * @throws IOException
     * @throws SQLException
     */
    public Frame() throws IOException, SQLException
    {
        backgroundMusic.loop();
        this.setTitle("Maze Game");
        BufferedImage myImage = ImageIO.read(getClass().getResource("/GUIPictures/map.jpeg"));
        this.setContentPane(new ImagePanel(myImage));
        final ImageIcon uwImage = new ImageIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/GUIPictures/w.gif")))
                .getImage().getScaledInstance(60, 40, Image.SCALE_DEFAULT));
        this.setIconImage(uwImage.getImage());
        Dimension screenSize= Toolkit.getDefaultToolkit().getScreenSize();
        int height=screenSize.height*4/5;
        int width=screenSize.width;
        this.setPreferredSize( new Dimension(width,height));
        this.setResizable(true);
        this.getContentPane().setLayout(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //add menu bar
        JMenuBar myMenuBar=createMenuBar();
        this.add(myMenuBar);
        this.setJMenuBar(myMenuBar);

        this.add(myMazeView);
        myMazeView.setLocation(0, 0);

        //add button panel
        JPanel buttonPanel = new ButtonPanel();
        this.add(buttonPanel);
        buttonPanel.setBounds(500, 0, 800, 100);

        //add question/answer panel
        myTextBoxes = new TextPanel();
        this.add(myTextBoxes);
        myTextBoxes.setBounds(500, 100, 800, 300);

        this.setVisible(true);
        this.pack();
        this.setLocationRelativeTo(null);
    }

    /**
     * a method to create JMenuBar
     * @return JMenuBar
     */
    public JMenuBar createMenuBar()
    {
        final JMenuBar menuBar = new JMenuBar();
        menuBar.add(createFileMenu());
        menuBar.add(createOptionMenu());
        menuBar.add(createHelpMenu());
        return menuBar;
    }

    /**
     * a method to create file JMenu
     * it has new game, save game, load game, and exit game functions
     * @return file JMenu
     */
    public JMenu createFileMenu()
    {
        JMenu myFileMenu=new JMenu("File");
        myFileMenu.setMnemonic(KeyEvent.VK_F);

        createNewMenuItem();
        myFileMenu.add(this.myNewMenuItem);
        myFileMenu.addSeparator();

        createSaveMenuItem();
        myFileMenu.add(this.mySaveMenuItem);
        myFileMenu.addSeparator();

        createLoadMenuItem();
        myFileMenu.add(this.myLoadMenuItem);
        myFileMenu.addSeparator();

        createExitMenuItem();
        myFileMenu.add(this.myExitMenuItem);

        return myFileMenu;
    }
    /**
     * a method to create new game menu item in file JMenu
     * new game menu item allows the user to start a new game
     */
    public void createNewMenuItem()
    {
        this.myNewMenuItem=new JMenuItem("New Game");
        this.myNewMenuItem.setMnemonic(KeyEvent.VK_N);
        this.myNewMenuItem.setEnabled(true);
        this.myNewMenuItem.addActionListener(e ->
        {
            TriviaMaze newMaze=new TriviaMaze(MAZE_SIZE);
            myController.setMyGameMaze(newMaze);
            this.repaint();
            this.validate();
            this.repaint();
        });
    }

    /**
     *  a method to create save game menu item in file JMenu
     *  save menu item allows the user to save the current game state
     */
    public void createSaveMenuItem()
    {
        this.mySaveMenuItem=new JMenuItem("Save Game");
        this.mySaveMenuItem.setMnemonic(KeyEvent.VK_S);
        this.mySaveMenuItem.setEnabled(true);
        this.mySaveMenuItem.addActionListener(event -> saveActionListener());
    }

    /**
     * actionListen for save menuitem
     */
    public void saveActionListener()
    {
        FileDialog fd = new FileDialog(new JFrame(), "Save Game", FileDialog.SAVE);
        fd.setVisible(true);
        if (fd.getFile() == null) return;
        String fileName = fd.getFile();
        try
        {
            File f = new File(fd.getDirectory(), fileName);
            f.setWritable(true);
            FileOutputStream file = new FileOutputStream(f);
            ObjectOutputStream out =new ObjectOutputStream(file);
            out.writeObject(myController.getGameMaze());
            out.close();
            file.close();
        }
        catch (IOException theE)
        {
            theE.printStackTrace();
        }
    }

    /**
     * a method to create load game menu item in file JMenu
     * load menu item allows the user to load the previous game state
     */
    public void createLoadMenuItem()
    {
        this.myLoadMenuItem=new JMenuItem("Load Game");
        this.myLoadMenuItem.setMnemonic(KeyEvent.VK_L);
        this.myLoadMenuItem.setEnabled(true);
        this.myLoadMenuItem.addActionListener(e -> loadActionListener());
    }


    /**
     * actionListen for load menuitem
     */
    public void loadActionListener()
    {
        FileDialog fd = new FileDialog(new JFrame(), "Load Game", FileDialog.LOAD);
        fd.setVisible(true);
        if (fd.getFile() == null) return;
        try
        {
            File f = new File(fd.getDirectory(), fd.getFile());
            FileInputStream file=new FileInputStream(f);
            ObjectInputStream in=new ObjectInputStream(file);
            TriviaMaze maze= (TriviaMaze)in.readObject();
            myController.setMyGameMaze(maze);
            this.repaint();
            this.validate();
            this.repaint();
            in.close();
            file.close();
        }
        catch (ClassNotFoundException | IOException ex)
        {
            ex.printStackTrace();
        }
    }

    /**
     * a method to create exit menu item in file JMenu
     * exit menu item allows the user to exit the game
     */
    public void createExitMenuItem()
    {
       myExitMenuItem=new JMenuItem("Exit");
       myExitMenuItem.setMnemonic(KeyEvent.VK_E);
       myExitMenuItem.setEnabled(true);
       myExitMenuItem.addActionListener(e -> System.exit(0));
    }

    /**
     * create the option JMenu
     * it has Volume menu item
     * it can adjust the game volume
     * @return the Options menu
     */
    public JMenu createOptionMenu()
    {
        final JMenu myOptionsMenu=new JMenu("Option");
        myOptionsMenu.setMnemonic(KeyEvent.VK_O);

        JMenuItem myVolumeMenuItem = new JMenuItem("Volume");
        myVolumeMenuItem.setMnemonic(KeyEvent.VK_V);
        myOptionsMenu.add(myVolumeMenuItem);

        final JSlider volumeSlider = new JSlider(SwingConstants.HORIZONTAL,
                VOLUME_MINIMUM, VOLUME_MAXIMUM, VOLUME_INITIAL);

        volumeSlider.setMajorTickSpacing(MAJOR_SPACING);
        volumeSlider.setMinorTickSpacing(MINOR_SPACING);
        volumeSlider.setPaintLabels(true);
        volumeSlider.setPaintTicks(true);
        volumeSlider.addChangeListener(theEvent ->
        {
            try
            {
                Music.changeVolume(volumeSlider.getValue());
            }
            catch (IllegalAccessException e)
            {
                e.printStackTrace();
            }
        });
        myOptionsMenu.add(volumeSlider);

        return myOptionsMenu;
    }


    /**
     * create the Help JMenu
     * including About and Instruction two menu items
     * @return the Help menu
     */
    public JMenu createHelpMenu()
    {
        final JMenu myHelpMenu=new JMenu("Help");
        myHelpMenu.setMnemonic(KeyEvent.VK_H);

        createAboutMenuItem();
        myHelpMenu.add(this.myAboutMenuItem);
        myHelpMenu.addSeparator();

        createInstructionMenuItem();
        myHelpMenu.add(this.myInstructionMenuItem);

        return myHelpMenu;
    }

    /**
     * a method to create about menu item in About JMenu
     * this menu item shows the information about this maze game
     */
    public void createAboutMenuItem()
    {
        this.myAboutMenuItem=new JMenuItem("About");
        this.myAboutMenuItem.setMnemonic(KeyEvent.VK_A);
        this.myAboutMenuItem.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                final ImageIcon uwImage = new ImageIcon
                        (new ImageIcon(Objects.requireNonNull(getClass().getResource("/GUIPictures/w.gif")))
                                .getImage().getScaledInstance(60,40,Image.SCALE_DEFAULT));
                JOptionPane.showMessageDialog(null, """
                        <Trivia Maze Game> by\s
                        Ian McLean\s
                        Kevin Yang\s
                        Qinyu Tao""","About",JOptionPane.INFORMATION_MESSAGE,uwImage);
            }
        });
    }

    /**
     * a method to create instruction menu item in About JMenu
     * this menu item shows the instruction for this maze game
     */
    public void createInstructionMenuItem()
    {
        this.myInstructionMenuItem=new JMenuItem("Instruction");
        this.myInstructionMenuItem.setMnemonic(KeyEvent.VK_I);
        this.myInstructionMenuItem.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                final ImageIcon uwImage = new ImageIcon
                        (new ImageIcon(Objects.requireNonNull(getClass().getResource("/GUIPictures/w.gif")))
                                .getImage().getScaledInstance(60,40,Image.SCALE_DEFAULT));
                JOptionPane.showMessageDialog(null,"<Game Instruction> \n Choose a direction " +
                                "to go and answer questions. \n 1. Black Room means this room is locked." +
                                "\n 2. Blue Room can be passed by answering question." +
                                "\n     If the answer is correct, you can unlock the door and this room will be White Room." +
                                "\n     If the answer is wrong, you seal the door and this room will be Black Room." +
                                "\n 3. You can go back to any White Room without answering questions." +
                                "\n 4. If you have exhausted all directions, you lose."+
                                "\n 5. If you go to the end, you win.",
                        "Instruction",JOptionPane.INFORMATION_MESSAGE,uwImage);
            }
        });
    }

    /**
     * this method can get the maze size and used for maze Panel size
     * @return int MAZE_SIZE
     */
    public static int getMazeSize(){
        return MAZE_SIZE;
    }

    /**
     * Main method to execute this project
     *
     * @param args, command line args, not used here
     * */
    public static void main(String[] args)
    {
        EventQueue.invokeLater(() ->
        {
            try
            {
                new Frame();
            }
            catch (IOException | SQLException e)
            {
                e.printStackTrace();
            }
        });
    }
}