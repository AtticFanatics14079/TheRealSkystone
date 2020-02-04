package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Vision;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

//WE ARE STARTING IN THE MIDDLE OF THE TILE THAT IS ABOVE THE RED DEPOT

@TeleOp(name="getSkystoneUse")

public class getSkystoneUse extends OpMode {
    //MecanumDrive robot = new MecanumDrive();
    SKystoneMethodMaybe camera = new SKystoneMethodMaybe();
    int skysposition;

    @Override
    public void init() {
        /*
        robot.setGrip(.1,hardwareMap);
        waitForStart();
        //Forward, then look for skystone.

        robot.MoveEncoderTicks(70,0,1,hardwareMap);
     */
        camera.PassHWMap(hardwareMap);
    }

    @Override
    public void loop() {
        skysposition = camera.getSkystonePosition();
        switch (skysposition) {
            case 0:
                System.out.println(" it IS left");
                break;
            case 1:
                System.out.println((" it IS Mid"));
                break;
            case 2:
                System.out.println(" it IS Right");
                break;
        }
        //robot.MoveEncoderTicks(-70,0,-1,hardwareMap);
    }
}
