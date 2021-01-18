package unsw.dungeon.frontend;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class StartScreen {
	
	private Stage stage;
	private String title;
	private StartController controller;
	private Scene scene;

	
    public StartScreen(Stage stage) throws IOException {
        this.stage = stage;
        title = "*very cool name*";

        controller = new StartController(this);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("StartView.fxml"));
        loader.setController(controller);

        // load into a Parent node called root
        Parent root = loader.load();
        scene = new Scene(root);
        //stage.sizeToScene(); // added
        
    }
    
    public void start() {
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }

    public StartController getController() {
        return controller;
    }
    
    public DungeonScreen newDungeon(String jsonFile) throws IOException {
    	DungeonScreen dungeonScreen = new DungeonScreen(stage, jsonFile);
    	EndScreen endScreen = new EndScreen(stage);
    	//endScreen.addStartScreen(this); // added
    	
    	// getcontrolller is null rn
    	dungeonScreen.getController().setStartScreen(this);
    	dungeonScreen.getController().setEndScreen(endScreen);
    	controller.setDungeonScreen(dungeonScreen);
    	return dungeonScreen;
    }

	public InstructionsScreen getInstructionsScreen() throws IOException {
		InstructionsScreen screen = new InstructionsScreen(stage, this);
		return screen;
	}
    
}
