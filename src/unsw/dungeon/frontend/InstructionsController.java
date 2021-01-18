package unsw.dungeon.frontend;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

public class InstructionsController {
	
	private StartScreen startScreen;
	private InstructionsScreen instructionScreen;
	

	@FXML
	private Button backButton; // takes you back to menu
	
	public InstructionsController(InstructionsScreen instructionScreen) {
    	this.instructionScreen = instructionScreen;
    }
	
    @FXML
    public void handleBack(ActionEvent event) throws IOException {
    	startScreen.start();
    }
    
    public void setStartScreen(StartScreen startScreen) {
    	this.startScreen = startScreen;
    }
    
}
