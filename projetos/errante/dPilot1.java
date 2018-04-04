import lejos.robotics.navigation.DifferentialPilot;
import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.nxt.UltrasonicSensor;
import lejos.nxt.TouchSensor;
import lejos.nxt.SensorPort;
import java.util.Random;


public class dPilot1 {

    public static void main(String[] args) {
        UltrasonicSensor sonic = new UltrasonicSensor(SensorPort.S4);
        DifferentialPilot pilot = new DifferentialPilot(5.6f, 11.2f, Motor.B, Motor.C, false); //in cm

        
        pilot.setTravelSpeed(4);
	    pilot.setRotateSpeed(100);

        Random random = new Random();
        boolean side = random.nextBoolean();
        int degree;
        int i;
        int max = 0;
        int i_max = 0;

        if (side)
            degree = 45;
        else
            degree = -45

        for (i = 0; i < 8; i++){
            pilot.rotate(degree, false);
            int d = sonic.getDistance();
            if (d > max){
                max = d;
                i_max = i;
            }

            if (d >= 250)
                break;
        }

        pilot.rotate(degree * (i_max + 1));


        while (true){
            while (sonic.getDistance() > 30)
                pilot.forward();
            pilot.stop();
            max = 0;
            i_max = 0;
            side = random.nextBoolean();
            if (side)
                degree = 45;
            else
                degree = -45

            for (i = 0; i < 8; i++){
                pilot.rotate(degree, false);
                int d = sonic.getDistance();
                if (d > max){
                    max = d;
                    i_max = i;
                }
            }

            pilot.rotate(degree * (i_max + 1));
        }

        Button.waitForAnyPress();
    }
}
