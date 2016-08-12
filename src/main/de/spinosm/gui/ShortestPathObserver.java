package de.spinosm.gui;

import java.util.Observable;
import java.util.Observer;

public class ShortestPathObserver extends Thread implements Observer {

	Object arg1;

	@Override
	public void update(Observable arg0, Object arg1) {
		this.arg1 = arg1;

	}
	
    public void run() {
        while(true) {
          try {
            sleep(5000);
          }
          catch(InterruptedException e) {
          }
          System.out.println(arg1);
        }
      }

}
