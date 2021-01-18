# Dungeon Style Desktop Game

## Overview

You have received a request from a client for an application for the playing of dungeon-style puzzles. With a partner from your lab class, you will follow an agile development process to design and implement a desktop Java application that satisfies the requirements of the client (see below). The final piece of software you deliver is expected to be of professional quality, user-friendly, and demonstrate the knowledge and skills you have acquired in this course.

## Project setup

Because this project uses JavaFX, it requires some additional setup steps in Eclipse. It's relatively straightforward if you're using a CSE computer, but if you're using your own computer you will need to download an alternate JDK.

0. Go [here](https://www.azul.com/downloads/zulu-community/?&package=jdk-fx) and download the Java 11 Zulu JDK FX for your OS. Unzip it once it finishes downloading.
1. In Eclipse, import this project as normal. You should see an exclamation mark on the project indicating an error.
2. Go to **Window -> Preferences**. On the left, under **Java** select **Installed JREs** then click **Add**. Ensure **Standard VM** is selected and click **Next**.
3. Click **Directory** and select the directory of the Zulu JDK you unzipped in step 1. Change **JRE name** to exactly `jdk-jfx`.
4. Click **Finish** then **Apply and Close**

If these steps worked, the project should no longer have the exclamation mark on it and you should be able to run the starter code.

To run the game in Eclipse, we need to run `DungeonApplication.java`. You can do this via the IDE by double clicking on `DungeonApplication.java` and then clicking the green play button in the toolbar.

## Preliminary client requirements

The client desires an application that lets the user move a player around a dungeon and try to overcome various challenges in order to "complete" the dungeon by reaching some goal. The simplest form of such a puzzle is a maze, where the player must find their way from the starting point to the exit.

![Maze][maze]

More advanced puzzles may contain things like boulders that need to be pushed onto floor switches,

![Boulders][boulders]

enemies that need to be fought with weapons, or collectables like potions and treasure.

![Advanced dungeon][advanced]

### Dungeon layout

To be specific, the layout of each dungeon is defined by a grid of squares, each of which may contain one or more entities. The different types of entities are as follows:

| Entity               | Example                                 | Description                                                                                                                                                                                                                                                                   |
| -------------------- | --------------------------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| Player               | ![Player][player]                       | Can be moved up, down, left, and right into adjacent squares, provided another entity doesn't stop them (e.g. a wall).                                                                                                                                                        |
| Wall                 | ![Wall][wall]                           | Blocks the movement of the player, enemies and boulders.                                                                                                                                                                                                                      |
| Exit                 | ![Exit][exit]                           | If the player goes through it the puzzle is complete.                                                                                                                                                                                                                         |
| Treasure             | ![Treasure][treasure]                   | Can be collected by the player.                                                                                                                                                                                                                                               |
| Door                 | ![Door][door_open] ![Door][door_closed] | Exists in conjunction with a single key that can open it. If the player holds the key, they can open the door by moving through it. Once open it remains so. The client will be satisfied if dungeons can be made with up to 3 doors.                                         |
| Key                  | ![Key][key]                             | Can be picked up by the player when they move into the square containing it. The player can carry only one key at a time, and only one door has a lock that fits the key. It disappears once it is used to open its corresponding door.                                       |
| Boulder              | ![Boulder][boulder]                     | Acts like a wall in most cases. The only difference being that it can be pushed by the player into adjacent squares. The player is only strong enough to push **one** boulder at a time.                                                                                      |
| Floor switch         | ![Floor switch][switch]                 | Switches behave like empty squares, so other entities can appear on top of them. When a boulder is pushed onto a floor switch, it is triggered. Pushing a boulder off the floor switch untriggers it.                                                                         |
| Portal               | ![Portal][portal]                       | Teleports entities to a corresponding portal.                                                                                                                                                                                                                                 |
| Enemy                | ![Enemy][enemy]                         | Constantly moves toward the player, stopping if it cannot move any closer. The player dies upon collision with an enemy.                                                                                                                                                      |
| Sword                | ![Sword][sword]                         | This can be picked up the player and used to kill enemies. Only one sword can be carried at once. Each sword is only capable of 5 hits and disappears after that. One hit of the sword is sufficient to destroy any enemy.                                                    |
| Invincibility potion | ![Invincibility][invincibility]         | If the player picks this up they become invincible to enemies. Colliding with an enemy should result in their immediate destruction. Because of this, all enemies will run away from the player when they are invincible. The effect of the potion only lasts a limited time. |

### Goals

In addition to its layout, each dungeon also has a goal that defines what must be achieved by the player for the dungeon to be considered complete. Basic goals are:

- Getting to an exit.
- Destroying all enemies.
- Having a boulder on all floor switches.
- Collecting all treasure.

More complex goals can be built by logically composing basic goals. For example,

- Destroying all enemies AND getting to an exit
- Collecting all treasure OR having a boulder on all floor switches
- Getting to an exit AND (destroying all enemies OR collecting all treasure)

If getting to an exit is one of a conjunction of conditions, it must be done last. For example, if the condition is to destroy all enemies AND get to an exit, the player must destroy the enemies _then_ get to the exit.

### Input

Your application will read from a JSON file containing a complete specification of the dungeon (the initial position of entities, goal, etc.). Example dungeons are included in the `dungeons` directory and the starter code contains an incomplete dungeon loader.

The dungeon files have the following format:

> { "width": _width in squares_, "height": _height in squares_, "entities": _list of entities_, "goal-condition": _goal condition_ }

Each entity in the list of entities is structured as:

> { "type": _type_, "x": _x-position_, "y": _y-position_ }

where _type_ is one of

> ["player", "wall", "exit", "treasure", "door", "key", "boulder", "switch", "portal", "enemy", "sword", "invincibility"]

The `door`, `key`, and `portal` entities include an additional field `id` containing a number. Keys open the door with the same `id` (e.g. the key with `id` 0 opens the door with `id` 0). Portals will teleport entities to the **one** other portal with the same ID.

The goal condition is a JSON object representing the logical statement that defines the goal. Basic goals are:

> { "goal": _goal_ }

where _goal_ is one of

> ["exit", "enemies", "boulders", "treasure"]

In the case of a more complex goal, _goal_ is the logical operator and the additional _subgoals_ field is a JSON array containing subgoals, which themselves are goal conditions. For example,

```JSON
{ "goal": "AND", "subgoals":
  [ { "goal": "exit" },
    { "goal": "OR", "subgoals":
      [ {"goal": "enemies" },
        {"goal": "treasure" }
      ]
    }
  ]
}
```

Note that the same basic goal _can_ appear more than once in a statement.

You can extend this format to include additional information if you wish, but your application should still work with files in the original format.

### User interface

The UI component of this project will be implemented in JavaFX. The starter code contains a very basic UI showing how a player can be moved around with the arrow keys, but it is missing many features (the player can walk through walls for one).

The client has given you free reign over the visual design of the program. Included in the starter code are some example assets, but you are free to use different ones. You can find them elsewhere or even create your own. The examples above came from [here](http://opengameart.org).

[player]: images/human_new.png
[wall]: images/brick_brown_0.png
[exit]: images/exit.png
[door_open]: images/open_door.png
[door_closed]: images/closed_door.png
[key]: images/key.png
[boulder]: images/boulder.png
[switch]: images/pressure_plate.png
[portal]: images/portal.png
[enemy]: images/deep_elf_master_archer.png
[sword]: images/greatsword_1_new.png
[invincibility]: images/brilliant_blue_new.png
[treasure]: images/gold_pile.png
[maze]: examples/maze.png
[boulders]: examples/boulders.png
[advanced]: examples/advanced.png
