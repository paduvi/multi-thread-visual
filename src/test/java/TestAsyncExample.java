
public class TestAsyncExample {

	private int x = 0;
	private int y = 0;

	private Runnable p1 = new Runnable() {
		@Override
		public void run() {
			while (true) {
				x = 2 * y;
				System.out.print(x + " ");
			}
		}
	};

	private Runnable p2 = new Runnable() {
		@Override
		public void run() {
			while (true) {
				y++;
				y = 2;
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
		TestAsyncExample test = new TestAsyncExample();
		test.start();
	}
}
