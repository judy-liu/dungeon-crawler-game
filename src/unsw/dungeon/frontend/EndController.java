package unsw.dungeon.frontend;

import java.io.IOException;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import unsw.dungeon.Dungeon;

public class EndController {
	
	@FXML
	private Label message;
	private Dungeon dungeon;
	
    @FXML
    private ImageView cookieImage;
    @FXML
    private Label cookieLabel;
    
	@FXML
	private Pane pane;
	
	@FXML
	private Button menuButton;
	
	@FXML
	private Label resultLabel;
	
	@FXML
	private Button nextButton;
	
	private StartScreen startScreen;
	
	private DungeonScreen dungeonScreen;
	
	public void setDungeon(Dungeon dungeon) {
		this.dungeon = dungeon;
		dungeon.getComplete().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (newValue.equals(Boolean.TRUE)) {
					setWinMessage();
					nextButton.setVisible(false);
					// centre the label
					resultLabel.layoutXProperty().bind(pane.widthProperty().subtract(resultLabel.widthProperty()).divide(2));
					resultLabel.setText("Great job! Click on menu to choose another puzzle to play.");
					
				}
			}
        });
		
		dungeon.getAlive().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (newValue.equals(Boolean.FALSE)) {
					setLoseMessage();
					resultLabel.setText("Oh no! Click to retry the puzzle or go back to menu to try another one!");
					// centre the label
					resultLabel.layoutXProperty().bind(pane.widthProperty().subtract(resultLabel.widthProperty()).divide(2));
					nextButton.setText("Restart"); // TODO: FIX
					cookieImage.setVisible(false);
					cookieLabel.setVisible(false);
				}
			}
        });
	}
	
	private void setWinMessage() {
		message.setText("You win! :)");
	}
	
	private void setLoseMessage() {
		message.setText("You lose :(");
	}
	
	public void setStartScreen(StartScreen startScreen) {
    	this.startScreen = startScreen;
    }
	
	@FXML
	private void handleMenu(ActionEvent event) {
		startScreen.start(); // need to  pass in startscreen
	}
	
	@FXML
	private void handleNext(ActionEvent event) throws IOException {
		if (nextButton.getText() == "Restart") {
			dungeonScreen.restart();
			
		}
	}
	
	public void addDungeonScreen(DungeonScreen dungeonScreen) throws IOException {
		this.dungeonScreen = dungeonScreen;
	}
	
}
