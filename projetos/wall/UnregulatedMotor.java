import lejos.nxt.Button;
import lejos.nxt.LightSensor;
import lejos.nxt.MotorPort;
import lejos.nxt.NXTMotor;
import lejos.nxt.SensorPort;
import lejos.util.Delay;
import lejos.nxt.LCD;
import lejos.nxt.UltrasonicSensor;


public class UnregulatedMotor {

   static NXTMotor mA;
   static NXTMotor mC;

   static double cp = 4;
   static double ci = 0.00001;
   static double cd = -1;
   static int k = (int)(20 * Math.sqrt(2));
   static int p = 40;


  	public static void main(String args[]) {

  		UltrasonicSensor sonic = new UltrasonicSensor(SensorPort.S1);
  		int medida, acumulo = 0, turn;
  		int erro[] = {0, 0};
	    mA = new NXTMotor(MotorPort.A);
	    mC = new NXTMotor(MotorPort.C);
	    mA.setPower(p);
	    mC.setPower(p);

    
		while (true){
			// Erro instantaneo
			// Erro acumulado
			// Erro do momento
			while (sonic.getDistance() < k - 2 || sonic.getDistance() > k + 2){
				medida = sonic.getDistance();
				erro[0] = erro[1]; // 0 é o último
				erro[1] = k - medida;
				acumulo = acumulo + erro[1];
				turn = (int)(cp * erro[1] + ci * acumulo + (erro[1] - erro[0]) * cd);
				changeMotors(p, turn);
			}	
			acumulo = 0;

			mA.setPower(p);
	    	mC.setPower(p);	
  		}
    }

    static private void changeMotors(int base, int power){
    	int a = base - power;
    	int c = base + power;

    	if (a < 0)
    		a = 10;
    	else if (a > 60)
    		a = 60;

    	if (c < 0)
    		c = 10;
    	else if (c > 60)
    		c = 60;

    	mA.setPower(a);
    	mC.setPower(c);
    }
}