package com.chotoxautinh.controller;

import com.chotoxautinh.Main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class RootController {

	private Main mainApp;

	@FXML
	private Button homeBtn;

	@FXML
	private Button toggleMenuBtn;

	@FXML
	private Button questionBtn;

	@FXML
	private Button exitBtn;
	
	@FXML
	private ListView<String> listMenu;

	@FXML
	private void initialize() {
		loadBtnIcon();
	}

	private void loadBtnIcon() {
		Image imageHome = new Image(getClass().getResourceAsStream("/img/home.png"));
		homeBtn.setGraphic(new ImageView(imageHome));
		homeBtn.setTooltip(new Tooltip("Home Page"));

		Image imageToggle = new Image(getClass().getResourceAsStream("/img/menu-toggle.png"));
		toggleMenuBtn.setGraphic(new ImageView(imageToggle));
		toggleMenuBtn.setTooltip(new Tooltip("Toggle Menu"));

		Image imageQuestion = new Image(getClass().getResourceAsStream("/img/question.png"));
		questionBtn.setGraphic(new ImageView(imageQuestion));
		questionBtn.setTooltip(new Tooltip("About"));

		Image imageExit = new Image(getClass().getResourceAsStream("/img/exit.png"));
		exitBtn.setGraphic(new ImageView(imageExit));
		exitBtn.setTooltip(new Tooltip("Exit"));
	}

	@FXML
	private void handleExpandAction(ActionEvent event) {
		if(listMenu.getStyleClass().indexOf("expandedMenuList") == -1){
			listMenu.getStyleClass().add("expandedMenuList");
		} else {
			listMenu.getStyleClass().remove("expandedMenuList");
		}
	}
	
	@FXML
	private void handleHomeAction(ActionEvent event) {
		mainApp.showStartView();
	}

	@FXML
	private void handleAboutAction(ActionEvent event) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("ChoToXauTinh - © 2016");
		alert.setHeaderText("Contact me at:");
		StringBuilder builder = new StringBuilder();
		builder.append("• Class: Advanced IT 2013 - HUST\n");
		builder.append("• Company: Techmaster\n");
		builder.append("• Phone: 0985797649\n");
		builder.append("• Email: viet@techmaster.vn\n");
		alert.setContentText(builder.toString());

		alert.showAndWait();
	}

	@FXML
	private void handleExitAction(ActionEvent event) {
		mainApp.closeStage();
	}

	public Main getMainApp() {
		return mainApp;
	}

	public void setMainApp(Main mainApp) {
		this.mainApp = mainApp;
	}

}
