import lejos.robotics.navigation.DifferentialPilot;
import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.nxt.UltrasonicSensor;
import lejos.nxt.TouchSensor;
import lejos.nxt.SensorPort;
import java.util.Random;


public class projeto3 {

    public static void main(String[] args) {
        UltrasonicSensor sonic = new UltrasonicSensor(SensorPort.S4);
        DifferentialPilot pilot = new DifferentialPilot(5.6f, 11.2f, Motor.B, Motor.C, false); //in cm

        int grau = 6;
        int least = 30;
        pilot.setTravelSpeed(15);
	    pilot.setRotateSpeed(1440);

        Random random = new Random();
        boolean side = random.nextBoolean();
        int degree;
        int i;
        int max = 0;    
        int i_max = 0;
        boolean c = true;

        if (side)
            degree = grau;
        else
            degree = grau * -1;

        i = 0;
        while (i < 360){
            pilot.rotate(degree, false);
            int d = sonic.getDistance();
            if (d > max){
                max = d;
                i_max = i;
            }
            if (d >= least){
                c = false;
                break;
            }
            i += degree;
        }

        if (c)
            pilot.rotate(degree * i_max);


        while (true){
            c = true;
            while (sonic.getDistance() > 30)
                pilot.forward();
            pilot.stop();
            max = 0;
            i_max = 0;
            side = random.nextBoolean();
            if (side)
                degree = grau;
            else
                degree = grau * -1;

            i = 0;
            while (i < 360){
                pilot.rotate(degree, false);
                int d = sonic.getDistance();
                if (d > max){
                    max = d;
                    i_max = i;
                }
                if (d >= least){
                    c = false;
                    break;
                }
                i += degree;
            }
            if (c)
                pilot.rotate(degree * i_max);
        }

    }
}
