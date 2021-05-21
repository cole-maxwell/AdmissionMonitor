package cs681;

import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;

public class ExitHandler implements Runnable {

	private AdmissionMonitor monitor = new AdmissionMonitor();
	private boolean done = false;
	private final ReentrantLock lock = new ReentrantLock();
	Condition overflowCondition = lock.newCondition();
	
	public ExitHandler() {}

	public void setDone(){
		lock.lock();
		try {
			done = true;
		}
		finally {
			lock.unlock();
		}
	}
	
	public void run() {
		lock.lock();
		try { 
			if (done) {
				System.out.println("Stopped monitoring exits...");
			}
			monitor.exit();
		} finally {
			lock.unlock();
		}
	}
}
	
