package com.chotoxautinh;

import java.io.IOException;

import com.chotoxautinh.controller.RootController;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

	private BorderPane rootLayout;
	private Stage primaryStage;

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Test");
		this.primaryStage.setResizable(false);
		this.primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/img/dog-icon.jpg")));

		initRootLayout();

		showStartView();
	}

	/**
	 * Initializes the root layout.
	 */
	public void initRootLayout() {
		try {
			// Load root layout from fxml file.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/RootLayout.fxml"));
			rootLayout = (BorderPane) loader.load();

			// Give the controller access to the main app.
			RootController controller = loader.getController();
			controller.setMainApp(this);

			// Show the scene containing the root layout.
			Scene scene = new Scene(rootLayout);
			scene.getStylesheets().add(getClass().getResource("/style/application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Shows the person overview inside the root layout.
	 */
	public void showStartView() {
		try {
			// Load person overview.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/BeginLayout.fxml"));
			VBox startView = (VBox) loader.load();

			// Set person overview into the center of root layout.
			rootLayout.setCenter(startView);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Close window
	 */
	public void closeStage() {
		this.primaryStage.close();
	}

	/**
	 * Returns the main stage.
	 * 
	 * @return
	 */
	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public static void main(String[] args) {
		launch(args);
	}
}
