package core;

import tileengine.TETile;
import tileengine.Tileset;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static utils.FileUtils.*;

public class WorldFile {

    public static boolean createEmptyFile(String fileName) {
        String filePath =  fileName;
        boolean flag = false;
        try {
            Path path = Paths.get(filePath);

            // 确保父目录存在
            Path parentDir = path.getParent();
            if (parentDir != null) {
                Files.createDirectories(parentDir);
            }

            // 创建空文件
            if (!Files.exists(path)) {
                Files.createFile(path);
                System.out.println("文件已创建: " + path.toAbsolutePath());
                flag = true;
            } else {
                System.out.println("文件已存在: " + path.toAbsolutePath());
                flag = false;
            }
        } catch (IOException e) {
            System.err.println("创建文件失败: " + e.getMessage());
            e.printStackTrace();
        }
        return flag;
    }
    public static int id = 1;
    //强制覆盖的SaveWorld
    public static void SaveWorld(TETile[][] tiles , long seed, String fileName,int id) {
        int i = id;
        if(fileName == "currentWorld.txt"){
            i = 0;
        }
        String filePath = "src/data/" + fileName;
        String worldString = seed + " " + i + "\n";
        worldString += TETile.toString(tiles);
        writeFile(filePath, worldString);
    }

    public static TETile[][] LoadWorld(String fileName) {
        String filePath = "src/data/" + fileName;
        TETile[][] tiles = null;
        if(!fileExists(filePath)) {
            return null;
        }
        String worldString = readFile(filePath);
        tiles = TETile.fromString(worldString,Tileset.TILE_MAP);
        return tiles;
    }

    public static long readSeed(String fileName) {
        String filePath = "src/data/" + fileName;
        String worldString = readFile(filePath);
        String[] worldStrings = worldString.split("\n");
        String seedString = worldStrings[0];
        String[] seedStrings = seedString.split(" ");
        return Long.parseLong(seedStrings[0]);
    }

    public static int readId(String fileName) {
        String filePath = "src/data/" + fileName;
        String worldString = readFile(filePath);
        String[] worldStrings = worldString.split("\n");
        String idString = worldStrings[0];
        String[] idStrings = idString.split(" ");
        return Integer.parseInt(idStrings[1]);

    }

    public static String[] readFileName(){
        File folder = new File("src/data");

        // 获取所有.txt文件名
        String[] fileNames = folder.list((dir, name) ->
                name.toLowerCase().endsWith(".txt")
        );

        return fileNames;
    }

    public static String readHistory(int id){
        String[] filename = readFileName();
        for(String fileName : filename){
            if(id == readId(fileName)){
                return fileName;
            }
        }
        return null;
    }

    public static int returnId(){
        int id = 0;
       String[] filename = readFileName();
       for(String fileName : filename){
           id = id + 1;
       }
       return id;
    }

//    public static void main(String[] args){
//        TETile[][] tiles = new TETile[8][8];
//        for (int x = 0; x < 8; x++) {
//            for (int y = 0; y < 8; y++) {
//                tiles[x][y] = Tileset.AVATAR;
//            }
//        }
//        tiles[0][1] = Tileset.FLOWER;
//        String[] history = readFileName();
//        for(String fileName : history){
//            System.out.println(fileName);
//        }
//        String filename = readFileName()[0];
//        SaveWorld(tiles, System.currentTimeMillis(), filename);
//        SaveWorld(tiles, System.currentTimeMillis(), "try.txt");
//        tiles = LoadWorld("initWorld.txt");
//        System.out.println(readSeed("initWorld.txt"));
//        if (tiles != null) {
//            TERenderer ter = new TERenderer();
//            ter.initialize(8, 8,0,0);
//            ter.renderFrame(tiles);
//        }
//    }


}
