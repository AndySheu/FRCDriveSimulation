
package org.usfirst.frc.team972.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Joystick;

public class Robot extends IterativeRobot {

	RobotDrive botDrive = new RobotDrive(0, 1, 2, 3);

	Joystick leftJoy = new Joystick(0);
	Joystick rightJoy = new Joystick(1);

	public void robotInit() {

	}

	public void teleopPeriodic() {

		double leftDriveSpeed = leftJoy.getY();
		double rightDriveSpeed = rightJoy.getY();

		botDrive.tankDrive(leftDriveSpeed, rightDriveSpeed);

	}
}