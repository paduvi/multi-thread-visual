/**
 * @author Cho To Xau Tinh
 *
 * Oct 20, 2016 - http://chotoxautinh.com/
 */
package com.chotoxautinh.controller;

import java.util.Date;

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
		Date start = new Date();
		textMsg.setVisible(false);
		blockingIndicator.setVisible(true);
		while (new Date().getTime() - start.getTime() <= 5000)
			; // 5000ms
		textMsg.setVisible(true);
		blockingIndicator.setVisible(false);
	}

	@FXML
	private void handleNonBlockingAction(ActionEvent event) {
		Date start = new Date();
		textMsg.setVisible(false);
		nonBlockingIndicator.setVisible(true);
		new Thread() {
			public void run() {
				while (new Date().getTime() - start.getTime() <= 5000)
					; // 5000ms
				textMsg.setVisible(true);
				nonBlockingIndicator.setVisible(false);
			};
		}.start();
	}
}
