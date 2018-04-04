import lejos.robotics.navigation.DifferentialPilot;
import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.Motor;

public class dPilot1 {

    public static void main(String[] args) {
        LCD.drawString("D.P. - TRAVEL", 0, 0);
        Button.waitForAnyPress();
        DifferentialPilot pilot = new DifferentialPilot(2.205f, 4.527f, Motor.C, Motor.B, false); //in inches
        LCD.clear();
        pilot.setTravelSpeed(6);
	pilot.setRotateSpeed(100	);
        pilot.travel(19.685, false); //approx. 50cm
        pilot.rotate(-90, false); // 90 degrees anti-clockwise
	pilot.travel(-19.685, false); //approx. 50cm
        pilot.rotate(-90, false); // 90 degrees anti-clockwise
	pilot.travel(19.685, false); //approx. 50cm
        pilot.rotate(-90, false); // 90 degrees anti-clockwise
	pilot.travel(-19.685, false); //approx. 50cm
	pilot.rotate(-45, false); // 90 degrees anti-clockwise
	pilot.setTravelSpeed(10);
	pilot.travel(19.685*1.41421356237, false); //approx. 50cm
pilot.setRotateSpeed(250	);
	pilot.rotate(720, false); // 90 degrees anti-clockwise
        // pilot.stop();
        Button.waitForAnyPress();
    }
}
