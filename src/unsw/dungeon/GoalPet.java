package unsw.dungeon;

import java.util.ArrayList;

public class GoalPet implements Goal {
	
	private Dungeon dungeon;
	private ArrayList<Pet> pets;
	private int collected;
	
	/**
	 * Constructor for a new pet goal
	 * @param dungeon dungeon the goal belongs to
	 */
	public GoalPet(Dungeon dungeon) {
		this.dungeon = dungeon;
		this.pets = new ArrayList<Pet>();
		addPetsToGoal();
		this.collected = 0;
	}
	
	/**
	 * Add all pets in the dungeon to the pet goal
	 */
	private void addPetsToGoal() {
		for (int i = 0; i < dungeon.getWidth(); i++) {
			for (int j = 0; j < dungeon.getHeight(); j++) {
				ArrayList<Entity> p = dungeon.getGridEntities(i, j);
				p.removeIf(e -> e.getClass() != Pet.class);
				for (Entity entity : p) {
					Pet pet = (Pet) entity;
					pets.add(pet);
					pet.setGoal(this);
				}
			}
		}
	}
	
	@Override
	public boolean checkComplete() {
		return pets.size() == collected;
	}

	@Override
	public void updateSelf() {
		collected++;
		dungeon.updateGoal();
	}

	@Override
	public String toString() {
		return "Deliver all snakes to the baskets";
	}
	
}
