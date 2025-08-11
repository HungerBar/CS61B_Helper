package core;
import edu.princeton.cs.algs4.StdDraw;
import tileengine.TERenderer;
import tileengine.TETile;
import tileengine.Tileset;

import java.awt.event.KeyEvent;
import java.security.SecureRandom;
import java.util.Random;
import java.util.Stack;

import static core.ButtonOperation.*;
import static core.DarkWorld.initDarkBackward;
import static core.DarkWorld.initDarkVision;
import static core.WorldFile.returnId;

public class main {
    public static int HEIGHT = 50;
    public static int WIDTH = 85;
    private static final long SEED = 2873123;
    private Random random = new Random(SEED);
    private TETile[][] world;
    private TETile[][] darkWorld;
    private int points = 0 ;

    public static class position{
        int x;
        int y;
        String name;

        public position(int x, int y , String name) {
            this.x = x;
            this.y = y;
            this.name = name;

        }

        @Override
        public boolean equals(Object other) {
            if(other instanceof position o) {
                if(o.name.equals(this.name)){
                    return true;
                }else{
                    return false;
                }
            }
            throw new IllegalArgumentException();
        }
    }

    private position playerPosition;

    private TERenderer renderer; //help render the tiles
    private initialWorldGenerator generator;//generate the initial world
    private long currentSeed;// current SEED
    private GameState currentState;
    private Stack<GameState> stateHistory;//store the history state

    public static void main(String[] args) {
        new main().run();
    }

    public void run(){

        initialize();

        AudioPlayer.playBackgroundMusic("/data/backgroundmusic.wav");
            // 播放资源文件夹中的音频
        gameLoop();
    }

    private void initialize(){
        // initialize the canvas
        renderer = new TERenderer();
        renderer.initialize(WIDTH, HEIGHT+10,0,10);
        // initialize the SEED and History
        currentSeed = SEED;
        stateHistory = new Stack<>();
        if(generator == null) {
            generator = randomWorldBuilder(currentSeed);
            generator.initialGenerateWorld();
            world = generator.world;
        }
        darkWorld = DarkWorld.initDarkWorld(WIDTH, HEIGHT);
        // transit to WELCOME_MENU
        transitionTo(GameState.WELCOME_MENU);
        StdDraw.enableDoubleBuffering();
    }

    // save current state(only save state!!!), transit to a new one
    private void transitionTo(GameState newState) {

        stateHistory.push(currentState);

        currentState = newState;
        onStateEnter(newState);
    }

    // back to the previous
    private void goBack() {
        if (!stateHistory.isEmpty()) {
            GameState previousState = stateHistory.pop();
            currentState = previousState;
            onStateEnter(previousState);
        }
    }

    private void gameLoop() {
        final int TARGET_FPS = 60;
        final long FRAME_TIME_MS = 1000 / TARGET_FPS;

        while (true) {
            long frameStart = System.currentTimeMillis();

            render();
            // 1. 处理输入
            int status = handleInput();

            // 2. 更新游戏状态
            update(status);

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

    //定义进入state的初始化操作
    private void onStateEnter(GameState State) {
        switch (State) {
            case WELCOME_MENU:
                renderer.initWelcomeMenu();
                break;
            case GAME_WORLD:
                    if(world != null){
                        renderer.renderFrame(world);
                        StdDraw.show();
                        StdDraw.pause(1000);
                        brightLightUp();
                        renderer.renderFrame(darkWorld);
                    }
                break;
            case SETTINGS:
                renderer.initSettings();
                break;
            case SEED_SELECTION:
                renderer.initSeedSelection();
                break;
        }
    }

    private int handleInput() {
        int status = -1;
        double mouseX = StdDraw.mouseX();
        double mouseY = StdDraw.mouseY();
        switch (currentState) {
            case WELCOME_MENU:
                status = welcomeMenuOperatioin(mouseX, mouseY);
                break;
            case GAME_WORLD:
                status = enterSettingsOperatioin(mouseX, mouseY);
                break;
            case SETTINGS:
                status = settingsOperatioin(mouseX, mouseY);
                break;
            case SEED_SELECTION:
                status = seedSelectionOperation(mouseX, mouseY);
                break;
        }
        return status;
    }

    private void update(int status) {
        switch (currentState) {
            case WELCOME_MENU:
                switch(status){
                    case 0:break;
                    case 1:
                        darkWorld = DarkWorld.initDarkWorld(WIDTH, HEIGHT);
                        playerPosition = initPlayer();
                        points = 0;
                        brightAround();
                        cacheDarkWorld();
                        generator = randomWorldBuilder(SEED);
                        generator.initialGenerateWorld();
                        world = generator.world;
                        transitionTo(GameState.GAME_WORLD);break;
                    //这是进入我的世界(SEED)
                    case 2:
                        currentSeed = generateASeed(currentSeed);
                        generator = randomWorldBuilder(currentSeed);
                        random = new Random(currentSeed);
                        generator.initialGenerateWorld();
                        world = generator.world;
                        darkWorld = DarkWorld.initDarkWorld(WIDTH, HEIGHT);
                        playerPosition = initPlayer();
                        points = 0;
                        brightAround();
                        cacheDarkWorld();
                        transitionTo(GameState.GAME_WORLD);
                        break;
                    //随机生成一个新的world(真的随机吗?)
                    case 3:transitionTo(GameState.SEED_SELECTION);break;
                    case 4:System.exit(0);break;
                }
                break;
            case GAME_WORLD:
                if(status == 1){
                    transitionTo(GameState.SETTINGS);
                }
                break;
            case SETTINGS:
                switch(status){
                    case 0:break;
                    case 1:transitionTo(GameState.WELCOME_MENU);break;
                    case 2:
                        saveDarkWorld();
                        break;
                    case 3:transitionTo(GameState.GAME_WORLD);break;
                    case 4:;
                        renderer.showBackdoooor();
                        StdDraw.pause(1000);
                        transitionTo(GameState.SETTINGS);
                        break;
                }
                break;
            case SEED_SELECTION:
                openWorldHistory(status);
                break;
        }
    }

    private void render() {
        double mouseX = StdDraw.mouseX();
        double mouseY = StdDraw.mouseY();
        switch (currentState) {
            case WELCOME_MENU:
                renderer.welcomeMenuBottom(mouseX, mouseY);
                break;
            case GAME_WORLD:
                handlePlayerMovement();
                renderer.showScore(points);
                renderer.enterSettingsBottom(mouseX, mouseY);
                break;
            case SETTINGS:
                renderer.initSettingsBottom(mouseX, mouseY);
                break;
            case SEED_SELECTION:
                renderer.initSeedSelectionBottom(mouseX, mouseY);
                break;
        }
        StdDraw.show();
    }


    public static initialWorldGenerator randomWorldBuilder(long seed){
        Random rand = new Random(seed);
        return new initialWorldGenerator(rand,WIDTH,HEIGHT);
    }

    public static long  generateASeed(long seed) {
        SecureRandom secureRandom = new SecureRandom();

        // 将当前种子作为熵源
        secureRandom.setSeed(seed);

        // 生成新种子
        return secureRandom.nextLong();
    }

    private void cacheDarkWorld() {
        if(darkWorld == null) return;
        WorldFile.SaveWorld(darkWorld,currentSeed,"currentWorld.txt",0);
    }

    private void saveDarkWorld() {
        String filename = renderer.initSaveWorldBox();

        // 处理取消操作或空文件名
        if (filename == null || filename.trim().isEmpty()) {
            transitionTo(GameState.WELCOME_MENU);
            return;
        }

        // 确保文件名以.txt结尾（不区分大小写）
        if (!hasTxtExtension(filename)) {
            filename = ensureTxtExtension(filename);
        }

        WorldFile.SaveWorld(darkWorld, currentSeed, filename,returnId());
        transitionTo(GameState.WELCOME_MENU);
    }

    // 检查文件名是否有.txt扩展名（不区分大小写）
    private boolean hasTxtExtension(String filename) {
        // 处理空文件名
        if (filename == null || filename.length() < 4) return false;

        // 获取文件扩展名（小写形式）
        String extension = filename.substring(filename.lastIndexOf('.') + 1).toLowerCase();
        return extension.equals("txt");
    }

    // 确保文件名以.txt结尾
    private String ensureTxtExtension(String filename) {
        // 移除任何现有扩展名
        int lastDotIndex = filename.lastIndexOf('.');
        if (lastDotIndex > 0) {
            filename = filename.substring(0, lastDotIndex);
        }

        // 添加.txt扩展名
        return filename + ".txt";
    }

    private void openWorldHistory(int id){
       if(id < 0) return;
       else if(id == 0){
           darkWorld = WorldFile.LoadWorld("currentWorld.txt");
           transitionTo(GameState.GAME_WORLD);
       }else{
           String filename = WorldFile.readHistory(id);
           points = 0;
           System.out.println(filename);
           darkWorld = WorldFile.LoadWorld(filename);
           playerPosition = foundPlayer();
           brightAround();
           currentSeed = WorldFile.readSeed(filename);

           generator = randomWorldBuilder(currentSeed);
           generator.initialGenerateWorld();
           world = generator.world;
           transitionTo(GameState.GAME_WORLD);
       }
    }


    /** Interactive-Game Part */
    private  position initPlayer(){
        while(true){
            int x = random.nextInt(WIDTH);
            int y = random.nextInt(HEIGHT);
            if((world[x][y] == Tileset.FLOOR || world[x][y] == Tileset.ROAD ) && !DarkWorld.ifKeyInSight(world,x,y) && !DarkWorld.ifExitInSight(world,x,y)){
                darkWorld[x][y] = Tileset.AVATAR;
                return new position(x,y,"U");
            }
        }
    }

    private position foundPlayer(){
        for(int x = 0; x < WIDTH; x++){
            for(int y = 0; y < HEIGHT; y++){
                if(darkWorld[x][y] == Tileset.AVATAR){
                    return new position(x,y,"U");
                }
            }
        }
        return null;
    }

    private void brightLightUp(){
        for(int x = 0; x < WIDTH; x++){
            for(int y = 0; y < HEIGHT; y++){
                if(world[x][y] == Tileset.FLOWER || world[x][y] == Tileset.STEP){
                   brightAPlace(x,y,5);
                }
            }
        }
    }

    //有视野
    private void brightAround(){
        darkWorld = initDarkVision(playerPosition.x, playerPosition.y, world,darkWorld,3);
    }

    //点亮(x,y)周围,r一定是奇数
    private void brightAPlace(int x, int y,int range){
        darkWorld = initDarkVision(x, y, world, darkWorld,range);
        darkWorld[x][y] = world[x][y];
    }
    //移动
    private  void movement(char movement) {
        int x = playerPosition.x;
        int y = playerPosition.y;
        TETile ntile = world[x][y];
        switch (movement) {
            case 'w':
                ntile = world[x][y+1];
                movehelper(ntile,x,y+1);
                break;
            case 'a':
                ntile = world[x-1][y];
                movehelper(ntile,x-1,y);
                break;
            case 's':
                ntile = world[x][y-1];
                movehelper(ntile,x,y-1);
                break;
            case 'd':
                ntile = world[x+1][y];
                movehelper(ntile,x+1,y);
                break;
            case 'q':
                ntile = world[x-1][y+1];
                if(ntile == Tileset.UNLOCKED_DOOR) movehelper(ntile,x-1,y+1);
                break;
            case 'e':
                ntile = world[x+1][y+1];
                if(ntile == Tileset.UNLOCKED_DOOR) movehelper(ntile,x+1,y+1);
                break;
            case 'z':
                ntile = world[x-1][y-1];
                if(ntile == Tileset.UNLOCKED_DOOR) movehelper(ntile,x-1,y-1);
                break;
            case 'x':
                ntile = world[x+1][y-1];
                if(ntile == Tileset.UNLOCKED_DOOR) movehelper(ntile,x+1,y-1);
                break;
        }
        renderer.renderFrame(darkWorld);
        if(darkWorld != world){
            cacheDarkWorld();
        }
    }

    private void movehelper(TETile ntile,int x,int y) {
        if(ntile == null) return;
        if(ntile == Tileset.FLOOR || ntile == Tileset.ROAD){
            initDarkBackward(playerPosition.x,playerPosition.y,darkWorld,3);
            playerPosition.x = x;playerPosition.y = y;
            brightLightUp();
            brightAround();
        }else if(ntile == Tileset.LOCKED_DOOR && points >= 3){
            renderer.showCongratulation();
            StdDraw.pause(2000);
            System.exit(0);
            //......
            //
        }else if(ntile == Tileset.UNLOCKED_DOOR){
            int flag = random.nextInt(8);
            initDarkBackward(playerPosition.x,playerPosition.y,darkWorld,3);
            if(flag == 0){
                playerPosition = strangebehaviour(x,y);
                brightLightUp();
                brightAround();
            }else{
                playerPosition.x = x;playerPosition.y = y;
                brightLightUp();
                brightAround();
            }
        }else if(ntile == Tileset.WALL){
            return;
        }else if(ntile == Tileset.STEP){
            initDarkBackward(playerPosition.x,playerPosition.y,darkWorld,3);
            world[x][y] = Tileset.ROAD;
            points ++;
            playerPosition.x = x;playerPosition.y = y;
            brightLightUp();
            brightAround();
        }
    }

    private void handlePlayerMovement() {
        if (StdDraw.hasNextKeyTyped()) {
            char key = StdDraw.nextKeyTyped();

            // 只处理移动相关的按键
            if (key == 'a' || key == 'd' || key == 'w' || key == 's' || key == 'q' || key == 'e' || key == 'z' || key == 'x') {
                // 调用移动逻辑（需要确保在实例中能访问movement方法）
                // 注意：由于movement是实例方法，这里需要实际对象的引用
                // 例如：gameInstance.movement(key);
                movement(key);  // 如果当前类是包含movement的实例
            }
        }
    }

    private position strangebehaviour(int a, int b) {
        while(true){for(int x = 0; x < WIDTH; x++){
            for(int y = 0; y < HEIGHT; y++){
                int flag = random.nextInt(100);
                if(world[x][y] == Tileset.UNLOCKED_DOOR && x!=a && y!=b && flag == 0){
                    return new position(x,y,"U");
                }
            }
        }}

    }


}
