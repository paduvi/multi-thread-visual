package com.chotoxautinh.controller;

import java.util.concurrent.Semaphore;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;

public class DemoSemaphoreController2 {

	@FXML
	private ProgressBar progressBar1;

	@FXML
	private ProgressBar progressBar2;

	@FXML
	private ProgressBar progressBar3;

	@FXML
	private Button startBtn;

	private Semaphore mutex1 = new Semaphore(0);
	private Semaphore mutex2 = new Semaphore(0);

	@FXML
	private void start(ActionEvent event) {
		progressBar1.setProgress(0);
		progressBar2.setProgress(0);
		progressBar3.setProgress(0);
		
		Thread thread1 = new Thread(task1);
		Thread thread2 = new Thread(task2);
		Thread thread3 = new Thread(task3);
		
		thread1.setDaemon(true);
		thread2.setDaemon(true);
		thread3.setDaemon(true);

		thread1.start();
		thread2.start();
		thread3.start();
	}

	private Runnable task1 = new Runnable() {

		@Override
		public void run() {
			try {
				startBtn.setDisable(true);
				for (int i = 0; i < 100; i++) {
					if (i == 50)
						mutex1.release();
					progressBar1.setProgress((i + 1) * 1.0 / 100);
					Thread.sleep(30);
				}
				mutex2.release();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		};
	};

	private Runnable task2 = new Runnable() {
		
		@Override
		public void run() {
			try {
				mutex1.acquire();
				for (int i = 0; i < 100; i++) {
					progressBar2.setProgress((i + 1) * 1.0 / 100);
					Thread.sleep(30);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		};
	};

	private Runnable task3 = new Runnable() {

		@Override
		public void run() {
			try {
				mutex2.acquire();
				for (int i = 0; i < 100; i++) {
					progressBar3.setProgress((i + 1) * 1.0 / 100);
					Thread.sleep(30);
				}
				startBtn.setDisable(false);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		};
	};
}
