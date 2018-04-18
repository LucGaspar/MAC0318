import lejos.nxt.*;
import lejos.robotics.navigation.DifferentialPilot;
import java.lang.Thread;	
import lejos.nxt.comm.*;
import java.io.*;
	
public class ErranteLCD {

	public static DataOutputStream dataOut;
    public static DataInputStream dataIn;
    public static USBConnection USBLink;

	public static class RobotMonitor extends Thread {	
		private int delay;
		UltrasonicSensor sonar;
		
		public RobotMonitor(int d, UltrasonicSensor sonar ) {
			this.setDaemon(true);
			this.delay = d;
			this.sonar = sonar;
		}	

		public void run() { 

			while(true) {	
				LCD.clear();
				LCD.drawString("Sonar = "  + sonar.getDistance(), 0 ,0);
				LCD.drawString("Motor B = " + Motor.B.getTachoCount(), 0, 1);
				LCD.drawString("Motor C = " + Motor.C.getTachoCount(), 0, 2);

				try { 
					dataOut.writeBytes("Sonar = " + sonar.getDistance());
					dataOut.writeBytes("Motor B = " + Motor.B.getTachoCount());
					dataOut.writeBytes("Motor C = " + Motor.C.getTachoCount());
            		dataOut.flush();
            		this.sleep(delay);
            	}
				catch (Exception e) { }
			} // end while	
		} // end run	
	} // end class	

	public static void main(String [] args) throws Exception {
		UltrasonicSensor sonic = new UltrasonicSensor(SensorPort.S4);
        DifferentialPilot pilot = new DifferentialPilot(5.6f, 11.2f, Motor.B, Motor.C, false); //in cm
		RobotMonitor rm = new RobotMonitor(400, sonic);	

		USBLink = USB.waitForConnection(0, NXTConnection.RAW);
        dataOut = USBLink.openDataOutputStream();
        dataIn = USBLink.openDataInputStream();

        // espera inicio da conexao
        while (dataIn.available() == 0);

        pilot.setTravelSpeed(20);
	    pilot.setRotateSpeed(120);
	    rm.start(); // inicia thread
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
