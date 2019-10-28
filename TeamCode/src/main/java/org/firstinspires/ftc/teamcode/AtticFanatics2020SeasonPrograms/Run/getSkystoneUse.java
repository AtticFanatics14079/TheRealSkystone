package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Run;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.Autonomous.MecanumDrive;
import org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Run.TeleOp.SKystoneMethodMaybe;

//WE ARE STARTING IN THE MIDDLE OF THE TILE THAT IS ABOVE THE RED DEPOT
public class getSkystoneUse extends LinearOpMode {
    MecanumDrive robot = new MecanumDrive();
    SKystoneMethodMaybe camera = new SKystoneMethodMaybe();
    @Override
    public void runOpMode() throws InterruptedException{
        robot.setGrip(.1,hardwareMap);
        waitForStart();
        //Forward, then look for skystone.
        robot.MoveEncoderTicks(70,0,1,hardwareMap);
        int skysposition;
        skysposition = camera.getSkystonePosition();
        switch (skysposition){
            case 0: System.out.println(" it IS left"); break;
            case 1: System.out.println((" it IS Mid")); break;
            case 2: System.out.println(" it IS Right"); break;
        }
        robot.MoveEncoderTicks(-70,0,-1,hardwareMap);

    }
}
