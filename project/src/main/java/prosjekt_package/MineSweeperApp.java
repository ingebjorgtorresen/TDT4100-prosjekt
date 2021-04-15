package prosjekt_package;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MineSweeperApp extends Application {
	@Override
	public void start(final Stage primaryStage) throws Exception {
		primaryStage.setTitle("Mine Sweeper");
		primaryStage.setScene(new Scene(FXMLLoader.load(MineSweeperApp.class.getResource("MineSweeper.fxml"))));
		primaryStage.setMaximized(false);
		primaryStage.show();
	}

	public static void main(final String[] args) {
		MineSweeperApp.launch(args);
	}
}
	
	
