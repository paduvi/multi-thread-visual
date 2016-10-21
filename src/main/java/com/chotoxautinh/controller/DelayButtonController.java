/**
 * @author Cho To Xau Tinh
 *
 * Oct 20, 2016 - http://chotoxautinh.com/
 */
package com.chotoxautinh.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;

public class DelayButtonController {

	@FXML
	private ProgressIndicator blockingIndicator;

	@FXML
	private ProgressIndicator nonBlockingIndicator;

	@FXML
	private Label textMsg;

	@FXML
	private void handleBlockingAction(ActionEvent event) {
		try {
			textMsg.setVisible(false);
			blockingIndicator.setVisible(true);
			Thread.sleep(5000); // 5000ms
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			textMsg.setVisible(true);
			blockingIndicator.setVisible(false);
		}
	}

	@FXML
	private void handleNonBlockingAction(ActionEvent event) {
		Thread backgroundThread = new Thread() {
			public void run() {
				try {
					textMsg.setVisible(false);
					nonBlockingIndicator.setVisible(true);
					sleep(5000); // 5000ms
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					textMsg.setVisible(true);
					nonBlockingIndicator.setVisible(false);
				}
			};
		};
		backgroundThread.setDaemon(true);
		backgroundThread.start();
	}
}
