package tileengine;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

/**
 * Contains constant tile objects, to avoid having to remake the same tiles in different parts of
 * the code.
 *
 * You are free to (and encouraged to) create and add your own tiles to this file. This file will
 * be turned in with the rest of your code.
 *
 * Ex:
 *      world[x][y] = Tileset.FLOOR;
 *
 * The style checker may crash when you try to style check this file due to use of unicode
 * characters. This is OK.
 */

public class Tileset {
    public static final TETile AVATAR = new TETile('@', Color.white, Color.black, "you", 0);
    public static final TETile WALL = new TETile('#', new Color(216, 128, 128), Color.darkGray, "wall","src/tileengine/MYPhotoTile/Wall_img.png", 1);
    public static final TETile FLOOR = new TETile('*', new Color(128, 192, 128), Color.black, "floor","src/tileengine/MYPhotoTile/Floor_img.png", 2);
    public static final TETile NOTHING = new TETile(' ', Color.black, Color.black, "nothing", 3);
    public static final TETile GRASS = new TETile('"', Color.green, Color.black, "grass","src/tileengine/MYPhotoTile/Grass_img.png" ,4);
    public static final TETile WATER = new TETile('≈', Color.blue, Color.black, "water","src/tileengine/MYPhotoTile/River_img.png", 5);
    public static final TETile FLOWER = new TETile('❀', Color.magenta, Color.pink, "flower","src/tileengine/MYPhotoTile/Flower_img.png", 6);
    public static final TETile LOCKED_DOOR = new TETile('█', Color.orange, Color.black,
            "locked door", "src/tileengine/MYPhotoTile/LockDoor_img.png",7);
    public static final TETile UNLOCKED_DOOR = new TETile('▢', Color.orange, Color.black,
            "unlocked door", "src/tileengine/MYPhotoTile/UnlockDoor_img.png",8);
    //public static final TETile SAND = new TETile('▒', Color.yellow, Color.black, "sand", 9);
    //public static final TETile MOUNTAIN = new TETile('▲', Color.gray, Color.black, "mountain", 10);
    public static final TETile TREE = new TETile('♠', Color.green, Color.black, "tree", 11);

    //public static final TETile CELL = new TETile('█', Color.white, Color.black, "cell", 12);
    public static final TETile ROAD = new TETile('▒', new Color(128, 192, 128), Color.black, "ROAD","src/tileengine/MYPhotoTile/Road_img.png",13);
    public static final TETile STEP = new TETile('▲', Color.orange, Color.black,
            "Step",14);
    public static final Map<Character, TETile> TILE_MAP = new HashMap<>();
    static {
        TILE_MAP.put('@', Tileset.AVATAR);
        TILE_MAP.put('#', Tileset.WALL);
        TILE_MAP.put('*', Tileset.FLOOR);
        TILE_MAP.put(' ', Tileset.NOTHING);
        TILE_MAP.put('"', Tileset.GRASS);
        TILE_MAP.put('≈', Tileset.WATER);
        TILE_MAP.put('❀', Tileset.FLOWER);
        TILE_MAP.put('█', Tileset.LOCKED_DOOR);
        TILE_MAP.put('▢', Tileset.UNLOCKED_DOOR);
        TILE_MAP.put('♠', Tileset.TREE);
        TILE_MAP.put('▒', Tileset.ROAD);
        TILE_MAP.put('▲', Tileset.STEP);
    }
}


