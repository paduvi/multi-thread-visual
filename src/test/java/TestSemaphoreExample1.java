import java.util.concurrent.Semaphore;

public class TestSemaphoreExample1 {

	private int x = 0;
	private int y = 0;
	private Semaphore mutex = new Semaphore(1);

	private Runnable p1 = new Runnable() {
		@Override
		public void run() {
			while (true) {
				try {
					mutex.acquire();
					x = 2 * y;
					System.out.print(x + " ");
					mutex.release();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	};

	private Runnable p2 = new Runnable() {
		@Override
		public void run() {
			while (true) {
				try {
					mutex.acquire();
					y++;
					y = 2;
					mutex.release();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	};

	public void start() {
		Thread t1 = new Thread(p1);
		// t1.setDaemon(true);

		Thread t2 = new Thread(p2);
		// t2.setDaemon(true);

		t1.start();
		t2.start();
	}

	public static void main(String[] args) {
		TestSemaphoreExample1 test = new TestSemaphoreExample1();
		test.start();
	}
}
