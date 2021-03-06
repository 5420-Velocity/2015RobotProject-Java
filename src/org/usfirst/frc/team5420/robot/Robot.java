package org.usfirst.frc.team5420.robot;

import edu.wpi.first.wpilibj.AnalogInput;
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
		//solenoid0 = new Solenoid(2);
		//solenoid1 = new Solenoid(3);
		encoder1 = new Encoder(6,7, false, Encoder.EncodingType.k4X);
		compressor0 = new Compressor(0);
		joystick0 = new Joystick(0); //Controller One
		joystick1 = new Joystick(1); //Controller Two
		UpperLimit = new DigitalInput(0);
		LowerLimit = new DigitalInput(9);
		LiftMotor= new VictorSP(2);
	}
	
	@Override
	public void robotPeriodic(){
		
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
	 * This is just a basic function to shorten the command/function
	 * @param value String Input
	 */
	private void putToConsole(String value){
		System.out.println(value);
	}
	
	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		//Drive Motor Control
		myRobot.arcadeDrive(-(joystick0.getRawAxis(1)), -(joystick0.getRawAxis(4))); //Pulling joystick values from Only One Controller not One from CTRL1 and the other from CTRL2
		
		//Get the Upper and and the Lower Status of the Sensor and Allow or Disallow Operation(s) control.
		//Look at "the axis or button number as a parameter and return the corresponding value" https://wpilib.screenstepslive.com/s/4485/m/13809/l/599723-joysticks
		double Yvalue = joystick1.getRawAxis(1);
		if (Yvalue < 0 && UpperLimit.get() == false){
			putToConsole("Uppper Limit has been triggered"); // The Console Log
			LiftMotor.set(Yvalue);			
		}
		// The Value is inverted in the Hardware on the NC/NO Switch
		else if(Yvalue > 0 && LowerLimit.get() == false){
			putToConsole("Lower Limit has been triggered"); // The Console Log
			LiftMotor.set(Yvalue);
		}
		else { //No Value or the 
			putToConsole("Set to Zero has been triggered"); // The Console Log
			LiftMotor.set(0);
		}
		
		//Lift Control
		if(joystick1.getRawButton(1)){
			putToConsole("Lift Close Triggered");
			solenoid0.set(true);
			solenoid1.set(false);
		}
		else {
			putToConsole("Lift Open Triggered");
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
