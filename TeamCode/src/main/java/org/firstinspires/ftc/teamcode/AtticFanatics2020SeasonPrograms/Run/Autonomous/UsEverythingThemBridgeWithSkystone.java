package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Run.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;
import org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.Autonomous.MecanumDrive;
import org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Run.TeleOp.SkyStoneVuforiaDetectorTeleop;

import java.util.ArrayList;
import java.util.List;

import static org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.DEGREES;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.XYZ;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.YZX;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesReference.EXTRINSIC;
import static org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection.BACK;

//WE ARE STARTING IN THE MIDDLE OF THE TILE THAT IS ABOVE THE RED DEPOT
public class UsEverythingThemBridgeWithSkystone extends LinearOpMode {
    MecanumDrive robot = new MecanumDrive();

    private static final VuforiaLocalizer.CameraDirection CAMERA_CHOICE = BACK;
    private static final boolean PHONE_IS_PORTRAIT = false  ;

    private static final String VUFORIA_KEY =
            "AcZJwA3/////AAABmb6AM3k1hkSnsGWrryRhRLQCrOez+E99u24y3SgIGlOjWUvGXDqPV0zKyc0F3doTGrWj9oSItlnrlNrTTFiqOE5U4JXeiOazV7ZpECzfSA/8XylXnksGO2Srtq+agqx+uNpwWBO6dbdJKTwWgNgYehamWdqRKxmmDirCwz76U80QfpdLt45zI32+VqD/Z61MTGdNCvP89+LUUaUPFk5KMznJKOD7ZcfmJemzrYeNLDR7sTsX9I927do5O5LzV4BOPqDnx5lJkBjO+UclTYjzZHfc8EGC9+ozbTZXdF5135B9jqQAFqK+Mq+XE5yjyiS34pyQpPLNsnvGI+uM+WuEh7nyhLRr8VPogPgjPkfu16WQ";

    // Since ImageTarget trackables use mm to specifiy their dimensions, we must use mm for all the physical dimension.
    // We will define some constants and conversions here
    private static final float mmPerInch        = 25.4f;
    private static final float mmTargetHeight   = (6) * mmPerInch;          // the height of the center of the target image above the floor

    // Constant for Stone Target
    private static final float stoneZ = 2.00f * mmPerInch;

    // Constants for the center support targets
    private static final float bridgeZ = 6.42f * mmPerInch;
    private static final float bridgeY = 23 * mmPerInch;
    private static final float bridgeX = 5.18f * mmPerInch;
    private static final float bridgeRotY = 59;                                 // Units are degrees
    private static final float bridgeRotZ = 180;

    // Constants for perimeter targets
    private static final float halfField = 72 * mmPerInch;
    private static final float quadField  = 36 * mmPerInch;

    // Class Members
    private OpenGLMatrix lastLocation = null;
    private VuforiaLocalizer vuforia = null;
    private boolean targetVisible = false;
    private float phoneXRotate    = 0;
    private float phoneYRotate    = 0;
    private float phoneZRotate    = 0;



    @Override
    public void runOpMode() throws InterruptedException{
        robot.setGrip(.1,hardwareMap);
        waitForStart();
        //Forward, then look for skystone.
        robot.MoveEncoderTicks(70,0,1,hardwareMap);
        int SkystonePosition = -1;

        /*
         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
         * We can pass Vuforia the handle to a camera preview resource (on the RC phone);
         * If no camera monitor is desired, use the parameter-less constructor instead (commented out below).
         */
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);

        // VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraDirection   = CAMERA_CHOICE;

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        // Load the data sets for the trackable objects. These particular data
        // sets are stored in the 'assets' part of our application.
        VuforiaTrackables targetsSkyStone = this.vuforia.loadTrackablesFromAsset("Skystone");

        VuforiaTrackable stoneTarget = targetsSkyStone.get(0);
        stoneTarget.setName("Stone Target");


        // For convenience, gather together all the trackable objects in one easily-iterable collection */
        List<VuforiaTrackable> allTrackables = new ArrayList<VuforiaTrackable>();
        allTrackables.addAll(targetsSkyStone);


        stoneTarget.setLocation(OpenGLMatrix
                .translation(0, 0, stoneZ)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, 90, 0, -90)));

        //Set the position of the bridge support targets with relation to origin (center of field)

        //Set the position of the perimeter targets with relation to origin (center of field)


        // We need to rotate the camera around it's long axis to bring the correct camera forward.
        if (CAMERA_CHOICE == BACK) {
            phoneYRotate = -90;
        } else {
            phoneYRotate = 90;
        }

        // Rotate the phone vertical about the X axis if it's in portrait mode
        if (PHONE_IS_PORTRAIT) {
            phoneXRotate = 90 ;
        }

        // Next, translate the camera lens to where it is on the robot.
        // In this example, it is centered (left to right), but forward of the middle of the robot, and above ground level.
        final float CAMERA_FORWARD_DISPLACEMENT  = 4.0f * mmPerInch;   // eg: Camera is 4 Inches in front of robot center
        final float CAMERA_VERTICAL_DISPLACEMENT = 8.0f * mmPerInch;   // eg: Camera is 8 Inches above ground
        final float CAMERA_LEFT_DISPLACEMENT     = 0;     // eg: Camera is ON the robot's center line

        OpenGLMatrix robotFromCamera = OpenGLMatrix
                .translation(CAMERA_FORWARD_DISPLACEMENT, CAMERA_LEFT_DISPLACEMENT, CAMERA_VERTICAL_DISPLACEMENT)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, YZX, DEGREES, phoneYRotate, phoneZRotate, phoneXRotate));

        /**  Let all the trackable listeners know where the phone is.  */
        for (VuforiaTrackable trackable : allTrackables) {
            ((VuforiaTrackableDefaultListener) trackable.getListener()).setPhoneInformation(robotFromCamera, parameters.cameraDirection);
        }

        // WARNING:
        // In this sample, we do not wait for PLAY to be pressed.  Target Tracking is started immediately when INIT is pressed.
        // This sequence is used to enable the new remote DS Camera Preview feature to be used with this sample.
        // CONSEQUENTLY do not put any driving commands in this loop.
        // To restore the normal opmode structure, just un-comment the following line:

        // waitForStart();

        // Note: To use the remote camera preview:
        // AFTER you hit Init on the Driver Station, use the "options menu" to select "Camera Stream"
        // Tap the preview window to receive a fresh image.

        targetsSkyStone.activate();

        while (SkystonePosition == -1) {

            // check all the trackable targets to see which one (if any) is visible.
            targetVisible = false;
            for (VuforiaTrackable trackable : allTrackables) {
                if (((VuforiaTrackableDefaultListener)trackable.getListener()).isVisible()) {
                    telemetry.addData("Visible Target", trackable.getName());

                    if(trackable.getName().equals("Stone Target")){
                        telemetry.addLine("Skystone is visible");
                    }

                    targetVisible = true;

                    // getUpdatedRobotLocation() will return null if no new information is available since
                    // the last time that call was made, or if the trackable is not currently visible.
                    OpenGLMatrix robotLocationTransform = ((VuforiaTrackableDefaultListener)trackable.getListener()).getUpdatedRobotLocation();
                    if (robotLocationTransform != null) {
                        lastLocation = robotLocationTransform;
                    }
                    break;
                }
            }

            // Provide feedback as to where the robot is located (if we know).
            String positionSkystone = "";

            if (targetVisible) {
                // express position (translation) of robot in inches.
                VectorF translation = lastLocation.getTranslation();
                telemetry.addData("Pos (in)", "{X, Y, Z} = %.1f, %.1f, %.1f",
                        translation.get(0) / mmPerInch, translation.get(1) / mmPerInch, translation.get(2) / mmPerInch);

                double xPosition = translation.get(1);
                if(xPosition < 0){
                    positionSkystone = "left";
                    SkystonePosition = 0;

                }else{
                    positionSkystone = "center";
                    SkystonePosition = 1;
                }




                // express the rotation of the robot in degrees.
                Orientation rotation = Orientation.getOrientation(lastLocation, EXTRINSIC, XYZ, DEGREES);
                telemetry.addData("Rot (deg)", "{Roll, Pitch, Heading} = %.0f, %.0f, %.0f", rotation.firstAngle, rotation.secondAngle, rotation.thirdAngle);
            }
            else {
                telemetry.addData("Visible Target", "none");
                positionSkystone = "right";
                SkystonePosition = 2;
            }
            telemetry.addData("Skystone Position ", positionSkystone);
            telemetry.update();
        }

        // Disable Tracking when we are done;
        targetsSkyStone.deactivate();


        //int SkystonePosition = 2;
        System.out.println(SkystonePosition);
        sleep(1000);
        //Strafe in front of skystone, pick it up
        //Turn left, drive to foundation
        if(SkystonePosition == 0){ // left
            robot.MoveEncoderTicks(20,-1,0,hardwareMap);
            sleep(1000);
            //Extend Gripper
            //Open Hand
            //Lower
            //Close Hand
            //Retract
            //Raise
            robot.TurnDegreesEncoder(-90,hardwareMap);
            robot.MoveEncoderTicks(230,0,1,hardwareMap);
        } else if(SkystonePosition == 1){ // center
            sleep(1000);
            //Extend Gripper
            //Open Hand
            //Lower
            //Close Hand
            //Retract
            //Raise
            robot.TurnDegreesEncoder(-90,hardwareMap);
            robot.MoveEncoderTicks(250,0,1,hardwareMap);
        } else if(SkystonePosition ==2){ // right
            robot.MoveEncoderTicks(20,1,0,hardwareMap);
            sleep(1000);
            //Extend Gripper
            //Open Hand
            //Lower
            //Close Hand
            //Retract
            //Raise
            robot.TurnDegreesEncoder(-90,hardwareMap);
            robot.MoveEncoderTicks(270,0,1,hardwareMap);
        }

        //Turn Right, drop block
        robot.TurnDegreesEncoder(90,hardwareMap);
        sleep(1000);
        //Extend
        //Open Hand
        //retract
        robot.TurnDegreesEncoder(90,hardwareMap);

        //Drive to second skystone, turn to face it, pick it up
        if(SkystonePosition == 0){
            robot.MoveEncoderTicks(305,0,1,hardwareMap);
        }
        else if(SkystonePosition == 1){
            robot.MoveEncoderTicks(325,0,1,hardwareMap);
        }
        else if(SkystonePosition == 2){
            robot.MoveEncoderTicks(345,0,1,hardwareMap);
        }
        robot.TurnDegreesEncoder(-90,hardwareMap);
        sleep(1000);
        //Extend
        //Lower
        //Close hand
        //Raise
        //Retract

        //Turn, Drive to foundation with second skystone, drop it off
        robot.TurnDegreesEncoder(-90,hardwareMap);
        if(SkystonePosition == 0){
            robot.MoveEncoderTicks(290,0,1,hardwareMap);
        }
        else if(SkystonePosition == 1){
            robot.MoveEncoderTicks(310,0,1,hardwareMap);
        }
        else if(SkystonePosition == 2){
            robot.MoveEncoderTicks(330,0,1,hardwareMap);
        }
        System.out.println("Arrived at foundation");
        robot.TurnDegreesEncoder(90,hardwareMap);
        System.out.println("Dropped 2nd Skystone");
        //Extend
        //Open Hand

        //Drive up to foundation, Grip it, Drive back into wall
        robot.MoveEncoderTicks(15,0,1,hardwareMap);
        sleep(500);
        //Foundation hooks down
        robot.MoveEncoderTicks(90,0,-1,hardwareMap);
        sleep(500);
        //Foundation hooks up
        System.out.println("Foundation moved");

        //Under Bridge, on Neutral Side
        robot.MoveEncoderTicks(70,1,0,hardwareMap);
        robot.MoveEncoderTicks(70,0,1,hardwareMap);
        robot.MoveEncoderTicks(65,1,0,hardwareMap);
    }
}
