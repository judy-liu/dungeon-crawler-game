package unsw.dungeon.frontend;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class StartController {
	private DungeonScreen dungeonScreen;
	private StartScreen startScreen;
	
	@FXML
	private ImageView level1;
	@FXML
	private ImageView level2;
	@FXML
	private ImageView level3;
	@FXML
	private ImageView level4;
	@FXML
	private ImageView level5;
	@FXML
	private ImageView level6;
	@FXML
	private ImageView level7;
	@FXML
	private ImageView level8;
	@FXML
	private ImageView level9;
	@FXML
	private ImageView level10;
	@FXML
	private ImageView level11;
	@FXML
	private ImageView level12;
	
	@FXML
	private Button markingDungeon;
	
	@FXML
	private Button instructionsButton;
	
	@FXML
	private Pane backgroundPane;

    public StartController(StartScreen startScreen) {
    	this.startScreen = startScreen;
    }
    
    public void setDungeonScreen(DungeonScreen dungeonScreen) {
    	this.dungeonScreen = dungeonScreen;
    }
    
    
    @FXML
    public void initialize() throws IOException {
    	
    	backgroundPane.setStyle("-fx-background-color: #96f8ff;");
    		
    	level1.setOnMouseClicked((MouseEvent e ) -> {
            DungeonScreen dungeonScreen;
			try {
				dungeonScreen = startScreen.newDungeon("Level1.json");
				dungeonScreen.start();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        	
        });
    	
    	level2.setOnMouseClicked((MouseEvent e ) -> {
            DungeonScreen dungeonScreen;
			try {
				dungeonScreen = startScreen.newDungeon("Level2.json");
				dungeonScreen.start();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        	
        });
    	
    	level3.setOnMouseClicked((MouseEvent e ) -> {
            DungeonScreen dungeonScreen;
			try {
				dungeonScreen = startScreen.newDungeon("Level3.json");
				dungeonScreen.start();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        	
        });
    	
    	level4.setOnMouseClicked((MouseEvent e ) -> {
            DungeonScreen dungeonScreen;
			try {
				dungeonScreen = startScreen.newDungeon("Level4.json");
				dungeonScreen.start();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        	
        });
    	
    	level5.setOnMouseClicked((MouseEvent e ) -> {
            DungeonScreen dungeonScreen;
			try {
				dungeonScreen = startScreen.newDungeon("Level5.json");
				dungeonScreen.start();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        	
        });
    	
    	level6.setOnMouseClicked((MouseEvent e ) -> {
            DungeonScreen dungeonScreen;
			try {
				dungeonScreen = startScreen.newDungeon("Level6.json");
				dungeonScreen.start();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        	
        });
    	
    	level7.setOnMouseClicked((MouseEvent e ) -> {
            DungeonScreen dungeonScreen;
			try {
				dungeonScreen = startScreen.newDungeon("Level7.json");
				dungeonScreen.start();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        	
        });
    	
    	level8.setOnMouseClicked((MouseEvent e ) -> {
            DungeonScreen dungeonScreen;
			try {
				dungeonScreen = startScreen.newDungeon("Level8.json");
				dungeonScreen.start();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        	
        });
    	
    	level9.setOnMouseClicked((MouseEvent e ) -> {
            DungeonScreen dungeonScreen;
			try {
				dungeonScreen = startScreen.newDungeon("Level9.json");
				dungeonScreen.start();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        	
        });
    	
    	level10.setOnMouseClicked((MouseEvent e ) -> {
            DungeonScreen dungeonScreen;
			try {
				dungeonScreen = startScreen.newDungeon("Level10.json");
				dungeonScreen.start();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        	
        });
    	
    	level11.setOnMouseClicked((MouseEvent e ) -> {
            DungeonScreen dungeonScreen;
			try {
				dungeonScreen = startScreen.newDungeon("Level11.json");
				dungeonScreen.start();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        	
        });
    	
    	level12.setOnMouseClicked((MouseEvent e ) -> {
            DungeonScreen dungeonScreen;
			try {
				dungeonScreen = startScreen.newDungeon("Level12.json");
				dungeonScreen.start();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        	
        });   	
    	
    	markingDungeon.setOnMouseClicked((MouseEvent e) -> {
    		DungeonScreen dungeonScreen;
    		try {
				dungeonScreen = startScreen.newDungeon("marking.json");
				dungeonScreen.start();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
    	});
    	
    }
    
    @FXML
    public void handleInstructions(ActionEvent event) throws IOException {
    	InstructionsScreen instructionsScreen = startScreen.getInstructionsScreen();
    	instructionsScreen.start();
    }
}
