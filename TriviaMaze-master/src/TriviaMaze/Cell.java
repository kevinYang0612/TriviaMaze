package TriviaMaze;
/*
 * Assignment: Course Project "Trivia Maze"
 *
 * Instructor: Tom Capaul
 *
 * */

import java.io.Serializable;

/**
 * This is a class called "Cell", each cell is a room in the m by n maze
 *
 * @author Bohan Yang, Ian Mclean, Qinyu Tao
 * @version June 1st 2022
 */
public class Cell implements Serializable
{
    /**
     * An enum class that marks each room status
     * */
    public enum RoomStatus
    {
        UNLOCKED, LOCKED, SEALED
    }

    /** The cell status of that also means the room status */
    protected RoomStatus myStatus;

    /**
     * a setter method to change the status of a room/cell
     *
     * @param theStatus, the status we want to change
     * */
    public void setStatus(final RoomStatus theStatus)
    {
        this.myStatus = theStatus;
    }
}
