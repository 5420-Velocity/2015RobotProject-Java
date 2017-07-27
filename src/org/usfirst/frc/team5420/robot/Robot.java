package org.usfirst.frc.team5420.robot;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	RobotDrive myRobot = new RobotDrive(0, 1);
	Timer timer = new Timer();
	public static  Solenoid solenoid0;
	public static Solenoid solenoid1;
	public static Encoder encoder1;
	public static Compressor compressor0;
	public static Joystick joystick0;
	public static Joystick joystick1;
	public static DigitalInput UpperLimit;
	public static DigitalInput LowerLimit;
	public static VictorSP LiftMotor;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		solenoid0 = new Solenoid(2);
		solenoid1 = new Solenoid(3);
		encoder1 = new Encoder(6,7, false, Encoder.EncodingType.k4X);
		compressor0 = new Compressor(0);
		joystick0 = new Joystick(0); //Controller One
		joystick1 = new Joystick(1); //Controller Two
		UpperLimit = new DigitalInput(0);
		LowerLimit = new DigitalInput(9);
		LiftMotor= new VictorSP(2);
	}
	

	/**
	 * This function is run once each time the robot enters autonomous mode
	 */
	@Override
	public void autonomousInit() {
		timer.reset();
		timer.start();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		// Drive for 2 seconds
		/*
		if (timer.get() < 2.0) {
			myRobot.drive(-0.5, 0.0); // drive forwards half speed
		} else {
			myRobot.drive(0.0, 0.0); // stop robot
		}
		*/
	}

	/**
	 * This function is called once each time the robot enters tele-operated
	 * mode
	 */
	@Override
	public void teleopInit() {
		compressor0.setClosedLoopControl(true);
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		//Drive Motor Control
		
		//Get the Upper and and the Lower Status of the Sensor and Allow or Disallow Operation(s) control.
		//Look at "the axis or button number as a parameter and return the corresponding value" https://wpilib.screenstepslive.com/s/4485/m/13809/l/599723-joysticks
		double Yvalue = joystick1.getRawAxis(1);
		if (Yvalue > 0 && !UpperLimit.get()){
			LiftMotor.set(Yvalue);			
		}
		else if(Yvalue < 0 && !LowerLimit.get()){
			LiftMotor.set(Yvalue);
		}
		else { //No Value or the 
			LiftMotor.set(0);
		}
		
		//Lift Control
		if(joystick0.getRawButton(0)){
			solenoid0.set(true);
			solenoid1.set(false);
		}
		else {
			solenoid0.set(false);
			solenoid1.set(true);
		}
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}
}
