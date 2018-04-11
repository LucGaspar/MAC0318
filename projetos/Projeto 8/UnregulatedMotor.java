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
  		int eA = 0;
  		int le = 0;
  		preto = 1;
  		branco= 0;
	    light = new LightSensor(SensorPort.S4);
	    mA = new NXTMotor(MotorPort.A);
	    mC = new NXTMotor(MotorPort.C);
	    mA.setPower(25);
	    mC.setPower(25);

    
		while (true){
			changeMotors(-1, 35, 35);
			while (light.getLightValue() > 58 || light.getLightValue() < 44){
				int color = light.getLightValue();
				int p = 30; 
				int s = 0;
				int e = color - border;
				eA = eA + e;
				int di = le - e;
				int turn = 0;
				if (di < 0)
					di = di * -1;
				if (eA > 30)
					eA = 30;
				if (eA < -30)
					eA = -30;

				if (color > border){
					if (eA < 0)
						turn = 30 + di - eA + e;
					turn = 30 + di + eA + e;
					changeMotors(preto, 10, turn);
					color = light.getLightValue();
				}
				if (color < border){
					if (eA < 0)
						turn = 30 + di * 1 - eA - e;
					turn = 30 + di * 1 + eA - e;
					changeMotors(branco, 10, turn);
					color = light.getLightValue();
				}
				LCD.clear();
				LCD.drawString("di: " + di, 0, 1);
				LCD.drawString("eA: " + eA, 0, 2);
				LCD.drawString("e: " + e, 0, 3);
				LCD.drawString("turn: " + turn, 0, 4);
				LCD.drawString("color: " + color, 0, 5);

				le = e;

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