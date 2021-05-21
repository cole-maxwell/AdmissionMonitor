package cs681;

import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;

public class AdmissionMonitor {

	private int currentVisitors = 0;
	private static ReentrantLock lock = new ReentrantLock();
	Condition exitCondition = lock.newCondition();
	Condition overflowCondition = lock.newCondition();

	public void enter() {
		System.out.println("Setting lock in AdmissionMonitor.enter()...");
		lock.lock();
		try {
			while (currentVisitors >= 5) {
				System.out.println("Too many visitors. Please wait!");
				overflowCondition.await();
				}
				currentVisitors++;
		} catch (InterruptedException exception) {
			System.out.println(exception.toString());
		} finally {
			System.out.println("Releasing lock in AdmissionMonitor.enter()...");
			lock.unlock();
		}
	}

	public void exit() {
		System.out.println("Setting lock in AdmissionMonitor.exit()...");
		lock.lock();
		try {
			currentVisitors--;
			System.out.println("Visitor exited --> signalAll()");
			exitCondition.signalAll();
		} finally {
			System.out.println("Releasing lock in AdmissionMonitor.exit()...");
			lock.unlock();
		}
	}
	public int countCurrentVisitors() {
		System.out.println("Number of current visitors: " + currentVisitors);
		return currentVisitors;
	}

	public static void main(String[] args) {

		for(int i = 0; i < 50; i++) {
			new Thread( new StatsHandler() ).start();
			new Thread( new EntranceHandler() ).start();
			new Thread( new ExitHandler() ).start();			
		}
		
	}
}
