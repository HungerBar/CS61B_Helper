## Design document for BYOW
### Introduction
create an engine for generating explorable worlds  
a 2D tile-based world exploration engine
__In fact,this document is a conclusion of my project,so there are lots of differences.__

### Important Library
#### `StdDraw`
It has 3 main function
1. accept keyboard-input
2. accept mouses-input
3. create a canvas, draw and show; 
#### `Files`
About reas files and write String into files
#### `Random`
use seed to build randomer

###  Project Skeleton
#### `src/data`
store the background music,and history files.
#### `src/tileengine`
About the render-methods:render the initialworld,buttons,hints
but not contain the `StdDraw.show()`(it is quite important)
You can also choose the GUI to finish this part.(Maybe,I'm not sure.)
#### `src/utils`
deal with the customized `Random` and `Files`.

### tileengine
#### TETile(provided)
implement the `Tile` object,and some interesting methods(`toString() .etc`)
#### TETile(provided)
some tiles ,you can customize your tiles by attaching files(mine in the `/data`),or simply change the input para.
#### TERenderer(provided)
build a renderer,can draw the `TETile[][]` into to the canavas(not show)
#### initTileWorld
to obey the rule,you need to build a persudorandom word with a provided seed.
`public static TETile[][] initTileWorld(int HEIGHT,int WIDTH,Long seed,...)`
maybe nested private class:`house`
#### Button
implement the `Button` object(very similiar to the `Tile`),remenber to add some useful public static method.
`public class Button`
- `public void draw()` and `public void drawHilight()`
- `public boolean iscontained()`

`public static void setButtonForWelcomeMenu()`
`public static void setButtonForGamestate()`
......
you can also draw the buttons with a one-to-one methods
#### Menu()
you need to draw your menu for every state.Remember to use the`StdDraw.clear()`.
`public static void initWelcomeMenu()`
`public static void initGamestate()` <-- in fact,we may not need this one.
......

### utils
this part,implementation is not the keypiont,try to cover fellow problems:
- how to save your play-history(or cache?)
- how to save the gameworld into a file,what should the file contain?A seed?A index?
- how to turn your file into the `TETile[][] world` or provide the necessary message.
- use a seed or a randomer?

### core
the `core` can alse seperate into 2 part:
- the GAME itself
- the organization

#### GAME
this part is definitely the most important part.However,I have no interest in it.
So,I mainly templement `movement()`,`socre`and `vision` to construct the GAME.
In my BYOW,player only has 3*3 vision.After get 3 points by getting the keys(Or collect 3 Keys?),they 
can easily win the game.

##### vision
To mimic the `vision`, I use a `TETile[][] darkworld`,which only the tile in vision is the same as the `world`(role except) and other tile == NOTHING. With customized `initTileWorld()`,the `key`and`exit` are both in vision.I also add `light` randomly to bright around.

##### movement()
To mimic the `movement`, I draw the initial `darkword` first after every single effective `movement`,then draw the role(or player),and bright around.
To cover the interactivity,`StdDraw` is useful.
And it's intuitive to build a `private ... movementHelper(...)` to ​implement movement logic

##### score
Or call it:__Victory Conditions__.How to show the Victory as well as updating?Will U cache the score?
Deside all the details even before beginning the hole project.

#### Organization
This part is the most difficult for me.
A GAME is __TOO DIFFICULT__ to implement considering the complexity.
How to implement such `demanded redirected`?

##### Finite State Machine(FSM)
This is my solution.Maybe not the most simplest, but it is highly-structed,and definitely complex enough to tackle this Problem.
Try to attach a `history` to every `state`,it is possible to finish the `undo`(However,i don't implement the generarily undo).

##### My FSM
Try to define a enum to save all the state.(mine are in `GameState.java`)
1. Welcom Menu:open my `world`;open a brand new `world`;open history;quit
2. Gamestate: designed for interacting;enter `settings`
3. settings: back to `GameState`;save the current `world`;back the `Welcom Menu`;
I also designed a backdoor for fun.
4. History: deal with choosing the history.

__My Advise__:Even though take every thing as a STATE is clear,but adding a STATE in fact is a annoying thing.Becase you have to add a `case` to all relative `switch()`.__​So add a STATE only when necessary.​​__ For example,I didn't add a STATE for _name the file when saving_.Because u will only enter that page after click the `Save Button`.

And the main part of my project is below
```java
public static void main(String[] args) {
        new main().run();
    }

public void run(){

  initialize();

  AudioPlayer.playBackgroundMusic("/data/backgroundmusic.wav");

  gameLoop();

}

private void gameLoop() {
        final int TARGET_FPS = 60;
        final long FRAME_TIME_MS = 1000 / TARGET_FPS;

        while (true) {
            long frameStart = System.currentTimeMillis();

            //使用renderer相关函数渲染
            render();

            // 1. 处理输入
            int status = handleInput();

            // 2. 更新游戏状态
            update(status);

            // 3.支持一个奇怪的undo
            if (StdDraw.isKeyPressed(KeyEvent.VK_ESCAPE)) {
                if(stateHistory.isEmpty()){
                   transitionTo(GameState.WELCOME_MENU);
                }else{
                    goBack();
                    StdDraw.pause(200);
                }
            }

            // 4. 控制帧率
            long elapsed = System.currentTimeMillis() - frameStart;
            if (elapsed < FRAME_TIME_MS) {
                StdDraw.pause((int)(FRAME_TIME_MS - elapsed));
            }
        }
    }

```
