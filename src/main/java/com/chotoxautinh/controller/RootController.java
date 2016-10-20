package com.chotoxautinh.controller;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.chotoxautinh.Main;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import javafx.animation.TranslateTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

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
	private HBox sideBar;

	@FXML
	private ListView<String> listMenu;

	@FXML
	private StackPane stackPane;

	private List<Node> viewList = new ArrayList<>();

	@FXML
	private void initialize() {
		loadBtnIcon();
		loadSectionList();
		showView(0);
	}

	/**
	 * Load button icon
	 */
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

	/**
	 * Shows the start view layout.
	 */
	private void showView(int index) {
		if (stackPane.getChildren().size() > 0)
			stackPane.getChildren().clear();
		stackPane.getChildren().add(viewList.get(index));
	}

	/**
	 * Load section list
	 */
	private void loadSectionList() {
		Gson gson = new Gson();
		try {
			List<JsonObject> list = gson.fromJson(new FileReader(getClass().getResource("/config/list.json").getPath()),
					new TypeToken<List<JsonObject>>() {
					}.getType());

			FXMLLoader beginLoader = new FXMLLoader();
			beginLoader.setLocation(getClass().getResource("/view/BeginLayout.fxml"));
			viewList.add(beginLoader.load());

			List<String> titleList = new ArrayList<>();

			for (JsonObject obj : list) {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("/view/" + obj.get("view").getAsString() + ".fxml"));
				viewList.add(loader.load());

				titleList.add(obj.get("title").getAsString());
			}

			ObservableList<String> items = FXCollections.observableArrayList(titleList);
			listMenu.setItems(items);

			listMenu.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
				public void changed(ObservableValue<? extends String> ov, String old_val, String new_val) {
					showView(listMenu.getSelectionModel().getSelectedIndex() + 1);
					TranslateTransition toggleSideBar = new TranslateTransition(Duration.seconds(0.3), sideBar);
					toggleSideBar.setByX(-350);

					toggleSideBar.play();
				}
			});
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	private void handleExpandAction(ActionEvent event) {
		double x;
		if (sideBar.getTranslateX() == -350) {
			x = 350;
		} else {
			x = -350;
		}
		TranslateTransition toggleSideBar = new TranslateTransition(Duration.seconds(0.3), sideBar);
		toggleSideBar.setByX(x);

		toggleSideBar.play();
	}

	@FXML
	private void handleHomeAction(ActionEvent event) {
		if (sideBar.getTranslateX() == 0) {
			TranslateTransition toggleSideBar = new TranslateTransition(Duration.seconds(0.3), sideBar);
			toggleSideBar.setByX(-350);

			toggleSideBar.play();
		}
		showView(0);
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
		mainApp.getPrimaryStage().close();
	}

	public Main getMainApp() {
		return mainApp;
	}

	public void setMainApp(Main mainApp) {
		this.mainApp = mainApp;
	}

}
