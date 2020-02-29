package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.Multithread.DriveObjectLibrary;

import com.qualcomm.robotcore.util.ElapsedTime;

public class Sequence implements Runnable{

    private double target, speed;
    private DriveObject drive;
    boolean toPosition;

    private Action action;
    private Sequence sequence;

    public interface Action {
        Thread runAction(); //Return null if not setting anything to a position, return the thread you are waiting on otherwise.
    }

    public Sequence(Action action, Sequence sequence){
        this.action = action;
        this.sequence = sequence;
    }

    public Sequence(Action action){
        this.action = action;
    }

    public void run(){
        if(sequence != null) sequence.run();
        Thread t = action.runAction();
        while(t.isAlive()){} //Not sure this works
    }
}
