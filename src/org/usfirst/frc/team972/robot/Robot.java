
package org.usfirst.frc.team972.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {

	Victor fl = new Victor(0);
	Victor rl = new Victor(1);
	Victor fr = new Victor(2);
	Victor rr = new Victor(3);
	RobotDrive botDrive = new RobotDrive(fl, rl, fr, rr);

	Joystick leftJoy;
	Joystick rightJoy;
	Joystick joy;

	boolean highGear = true, gearshiftPressedLastTime = false;
	boolean reverseFront = true, reversePressedLastTime = false;
	boolean squaredInputs = true, squaredPressedLastTime = false;

	boolean joystick = true;

	public void robotInit() {
		// v2.setInverted(true);
		// v3.setInverted(true);
	}

	public void teleopInit() {
		if (joystick) {
			leftJoy = new Joystick(0);
			rightJoy = new Joystick(1);
			joy = new Joystick(2);
		}
	}

	public void teleopPeriodic() {

		double leftDriveSpeed;
		double rightDriveSpeed;
		boolean gearshiftPressed;
		boolean reversePressed;
		boolean squaredPressed;

		if (joystick) {
			leftDriveSpeed = -leftJoy.getY();
			rightDriveSpeed = -rightJoy.getY();
			gearshiftPressed = rightJoy.getRawButton(1);
			reversePressed = leftJoy.getRawButton(1);
			squaredPressed = leftJoy.getRawButton(2);
		} else {
			// Doesn't actually work
			leftDriveSpeed = leftJoy.getY();
			rightDriveSpeed = rightJoy.getY();
			gearshiftPressed = rightJoy.getRawButton(1);
			reversePressed = leftJoy.getRawButton(1);
			squaredPressed = leftJoy.getRawButton(1);
		}

		System.out.println("LJ " + leftDriveSpeed);
		System.out.println("RJ " + rightDriveSpeed);

		// Check gearshift
		if (gearshiftPressed && !gearshiftPressedLastTime) {
			highGear = !highGear;
		}
		gearshiftPressedLastTime = gearshiftPressed;

		// Check reverse front
		if (reversePressed && !reversePressedLastTime) {
			reverseFront = !reverseFront;
		}
		reversePressedLastTime = reversePressed;

		// Check squared inputs
		if (squaredPressed && !squaredPressedLastTime) {
			squaredInputs = !squaredInputs;
		}
		squaredPressedLastTime = squaredPressed;

		// Apply Squared inputs
		if (squaredInputs) {
			if (leftDriveSpeed < 0) {
				leftDriveSpeed = -Math.pow(leftDriveSpeed, 2);
			} else if (leftDriveSpeed > 0) {
				leftDriveSpeed = Math.pow(leftDriveSpeed, 2);
			}
			if (rightDriveSpeed < 0) {
				rightDriveSpeed = -Math.pow(rightDriveSpeed, 2);
			} else if (rightDriveSpeed > 0) {
				rightDriveSpeed = Math.pow(rightDriveSpeed, 2);
			}
		}

		// Apply gearshift
		if (!highGear) {
			leftDriveSpeed /= 2;
			rightDriveSpeed /= 2;
		}

		// Apply reverse front

		if (reverseFront) {
			leftDriveSpeed = leftDriveSpeed * -1;
			rightDriveSpeed = rightDriveSpeed * -1;
			System.out.println("REVERSE");
			System.out.println("LS: " + leftDriveSpeed);
			System.out.println("RS: " + rightDriveSpeed);
			botDrive.tankDrive(rightDriveSpeed, leftDriveSpeed);
		} else {
			System.out.println("LS: " + leftDriveSpeed);
			System.out.println("RS: " + rightDriveSpeed);
			botDrive.tankDrive(leftDriveSpeed, rightDriveSpeed);
		}
		
		SmartDashboard.putBoolean("HIGH GEAR", highGear);
		SmartDashboard.putBoolean("REVERSE FRONT", reverseFront);
		SmartDashboard.putBoolean("SQUARED INPUTS", squaredInputs);
	}
}