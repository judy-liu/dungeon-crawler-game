package unsw.dungeon.frontend;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import unsw.dungeon.*;

/**
 * A DungeonLoader that also creates the necessary ImageViews for the UI,
 * connects them via listeners to the model, and creates a controller.
 * @author Robert Clifton-Everest
 *
 */
public class DungeonControllerLoader extends DungeonLoader {

    private List<ImageView> entities;

    private ArrayList<Enemy> enemies;
    
    @FXML
    private GridPane inventoryGrid;

    //Images
    private Image playerImage;
    private Image playerInvincibleImage;
    private Image wallImage;
    private Image treasureImage;
    private Image invincibilityPotionImage;
    private Image swordImage;
    private Image keyImage;
    private Image enemyImage;
    private Image boulderImage;
    private Image switchImage;
    private Image doorImage;
    private Image openDoorImage;
    private Image portalImage;
    private Image petImage;
    private Image emptyBasketImage;
    private Image fullBasketImage;
    private Image exitImage;

    private ColorAdjust colourNight;
    private ColorAdjust[] doorColour;

    public DungeonControllerLoader(String filename)
            throws FileNotFoundException {
        super(filename);
        entities = new ArrayList<>();
        playerImage = new Image("/human_new.png");
        playerInvincibleImage = new Image("/human_invincible.png");
        wallImage = new Image("/brick_brown_0.png");
        treasureImage = new Image("/gold_pile.png");

        invincibilityPotionImage = new Image("/brilliant_blue_new.png");
        swordImage = new Image("./greatsword_1_new.png");
        keyImage = new Image("./key.png");
        enemyImage = new Image("/deep_elf_master_archer.png");
        boulderImage = new Image("/boulder.png");
        switchImage = new Image("/pressure_plate.png");
        doorImage = new Image("/closed_door_light.png");
        openDoorImage = new Image("/open_door_light.png");
        enemies = new ArrayList<Enemy>();
        portalImage = new Image("./portal.png");
        //portalImage = new Image(("file:/portal.gif"));
        petImage = new Image("/snek.png");
        emptyBasketImage = new Image("/empty_basket.png");
        fullBasketImage = new Image("/full_basket.png");
        exitImage = new Image("/exit.png");
       
        colourNight = new ColorAdjust(-0.3, -0.1, -0.2, -0.2);

        doorColour = new ColorAdjust[5];
        doorColour[0] = new ColorAdjust(0.7, 0.2, 0, 0); // light blue
        doorColour[1] = new ColorAdjust(0.1, 0.4, 0, 0); // yellow
        doorColour[2] = new ColorAdjust(-0.3, 0.2, 0, 0); // red
        doorColour[3] = new ColorAdjust(-0.7, 0.2, 0, 0); // purple
        doorColour[4] = new ColorAdjust(0.5, 0.2, 0, 0); // green
    }
    
    
    public List<ImageView> getEntities() {
		return entities;
	}

	public ArrayList<Enemy> getEnemies() {
		return enemies;
	}



	@Override
    public void onLoad(Entity player) {
        ImageView view = new ImageView(playerImage);
        setDayListener(player, view);
        addEntity(player, view);
        // add listener here
        // listen to player's invincibility status
        Player play = (Player) player;
        play.getInvincibilityStateProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                    Number oldValue, Number newValue) {
            	if (newValue.intValue() == 0) {
            		view.setImage(playerImage);
            	}
            	else {
            		view.setImage(playerInvincibleImage);
            	}
            }
        });
    }

    @Override
    public void onLoad(Wall wall) {
        ImageView view = new ImageView(wallImage);
        setDayListener(wall, view);
        addEntity(wall, view);
    }
	
	@Override
	public void onLoad(Enemy enemy) {
		// TODO Auto-generated method stub
		ImageView view = new ImageView(enemyImage);
		setDayListener(enemy, view);
		addEntity(enemy, view);
		enemies.add(enemy);
	}

	@Override
	public void onLoad(Boulder boulder) {
		// TODO Auto-generated method stub
		ImageView view = new ImageView(boulderImage);
		setDayListener(boulder, view);
		addEntity(boulder, view);
	}

	@Override
	public void onLoad(Switch swit) {
		// TODO Auto-generated method stub
		ImageView view = new ImageView(switchImage);
		setDayListener(swit, view);
		addEntity(swit, view);	
	}

	@Override 
	public void onLoad(Treasure treasure) {
		ImageView view = new ImageView(treasureImage);
		setDayListener(treasure, view);
        addEntity(treasure, view);
	}	
	
	@Override
	public void onLoad(Key key) {
		ImageView view = new ImageView(keyImage);
		view.setEffect(doorColour[key.getKeyID()]);
		key.getDungeon().getDay().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (newValue.equals(Boolean.TRUE)) {
					// it is day
					view.setEffect(doorColour[key.getKeyID()]);
				} else {
					// it is night
					view.setEffect(colourNight);
				}
			}
        });
        addEntity(key, view);
	}
	
	@Override 
	public void onLoad(Sword sword) {
		ImageView view = new ImageView(swordImage);
		setDayListener(sword, view);
        addEntity(sword, view);
	}
	
	@Override 
	public void onLoad(InvincibilityPotion invincibilityPotion) {
		ImageView view = new ImageView(invincibilityPotionImage);
		setDayListener(invincibilityPotion, view);
        addEntity(invincibilityPotion, view);
	}
	
	@Override 
	public void onLoad(Door door) {
		ImageView view = new ImageView(doorImage);
		view.setEffect(doorColour[door.getId()]);
		door.locked().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (newValue.equals(Boolean.FALSE)) {
					view.setImage(openDoorImage);
				}
			}
		});
		door.getDungeon().getDay().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (newValue.equals(Boolean.TRUE)) {
					// it is day
					view.setEffect(doorColour[door.getId()]);
				} else {
					// it is night
					view.setEffect(colourNight);
				}
			}
        });
        addEntity(door, view);
	}
	
	@Override 
	public void onLoad(Portal portal) {
		ImageView view = new ImageView(portalImage);
		setDayListener(portal, view);
        addEntity(portal, view);
	}


	@Override
	public void onLoad(Pet pet) {
		ImageView view = new ImageView(petImage);
		setDayListener(pet, view);
        addEntity(pet, view);
	}
	
    @Override
    public void onLoad(Exit exit) {
        ImageView view = new ImageView(exitImage);
        setDayListener(exit, view);
        addEntity(exit, view);
    }
	
	@Override
	public void onLoad(Basket basket) {
		ImageView view = new ImageView(emptyBasketImage);
		basket.hasPet().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (newValue.equals(Boolean.TRUE)) {
					view.setImage(fullBasketImage);
				}
			}
        });
		setDayListener(basket, view);
        addEntity(basket, view);
	}
	
	private void setDayListener(Entity entity, ImageView view) {
		entity.getDungeon().getDay().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (newValue.equals(Boolean.TRUE)) {
					// it is day
					view.setEffect(null);
				} else {
					// it is night
					view.setEffect(colourNight);
				}
			}
        });
	}
	
    private void addEntity(Entity entity, ImageView view) {
        trackPosition(entity, view);
        entity.inDungeon().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (newValue.equals(Boolean.FALSE)) {
					view.setVisible(false);
				} else {
					view.setVisible(true);
				}
			}
        });
        entities.add(view);
    }

    /**
     * Set a node in a GridPane to have its position track the position of an
     * entity in the dungeon.
     *
     * By connecting the model with the view in this way, the model requires no
     * knowledge of the view and changes to the position of entities in the
     * model will automatically be reflected in the view.
     * @param entity
     * @param node
     */
    private void trackPosition(Entity entity, Node node) {
        GridPane.setColumnIndex(node, entity.getX());
        GridPane.setRowIndex(node, entity.getY());
        entity.x().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                    Number oldValue, Number newValue) {
            	// make sure the moved item is the topmost entity in the view
            	GridPane grid = (GridPane) node.getParent();
            	grid.getChildren().remove(node);
            	grid.getChildren().add(node);
                GridPane.setColumnIndex(node, newValue.intValue());
            }
        });
        entity.y().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                    Number oldValue, Number newValue) {
            	GridPane grid = (GridPane) node.getParent();
            	grid.getChildren().remove(node);
            	grid.getChildren().add(node);
                GridPane.setRowIndex(node, newValue.intValue());
            }
        });
    }

    /**
     * Create a controller that can be attached to the DungeonView with all the
     * loaded entities.
     * @return
     * @throws FileNotFoundException
     */
    public DungeonController loadController() throws FileNotFoundException {
        return new DungeonController(load(), entities, enemies);
    }

}
