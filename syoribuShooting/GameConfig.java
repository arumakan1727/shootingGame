package syoribuShooting;

import syoribuShooting.system.GifReader;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public final class GameConfig
{
    
    public static final int WINDOW_WIDTH;
    public static final int WINDOW_HEIGHT;
    public static final int FPS;
    public static final boolean isFullScreen;
    public static final String PATH_IMAGE;
    public static final Cursor shootingCursor;
    public static BufferedImage
            img_shootingCursor,
            img_target,
            img_back01;

    private GameConfig() {}
    
    static {
        final Toolkit toolkit = Toolkit.getDefaultToolkit();

        WINDOW_WIDTH    = (int)(toolkit.getScreenSize().getWidth());
        WINDOW_HEIGHT   = (int)(toolkit.getScreenSize().getHeight());
        FPS = 40;
        isFullScreen = false;
        PATH_IMAGE = "/images/";

        try {
            img_shootingCursor = ImageIO.read(Game.class.getResourceAsStream(PATH_IMAGE + "shooting_cursor.png"));
            img_target  = ImageIO.read(Game.class.getResourceAsStream(PATH_IMAGE + "normal_target.png"));
            img_back01  = ImageIO.read(Game.class.getResourceAsStream(PATH_IMAGE + "back01.jpg"));
        } catch (IOException e){
            e.printStackTrace();
            System.exit(1);
        }

        shootingCursor = toolkit.createCustomCursor(img_shootingCursor, new Point(32, 32), "Shooting-Cursor");
        System.out.println("GetResource: done");
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {}
    }

}
