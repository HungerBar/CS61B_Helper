package tileengine;

import edu.princeton.cs.algs4.StdDraw;
public  class Button {
        double x, y;         // 按钮中心坐标
        double width, height; // 按钮尺寸
        String text;         // 按钮文本
        public int id;              // 按钮标识符

        public Button(double x, double y, int width, int height, String text, int id) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.text = text;
            this.id = id;
        }

        // 检查点是否在按钮内
        public boolean contains(double px, double py) {
            return (px >= x - width / 2) && (px <= x + width / 2) &&
                    (py >= y - height / 2) && (py <= y + height / 2);
        }

        // 绘制按钮
        public void draw() {
            // 绘制矩形背景
            StdDraw.setPenColor(StdDraw.LIGHT_GRAY);
            StdDraw.filledRectangle(x, y, width / 2, height / 2);

            // 绘制边框
            StdDraw.setPenColor(StdDraw.DARK_GRAY);
            StdDraw.rectangle(x, y, width / 2, height / 2);

            // 绘制文本
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.text(x, y, text);
        }

        // 高亮绘制按钮（鼠标悬停时）
        public void drawHighlight() {
            // 绘制高亮矩形背景
            StdDraw.setPenColor(StdDraw.BOOK_LIGHT_BLUE);
            StdDraw.filledRectangle(x, y, width / 2, height / 2);

            // 绘制边框
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.rectangle(x, y, width / 2, height / 2);

            // 绘制文本
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(0.005);
            StdDraw.text(x, y, text);
        }

    }

