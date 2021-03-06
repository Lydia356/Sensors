package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevColorSensorV3;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.*;
@TeleOp(name = "MecanumSample1", group = "Honors Robotics")
public class OpMode extends com.qualcomm.robotcore.eventloop.opmode.OpMode {
    String name;
    String group;
    TouchSensor touch;
    ColorSensor colorSensor;
    NormalizedColorSensor normalizedColorSensor;
    RevColorSensorV3 revColorSensorV3;

    DcMotor back_left;
    DcMotor back_right;
    DcMotor arm;
    Servo armServo;


    public void armUp() {
        arm.setPower(1);
    }

    public void armDown() {
        arm.setPower(-1);
    }

    float x = gamepad1.left_stick_x;
    float y = gamepad1.left_stick_y;
    float xr = gamepad1.right_stick_x;
    float yr = gamepad1.right_stick_y;

    public void move() {
        back_right.setPower(y);
        back_left.setPower(y);
        telemetry.update();
        telemetry.addData("The value of x is", x);
        telemetry.addData("The value of y is", y);
    }

    public void servo() {
        armServo.setPosition(xr);
    }

    public void rotate() {
        back_right.setPower(-x);
        back_left.setPower(x);
        telemetry.update();
        telemetry.addData("The value of x is", x);
        telemetry.addData("The value of y is", y);
    }

    public void stop() {
        if (gamepad1.start) {
            back_left.setPower(0);
            back_right.setPower(0);
            telemetry.update();
            telemetry.addData("stop", "Stopping");
        }
    }

    public void shutDown() {
        back_left.setPower(0);
        back_right.setPower(0);
        arm.setPower(0);
    }

    public void checkControllerCalibration() {
        telemetry.addData("Value of left stick y", y);
        telemetry.addData("Value of left stick x", x);
        if (y == 0 || x == 0) {
            if (y == 0) {
                telemetry.addData("Y equals 0", y);
            }

            if (x == 0) {
                telemetry.addData("X equals 0", x);
            }
        }
    }

    int timesButtonPressed;

    int DII = 36;
    double armUp = 7;
    double armDown = 0;
    double order = 0;
    double servoPosition = 1;
    int seconds = timesButtonPressed;

    @Override
    public void init_loop() {

        if (touch.isPressed() == true) {
            timesButtonPressed++;
        }
    }

        public void autonomous() throws InterruptedException {
            armServo.setPosition(servoPosition);
            for (int i = 0; i < timesButtonPressed; i++) {
                while (arm.getCurrentPosition() < armUp) {
                    arm.setPower(1);
                    if (arm.getCurrentPosition() == armUp) {
                        arm.setPower(0);
                        arm.setPower(-1);
                        if (arm.getCurrentPosition() == armDown) {
                            arm.setPower(0);
                            if (i == timesButtonPressed) {
                                order++;
                            }
                        }
                    }
                }
            }

            while (order == 1) {
                Thread.sleep(timesButtonPressed);
                order++;
            }

            while (order == 2) {
                back_left.setPower(0);
                back_right.setPower(0);

                back_left.getMotorType().getTicksPerRev();
                back_right.getMotorType().getTicksPerRev();

                back_left.getCurrentPosition();
                back_left.getCurrentPosition();


                double distance = 72 * DII;
                double startingPosition = back_left.getCurrentPosition();
                while (back_left.getCurrentPosition() < distance) {
                    if (colorSensor.red() > colorSensor.blue() && colorSensor.red() > colorSensor.green()) {
                        back_left.setPower(0);
                        back_right.setPower(0);
                    }
                    if (colorSensor.blue() > colorSensor.red() && colorSensor.blue() > colorSensor.green()) {
                        back_left.setPower(0.5);
                        back_right.setPower(0.5);
                    }
                    if (colorSensor.green() > colorSensor.red() && colorSensor.green() > colorSensor.blue()) {
                        back_left.setPower(1);
                        back_right.setPower(1);
                    }
                    if (back_left.getCurrentPosition() == distance) {
                        back_right.setPower(0);
                        back_left.setPower(0);
                        armServo.setPosition(0);
                    }
                    while (back_left.getCurrentPosition() > startingPosition) {
                        if (colorSensor.red() > colorSensor.blue() && colorSensor.red() > colorSensor.green()) {
                            back_left.setPower(0);
                            back_right.setPower(0);
                        }
                        if (colorSensor.blue() > colorSensor.red() && colorSensor.blue() > colorSensor.green()) {
                            back_left.setPower(-0.5);
                            back_right.setPower(-0.5);
                        }
                        if (colorSensor.green() > colorSensor.red() && colorSensor.green() > colorSensor.blue()) {
                            back_left.setPower(-1);
                            back_right.setPower(-1);
                        }
                        if (back_left.getCurrentPosition() == startingPosition) {
                            back_left.setPower(0);
                            back_right.setPower(0);
                        }
                    }
                }
            }
        }




        @Override
        public void init() {
            armServo = hardwareMap.servo.get("armServo");
            colorSensor = hardwareMap.colorSensor.get("colorSensor");
            normalizedColorSensor = (NormalizedColorSensor) hardwareMap.colorSensor.get("colorSensor");
            revColorSensorV3 = (RevColorSensorV3) hardwareMap.colorSensor.get("colorSensor");
            touch = hardwareMap.touchSensor.get("touchSensor");
            try {
                autonomous();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void loop() {
            if(colorSensor.red()>0) {
                telemetry.addData("ColorSensor Reading: Red", colorSensor.red());
                telemetry.addData("V3 Sensor Reading: Red", revColorSensorV3.getNormalizedColors().red);
                telemetry.update();
            }
            if(colorSensor.green()>0) {
                telemetry.addData("ColorSensor Reading: Blue", colorSensor.blue());
                telemetry.addData("V3 Sensor Reading: Blue", revColorSensorV3.getNormalizedColors().blue);
                telemetry.update();
            }
            if(colorSensor.blue()>0) {
                telemetry.addData("ColorSensor Reading: Green", colorSensor.green());
                telemetry.addData("V3 Sensor Reading: Green", revColorSensorV3.getNormalizedColors().green);
                telemetry.update();
            }
            y = gamepad1.left_stick_y;
            x = gamepad1.right_stick_x;
            telemetry.update();
            telemetry.addData("Y value:",y);
            telemetry.addData("X value:",x);

            if(gamepad1.y==true){
                armUp();
            }
            if(gamepad1.a==true){
                armDown();
            }

            if(gamepad1.left_bumper==true){
                stop();
            }

            while(touch.isPressed()){
                if(touch.isPressed()){
                    if(touch.isPressed()){
                        if(touch.isPressed()){
                            telemetry.update();
                            telemetry.addData("Unlocked",touch);
                        }
                    }
                }
            }

            rotate();
            move();
            servo();

        }
    }



