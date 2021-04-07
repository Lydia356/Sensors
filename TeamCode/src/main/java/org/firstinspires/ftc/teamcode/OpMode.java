package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.TouchSensor;

public class OpMode {
    @TeleOp(name = "MecanumSample1", group = "Honors Robotics")
    public class MecanumSample1 extends com.qualcomm.robotcore.eventloop.opmode.OpMode {
        String name;
        String group;
        TouchSensor touch;
        ColorSensor color;


        DcMotor back_left;
        DcMotor back_right;
        DcMotor arm;
        DcMotor servo;

        boolean isFullPower = true;
        float x;
        float y;

        public void move(){
            back_right.setPower(y);
            back_left.setPower(y);
            telemetry.update();
            telemetry.addData("The value of x is", x);
            telemetry.addData("The value of y is",y);
        }

        public void rotate(){
            back_right.setPower(-y);
            back_left.setPower(y);
            telemetry.update();
            telemetry.addData("The value of x is", x);
            telemetry.addData("The value of y is",y);
        }

        public void stop(){
            if (gamepad1.start) {
                back_left.setPower(0);
                back_right.setPower(0);
                telemetry.update();
                telemetry.addData("stop", "Stopping");
            }
        }

        public void checkControllerCalibration(){
            telemetry.addData("Value of left stick y", y);
            telemetry.addData("Value of left stick x", x);
            if(y == 0|| x == 0) {
                if (y == 0) {
                    telemetry.addData("Y equals 0", y);
                }

                if (x == 0) {
                    telemetry.addData("X equals 0", x);
                }
            }
        }


        @Override
        public void init() {
        }

        @Override
        public void loop() {
            y = gamepad1.left_stick_y;
            x = gamepad1.right_stick_x;
            telemetry.update();
            telemetry.addData("Y value:",y);
            telemetry.addData("X value:",x);


            rotate();
            move();

        }
    }

}


