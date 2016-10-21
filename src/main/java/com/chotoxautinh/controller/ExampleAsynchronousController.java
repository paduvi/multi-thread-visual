package com.chotoxautinh.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

public class ExampleAsynchronousController {

	@FXML
	private Button startBtn;

	@FXML
	private TextArea textArea;

	private int x;
	private int y;
	private int flag;

	private Runnable p1 = new Runnable() {
		@Override
		public void run() {
			while (flag > 0) {
				x = 2 * y;
				flag--;
				String txt = x + " ";

				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						textArea.appendText(txt);
					}
				});
			}
		}
	};

	private Runnable p2 = new Runnable() {
		@Override
		public void run() {
			while (flag > 0) {
				y++;
				y = 2;
			}
		}
	};

	@FXML
	private void start(ActionEvent event) {
		x = 0;
		y = 0;
		flag = 1000;

		startBtn.setDisable(true);
		textArea.setText("");
		ExecutorService executor = Executors.newFixedThreadPool(2);
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
					executor.shutdown();
					startBtn.setDisable(false);
				}
			}
		}.start();
	}

}
