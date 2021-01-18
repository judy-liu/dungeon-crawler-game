package unsw.dungeon.frontend;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class EndScreen {

	private Stage stage;
	private String title;
	private EndController controller;
	private Scene scene;
	
//	private StartScreen startScreen; // added
	//private DungeonScreen dungeonScreen;
	
	public EndScreen(Stage stage) throws IOException {
		this.stage = stage;
		title = "Game End";
        controller = new EndController();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("EndView.fxml"));
        loader.setController(controller);
        
        
        // load into a Parent node called root
        Parent root = loader.load();
        scene = new Scene(root, 600, 400);
	}
	
	public EndController getController() {
		return controller;
	}

	public void start() {
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
	}
	
	public void addDungeonScreen(DungeonScreen dungeonScreen) throws IOException {
		controller.addDungeonScreen(dungeonScreen);
	}
	

}
