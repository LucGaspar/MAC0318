import lejos.robotics.navigation.DifferentialPilot;
import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.nxt.UltrasonicSensor;
import lejos.nxt.TouchSensor;
import lejos.nxt.SensorPort;

public class errante {

    public static void main(String[] args) {
        UltrasonicSensor sonic = new UltrasonicSensor(SensorPort.S4);
        DifferentialPilot pilot = new DifferentialPilot(5.6f, 11.2f, Motor.B, Motor.C, false); //in cm

        pilot.setTravelSpeed(20);
	    pilot.setRotateSpeed(120);

        while (true){
            pilot.rotate(360, true);
            while (sonic.getDistance() < 40)
                continue;
            pilot.stop();
            while (sonic.getDistance() > 25)
                pilot.forward();
        }

    }
}
