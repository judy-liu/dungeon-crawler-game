package unsw.dungeon.frontend;


import java.io.IOException;
import javafx.scene.layout.GridPane;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class DungeonScreen {
	
	private Stage stage;
	private String title;
	private DungeonController controller;
	private Scene scene;
	
	private String currentJson;
	
	// added
	private DungeonControllerLoader dungeonLoader;
	
    
    @FXML
    private GridPane inventoryGrid;

	
    public DungeonScreen(Stage stage, String jsonFile) throws IOException {
        this.stage = stage;
        this.currentJson = jsonFile;
        title = "Dungeon";
        
        stage.sizeToScene();
        stage.setMinWidth(stage.getWidth());
//        stage.setMinHeight(stage.getHeight());
        
        dungeonLoader = new DungeonControllerLoader(jsonFile);

        controller = dungeonLoader.loadController();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("DungeonView1.fxml"));
        loader.setController(controller);
        
        Parent root = loader.load();
        scene = new Scene(root);
        root.requestFocus();
        
        EndScreen endScreen = new EndScreen(stage);
        controller.setEndScreen(endScreen);
        
        controller.setDungeonScreen(this);

        

    }
    
    public void start() throws IOException {  
    	
        stage.setTitle(title);
        stage.setScene(scene);
        scene.getRoot().requestFocus();
        stage.show();
        
        controller.playTimelines();
    }

    public DungeonController getController() {
        return controller;
    }

    public void restart() throws IOException {
        dungeonLoader = new DungeonControllerLoader(currentJson);
        controller = dungeonLoader.loadController();
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("DungeonView1.fxml"));
        loader.setController(controller);
        
        Parent root = loader.load();
        scene.setRoot(root);
        
        controller.setDungeonScreen(this);
        controller.setStartScreen(new StartScreen(stage));
        controller.setEndScreen(new EndScreen(stage));
        
        
        start();
    }
	
}
