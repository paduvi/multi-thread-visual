/**
 * @author Cho To Xau Tinh
 *
 * Oct 20, 2016 - http://chotoxautinh.com/
 */
package com.chotoxautinh.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class SimpleProgressController {

	@FXML
	private Label statusLabel;

	@FXML
	private TextArea textArea;

	@FXML
	private void handleBlockingAction(ActionEvent event) {
		Thread backgroundThread = new Thread() {
			@Override
			public void run() {
				for (int i = 1; i <= 10; i++) {
					try {
						String status = "Processing " + i + " of " + 10;
						statusLabel.setText(status);
						textArea.appendText(status + "\n");
						sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		// Terminate the running thread if the application exits
		backgroundThread.setDaemon(true);
		// Start the thread
		backgroundThread.start();
	}

	@FXML
	private void handleNonBlockingAction(ActionEvent event) {
		Thread backgroundThread = new Thread() {
			@Override
			public void run() {
				for (int i = 1; i <= 10; i++) {
					try {
						String status = "Processing " + i + " of " + 10;
						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								statusLabel.setText(status);
							}
						});

						textArea.appendText(status + "\n");
						sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		// Terminate the running thread if the application exits
		backgroundThread.setDaemon(true);
		// Start the thread
		backgroundThread.start();
	}
}
