package core;

import edu.princeton.cs.algs4.StdDraw;
import tileengine.Button;
import tileengine.TERenderer;

import static core.main.HEIGHT;
import static core.main.WIDTH;

public class ButtonOperation {
    public static int welcomeMenuOperatioin(double mouseX, double mouseY) {
        int status = 0;
        Button[] buttons = TERenderer.buttons;
        Button hoveredButton = null;
        for (Button btn : buttons) {
            if (btn.contains(mouseX, mouseY)) {
                hoveredButton = btn;
                break;
            }
        }
        if (StdDraw.isMousePressed()) {
            if (hoveredButton != null) {
                // 根据按钮ID执行不同操作
               status = hoveredButton.id;
            }
            // 等待鼠标释放以避免多次触发
            while (StdDraw.isMousePressed()) {
                StdDraw.pause(10);
            }
        }
        return status;
    }

    public static int enterSettingsOperatioin(double mouseX, double mouseY) {
        int status = -1;
        Button button = TERenderer.buttons[0];
        Button hoveredButton = null;
        if (button.contains(mouseX, mouseY)) {
            hoveredButton = button;
        }
        if (StdDraw.isMousePressed()) {
            if (hoveredButton != null) {
                status = 1;
            }
            while (StdDraw.isMousePressed()) {
                StdDraw.pause(10);
            }
        }
        return status;
    }

    public static int settingsOperatioin(double mouseX, double mouseY) {
        int status = -1;
        int width = WIDTH;
        int height = HEIGHT+10;
        // 由于种种原因，这里的height需要比renderer中的大10

       Button[] buttons = TERenderer.buttons;
        Button hoveredButton = null;
        for (Button btn : buttons) {
            if (btn.contains(mouseX, mouseY)) {
                hoveredButton = btn;
                break;
            }
        }
        if (StdDraw.isMousePressed()) {
            if (hoveredButton != null) {
                // 根据按钮ID执行不同操作
               status = hoveredButton.id;
            }
            // 等待鼠标释放以避免多次触发
            while (StdDraw.isMousePressed()) {
                StdDraw.pause(10);
            }
        }
        return status;
    }

    public static int seedSelectionOperation(double mouseX, double mouseY) {
        Button[] buttons = TERenderer.buttons;
        int status = -1;
        Button hoveredButton = null;
        for (Button btn : buttons) {
            if (btn.contains(mouseX, mouseY)) {
                hoveredButton = btn;
                break;
            }
        }
        if (StdDraw.isMousePressed()) {
            if (hoveredButton != null) {
                status = hoveredButton.id;    // 根据按钮ID执行不同操作
            }
            // 等待鼠标释放以避免多次触发
            while (StdDraw.isMousePressed()) {
                StdDraw.pause(10);
            }
        }

        return status;
    }


}
