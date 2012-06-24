package de.propra12.gruppe04.dynamiteboy.Game;

import de.propra12.gruppe04.dynamiteboy.Item.Exit;
import de.propra12.gruppe04.dynamiteboy.Item.Item;

/**
 * This class is for storing constants, so you only have to declare them ONCE
 * 
 */
public class C {
	// Network
	public static final int SERVER = 1;
	public static final int CLIENT = 0;
	// Game
	public static final double GAME_FREQUENCY = 30.0;
	public static final double MAX_FPS = 60;
	public static final double TIME_BETWEEN_UPDATES = 1000000000 / GAME_FREQUENCY;
	public static final int MAX_UPDATES_BEFORE_RENDER = 5;
	public static final double TARGET_TIME_BETWEEN_RENDERS = 1000000000 / MAX_FPS;
	// Side / movement
	public static final int LEFT = 0, DOWN = 1, RIGHT = 2, UP = 3;
	// Player
	public static final int COLLISION_X_OFFSET = 5;
	public static final int COLLISION_Y_OFFSET = 3;
	public static final int BOMBS = 10;
	public static final int PLAYER1 = 0, PLAYER2 = 1;
	// Bomb
	public static final int BOMB_DELAY = 3000;
	public static final int EXPLOSION_DURATION = 400;
	public static final int BOMB_RADIUS = 3;

	// Destroyable Field
	public static final String DESTROYABLEFIELD_DEFAULT_PIC = "../images/db_field_destroyable.png";
	public static final boolean DESTROYABLEFIELD_DEFAULT_BLOCKED = true;
	public static final boolean DESTROYABLEFIELD_DEFAULT_DESTROYABLE = true;
	public static final boolean DESTROYABLEFIELD_DEFAULT_EXPLODABLE = true;
	public static final Item DESTROYABLEFIELD_DEFAULT_ITEM = null;
	// Exit Field
	public static final String EXITFIELD_DEFAULT_PIC = "../images/db_field_exit.gif";
	public static final boolean EXITFIELD_DEFAULT_BLOCKED = false;
	public static final boolean EXITFIELD_DEAFULT_DESTROYABLE = false;
	public static final boolean EXITFIELD_DEFAULT_EXPLODABLE = true;
	public static final Item EXITFIELD_DEFAULT_ITEM = new Exit(false);
	// Floor Field
	public static final String FLOORFIELD_DEFAULT_PIC = "../images/db_field_floor.png";
	public static final boolean FLOORFIELD_DEFAULT_BLOCKED = false;
	public static final boolean FLOORFIELD_DEAFULT_DESTROYABLE = false;
	public static final boolean FLOORFIELD_DEFAULT_EXPLODABLE = true;
	public static final Item FLOORFIELD_DEFAULT_ITEM = null;
	// Wall Field
	public static final String WALLFIELD_DEFAULT_PIC = "../images/db_field_wall.png";
	public static final boolean WALLFIELD_DEFAULT_BLOCKED = true;
	public static final boolean WALLFIELD_DEAFULT_DESTROYABLE = false;
	public static final boolean WALLFIELD_DEFAULT_EXPLODABLE = false;
	public static final Item WALLFIELD_DEFAULT_ITEM = null;
	public static final boolean WALLFIELD_DEFAULT_DEADLY = false;

}
