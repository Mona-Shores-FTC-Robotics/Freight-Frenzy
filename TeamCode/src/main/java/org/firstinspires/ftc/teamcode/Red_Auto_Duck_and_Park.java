/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * This file illustrates the concept of driving a path based on Gyro heading and encoder counts.
 * It uses the common Pushbot hardware class to define the drive on the robot.
 * The code is structured as a LinearOpMode
 *
 * The code REQUIRES that you DO have encoders on the wheels,
 *   otherwise you would use: PushbotAutoDriveByTime;
 *
 *  This code ALSO requires that you have a Modern Robotics I2C gyro with the name "gyro"
 *   otherwise you would use: PushbotAutoDriveByEncoder;
 *
 *  This code requires that the drive Motors have been configured such that a positive
 *  power command moves them forward, and causes the encoders to count UP.
 *
 *  This code uses the RUN_TO_POSITION mode to enable the Motor controllers to generate the run profile
 *
 *  In order to calibrate the Gyro correctly, the robot must remain stationary during calibration.
 *  This is performed when the INIT button is pressed on the Driver Station.
 *  This code assumes that the robot is stationary when the INIT button is pressed.
 *  If this is not the case, then the INIT should be performed again.
 *
 *  Note: in this example, all angles are referenced to the initial coordinate frame set during the
 *  the Gyro Calibration process, or whenever the program issues a resetZAxisIntegrator() call on the Gyro.
 *
 *  The angle of movement/rotation is assumed to be a standardized rotation around the robot Z axis,
 *  which means that a Positive rotation is Counter Clock Wise, looking down on the field.
 *  This is consistent with the FTC field coordinate conventions set out in the document:
 *  ftc_app\doc\tutorial\FTC_FieldCoordinateSystemDefinition.pdf
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

@Autonomous(name="Red_Auto Duck & Park", group="Pushbot")
@Disabled
public class Red_Auto_Duck_and_Park extends LinearOpMode {

    /* Declare OpMode members. */
    // HardwarePushbot robot   = new HardwarePushbot();   // Use a Pushbot's hardware
    ModernRoboticsI2cGyro gyro = null;                    // Additional Gyro device

    DriveTrain MecDrive = new DriveTrain();
    private ElapsedTime runtime = new ElapsedTime();
    Intake intake = new Intake();
    CarouselDuck spinner = new CarouselDuck();
    Lift lift = new Lift();

    OpenCVWebcam2 Vision = new OpenCVWebcam2();

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initializing");

        MecDrive.init(hardwareMap);
        intake.init(hardwareMap);
        lift.init(hardwareMap);
        spinner.init(hardwareMap);
        spinner.DuckArm.setPosition(spinner.arm);

       // Vision.init(hardwareMap);

        // Tell the driver that initialization is complete.
        telemetry.addData("Status", "Initialized");

        telemetry.addData(">", "Robot Ready.");
        telemetry.update();

        waitForStart();

        spinner.DuckArm.setPosition(spinner.rest);
        spinner.DuckSpinner.setPower(-0.5);
        spinner.carouselDuck();
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 3.0)) {

            telemetry.addData("Path", "Leg 1: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();

        }
        spinner.stopSpinner = true;
        spinner.DuckArm.setPosition(spinner.arm);

        sleep(2000);

        MecDrive.drive = 0.0;
        MecDrive.strafe = 0.0;
        MecDrive.turn = 1.0;
        MecDrive.MecanumDrive();
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 0.25)) {
            telemetry.addData("Path", "Leg 1: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();

        }
        MecDrive.drive = 1.0;
        MecDrive.strafe = 0.0;
        MecDrive.turn = 0.0;
        MecDrive.MecanumDrive();
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 1.75)) {
            telemetry.addData("Path", "Leg 1: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();

        }
        /**MecDrive.drive = 0.0;
        MecDrive.strafe = 0.0;
        MecDrive.turn = 1.0;
        MecDrive.MecanumDrive();
        //runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 0.5)) {
            telemetry.addData("Path", "Leg 1: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();

        }
        MecDrive.drive = 1.0;
        MecDrive.strafe = 0.0;
        MecDrive.turn = 0.0;
        MecDrive.MecanumDrive();
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 0.5)) {
            telemetry.addData("Path", "Leg 1: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();

        }

        //lift.elevator = 1.0;
        //lift.liftPosition = *low; //camera value

        intake.intake();
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 1.0)) {
            intake.Drop = true;
        }
        intake.stopIntake = true;

        MecDrive.drive = -1.0;
        MecDrive.strafe = 0.0;
        MecDrive.turn = 0.0;
        MecDrive.MecanumDrive();
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 0.2)) {
            telemetry.addData("Path", "Leg 1: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }
        MecDrive.drive = 0.0;
        MecDrive.strafe = 0.0;
        MecDrive.turn = -1.0;
        MecDrive.MecanumDrive();
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 0.5)) {
            telemetry.addData("Path", "Leg 1: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();

        }
        MecDrive.drive = 0.0;
        MecDrive.strafe = -1.0;
        MecDrive.turn = 0.0;
        MecDrive.MecanumDrive();
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 0.5)) {
            telemetry.addData("Path", "Leg 1: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }
        MecDrive.drive = 1.0;
        MecDrive.strafe = 0.0;
        MecDrive.turn = 0.0;
        MecDrive.MecanumDrive();
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 3.0)) {
            telemetry.addData("Path", "Leg 1: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
        }

    }*/
    }
}