package syoribuShooting;

import syoribuShooting.system.GifReader;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public final class GameConfig
{
    private static boolean enableCursor;

    public static final int WINDOW_WIDTH;
    public static final int WINDOW_HEIGHT;
    public static final int FPS;
    public static final boolean isFullScreen;
    public static final String PATH_IMAGE;
    public static Cursor shootingCursor, shootingCursorGreen;
    public static BufferedImage
            img_shootingCursor,
            img_shootingCursorGreen,
            img_targetA, img_targetB, img_targetC,
            img_back01;
    public static ArrayList<BufferedImage> anim_hit = new ArrayList<>();

    private GameConfig() {}
    
    static {
        final Toolkit toolkit = Toolkit.getDefaultToolkit();

        WINDOW_WIDTH = (int) (toolkit.getScreenSize().getWidth());
        WINDOW_HEIGHT = (int) (toolkit.getScreenSize().getHeight());
        FPS = 40;
        isFullScreen = false;
        enableCursor = true;
        PATH_IMAGE = "/images/";

        try {
            img_targetA = ImageIO.read(Game.class.getResourceAsStream(PATH_IMAGE + "super_rare_target.png"));
            img_targetB = ImageIO.read(Game.class.getResourceAsStream(PATH_IMAGE + "high_points_target.png"));
            img_targetC = ImageIO.read(Game.class.getResourceAsStream(PATH_IMAGE + "normal_target.png"));
            img_back01 = ImageIO.read(Game.class.getResourceAsStream(PATH_IMAGE + "back01.jpg"));
            anim_hit = GifReader.readGif(Game.class.getResourceAsStream(PATH_IMAGE + "hit-animation.gif"));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        if (enableCursor) {
            try {
                img_shootingCursor = ImageIO.read(Game.class.getResourceAsStream(PATH_IMAGE + "shooting_cursor.png"));
                img_shootingCursorGreen = ImageIO.read(Game.class.getResourceAsStream(PATH_IMAGE + "shooting_cursor_green.png"));
                shootingCursor = toolkit.createCustomCursor(img_shootingCursor, new Point(32, 32), "Shooting-Cursor");
                shootingCursorGreen = toolkit.createCustomCursor(img_shootingCursorGreen, new Point(32, 32), "Shooting-Cursor-Green");
            }
            catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
            }
            catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
                enableCursor = false;
            }
        } else {
            shootingCursor = null;
            shootingCursorGreen = null;
        }

        System.out.println("GetResource: done");
        System.out.println("Window: " + WINDOW_WIDTH + "x" + WINDOW_HEIGHT);
        System.out.println("FPS: " + FPS);
        System.out.printf("targetA: %dx%d\n", img_targetA.getWidth(), img_targetA.getHeight());
        System.out.printf("targetB: %dx%d\n", img_targetB.getWidth(), img_targetB.getHeight());
        System.out.printf("targetC: %dx%d\n", img_targetC.getWidth(), img_targetC.getHeight());
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {}
    }

    public static boolean isEnableCursor() { return enableCursor; }

}
