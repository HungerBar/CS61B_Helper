package core;

import tileengine.TETile;
import tileengine.Tileset;

import java.util.Random;

public class DarkWorld {
    public  int HEIGHT = 50;
    public  int WIDTH = 85;
    public Random random;

    public static TETile[][]  initDarkWorld(int width, int  height) {
        TETile[][] darkWorld = new TETile[width][height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                darkWorld[x][y] = Tileset.NOTHING;
            }
        }
        return darkWorld;
    }

    public static TETile[][] initDarkVision(int x, int y, TETile[][] world, TETile[][] darkWorld, int range) {
        // 确保范围为奇数且至少为1
        if (range < 1) range = 1;
        if (range % 2 == 0) range += 1;  // 将偶数转为奇数

        int half = (range - 1) / 2;  // 计算单边扩展距离
        int width = world[0].length;
        int height = world.length;

        // 计算视野边界（确保不超出数组范围）
        int startX = Math.max(0, x - half);
        int endX = Math.min(height - 1, x + half);
        int startY = Math.max(0, y - half);
        int endY = Math.min(width - 1, y + half);

        // 更新视野范围内的图块
        for (int i = startX; i <= endX; i++) {
            for (int j = startY; j <= endY; j++) {
                // 玩家位置设为AVATAR，其他位置复制world
                darkWorld[i][j] = (i == x && j == y) ? Tileset.AVATAR : world[i][j];
            }
        }

        return darkWorld;
    }

    public static TETile[][] initDarkBackward(int x, int y, TETile[][] darkWorld, int range) {
        // 确保范围为奇数且至少为1
        if (range < 1) range = 1;
        if (range % 2 == 0) range += 1;  // 将偶数转为奇数

        int half = (range - 1) / 2;  // 计算单边扩展距离
        int width = darkWorld[0].length;
        int height = darkWorld.length;

        // 计算清除边界（确保不超出数组范围）
        int startX = Math.max(0, x - half);
        int endX = Math.min(height - 1, x + half);
        int startY = Math.max(0, y - half);
        int endY = Math.min(width - 1, y + half);

        // 清除指定范围内的图块
        for (int i = startX; i <= endX; i++) {
            for (int j = startY; j <= endY; j++) {
                darkWorld[i][j] = Tileset.NOTHING;
            }
        }

        return darkWorld;
    }

    public static main.position whatInVision(TETile[][] world, int x, int y, int r) {
        int half = (r - 1) / 2;
        int width = world[0].length;
        int height = world.length;


        int startX = Math.max(0, x - half);
        int endX = Math.min(height - 1, x + half);
        int startY = Math.max(0, y - half);
        int endY = Math.min(width - 1, y + half);

        for (int i = startX; i <= endX; i++) {
            for (int j = startY; j <= endY; j++) {
                if (world[i][j] == Tileset.STEP)
                    return new main.position(i, j,"Key");
                else if(world[i][j] == Tileset.FLOWER){
                    return new main.position(i, j,"Light");
                }else if(world[i][j] == Tileset.LOCKED_DOOR){
                    return new main.position(i, j,"Exit");
                }
            }
        }
        return null;

    }

    public static boolean ifLightInSight(TETile[][] world, int x, int y) {
        main.position position = whatInVision(world, x, y,5);
        if (position == null) return false;
        else if (position.name.equals("Light")) return true;
        else return false;
    }

    public static boolean ifExitInSight(TETile[][] world, int x, int y) {
        main.position position = whatInVision(world, x, y,15);
        if (position == null) return false;
        else if (position.name.equals("Exit")) return true;
        else return false;
    }

    public static boolean ifKeyInSight(TETile[][] world, int x, int y) {
        main.position position = whatInVision(world, x, y,15);
        if (position == null) return false;
        else if (position.name.equals("Key")) return true;
        else return false;
    }





}
