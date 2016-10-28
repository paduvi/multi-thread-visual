/**
 * @author Cho To Xau Tinh
 *
 * Oct 28, 2016 - http://chotoxautinh.com/
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
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class ProducerConsumer1Controller {

	@FXML
	private HBox hBox;

	@FXML
	private Button btn;

	private final int BUFFER_SIZE = 10;
	private int in = 0;
	private int out = 0;

	private Semaphore empty = new Semaphore(BUFFER_SIZE);
	private Semaphore full = new Semaphore(0);

	private boolean flag = false;

	@FXML
	private void btnHandler(ActionEvent event) {
		if (!flag) {
			flag = true;
			btn.setText("Stop");

			ExecutorService executor = Executors.newFixedThreadPool(2);
			List<Callable<Object>> todo = new ArrayList<Callable<Object>>(2);

			todo.add(Executors.callable(producer));
			todo.add(Executors.callable(consumer));

			new Thread() {
				@Override
				public void run() {
					try {
						executor.invokeAll(todo);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}.start();
		} else {
			flag = false;
			resetItem();
			btn.setText("Start");
		}
	}

	private void resetItem() {
		for (Node node : hBox.getChildren()) {
			Circle circle = (Circle) node;
			circle.setFill(Color.DODGERBLUE);
		}
	}

	private Runnable producer = new Runnable() {

		@Override
		public void run() {
			try {
				while (flag) {
					empty.acquire();
					addItem(in);
					in = (in + 1) % BUFFER_SIZE;
					Thread.sleep((long) Math.floor(Math.random() * 100));
					full.release();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	};

	private Runnable consumer = new Runnable() {

		@Override
		public void run() {
			try {
				while (flag) {
					full.acquire();
					getItem(out);
					out = (out + 1) % BUFFER_SIZE;
					Thread.sleep((long) Math.floor(Math.random() * 100));
					empty.release();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	};

	private void addItem(int index) {
		((Circle) hBox.getChildren().get(index)).setFill(Color.GREENYELLOW);
	}

	private void getItem(int index) {
		((Circle) hBox.getChildren().get(index)).setFill(Color.DODGERBLUE);
	}
}
