/**
 * @author Cho To Xau Tinh
 *
 * Oct 21, 2016 - http://chotoxautinh.com/
 */
package com.chotoxautinh.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;

public class MicroServiceController {

	@FXML
	private ProgressBar singleProgressBar;

	@FXML
	private ProgressBar multiProgressBar;

	@FXML
	private Button startBtn;

	private final Object lock = new Object();

	@FXML
	private void start(ActionEvent event) {
		startBtn.setDisable(true);
		singleProgressBar.setProgress(0);
		multiProgressBar.setProgress(0);
		ExecutorService executor = Executors.newFixedThreadPool(5);
		List<Callable<Object>> todo = new ArrayList<Callable<Object>>(5);

		todo.add(Executors.callable(singleTask));
		for (int i = 0; i < 4; i++) {
			todo.add(Executors.callable(new Thread(dividedTask)));
		}

		new Thread() {
			@Override
			public void run() {
				try {
					executor.invokeAll(todo);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					startBtn.setDisable(false);
				}
			}
		}.start();

	}

	private Runnable singleTask = new Runnable() {
		@Override
		public void run() {
			for (int i = 0; i < 100; i++) {
				singleProgressBar.setProgress(singleProgressBar.getProgress() + 0.01);
				try {
					Thread.sleep(30); // 30ms
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
	};

	private Runnable dividedTask = new Runnable() {
		@Override
		public void run() {
			for (int i = 0; i < 25; i++) {
				synchronized (lock) {
					multiProgressBar.setProgress(multiProgressBar.getProgress() + 0.01);
				}

				try {
					Thread.sleep(30); // 30ms
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	};
}
