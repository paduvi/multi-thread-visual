/**
 * @author Cho To Xau Tinh
 *
 * Oct 22, 2016 - http://chotoxautinh.com/
 */
package com.chotoxautinh.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;

public class DeadLockExampleController {

	@FXML
	private ProgressBar progressBar1;

	@FXML
	private ProgressBar progressBar2;

	@FXML
	private Button startBtn;

	@FXML
	private Button stopBtn;

	private Semaphore mutex1;
	private Semaphore mutex2;
	private ExecutorService executor;
	private boolean flag;

	@FXML
	private void start(ActionEvent event) {
		progressBar1.setProgress(0);
		progressBar2.setProgress(0);
		mutex1 = new Semaphore(1);
		mutex2 = new Semaphore(1);
		flag = true;
		startBtn.setDisable(true);
		stopBtn.setDisable(false);

		executor = Executors.newFixedThreadPool(2);
		List<Callable<Object>> todo = new ArrayList<Callable<Object>>(2);

		todo.add(Executors.callable(p1));
		todo.add(Executors.callable(p2));

		new Thread() {
			@Override
			public void run() {
				try {
					executor.invokeAll(todo);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					stopProcess();
				}
			}
		}.start();
	}

	private void stopProcess() {
		executor.shutdownNow();
		flag = false;
		startBtn.setDisable(false);
		stopBtn.setDisable(true);
	}

	@FXML
	private void stop(ActionEvent event) {
		stopProcess();
	}

	private Runnable p1 = new Runnable() {

		@Override
		public void run() {
			try {
				while (flag) {
					mutex1.acquire();
					mutex2.acquire();
					double value = progressBar1.getProgress() + 0.01;
					if (value > 1)
						value--;
					progressBar1.setProgress(value);
					Thread.sleep(20);
					mutex2.release();
					mutex1.release();
				}
			} catch (InterruptedException e) {
				mutex1 = null;
				mutex2 = null;
			}
		}
	};

	private Runnable p2 = new Runnable() {

		@Override
		public void run() {
			try {
				while (flag) {
					mutex2.acquire();
					mutex1.acquire();
					double value = progressBar2.getProgress() + 0.01;
					if (value > 1)
						value--;
					progressBar2.setProgress(value);
					Thread.sleep(20);
					mutex1.release();
					mutex2.release();
				}
			} catch (InterruptedException e) {
				mutex1 = null;
				mutex2 = null;
			}
		}
	};
}
