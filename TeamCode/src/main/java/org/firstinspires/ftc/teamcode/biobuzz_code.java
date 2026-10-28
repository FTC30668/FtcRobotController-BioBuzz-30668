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

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Original FTC opmode header block
 *
 * This file contains an minimal example of a Linear "OpMode". An OpMode is a 'program' that runs in either
 * the autonomous or the teleop period of an FTC match. The names of OpModes appear on the menu
 * of the FTC Driver Station. When an selection is made from the menu, the corresponding OpMode
 * class is instantiated on the Robot Controller and executed.
 *
 * This particular OpMode just executes a basic Tank Drive Teleop for a two wheeled robot
 * It includes all the skeletal structure that all linear OpModes contain.
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 **/

/** Parent OpMode Class
 * All Teleop and Autonomous OpModes should inherit from (extend) ParentOpMode.
 * Each child/subclass OpMode should have its own unique runOpMode() method that will
 * override the ParentOpMode runOpMode() method.
 **/

@TeleOp(name="Parent Opmode Example", group="Linear Opmode")
@Disabled
public class biobuzz_code extends LinearOpMode {

    // Declare OpMode members, hardware variables
    public ElapsedTime runtime = new ElapsedTime();

    private DcMotor RightDrivetrain = null; // :}
    private DcMotor Intake = null;
    private DcMotor LeftDrivetrain = null;
    private DcMotorEx Flywheel = null;
    private CRServo pinwheel = null;
    private CRServo LeftServo = null;

    private CRServo RightServo = null;
    private DcMotorSimple RightMW = null;
    private DcMotorSimple LeftMW = null;



    //Other Global Variables
    //put global variables here...
    //
    double encoderpulsesperrev=537.7;
    boolean poxOn = false;

    //init
    public void initialize(){
        // Initialize the hardware variables. Note that the strings used here as parameters
        // to 'get' must correspond to the names assigned during the robot configuration
        // step (using the FTC Driver Station app or Driver Hub).
        Intake = hardwareMap.get(DcMotor.class, "intake");
        RightDrivetrain = hardwareMap.get(DcMotor.class, "right_drivetrain");
        LeftDrivetrain = hardwareMap.get(DcMotor.class,"left_drivetrain");
        Flywheel = hardwareMap.get(DcMotorEx.class, "flywheel");

        pinwheel = hardwareMap.get(CRServo.class,"center_wheel");
        LeftServo = hardwareMap.get(CRServo.class,"left_intake");
        RightServo = hardwareMap.get(CRServo.class,"center_wheel");

        RightMW = hardwareMap.get(DcMotorSimple.class,"left_intake");
        LeftMW = hardwareMap.get(DcMotorSimple.class,"center_wheel");


        //Set Motor  and servo Directions
        Intake.setDirection(DcMotor.Direction.FORWARD);
        RightDrivetrain. setDirection(DcMotor.Direction.FORWARD);
        LeftDrivetrain.setDirection(DcMotor.Direction.REVERSE);
        Flywheel.setDirection(DcMotorEx.Direction.FORWARD);

        pinwheel.setDirection(CRServo.Direction.FORWARD);
        LeftServo.setDirection(CRServo.Direction.REVERSE);
        RightServo.setDirection(CRServo.Direction.FORWARD);

        RightMW.setDirection(DcMotorSimple.Direction.FORWARD);
        LeftMW.setDirection(DcMotorSimple.Direction.REVERSE);



        //Set brake or coast modes.
        Intake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE); //BRAKE or FLOAT (Coast)
        RightDrivetrain.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE); //BRAKE or FLOAT (Coast)
        LeftDrivetrain.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Flywheel.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.FLOAT);
        //Update Driver Station Status Message after init
        telemetry.addData("Status:", "Initialized");
        telemetry.update();
    }

    /**
     * runOpMode() will be overridden in child OpMode.
     * Basic structure should remain intact (init, wait for start, while(opModeIsActive),
     * Additionally, Emergency conditions should be checked during every cycle
     */
    @Override
    public void runOpMode() {

        initialize();

        // Init loop - optional
        while(opModeInInit()){
            // Code in here will loop continuously until OpMode is started
        }

        // Wait for the game to start (driver presses PLAY) - May not be needed if using an init Loop
        waitForStart();
        runtime.reset();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            // code here should never actually execute in parent opmode.
            // This function will be be overridden by child opmode classes


            //include emergency stop check in all runOpMode() functions/methods
            //implementation depends on which E-stop function will be used (boolean/void)
            if(emergencyStopped()){
                terminateOpModeNow();   // New method in 2022. (Immediately, Cleanly exits OpMode)
                break;
            }

//            checkEmergencyStop(); // Stops motors and Terminates if buttons are pressed
            //without additional code in the while(opModeIsActive) loop.

            telemetry.update();
        }
    }

    /*****************************/
    //Controls should be mapped here to avoid accessing gamepad directly in other functions/methods
    //This also makes it simpler to re-map controls as desired
    //CONTROLLER MAP

    // Thumbsticks
    public double left_sticky_x(){
        return gamepad1.left_stick_x;
    }
    public double left_sticky_y() { return -gamepad1.left_stick_y;}


    public double right_sticky_x(){
        return gamepad1.right_stick_x;
    }
    public double right_sticky_y() { return -gamepad1.right_stick_y;}


    // Buttons
    public boolean emergencyButtons(){
        // check for combination of buttons to be pressed before returning true
        if(gamepad1.back==true&&gamepad1.start==true){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean poxThrowerButton(){
        return gamepad1.yWasPressed();
    }

    public boolean poxthowertrigger(){
        return gamepad1.right_trigger >.5;
    }

    public boolean plagueIntakeButton(){
        return gamepad1.left_bumper;
    }
    public boolean plagueOuttakeButton(){
        return gamepad1.left_trigger_pressed;
    }


    public boolean triggerButton(){
        if((gamepad1.right_trigger>.25)||(gamepad2.right_trigger>.25)){
            return true;         // Converts analog triggers into digital button presses (booleans)
        }
        else{
            return false;
        }
    }


    /****************************/
    // Emergency Stop Functions
    // Only one of these is needed.
    // If using boolean version, call to function will need to be
    // placed in conditional (if/then) statement with code to break from loop or terminate opmode.

    public boolean emergencyStopped(){
        if (emergencyButtons()) {
            //stop all motors, servos, etc.
            stopDrive();
            return true;
        }
        else {
            return false;
        }
    }

    /*public void checkEmergencyStop(){
        if(emergencyButtons()){
            //stop all motors, servos, etc.
            terminateOpModeNow();   // Force exit of OpMode
        }
    }*/



    /*****************************/
    //Drive Methods

    // Assign left and right drive speed using arguments/parameters rather than hardcoding
    // thumb stick values inside function body. This will allow tank drive to be reused for
    // autonomous programs without additional work
    public void tankdrive(double left, double right){
        RightDrivetrain.setPower(right);
        LeftDrivetrain.setPower(left);
        RightMW.setPower(right);
        LeftMW.setPower(left);
    }
    public void stopDrive(){
        tankdrive(0,0);
    }
    //I am made of bees. ;) bbbbbbbbbbbbbzzzzzzzzzzzzzz


    /*****************************/
    //Shooter/Pox-Thrower Functions

    public void yosamysam(){
        double VUNITS=1000;  // Launcher/Pox-thrower Speed
        double sideThrowSpeed = 0.5;
        if(poxThrowerButton()){poxOn = !poxOn;}
        if(!poxOn){
            Flywheel.setVelocity(0);

        }else{
            Flywheel.setPower(VUNITS);


        }
        if (poxthowertrigger()){
            pinwheel.setPower(sideThrowSpeed);

        }
        else{
            pinwheel.setPower(0);

        }

    }






    /*****************************/
    //More Methods (Functions)

    public void chargeIntake(float speed){
        if(plagueIntakeButton()){
            Intake.setPower(speed);}else if(plagueOuttakeButton()){
            Intake.setPower(speed*-1);}else{
            Intake.setPower(0);}
    }

    /*****************************/
    //Autonomous Functions
    public void autonomousPlague(float time,double endL,double laysR ){
        tankdrive(endL, laysR);
        if(!opModeIsActive()||emergencyStopped()){return;}
        sleep((long)(time*1000));
        stopDrive();
    }
    /*public void drivedistance(double inches){

/*
        double currentTime = runtime.seconds();

        Intake.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        RightDrivetrain.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        int terat = (int) (-1*0.0842*encoderpulsesperrev*inches);
        Intake.setTargetPosition(terat);
        RightDrivetrain.setTargetPosition(terat);
        Intake.setMode((DcMotor.RunMode.RUN_TO_POSITION));
        RightDrivetrain.setMode((DcMotor.RunMode.RUN_TO_POSITION));
        Intake.setPower(0.7);
        RightDrivetrain.setPower(0.7);
        while((Intake.isBusy())||(RightDrivetrain.isBusy())||runtime.seconds()>10+currentTime){
            telemetry.addData("target", terat);
            telemetry.addData(("left"), Intake.getCurrentPosition());
            telemetry.addData(("right"), RightDrivetrain.getCurrentPosition());
            telemetry.update();

        }




    }

    public void turn(double degrees, String leftright){

        Intake.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        RightDrivetrain.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        int distance = (int) (0.0123*encoderpulsesperrev*degrees);
        if(leftright=="left"){
            Intake.setTargetPosition(distance*-1);
            RightDrivetrain.setTargetPosition(distance);}
        else{
            Intake.setTargetPosition(distance);
            RightDrivetrain.setTargetPosition(distance*-1);}
        Intake.setMode((DcMotor.RunMode.RUN_TO_POSITION));
        RightDrivetrain.setMode((DcMotor.RunMode.RUN_TO_POSITION));
        Intake.setPower(0.7);
        RightDrivetrain.setPower(0.7);
        while((Intake.isBusy())||(RightDrivetrain.isBusy())){
            telemetry.addData("target", distance);
            telemetry.addData(("left"), Intake.getCurrentPosition());
            telemetry.addData(("right"), RightDrivetrain.getCurrentPosition());
            telemetry.update();
        }




    }




    public void autonomousShooting(double speed, float delay){
        for(int i=0; i<3; i++) {/*
            LeftDrivetrain.setPower(speed);
            sleep((long) (delay * 1000));
            pinwheel.setPower(speed);
            LeftServo.setPower(speed);
            sleep((long) (delay * 1000));
            if(!opModeIsActive()||emergencyStopped()){break;}
        }
    }
    //y to charge
    //right trigger to shoot


*/

}
