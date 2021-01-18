package unsw.dungeon.frontend;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import unsw.dungeon.Dungeon;
import unsw.dungeon.Enemy;
import unsw.dungeon.Entity;
import unsw.dungeon.Player;
import unsw.dungeon.Sword;
import unsw.dungeon.Treasure;

/**
 * A JavaFX controller for the dungeon.
 * @author Robert Clifton-Everest
 *
 */
public class DungeonController {

    @FXML
    private GridPane squares;
    
    @FXML
    private BorderPane borderPane;
    
    @FXML
    private GridPane topGrid;
    
    @FXML
    private Button menuButton;
    

    private List<ImageView> initialEntities;
    private List<ImageView> floorTiles;

    private Player player;
    
    // added
    //private Sword currentSword;

    private Dungeon dungeon;

    private Timeline timeline;
    private Timeline dayNight;
    private Label dayLabel;
    private int counter;
    private KeyFrame enemySlow;
    private KeyFrame enemyFast;
    private String dayText;
    
    private StartScreen startScreen;

    private EndScreen endScreen;

    private Image treasureImage;
    private Image invincibilityPotionImage;
    private Image swordImage;
    private Image keyImage;
    
    private ImageView swordView;
    private ImageView potionView;
    private ImageView treasureView;
    private ImageView keyView;
    
    private Label treasureLabel;
    private Label potionLabel;
    private Label swordLabel;
    private Label keyLabel;
    private Label inventoryLabel;
    private Label goalName;
    
    private ColorAdjust colourNew;
    private ColorAdjust colourUncollected;
    private ColorAdjust colourNight;
	
    private DungeonScreen dungeonScreen;
    
    private Image dayBack;
    private Image nightBack;
    private BackgroundSize bSize;
    
    //private EndController endController;
    
    public DungeonController(Dungeon dungeon, List<ImageView> initialEntities, List<Enemy> enemies) {
        this.dungeon = dungeon;
        this.player = dungeon.getPlayer();
        this.initialEntities = new ArrayList<>(initialEntities);
        this.floorTiles = new ArrayList<>();
        
        setUpTimeline(enemies);
        
        setUpDayNight();
        
        addDungeonListeners();
        
        counter = 10;
        dayText = "Day: ";
        
        this.colourNew = new ColorAdjust(0, 0.1, 0.3, 0.5);
        
        this.colourUncollected = new ColorAdjust(-0.4, -0.1, -0.6, -0.8);
        
        colourNight = new ColorAdjust(-0.3, -0.1, -0.2, -0.2);
        
    }

    public void setDungeonScreen(DungeonScreen dungeonScreen) {
    	this.dungeonScreen = dungeonScreen;
    }
    
    private void setUpTimeline(List<Enemy> enemies) {
        timeline = new Timeline();
        enemySlow = new KeyFrame(Duration.seconds(0.9), new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				for (Enemy enemy : enemies) {
					if (enemy.inDungeon().get()) enemy.move();
				}
			}
		});
        enemyFast = new KeyFrame(Duration.seconds(0.4), new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				for (Enemy enemy : enemies) {
					if (enemy.inDungeon().get()) enemy.move();
				}
			}
		});
        timeline.getKeyFrames().add(enemySlow);
        timeline.setCycleCount(Animation.INDEFINITE);
    }
    
    private void setUpDayNight() {
        counter = 10;
        dayText = "Day: ";
        
        dayNight = new Timeline();
        KeyFrame switchDay = new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				counter--;
				if (counter == 0) {
					dungeon.switchDay();
					counter = 10;
				}
				dayLabel.setText(dayText + counter + "s");
				
			}
		});
        dayNight.getKeyFrames().add(switchDay);
        dayNight.setCycleCount(Animation.INDEFINITE);
    }
    
    private void addDungeonListeners() {
        dungeon.getAlive().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (newValue.equals(Boolean.FALSE)) {
					stopTimelines();
					endScreen.start();
					// add this screen to endscreen
				}
			}
        });
        
        dungeon.getComplete().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (newValue.equals(Boolean.TRUE)) {
					stopTimelines();
					endScreen.start();
				}
			}
        });
        
        dungeon.getDay().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (newValue.equals(Boolean.TRUE)) {
					// it is day
					dayText = "Day: ";
					changeToDay();
					timeline.stop();
					timeline.getKeyFrames().clear();
					timeline.getKeyFrames().add(enemySlow);
					timeline.play();
					
					
				} else {
					// it is night
					dayText = "Night: ";
					changeToNight();
					timeline.stop();
					timeline.getKeyFrames().clear();
					timeline.getKeyFrames().add(enemyFast);
					timeline.play();
				}
			}
        });
    }
    
    public void playTimelines() {
    	timeline.play();
    	dayNight.play();
    }
    
    public void stopTimelines() {
    	timeline.stop();
    	dayNight.stop();
    }
    
    public void setStartScreen(StartScreen startScreen) {
    	this.startScreen = startScreen;
    }
    
    public void setEndScreen(EndScreen endScreen) throws IOException {
    	this.endScreen = endScreen;
    	EndController endController = this.endScreen.getController();
    	endController.setDungeon(dungeon);
    	endController.setStartScreen(this.startScreen); 
    	endController.addDungeonScreen(dungeonScreen);
    }
    
    private void makeSquares() {
        Image ground = new Image("/dirt_0_new.png");
        
        squares.setAlignment(Pos.CENTER);
        squares.setGridLinesVisible(true);

        // Add the ground first so it is below all other entities
        for (int x = 0; x < dungeon.getWidth(); x++) {
            for (int y = 0; y < dungeon.getHeight(); y++) {
            	ImageView floor = new ImageView(ground);
                squares.add(floor, x, y);
                floorTiles.add(floor);
            }
        }

        for (ImageView entity : initialEntities) {
        	squares.getChildren().add(entity);
        }
    }
    
    @FXML
    public void initialize() {
        
        BorderPane.setAlignment(squares, Pos.CENTER);
        
        makeSquares();
    	
        // initialise inventory
	    treasureImage = new Image("/gold_pile.png");
	    invincibilityPotionImage = new Image("/brilliant_blue_new.png");
	    swordImage = new Image("./greatsword_1_new.png");
	    keyImage = new Image("./key.png");
	    
	    swordView = new ImageView(swordImage);
	    swordView.setEffect(colourUncollected);
	    treasureView = new ImageView(treasureImage);
	    treasureView.setEffect(colourUncollected);
	    potionView = new ImageView(invincibilityPotionImage);
	    potionView.setEffect(colourUncollected);
	    keyView = new ImageView(keyImage);
	    keyView.setEffect(colourUncollected);
	   
	    GridPane inventoryGrid = new GridPane();
	    
	    inventoryGrid.setGridLinesVisible(true);
	    inventoryGrid.setCenterShape(true);
	    inventoryGrid.setStyle("-fx-background-color: lightgray;");
	  
	    inventoryGrid.add(treasureView, 0, 1);
		inventoryGrid.add(keyView, 0, 2);
		inventoryGrid.add(swordView, 0, 3);
		inventoryGrid.add(potionView, 0, 4);
		
		treasureLabel = new Label("0");
		potionLabel = new Label("Not Active");
		potionLabel.setWrapText(true);
		swordLabel = new Label("0");
		swordLabel.setWrapText(true);
		keyLabel = new Label("0");
		
		inventoryGrid.add(treasureLabel, 1, 1);
		inventoryGrid.add(keyLabel, 1, 2);
		inventoryGrid.add(swordLabel, 1, 3);
		inventoryGrid.add(potionLabel, 1, 4);
		inventoryGrid.setHalignment(treasureLabel, HPos.CENTER); // aligning text to middle
		inventoryGrid.setHalignment(keyLabel, HPos.CENTER);
		inventoryGrid.setHalignment(swordLabel, HPos.CENTER);
		inventoryGrid.setHalignment(potionLabel, HPos.CENTER);
		
		// LAYOUT STUFF HERE
		
		Font fontBigger = Font.font("Comic Sans MS", FontWeight.EXTRA_BOLD, 16); 
		Font fontSmaller = Font.font("Comic Sans MS", FontWeight.BOLD, 16);
		
		this.inventoryLabel = new Label("Inventory");
		inventoryLabel.setFont(fontSmaller);
		
		GridPane rightGrid = new GridPane();
		rightGrid.add(inventoryLabel, 0, 0);
		rightGrid.add(inventoryGrid, 0, 1);
		
		borderPane.setRight(rightGrid);
		this.bSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false);
        this.dayBack = new Image("/Day_Sky.png");
        this.nightBack = new Image("/Night_Sky.jpg");
        borderPane.setBackground(new Background(new BackgroundImage(dayBack,
                BackgroundRepeat.REPEAT,
                BackgroundRepeat.REPEAT,
                BackgroundPosition.CENTER,
                bSize)));
		
		this.goalName = new Label("GOAL: " + this.dungeon.getGoal());
		goalName.setFont(fontBigger);
		goalName.setWrapText(true);
		goalName.setTextAlignment(TextAlignment.CENTER);
		topGrid.add(goalName, 0, 3);
		topGrid.setHalignment(goalName, HPos.CENTER);
		
		
		// need to make day label better looking
        dayLabel = new Label(dayText + counter + "s");
        dayLabel.setFont(fontBigger);
        topGrid.add(dayLabel, 0, 1);
        topGrid.setHalignment(dayLabel, HPos.CENTER);
		
		// add listener to player.inventorySize (the integer property one)
        player.getInventorySizeProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                    Number oldValue, Number newValue) { 
            	displayInventoryItems();
            }
        });
        
        player.getInvincibilityStateProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                    Number oldValue, Number newValue) {
            	updatePotion((int)newValue);
            }
        });
        
        
       
    }

    private void changeToNight() {
    	for (ImageView i : floorTiles) {
    		i.setEffect(colourNight);
    	}
    	// change text to white
    	inventoryLabel.setTextFill(Color.web("#ffffff"));
    	goalName.setTextFill(Color.web("#ffffff"));
    	dayLabel.setTextFill(Color.web("#ffffff"));
    	
    	//borderPane.setStyle("-fx-background-color: black;"); // set background to black
		borderPane.setBackground(new Background(new BackgroundImage(nightBack,
                BackgroundRepeat.REPEAT,
                BackgroundRepeat.REPEAT,
                BackgroundPosition.CENTER,
                bSize)));
    	
    }
    
    private void changeToDay() {
    	for (ImageView i : floorTiles) {
    		i.setEffect(null);
    	}
    	// change text to black
    	inventoryLabel.setTextFill(Color.web("#000000"));
    	goalName.setTextFill(Color.web("#000000"));
    	dayLabel.setTextFill(Color.web("#000000"));
    	
    	//borderPane.setStyle("-fx-background-color: lightblue;"); // set background to blue
		borderPane.setBackground(new Background(new BackgroundImage(dayBack,
                BackgroundRepeat.REPEAT,
                BackgroundRepeat.REPEAT,
                BackgroundPosition.CENTER,
                bSize)));
    }
    
    @FXML
    public void handleKeyPress(KeyEvent event) {
        switch (event.getCode()) {
        case UP:
            player.moveUp();
            break;
        case DOWN:
            player.moveDown();
            break;
        case LEFT:
            player.moveLeft();
            break;
        case RIGHT:
            player.moveRight();
            break;
        case W:
        	player.swingSwordUp();
        	break;
        case A:
        	player.swingSwordLeft();
        	break;
        case S:
        	player.swingSwordDown();
        	break;
        case D:
        	player.swingSwordRight();
        	break;
        case P:
        	player.putBasket();
        	break;
        default:
            break;
        }
    }

    public void menu() throws IOException {
    	stopTimelines();
    	startScreen.start();
    }
    
    public void restart() throws IOException {
    	stopTimelines();
    	dungeonScreen.restart();
    } 
    
    /**
     * Function for updating the amount of each inventory item on screen. 
     */
    @FXML
    public void displayInventoryItems() {
    	
    	if (player.getInventoryItem("key") != null && this.keyLabel != null) {
    		this.keyLabel.setText("1");
    		this.keyView.setEffect(colourNew);
    	}
    	else if (player.getInventoryItem("key") == null && this.keyLabel != null) {
    		this.keyLabel.setText("0");
    		this.keyView.setEffect(colourUncollected);
    	}
    	
    	if (player.getInventoryItem("sword") != null && this.swordLabel != null) {
    		// if the player has a sword, add a listener to that sword and update whenever hit --
    		Sword sword = (Sword) player.getInventoryItem("sword");
    		sword.getHits();
    		
    		// set colour of sword
    		this.swordView.setEffect(this.colourNew);
    		
    		this.swordLabel.setText(sword.getHits().getValue().toString());
    		sword.getHits().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observable,
                        Number oldValue, Number newValue) {
                	updateSwordHits((int) newValue);
                	
                }
            });
    		
    	}
    	else if (player.getInventoryItem("sword") == null && this.swordLabel != null) {
    		this.swordLabel.setText("0");
    	}
    	
    	if (!player.isInvincible() && this.potionLabel != null) {
    		this.potionLabel.setText("Not Active");
    	}
    	
    	
    	// sum up treasure
    	int count = 0;
    	for (Entity e : player.getInventory()) {
    		if (e.getClass() == Treasure.class) {
    			count++;
    		}
    	}
    	
    	if (count > 0) {
    		this.treasureView.setEffect(colourNew);
    	}
    	this.treasureLabel.setText(Integer.toString(count));
    	
	
    }
    
    /**
     * Updates moves left for invincibility potion.
     * @param newNum
     */
    @FXML
    public void updatePotion(Integer newNum) {
    	this.potionView.setEffect(colourNew);
    	this.potionLabel.setText(newNum.toString() + " Move(s) Left!");
    	if (newNum == 0) {
    		this.potionView.setEffect(this.colourUncollected);
    	}
    }
    
    @FXML
    public void updateSwordHits(Integer newNum) {
    	this.swordLabel.setText(newNum.toString() + " Hit(s) Left!");
    	if (newNum == 0) {
    		this.swordView.setEffect(this.colourUncollected);
    	}
    }

}

