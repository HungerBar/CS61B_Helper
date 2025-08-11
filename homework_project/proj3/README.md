## Design document for BYOW
### Introduction
create an engine for generating explorable worlds   
a 2D tile-based world exploration engine
### Skeleton Code
- TileEngine(No modification needed)
  - `TERenderer.java`:contains rendering-related methods
  - `TETile.java`:the typed used for representing tiles in the world
  - `Tileset.java`:a library of provided tiles
- core
  - `AutograderBuddy.java`:test(simulate) without rendering
  - `Main.java`:start the entire system;read command line arguments and call function in `word.java`
  - `world.java`: __WORLD__
- utils
  - `FileUtiles.java`:deal with File
  - `RandomUtiles.java`：Random engine

### World Generation
#### Rules

__Valid__
1. ~~2D grid, drawn using `TileEngine`~~
2. ~~include distinct rooms hallways(may also include outdoor spaces)~~
3. some rooms should be rectangular(other shape as well)
4. generate hallways include turns and has moderate frequency(20%~)
5. Dead-end hallways are not allowed 
6. ~~rooms,hallways has distinctive wall~~
7. Rooms and hallways should be connected(no gaps in the floor between adjacent rooms or hallways)
8. All rooms should be reachable 
9. floor tiles couldn't appear on the edge 
10. populate above 50% of the world with rooms and hallways

__Sufficient Random__
1. pseudo-randomly generated 
2. hallway's width == 1 (???)

#### Some Helper Object
- Random:provide a engine (seed)
- TETile[][] world
`world[0][0]`: bottom-left
`world[9][0]`: (9,0)
before fill them in,calling `renderFram`:
```
  /**
  * Initializes StdDraw parameters and launches the StdDraw window. w and h are the
  * width and height of the world in number of tiles. If the TETile[][] array that you
  * pass to renderFrame is smaller than this, then extra blank space will be left
  * on the right and top edges of the frame. For example, if you select w = 60 and
  * h = 30, this method will create a 60 tile wide by 30 tile tall window. If
  * you then subsequently call renderFrame with a TETile[50][25] array, it will
  * leave 10 tiles blank on the right side and 5 tiles blank on the top side. If
  * you want to leave extra space on the left or bottom instead, use the other
  * initializatiom method.
  * @param w width of the window in tiles
  * @param h height of the window in tiles.
    */
```

#### TODO Summary
TODO 1.1:figure out the orientation with a small sample programs (Valid1)
TODO 1.2:find wonderful Tile set(Valid2,6)
TODO 1.3:Normal World(Valid3,4,5,7,8,9,10;Sufficient Random2)
TODO 1.4:support the new WORLD with a seed as input(Sufficient Random1)
TODO 1.5:design a GUI
TODO 1.6:support Create a new WORLD,open a WORLD,Quit


#### 1.1 figure out the orientation with a small sample programs
The `TETile` object is used to represent a single tile in your world. A 2D array of tiles make up a
board, and can be drawn to the screen using the `TERenderer` class.
`TETile`
- `TETile(char character, Color textColor, Color backgroundColor, String description,
  String filepath, int id)// filepath isn't necessary`
- `TETile(t,textcolor/character)//override the textcolor/char`
- `draw(x,y)//draw the tile at (x,y)`
- `TETile colorVariant(TETile t, int dr, int dg, int db, Random r)//返回轻微调色的tile`
- `toString()`
- `copyOf()`
- `equals()//相同id的tile时相等`

`TErnederer`
- `initialize(int w, int h, int xOff, int yOff)//initialize with deltaX deltaY,xOff yOff isn't necessary;If the TETile[][] array that you
pass to renderFrame is smaller than this, then extra blank space will be left on the right and top edges of the frame. 
By varying xOffset, yOffset, and the size of the screen when initialized, you can leave
empty space in different places to leave room for other information, such as a GUI. 
This method assumes that the xScale and yScale have been set such that the max x value is the width of the screen in tiles, 
and the max y value is the height of  the screen in tiles.`

Answer:`/src/core/dullworld.java`

#### 1.2 find wonderful Tile set(Valid2,6)
Source URL:[This](https://icons8.com/icons/set/wall-16x16)

`Tileset`
- WALL![img.png](src/tileengine/MYPhotoTile/Wall_img.png)
- Floor![img.png](src/tileengine/MYPhotoTile/Floor_img.png)
- Grass![Grass_img.png](src/tileengine/MYPhotoTile/Grass_img.png)
- Water![River_img.png](src/tileengine/MYPhotoTile/River_img.png)
- Flower![Flower_img.png](src/tileengine/MYPhotoTile/Flower_img.png)
- Lockdoor![LockDoor_img.png](src/tileengine/MYPhotoTile/LockDoor_img.png)
- Unlockdoor![UnlockDoor_img.png](src/tileengine/MYPhotoTile/UnlockDoor_img.png)
- Tree![Tree_img.png](src/tileengine/MYPhotoTile/Tree_img.png)
- Road![Road_img.png](src/tileengine/MYPhotoTile/Road_img.png)

Answer:`/src/cor/dullworld.java`

#### 1.3 Normal World(Valid3,4,5,7,8,9,10;Sufficient Random2)
1. initialize the world with NOTHING

use `initialWithNothing` method

---
2. Random create rectangular room

use `buildHouse` method  
randomly choose a dot(x,y) as the left-bottom of the house ,randomly generate the width and height
---
3.use hallways to connect
connect the house with `buildRoad`

---
4.create the door


change the tile that connect the road into open door   
lock it if it has a unlocked neighbor  
change all the road-tile into step if its
roadNeighborCount(x,y) ==1 || (roadNeighborCount(x,y) == 0 && stepNeighborCount(x,y)==1))&& doorNeighborCount(x,y) ==1  
open all the door that stepNeighbor >=1;
clean the Step 
then we can ensure the connectivity

__However__
the way we build the road is not elegant.It is complex and the road is ugly.   
The maybe __improvement__:
add more detail into the `house` object   
and use the Minimum Spanning Tree (MST) to build the road Maybe better  
one __question__: MST_Road maybe too sprase,so how to __balance__ is the key point;

---
5. fill the Nothing with Water,Flower,Tree,Grass(randomly)

use `buildAttaches` method

##### key methods
```java
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
    }
```

#### TODO 1.4:support the new WORLD with a seed as input(Sufficient Random1)
write a method:accept the seed,generate the random,generate.
Answer:`Main/randomWorldBuilder` method

#### TODO 1.5:design a GUI

