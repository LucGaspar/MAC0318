 import lejos.nxt.Button;
 import lejos.nxt.LCD;
 import lejos.nxt.Motor;
import lejos.util.Delay;
 /*
 * Programa 1
 */
public class MotorTutor1
 {    
      public static void main(String[] args)
      {
	   for (int i = 1; i < 5; i++){
		   LCD.drawString("Programa 1", 0, 0);
		   LCD.clear();
		   Motor.B.setSpeed(120);
		   Motor.C.setSpeed(120);
		   Motor.B.forward();
		   Motor.C.forward();
		   double raid = 2.8;
		   double c = 50/(2*3.14*raid);
		   
		   while (Motor.B.isMoving()){

		   	if (Motor.C.getTachoCount() >= 360 * c * i){
				Motor.C.stop();
				Motor.B.stop();
				Motor.B.rotate(180, true);
				Motor.C.rotate(-180, false);
				break;
		   	}
}
	   }
	   Motor.B.stop();
	   Motor.C.stop();
           Button.waitForAnyPress(); 
      }
 }
