package cs681;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;

public class StatsHandler implements Runnable {

	private AdmissionMonitor monitor = new AdmissionMonitor();
	private boolean done = false;
	private final ReentrantLock lock = new ReentrantLock();
	Condition condition = lock.newCondition();
	
	public StatsHandler() {}

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
				System.out.println("Stopped monitoring stats...");
			}
			monitor.countCurrentVisitors();
		} finally {
		lock.unlock();
		}
	}
}
