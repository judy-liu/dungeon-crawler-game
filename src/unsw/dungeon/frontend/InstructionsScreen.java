package unsw.dungeon.frontend;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class InstructionsScreen {
	
	private Stage stage;
	private String title;
	private InstructionsController controller;
	private Scene scene;
	
    public InstructionsScreen(Stage stage, StartScreen startScreen) throws IOException {
        this.stage = stage;
        title = "Instructions";

        controller = new InstructionsController(this);
        controller.setStartScreen(startScreen);
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("InstructionsView.fxml"));
        loader.setController(controller);

        // load into a Parent node called root
        Parent root = loader.load();
        scene = new Scene(root);
        root.requestFocus();
        stage.sizeToScene(); // added
    }

	public void start() {
		// TODO Auto-generated method stub
		stage.setTitle(title);
        stage.setScene(scene);
        //stage.setHeight(400);
        //stage.setWidth(600);
        //stage.sizeToScene(); // added
        stage.show();
	}
}
