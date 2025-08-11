package tileengine;

import edu.princeton.cs.algs4.StdDraw;

import java.awt.Color;
import java.awt.Font;
import java.util.Random;

import static core.main.HEIGHT;
import static core.main.WIDTH;
import static core.WorldFile.readFileName;
import static core.WorldFile.readId;

/**
 * Utility class for rendering tiles. You do not need to modify this file. You're welcome
 * to, but be careful. We strongly recommend getting everything else working before
 * messing with this renderer, unless you're trying to do something fancy like
 * allowing scrolling of the screen or tracking the avatar or something similar.
 */
public class TERenderer {
    private static final int TILE_SIZE = 16;
    private int width;
    private int height;
    private int xOffset;
    private int yOffset;
    public static Button[] buttons;

    /**
     * Same functionality as the other initialization method. The only difference is that the xOff
     * and yOff parameters will change where the renderFrame method starts drawing. For example,
     * if you select w = 60, h = 30, xOff = 3, yOff = 4 and then call renderFrame with a
     * TETile[50][25] array, the renderer will leave 3 tiles blank on the left, 7 tiles blank
     * on the right, 4 tiles blank on the bottom, and 1 tile blank on the top.
     * @param w width of the window in tiles
     * @param h height of the window in tiles.
     */
    public void initialize(int w, int h, int xOff, int yOff) {
        this.width = w;
        this.height = h;
        this.xOffset = xOff;
        this.yOffset = yOff;
        StdDraw.setCanvasSize(width * TILE_SIZE, height * TILE_SIZE);
        resetFont();
        StdDraw.setXscale(0, width);
        StdDraw.setYscale(0, height);

        StdDraw.clear(new Color(0, 0, 0));

        StdDraw.enableDoubleBuffering();
        StdDraw.show();
    }


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
    public void initialize(int w, int h) {
        initialize(w, h, 0, 0);
    }

    /**
     * Takes in a 2d array of TETile objects and renders the 2d array to the screen, starting from
     * xOffset and yOffset.
     *
     * If the array is an NxM array, then the element displayed at positions would be as follows,
     * given in units of tiles.
     *
     *              positions   xOffset |xOffset+1|xOffset+2| .... |xOffset+world.length
     *                     
     * startY+world[0].length   [0][M-1] | [1][M-1] | [2][M-1] | .... | [N-1][M-1]
     *                    ...    ......  |  ......  |  ......  | .... | ......
     *               startY+2    [0][2]  |  [1][2]  |  [2][2]  | .... | [N-1][2]
     *               startY+1    [0][1]  |  [1][1]  |  [2][1]  | .... | [N-1][1]
     *                 startY    [0][0]  |  [1][0]  |  [2][0]  | .... | [N-1][0]
     *
     * By varying xOffset, yOffset, and the size of the screen when initialized, you can leave
     * empty space in different places to leave room for other information, such as a GUI.
     * This method assumes that the xScale and yScale have been set such that the max x
     * value is the width of the screen in tiles, and the max y value is the height of
     * the screen in tiles.
     * @param world the 2D TETile[][] array to render
     */
    public void renderFrame(TETile[][] world) {
        StdDraw.clear(new Color(0, 0, 0));
        drawTiles(world);
        StdDraw.show();
    }

    /**
     * Draws all world tiles without clearing the canvas or showing the tiles.
     * @param world the 2D TETile[][] array to render
     */
    public void drawTiles(TETile[][] world) {
        int numXTiles = world.length;
        int numYTiles = world[0].length;
        for (int x = 0; x < numXTiles; x += 1) {
            for (int y = 0; y < numYTiles; y += 1) {
                if (world[x][y] == null) {
                    throw new IllegalArgumentException("Tile at position x=" + x + ", y=" + y
                            + " is null.");
                }
                world[x][y].draw(x + xOffset, y + yOffset);
            }
        }
    }

    /**
     * Resets the font to default settings. You should call this method before drawing any tiles
     * if you changed the pen settings.
     */
    public void resetFont() {
        Font font = new Font("Monaco", Font.BOLD, TILE_SIZE - 2);
        StdDraw.setFont(font);
    }

    /** My own methods */
    private static int calculateBaseFontSize(int width, int height) {
        // 取画布宽度和高度的较小值作为基准
        int minDimension = Math.min(width, height);
        // 按比例计算基础字体大小
        return minDimension / 100;
    }

    public void initWelcomeMenu() {
        StdDraw.clear(StdDraw.LIGHT_GRAY);
        int baseFontSize = calculateBaseFontSize(width * TILE_SIZE, height * TILE_SIZE);
        Font titleFont = new Font("SansSerif", Font.BOLD, (int)(baseFontSize * 8.0));
        StdDraw.setFont(titleFont);
        StdDraw.setPenColor(StdDraw.DARK_GRAY);
        StdDraw.text(width * 0.5-2,height * 0.75  ,"CS61B:BYOW");//主标题

        Font subtitleFont = new Font("Serif", Font.BOLD | Font.ITALIC, (int)(baseFontSize * 3.5));
        StdDraw.setFont(subtitleFont);
        StdDraw.setPenColor(StdDraw.DARK_GRAY);
        StdDraw.text(width * 0.7 , height * 0.75 - 5, "by HungerBar");

        Font indicationFont = new Font("Serif1", Font.BOLD | Font.ITALIC, (int)(baseFontSize * 1.5));
        StdDraw.setFont(indicationFont);
        StdDraw.setPenColor(StdDraw.DARK_GRAY);
        StdDraw.text(width * 0.5 , 10, "Try to click the Button");

        resetFont();

    }
    public void welcomeMenuBottom(double mouseX, double mouseY) {
        buttons = new Button[]{
                new Button(width * 0.5, height * 0.5, 10, 3, "Enter My World", 1),
                new Button(width * 0.5, height * 0.5 - 5, 10, 3, "Enter A New World", 2),
                new Button(width * 0.5, height * 0.5 - 10, 10, 3, "Open Exited World", 3),
                new Button(width * 0.5, height * 0.5 - 15, 10, 3, "Quit", 4)
        };
        Button hoveredButton = null;
        for (Button btn : buttons) {
            if (btn.contains(mouseX, mouseY)) {
                hoveredButton = btn;
                break;
            }
        }
        for (Button btn : buttons) {
            if (btn == hoveredButton) {
                btn.drawHighlight();

            } else {
                btn.draw();
            }
            StdDraw.pause(10);
        }
    }

    public void enterSettingsBottom(double mouseX, double mouseY) {
        buttons = new Button[] { new Button(7.0,6.0, 10,4,"Settings",0) };
        Button button = buttons[0];
        Button hoveredButton = null;
        if (button.contains(mouseX, mouseY)) {
            hoveredButton = button;
        }

        if (hoveredButton != null) {
            button.drawHighlight();
        }else{
            button.draw();
        }

        StdDraw.pause(10);
    }

    public void initSettings() {

        StdDraw.clear(StdDraw.LIGHT_GRAY);
        int baseFontSize = calculateBaseFontSize(width * TILE_SIZE, height * TILE_SIZE);
        Font subtitleFont = new Font("Serif", Font.BOLD | Font.ITALIC, (int)(baseFontSize * 4));
        StdDraw.setFont(subtitleFont);
        StdDraw.setPenColor(StdDraw.DARK_GRAY);
        StdDraw.text(width * 0.5 , height * 0.8 + 5, "Settings");
        resetFont();

    }
    public void initSettingsBottom(double mouseX, double mouseY) {
        buttons = new Button[]{
                new Button(width * 0.5, height * 0.5 + 10, 10, 3, "Back To Main Menu", 1),
                new Button(width * 0.5, height * 0.5 + 5, 10, 3, "Save And Quit", 2),
                new Button(width * 0.5, height * 0.5 , 10, 3, "Back To This World", 3),
                new Button(width * 0.5, height * 0.5 - 5, 10, 3, "Backdooooooooor", 4)
        };
        Button hoveredButton = null;
        for (Button btn : buttons) {
            if (btn.contains(mouseX, mouseY)) {
                hoveredButton = btn;
                break;
            }
        }
        for (Button btn : buttons) {
            if (btn == hoveredButton) {
                btn.drawHighlight();

            } else {
                btn.draw();
            }
            StdDraw.pause(10);
        }
    }

    public void initSeedSelection() {

        StdDraw.clear(StdDraw.LIGHT_GRAY);
        int baseFontSize = calculateBaseFontSize(width * TILE_SIZE, height * TILE_SIZE);
        Font subtitleFont = new Font("Serif", Font.BOLD | Font.ITALIC, (int)(baseFontSize * 4));
        StdDraw.setFont(subtitleFont);
        StdDraw.setPenColor(StdDraw.DARK_GRAY);
        StdDraw.text(width * 0.5 , height * 0.8 + 8, "History");
        resetFont();
    }
    public void initSeedSelectionBottom(double mouseX, double mouseY) {
        String[] filenames = readFileName();
        if (filenames == null) {
            System.out.println("No files found");
            throw  new Error("No files found");
        }
        double x = width * 0.5 ; int w = 15 ; int h = 3;int i = 0;
        double y = height * 0.7 + 7;
        buttons = new Button[filenames.length];
        buttons[0] = new Button(x, y, 15, 3, "Back To Current World", 0);
        i = 1; y = y-5;
        for(String file :  filenames) {
            if(!file.equals("currentWorld.txt")) {
                buttons[i] = new Button(x, y, w, h, file ,readId(file));
                y = y - 5;
                i = i + 1;
            }
        }

        Button hoveredButton = null;
        for (Button btn : buttons) {
            if (btn.contains(mouseX, mouseY)) {
                hoveredButton = btn;
                break;
            }
        }
        for (Button btn : buttons) {
            if (btn == hoveredButton) {
                btn.drawHighlight();

            } else {
                btn.draw();
            }
            StdDraw.pause(10);
        }
    }

    /** interactivity*/
    public void showScore(int score) {
        String text = "Key Number: " + score;
        int baseFontSize = calculateBaseFontSize(width * TILE_SIZE, height * TILE_SIZE);
        Font subtitleFont = new Font("Serif", Font.BOLD, (int)(baseFontSize * 4));
        StdDraw.setFont(subtitleFont);
        StdDraw.setPenColor(StdDraw.DARK_GRAY);
        StdDraw.text(width*0.5 -15 , 5, text);
        resetFont();
    }
    public void showCongratulation(){
        StdDraw.clear(StdDraw.LIGHT_GRAY);
        drawBackground();
        drawWinText();
        drawTrophy(WIDTH / 2, HEIGHT / 2 - 5);
        drawFireworks();
        StdDraw.show();
    }
    private void drawBackground() {
        for (int y = 0; y <= HEIGHT + 10; y++) {
            // 创建从深蓝到金色的垂直渐变
            int blue = 70 + (int)(y * 1.5);
            Color bgColor = new Color(10, 20, Math.min(blue, 255));
            StdDraw.setPenColor(bgColor);
            StdDraw.line(0, y, WIDTH, y);
        }

        // 绘制装饰性星星
        StdDraw.setPenColor(StdDraw.YELLOW);
        Random rand = new Random();
        for (int i = 0; i < 40; i++) {
            double x = rand.nextDouble() * WIDTH;
            double y = (HEIGHT+10) * 0.6 + rand.nextDouble() * ((HEIGHT+10) * 0.4);
            double r = 0.08 + rand.nextDouble() * 0.15;
            StdDraw.filledCircle(x, y, r);
        }
    }

    // 绘制胜利文字
    private void drawWinText() {
        // 主标题 - 使用相对大小
        Font font = new Font("Arial", Font.BOLD, (int)(HEIGHT * 0.8));
        StdDraw.setFont(font);

        // 文字阴影效果
        StdDraw.setPenColor(new Color(30, 30, 30));
        StdDraw.text(WIDTH/2 + 0.4, HEIGHT * 0.8 - 0.4, "Congratulation!");

        StdDraw.setPenColor(StdDraw.YELLOW);
        StdDraw.text(WIDTH/2, HEIGHT * 0.8, "Congratulation!");

    }

    // 绘制奖杯
    private void drawTrophy(double x, double y) {
        // 奖杯底座
        StdDraw.setPenColor(StdDraw.LIGHT_GRAY);
        StdDraw.filledRectangle(x, y - 2, 3, 0.8);

        // 奖杯杯身
        double[] cupX = {x - 4, x - 2, x + 2, x + 4, x + 2, x - 2};
        double[] cupY = {y - 2, y + 4, y + 4, y - 2, y, y};
        StdDraw.setPenColor(StdDraw.YELLOW);
        StdDraw.filledPolygon(cupX, cupY);

        // 奖杯装饰
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.filledCircle(x, y + 5, 1.5);
    }

    // 绘制烟花动画
    private void drawFireworks() {
        Random rand = new Random();
        Color[] colors = {StdDraw.RED, StdDraw.ORANGE, StdDraw.YELLOW,
                StdDraw.GREEN, StdDraw.CYAN, StdDraw.MAGENTA};

        for (int i = 0; i < 12; i++) {
            double x = 5 + rand.nextDouble() * (WIDTH - 10);
            double y = 10 + rand.nextDouble() * (HEIGHT * 0.4);
            Color color = colors[rand.nextInt(colors.length)];

            // 烟花爆炸效果
            for (int j = 0; j < 25; j++) {
                double angle = rand.nextDouble() * Math.PI * 2;
                double length = 2 + rand.nextDouble() * 4;
                double x2 = x + Math.cos(angle) * length;
                double y2 = y + Math.sin(angle) * length;

                StdDraw.setPenColor(color);
                StdDraw.setPenRadius(0.003);
                StdDraw.line(x, y, x2, y2);

                // 添加延迟以创建动画效果
                StdDraw.show(30);

                // 用半透明覆盖层创建拖尾效果
                StdDraw.setPenColor(new Color(0, 0, 0, 30));
                StdDraw.filledRectangle(WIDTH/2, HEIGHT/2, WIDTH/2, HEIGHT/2);

                // 重绘静态元素
                drawBackground();
                drawWinText();
                drawTrophy(WIDTH / 2, HEIGHT / 2 - 5);
            }
        }
    }


    public void showBackdoooor(){
        StdDraw.clear(StdDraw.LIGHT_GRAY);
        int baseFontSize = calculateBaseFontSize(width * TILE_SIZE, height * TILE_SIZE);
        Font subtitleFont = new Font("Serif", Font.BOLD, (int)(baseFontSize * 4));
        StdDraw.setFont(subtitleFont);
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.text(width * 0.5 , height * 0.5 + 10, "V Me 50");
        resetFont();
        Font subtitleFont1 = new Font("Serif", Font.BOLD | Font.ITALIC, (int)(baseFontSize * 3.5));
        StdDraw.setFont(subtitleFont1);
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.text(width * 0.5 , height * 0.5 - 5, "I’ll get the KFC guy to help you😝");
        StdDraw.show();
    }



    /** file*/
    public String initSaveWorldBox() {
        int BOX_X = (int) (width * 0.5) ;int BOX_Y = (int) (height * 0.65);
        int BOX_WIDTH = 10; int BOX_HEIGHT = 5;
        StringBuilder input = new StringBuilder();
        boolean isEnterPressed = false;
        Font font = new Font("Arial", Font.PLAIN, 24);
        StdDraw.setFont(font);

        while (!isEnterPressed) {
            StdDraw.clear(StdDraw.WHITE);

            // 绘制输入框
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.rectangle(BOX_X, BOX_Y, BOX_WIDTH / 2, BOX_HEIGHT / 2);

            // 绘制文本
            StdDraw.setPenColor(StdDraw.BLACK);
            String displayText = input.toString();
            if (System.currentTimeMillis() / 500 % 2 == 0) {
                displayText += "|";  // 闪烁的光标
            }
            StdDraw.text(BOX_X, BOX_Y, displayText);

            // 显示提示
            StdDraw.text(BOX_X, BOX_Y + 10, "Press ENTER to submit");

            StdDraw.show();

            // 处理键盘输入
            if (StdDraw.hasNextKeyTyped()) {
                char c = StdDraw.nextKeyTyped();
                if (c == '\n' || c == '\r') {  // 回车键
                    isEnterPressed = true;
                } else if (c == 8 && input.length() > 0) {  // 退格键
                    input.deleteCharAt(input.length() - 1);
                } else if (c >= 32 && c <= 126) {  // 可打印字符
                    input.append(c);
                }
            }
            StdDraw.pause(30);  // 控制刷新率
        }
        return input.toString();
    }


}
