package syoribuShooting;

import syoribuShooting.sprite.TargetType;
import syoribuShooting.system.Utils;

import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;

public final class GameConfig
{
    public enum Allocation
    {
        TOP, BOTTOM, LEFT, RIGHT, CENTER, ALL;
        public static final int BORDER_HEIGHT = VIRTUAL_HEIGHT / 3;
        public static final int BORDER_WIDTH  = VIRTUAL_WIDTH / 4;
        public static final int MARGIN = 100;
    }

    static final int VIRTUAL_WIDTH;
    static final int VIRTUAL_HEIGHT;
    static int REAL_HEIGHT;
    static int REAL_WIDTH;
    static final double REAL_VIRTUAL_CORRECTION;
    static final int OUTER_WINDOW_PLUS;
    static final int OUTER_WINDOW_MINUS;
    static final int FPS;
    static final boolean isFullScreen;
    static final String PATH_IMAGE, PATH_XML, PATH_SOUNDS;
    static final String FIRST_STAGE_FILE_PATH;
    static final int ID_SHOOTING_CURSOR_NORMAL;
    static final int ID_SHOOTING_CURSOR_GREEN;
    static final int ID_CLEAR_CURSOR;
    static BufferedImage
            img_targetA, img_targetB, img_targetC,
            img_scoreUP, img_timeDecrease;
    static final URL se_gun, se_bomb, se_explosion, se_buzzer, bgm_shooting, bgm_overheat;

    private GameConfig() {}

    static {
        final Toolkit toolkit = Toolkit.getDefaultToolkit();

        VIRTUAL_WIDTH   = 1920;
        VIRTUAL_HEIGHT  = 1080;
        REAL_WIDTH      = (int) (toolkit.getScreenSize().getWidth());
        REAL_HEIGHT     = (int) (toolkit.getScreenSize().getHeight());
//        REAL_WIDTH = (int)(1920 * 0.6);
//        REAL_HEIGHT = (int)(1080 * 0.6);
        OUTER_WINDOW_PLUS   = VIRTUAL_WIDTH + 500;
        OUTER_WINDOW_MINUS  = -500;
        FPS          = 50;
        isFullScreen = false;
        REAL_VIRTUAL_CORRECTION = (double)VIRTUAL_HEIGHT / (double)REAL_HEIGHT;
        
        ID_SHOOTING_CURSOR_NORMAL = 10;
        ID_SHOOTING_CURSOR_GREEN  = 11;
        ID_CLEAR_CURSOR = 13;

        PATH_IMAGE      = "/images/";
        PATH_XML        = "/stageData/";
        PATH_SOUNDS     = "/sounds/";
        FIRST_STAGE_FILE_PATH = PATH_XML + "stage-e01.xml";

        img_targetA = readImage("super_rare_target.png");
        img_targetB = readImage("high_points_target.png");
        img_targetC = readImage("normal_target.png");
        img_scoreUP = readImage("scoreUp.png");
        img_timeDecrease = readImage("timeDown.png");

        se_gun          = getResource(PATH_SOUNDS + "gun.mp3");
        se_bomb         = getResource(PATH_SOUNDS + "bomb.mp3");
        se_explosion    = getResource(PATH_SOUNDS + "explosion3.mp3");
        se_buzzer       = getResource(PATH_SOUNDS + "se_buzzer.mp3");
        bgm_shooting    = getResource(PATH_SOUNDS + "boss3loop.mp3");
        bgm_overheat    = getResource(PATH_SOUNDS + "bgm_overheat.mp3");

        System.out.println("GetResource: done");
        System.out.println("Vertual: " + VIRTUAL_WIDTH + "x" + VIRTUAL_HEIGHT);
        System.out.println("Real-Virtual-Correction: " + REAL_VIRTUAL_CORRECTION);
        System.out.println("Real:    " + REAL_WIDTH + "x" + REAL_HEIGHT);
        System.out.println("FPS: " + FPS);
        System.out.printf("targetA: %dx%d\n", img_targetA.getWidth(), img_targetA.getHeight());
        System.out.printf("targetB: %dx%d\n", img_targetB.getWidth(), img_targetB.getHeight());
        System.out.printf("targetC: %dx%d\n", img_targetC.getWidth(), img_targetC.getHeight());
        System.out.println(toolkit.getBestCursorSize(64, 64));
        try {
            Thread.sleep(100);
        } catch (InterruptedException ignored) {}
    }

    private static int beforeRandX=0, beforeRandY=0;

    static int randomX(Allocation alloc)
    {
        int ret;
        switch (alloc) {
            case LEFT:
                ret =  Utils.nextInt(Allocation.MARGIN, Allocation.BORDER_WIDTH);
                break;
            case RIGHT:
                ret =  Utils.nextInt(VIRTUAL_WIDTH - Allocation.BORDER_WIDTH, VIRTUAL_WIDTH - Allocation.MARGIN);
                break;
            case CENTER:
                ret =  Utils.nextInt(Allocation.BORDER_WIDTH, VIRTUAL_WIDTH - Allocation.BORDER_WIDTH);
                break;
            default:
                ret =  Utils.nextInt(Allocation.MARGIN, VIRTUAL_WIDTH -Allocation.MARGIN);
        }
        if (Math.abs(ret - beforeRandX) < 100) return randomX(alloc);

        beforeRandX = ret;
        return ret;
    }

    static int randomY(Allocation alloc)
    {
        int ret;
        switch (alloc) {
            case TOP:
                ret =  Utils.nextInt(Allocation.MARGIN, Allocation.BORDER_HEIGHT);
                break;
            case BOTTOM:
                ret =  Utils.nextInt(VIRTUAL_HEIGHT - Allocation.BORDER_HEIGHT, VIRTUAL_HEIGHT - Allocation.MARGIN);
                break;
            case CENTER:
                ret =  Utils.nextInt(Allocation.BORDER_HEIGHT, VIRTUAL_HEIGHT - Allocation.BORDER_HEIGHT);
                break;
            default:
                ret =  Utils.nextInt(Allocation.MARGIN, VIRTUAL_HEIGHT -Allocation.MARGIN);
        }
        if (Math.abs(ret - beforeRandY) < 50) return randomY(alloc);

        beforeRandY = ret;
        return ret;
    }

    static List<BufferedImage> readNumberedImages(String fileFormat, int from, int to)
    {
        List<BufferedImage> list = new LinkedList<>();
        for (int i = from; i <= to; ++i) {
            list.add(readImage(String.format(fileFormat, i)));
        }
        return list;
    }

    static InputStream getResourceAsStream(String filePath)
    {
        return Main.class.getResourceAsStream(filePath);
    }
    
    static URL getResource(String filePath)
    {
        return Main.class.getResource(filePath);
    }

    static TargetType randomTargetType()
    {
        int rnd = Utils.nextInt(0, 99);
        if (rnd < 15) return TargetType.rankA;
        if (rnd < 40) return TargetType.rankB;
        return TargetType.rankC;
    }
    
    static BufferedImage readImage(final String fileName)
    {
        BufferedImage img = null;
        try
        {
            img = ImageIO.read(Game.class.getResourceAsStream(PATH_IMAGE + fileName));
        } catch (IOException e)
        {
            e.printStackTrace();
            System.exit(1);
        }
        
        return img;
    }
    

}
