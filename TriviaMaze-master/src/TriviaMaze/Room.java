package TriviaMaze;
/*
 * Assignment: Course Project "Trivia Maze"
 *
 * Instructor: Tom Capaul
 *
 * */
import TriviaMaze.Question.Question;
import java.io.Serializable;
/**
 * This is a class called "Room". It is a subclass of cell that
 * The room will contain a question
 *
 * @author Bohan Yang, Ian Mclean, Qinyu Tao
 * @version June 1st 2022
 */
public class Room extends Cell implements Serializable
{
    /** the question that will come along when entering this room. */
    private final Question myQuestion;

    /** the status that the room in the maze contains. */
    private RoomStatus myStatus;

    public Room(final Question theQuestion)
    {
        myQuestion = theQuestion;
        myStatus = RoomStatus.LOCKED;
    }
    /**
     * a getter method to return the question that the room contains
     *
     * @return the question
     * */
    public Question getMyQuestion()
    {
        return this.myQuestion;
    }

    /**
     * a getter method to return the room status that either be
     * locked, unlocked, or sealed
     *
     * @return the status of that room
     * */
    public RoomStatus getMyStatus() {return this.myStatus;}

    /**
     * a setter method to set the status to a room
     *
     * @param theStatus, the status that is going to assign to the room
     * */
    public void setStatus(final RoomStatus theStatus)
    {
        this.myStatus = theStatus;
    }

    /**
     * Unlock a room
     * */
    public void unlock()
    {
        if (this.myStatus == RoomStatus.SEALED)
        {
            throw new RuntimeException("Attempted to unlock a sealed door");
        }
        else if(this.myStatus == RoomStatus.UNLOCKED)
        {
            throw new RuntimeException("Room is already unlocked");
        }
        else
        {
            this.myStatus = RoomStatus.UNLOCKED;
        }
    }
}
