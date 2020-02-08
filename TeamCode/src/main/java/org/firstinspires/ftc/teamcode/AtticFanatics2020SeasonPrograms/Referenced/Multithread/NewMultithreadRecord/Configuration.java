package org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.Multithread.NewMultithreadRecord;

import com.qualcomm.robotcore.hardware.DcMotorImplEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.AtticFanatics2020SeasonPrograms.Referenced.Multithread.NewMultithreadRecord.Setup.DriveObject;

import java.util.HashMap;

public class Configuration {

    HashMap<String, DriveObject> hardware = new HashMap<>();

    Configuration(HardwareMap hwMap){
        //Add all hardware devices here.
        //Example: hardware.put("motor1", new DriveObject(DriveObject.type.DcMotorImplEx, "left_back_motor", DriveObject.classification.Drivetrain, hwMap));
        //In this example, "motor1" is whatever you want to call the motor on the code end, and "left_back_motor" is whatever your configuration says.
        hardware.put("Ingester", new DriveObject(DriveObject.type.DcMotorImplEx, "ingester", DriveObject.classification.Default, hwMap));
    }
}
