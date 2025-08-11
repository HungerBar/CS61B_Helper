package core;

import tileengine.TETile;
import tileengine.Tileset;

import java.util.ArrayList;
import java.util.Random;

public class initialWorldGenerator {
    public  int HEIGHT = 50;
    public  int WIDTH = 85;
    public Random random;
    TETile[][] world;

    public initialWorldGenerator(Random r) {
        random = r;
        world = new TETile[WIDTH][HEIGHT];
    }

    public initialWorldGenerator(Random r, int width, int height) {
        random = r;
        HEIGHT = height;
        WIDTH = width;
        world = new TETile[WIDTH][HEIGHT];
    }

    public void initialGenerateWorld() {
       world = initialWithNothing();
       randomHouseAndRoad();
       cleanSingleRoad();
       buildDoor();
       buildStep();
       openDoor();
       cleanStep();
       buildwall();
       buildAttaches();
       buildLight();
       buildKey();
       buildExit();
    }

    //initialization,tile==NOTHING
    private TETile[][] initialWithNothing(){
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                world[x][y] = Tileset.NOTHING;
            }
        }
        return world;
    }

    //Build randomHouse
    //build the road between current house and previous house to ensure connectivity
    //Randomly add road between current house and exited house
    //Stop until tilecount > totaltiles * 0.6
    //return: random door_array
    private void randomHouseAndRoad(){
        ArrayList<house> houses = new ArrayList<>();
        int tilecount = 0;
        int totaltiles = WIDTH * HEIGHT;
        int x,y;
        int p=0,q=0;
        int i=0;
        house h;
        while (tilecount < (totaltiles * 0.4)){
            x = random.nextInt(1,WIDTH-8);
            y= random.nextInt(1,HEIGHT-8);
            while((Math.abs(x - p) <10 || Math.abs(y-q) < 10) || (Math.abs(x-p) >30 || Math.abs(y-q) > 30)){
                x = random.nextInt(1,WIDTH-8);
                y= random.nextInt(1,HEIGHT-8);
            }
            h = new house(i,x,y);
            tilecount += buildHouse(x,y);
            if(i>0){
                tilecount += buildRoad(h,houses.getLast());
            }
            if(i>1){
               int flag = random.nextInt(i-1);
               int flag2 = random.nextInt(50);
               if(flag2 == 0) {
                   tilecount += buildRoad(h,houses.get(flag));
               }
           }
            houses.add(h);
            i++;
            p = x;
            q = y;
        }
    }

    //id
    //location is a dot in this house
    private class house{
        int id;
        int[] location = new int[2];

        public house(int i, int x, int y) {
            id = i;
            location[0] = x;
            location[1] = y;
        }
    }

    //build road between houses
    private int buildRoad(house h1, house h2) {
        int count = 0;
        if (h1.id == h2.id) {
            return 0;
        }
        int x1 = h1.location[0];int y1 = h1.location[1];
        int x2 = h2.location[0];int y2 = h2.location[1];
        if (x1 <= x2) {
            if(h1.id == h2.id +1) {count = buildRoadHelper(x1, x2, y1, y2);}
            else count = buildshortRoadHelper(x1,x2,y1,y2);
        }else{
            if(h1.id == h2.id +1) {count = buildRoadHelper(x2, x1, y2, y1);}
            else count = buildshortRoadHelper(x2,x1,y2,y1);
        }
        return count;
    }

    private int buildRoadHelper(int x1, int x2, int y1, int y2) {
        int count = 0;
        int flag;
        if (y1 <= y2) {
            while(x1 < x2 && y1 < y2) {
                flag = random.nextInt(2);
                if(flag == 0) {
                    x1++;
                    if(world[x1][y1] == Tileset.NOTHING) {
                        world[x1][y1] = Tileset.ROAD;
                        count++;
                    }
                }else{
                    y1++;
                    if(world[x1][y1] == Tileset.NOTHING) {
                        world[x1][y1] = Tileset.ROAD;
                        count++;
                    }
                }
            }
            if(x1 == x2){
                while(y1 < y2){
                    y1++;
                    if(world[x1][y1] == Tileset.NOTHING) {
                        world[x1][y1] = Tileset.ROAD;
                        count++; }
                }
            }else{
                while(x1 < x2){
                    x1++;
                    if(world[x1][y1] == Tileset.NOTHING) {
                        world[x1][y1] = Tileset.ROAD;
                        count++;
                    }
                }
            }
        }
        else{
            while(x1 < x2 && y1 > y2){
                flag = random.nextInt(2);
                if(flag == 0) {
                    x1++;
                    if(world[x1][y1] == Tileset.NOTHING) {
                        world[x1][y1] = Tileset.ROAD;
                        count++;
                    }
                }else{
                    y1--;
                    if(world[x1][y1] == Tileset.NOTHING) {
                        world[x1][y1] = Tileset.ROAD;
                        count++;
                    }
                }
            }
            if(x1 == x2){
                while(y1 > y2){
                    y1--;
                    if(world[x1][y1] == Tileset.NOTHING) {
                        world[x1][y1] = Tileset.ROAD;
                        count++;
                    }
                }
            }else{
                while(x1 < x2){
                    x1++;
                    if(world[x1][y1] == Tileset.NOTHING) {
                        world[x1][y1] = Tileset.ROAD;
                        count++;
                    }
                }
            }

        }
        return count;
    }

    private int buildshortRoadHelper(int x1, int x2, int y1, int y2) {
        int count = 0;
        int flag;
        if (y1 <= y2) {
            while(x1 < x2 && y1 < y2) {
                flag = random.nextInt(2);
                if(flag == 0) {
                    x1++;
                    if(world[x1][y1] == Tileset.NOTHING) {
                        world[x1][y1] = Tileset.ROAD;
                        count++;
                    }else if(world[x1][y1] == Tileset.ROAD){
                        return count;
                    }
                }else{
                    y1++;
                    if(world[x1][y1] == Tileset.NOTHING) {
                        world[x1][y1] = Tileset.ROAD;
                        count++;
                    }else if(world[x1][y1] == Tileset.ROAD){
                        return count;
                    }
                }
            }
            if(x1 == x2){
                while(y1 < y2){
                    y1++;
                    if(world[x1][y1] == Tileset.NOTHING) {
                        world[x1][y1] = Tileset.ROAD;
                        count++; }
                    else if(world[x1][y1] == Tileset.ROAD){
                        return count;
                    }

                }
            }else{
                while(x1 < x2){
                    x1++;
                    if(world[x1][y1] == Tileset.NOTHING) {
                        world[x1][y1] = Tileset.ROAD;
                        count++;
                    }else if(world[x1][y1] == Tileset.ROAD){
                        return count;
                    }
                }
            }
        }
        else{
            while(x1 < x2 && y1 > y2){
                flag = random.nextInt(2);
                if(flag == 0) {
                    x1++;
                    if(world[x1][y1] == Tileset.NOTHING) {
                        world[x1][y1] = Tileset.ROAD;
                        count++;
                    }else if(world[x1][y1] == Tileset.ROAD){
                        return count;
                    }
                }else{
                    y1--;
                    if(world[x1][y1] == Tileset.NOTHING) {
                        world[x1][y1] = Tileset.ROAD;
                        count++;
                    }else if(world[x1][y1] == Tileset.ROAD){
                        return count;
                    }
                }
            }
            if(x1 == x2){
                while(y1 > y2){
                    y1--;
                    if(world[x1][y1] == Tileset.NOTHING) {
                        world[x1][y1] = Tileset.ROAD;
                        count++;
                    }else if(world[x1][y1] == Tileset.ROAD){
                        return count;
                    }
                }
            }else{
                while(x1 < x2){
                    x1++;
                    if(world[x1][y1] == Tileset.NOTHING) {
                        world[x1][y1] = Tileset.ROAD;
                        count++;
                    }else if(world[x1][y1] == Tileset.ROAD){
                        return count;
                    }
                }
            }

        }
        return count;
    }

    //build house with a (x,y)
    private int buildHouse(int x,int y){
        int count = 0;
        int w = random.nextInt(-2,3);
        int h = random.nextInt(-2,3);
        int Rightboundary = WIDTH-2;
        int Topboundary = HEIGHT-2;
        Topboundary = Math.min(Topboundary,h+y+5);
        Rightboundary = Math.min(Rightboundary,w+x+5);
        for(int i = x; i <= Rightboundary; i++) {
            for(int j = y; j <= Topboundary; j++) {
                if(world[i][j] == Tileset.NOTHING) {
                    count++;
                }
                world[i][j] = Tileset.FLOOR;
            }
        }
        return count;
    }

    private void cleanSingleRoad(){
        for(int y = 1; y < HEIGHT-5; y++){
            for(int x = 1; x < WIDTH-5; x++){
                if(world[x][y] == Tileset.ROAD) {
                    if(roadNeighborCount(x,y) == 0) {
                        world[x][y] = Tileset.NOTHING;
                    }
                }
            }
        }
    }

    private int roadNeighborCount(int x,int y){
        int count = 0;
        if(world[x-1][y] == Tileset.ROAD) {
            count++;
        }if (world[x][y-1] == Tileset.ROAD) {
            count++;
        }if (world[x][y+1] == Tileset.ROAD) {
            count++;
        }if (world[x+1][y] == Tileset.ROAD) {
            count++;
        }
        return count;
    }

    private int floorNeighborCount(int x,int y){
        int count = 0;
        if(world[x][y-1] == Tileset.FLOOR) {
            count++;
        }if (world[x][y+1] == Tileset.FLOOR) {
            count++;
        }if (world[x+1][y] == Tileset.FLOOR) {
            count++;
        }if (world[x-1][y] == Tileset.FLOOR) {
            count++;
        }
        return count;
    }
//
    private int stepNeighborCount(int x,int y){
        int count = 0;
        if(world[x][y-1] == Tileset.STEP) {
            count++;
        }if (world[x][y+1] == Tileset.STEP) {
            count++;
        }if (world[x+1][y] == Tileset.STEP) {
            count++;
        }if (world[x-1][y] == Tileset.STEP) {
            count++;
        }
        return count;
    }

   private int nothingNeighborCount(int x,int y){
        int count = 0;
        if(world[x][y-1] == Tileset.NOTHING) {
            count++;
        }if (world[x][y+1] == Tileset.NOTHING) {
            count++;
        }if (world[x+1][y] == Tileset.NOTHING) {
            count++;
        }if (world[x-1][y] == Tileset.NOTHING) {
            count++;
        }
        return count;
    }

   private int doorNeighborCount(int x,int y){
        int count = 0;
        if(world[x][y-1] == Tileset.LOCKED_DOOR || world[x][y-1] == Tileset.UNLOCKED_DOOR) {
            count++;
        }if (world[x][y+1] == Tileset.LOCKED_DOOR || world[x][y+1] == Tileset.UNLOCKED_DOOR) {
            count++;
        }if(world[x+1][y] == Tileset.LOCKED_DOOR || world[x+1][y] == Tileset.UNLOCKED_DOOR) {
            count++;
        }if(world[x-1][y] == Tileset.LOCKED_DOOR || world[x-1][y] == Tileset.UNLOCKED_DOOR) {
            count++;
        }
        return count;
    }

   private void buildDoor(){
        for(int y = 1; y < HEIGHT-5; y++){
            for(int x = 1; x < WIDTH-5; x++){
                if(world[x][y] == Tileset.FLOOR) {
                    if(roadNeighborCount(x,y) >=1 ) {
                        int flag = 0;
                        if(doorNeighborCount(x,y) >=1 )  flag = 1;
                        if(flag == 0) {
                            world[x][y] = Tileset.UNLOCKED_DOOR;
                        }else{
                            world[x][y] = Tileset.LOCKED_DOOR;
                        }
                    }
                }
            }
        }
    }

   private void buildStep(){
        for(int y = 1; y < HEIGHT-5; y++){
            for(int x = 1; x < WIDTH-5; x++) {
                if (world[x][y] == Tileset.ROAD ) {
                    if((roadNeighborCount(x,y) ==1 || (roadNeighborCount(x,y) == 0 && stepNeighborCount(x,y)==1))&& doorNeighborCount(x,y) ==1 ) {
                        world[x][y] = Tileset.STEP;
                    }
                }
            }
        }
    }

   private void openDoor(){
        for(int y = 1; y < HEIGHT-5; y++){
            for(int x = 1; x < WIDTH-5; x++){
                if(world[x][y] == Tileset.LOCKED_DOOR) {
                    if(stepNeighborCount(x,y) ==1 ) {
                        world[x][y] = Tileset.UNLOCKED_DOOR;
                    }
                }
            }
        }
    }

   private void cleanStep(){
        for(int y = 1; y < HEIGHT-5; y++){
            for(int x = 1; x < WIDTH-5; x++){
                if(world[x][y] == Tileset.STEP) {
                    world[x][y] = Tileset.ROAD;
                }
            }
        }
    }

   private void buildwall(){
        for(int y = 1; y < HEIGHT-1; y++){
            for(int x = 1; x < WIDTH-1; x++) {
               if(world[x][y] == Tileset.FLOOR) {
                   if(nothingNeighborCount(x,y) >=1 ) {
                       world[x][y] = Tileset.WALL;
                   }
               }
               if(world[x][y] == Tileset.LOCKED_DOOR) {
                   world[x][y] = Tileset.WALL;
               }
               if(world[x][y] == Tileset.NOTHING) {
                   if(roadNeighborCount(x,y) >=1 ) {
                       world[x][y] = Tileset.WALL;
                   }
               }
            }
        }
    }

   private void buildAttaches(){
        for(int y = 0; y < HEIGHT-1; y++){
            for(int x = 0; x < WIDTH-1; x++){
                if(world[x][y] == Tileset.NOTHING) {
                    int flag = random.nextInt(5);
                    switch(flag) {
                        case 0: world[x][y] = Tileset.GRASS;break;
                        case 1: world[x][y] = Tileset.WATER;break;
                        case 2: world[x][y] = Tileset.WATER;break;
                        case 3: world[x][y] = Tileset.TREE;break;
                        case 4:
                            flag = random.nextInt(15);
                            if(flag == 0) {
                                world[x][y] = Tileset.FLOWER;
                            }
                            break;
                    }
                }
            }
        }
    }

   private void buildLight(){
        int c=0;
        while(c<5) {
            for (int x = 0; x < WIDTH; x++) {
                for (int y = 0; y < HEIGHT; y++) {
                    int flag = random.nextInt(10000);
                    if (world[x][y] == Tileset.FLOOR && flag == 0 ) {
                        world[x][y] = Tileset.FLOWER;
                        c++;
                    }
                }
            }
        }
    }

   private  void buildKey(){
        int key = 0;
        while(key < 3) {
            for(int y = 0; y < HEIGHT-1; y++){
                for (int x = 0; x < WIDTH-1; x++) {
                        int flag = random.nextInt(10000);
                        if(flag == 0 && world[x][y] == Tileset.ROAD && !DarkWorld.ifLightInSight(world, x, y)) {
                            world[x][y] = Tileset.STEP;
                            key++;
                        }

                }
            }
        }
   }

   private  void buildExit(){
        while(true) {
            for (int x = 0; x < WIDTH; x++) {
                for (int y = 0; y < HEIGHT; y++) {
                    int flag = random.nextInt(1000000);
                    if (world[x][y] == Tileset.FLOOR && flag == 0 && DarkWorld.ifLightInSight(world, x, y)) {
                        world[x][y] = Tileset.LOCKED_DOOR;
                        return;
                    }
                }
            }
        }
   }
}
