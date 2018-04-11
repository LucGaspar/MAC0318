import lejos.nxt.Button;
import lejos.nxt.LightSensor;
import lejos.nxt.MotorPort;
import lejos.nxt.NXTMotor;
import lejos.nxt.SensorPort;
import lejos.util.Delay;
import lejos.nxt.LCD;

public class UnregulatedMotor {

   static LightSensor light;
   static NXTMotor mA;
   static NXTMotor mC;
   static int border = 51;

  	public static void main(String args[]) {
  		int preto, branco;
  		preto = 1;
  		branco= 0;
	    light = new LightSensor(SensorPort.S4);
	    mA = new NXTMotor(MotorPort.A);
	    mC = new NXTMotor(MotorPort.C);
	    mA.setPower(25);
	    mC.setPower(25);

    
		while (true){
			changeMotors(-1, 25, 25);
			while (light.getLightValue() > 58 || light.getLightValue() < 44){
				int color = light.getLightValue();
				int d = 0;
				int p = 35; 
				int e = 0;
				if (color > border){
					d = preto;
					e = (color - border);
					changeMotors(d, 10, p + e * 2);
					color = light.getLightValue();
				}
				if (color < border){
					d = branco;
					e = (border - color) * 3;
					changeMotors(d, 10, p + e * 2);
					color = light.getLightValue();
				}
			}
  		}

    }

    // Direction 1 é Mais claro
    // Direction 2 é Mais escuro
    static private void changeMotors(int direction, int q, int p){
    	if (direction == -1){
    		mA.setPower(p);
    		mC.setPower(p);
    		return;
    	}
    	// Direita
    	if (direction == 1){
    		mA.setPower(p);
    		mC.setPower(q);
    	}
    	// Esquerda
    	if (direction == 0){
    		mA.setPower(q);
    		mC.setPower(p);
    	}
    }
}