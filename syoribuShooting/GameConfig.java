package syoribuShooting;

import syoribuShooting.system.GifReader;
import syoribuShooting.system.Utils;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

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

    private static boolean enableCursor;

    public static final int VIRTUAL_WIDTH;
    public static final int VIRTUAL_HEIGHT;
    public static final int REAL_HEIGHT;
    public static final int REAL_WIDTH;
    public static final double REAL_VIRTUAL_CORRECTION;
    public static final int OUTER_WINDOW_PLUS;
    public static final int OUTER_WINDOW_MINUS;
    public static final int NUM_BACK_IMAGE;
    public static final int FPS;
    public static final boolean isFullScreen;
    public static final String PATH_IMAGE, PATH_XML;
    public static final String STAGEDATA_PREFIX;
    public static final String FIRST_STAGE_FILE_PATH;
    public static Cursor shootingCursor, shootingCursorGreen;
    public static final int ID_SHOOTING_CURSOR_NORMAL;
    public static final int ID_SHOOTING_CURSOR_GREEN;
    public static BufferedImage
            img_shootingCursor,
            img_shootingCursorGreen,
            img_targetA, img_targetB, img_targetC,
            img_back[];
    public static ArrayList<BufferedImage> anim_hit = new ArrayList<>();

    private GameConfig() {}

    static {
        final Toolkit toolkit = Toolkit.getDefaultToolkit();

        VIRTUAL_WIDTH   = 1920;
        VIRTUAL_HEIGHT  = 1080;
        REAL_WIDTH      = (int) (toolkit.getScreenSize().getWidth());
        REAL_HEIGHT     = (int) (toolkit.getScreenSize().getHeight());
        OUTER_WINDOW_PLUS   = VIRTUAL_WIDTH + 500;
        OUTER_WINDOW_MINUS  = -500;
        FPS          = 44;
        isFullScreen = false;
        enableCursor = true;
        REAL_VIRTUAL_CORRECTION = (double)VIRTUAL_HEIGHT / (double)REAL_HEIGHT;
        
        ID_SHOOTING_CURSOR_NORMAL = 10;
        ID_SHOOTING_CURSOR_GREEN  = 11;

        NUM_BACK_IMAGE  = 1;
        PATH_IMAGE      = "/images/";
        PATH_XML        = "/stageData/";
        STAGEDATA_PREFIX= "stage";
        FIRST_STAGE_FILE_PATH = PATH_XML + "stage-a01.xml";
        img_back = new BufferedImage[NUM_BACK_IMAGE + 1];

        try {
            img_targetA = ImageIO.read(Game.class.getResourceAsStream(PATH_IMAGE + "super_rare_target.png"));
            img_targetB = ImageIO.read(Game.class.getResourceAsStream(PATH_IMAGE + "high_points_target.png"));
            img_targetC = ImageIO.read(Game.class.getResourceAsStream(PATH_IMAGE + "normal_target.png"));
            anim_hit = GifReader.readGif(Game.class.getResourceAsStream(PATH_IMAGE + "hit-animation.gif"));

            for (int i = 1; i <= NUM_BACK_IMAGE; i++) {
                img_back[i] = ImageIO.read(Game.class.getResourceAsStream(PATH_IMAGE + "back0" + i + ".jpg"));
            }
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
        System.out.println("Vertual: " + VIRTUAL_WIDTH + "x" + VIRTUAL_HEIGHT);
        System.out.println("Real-Virtual-Correction: " + REAL_VIRTUAL_CORRECTION);
        System.out.println("Real:    " + REAL_WIDTH + "x" + REAL_HEIGHT);
        System.out.println("FPS: " + FPS);
        System.out.printf("targetA: %dx%d\n", img_targetA.getWidth(), img_targetA.getHeight());
        System.out.printf("targetB: %dx%d\n", img_targetB.getWidth(), img_targetB.getHeight());
        System.out.printf("targetC: %dx%d\n", img_targetC.getWidth(), img_targetC.getHeight());
        System.out.println(toolkit.getBestCursorSize(64, 64));
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {}
    }

    public static boolean isEnableCursor() { return enableCursor; }


    private static int beforeRandX=0, beforeRandY=0;

    public static int randomX(Allocation alloc)
    {
        int ret;
        switch (alloc) {
            case LEFT:
                ret =  Utils.nextInt(0+Allocation.MARGIN, Allocation.BORDER_WIDTH);
                break;
            case RIGHT:
                ret =  Utils.nextInt(VIRTUAL_WIDTH - Allocation.BORDER_WIDTH, VIRTUAL_WIDTH - Allocation.MARGIN);
                break;
            case CENTER:
                ret =  Utils.nextInt(Allocation.BORDER_WIDTH, VIRTUAL_WIDTH - Allocation.BORDER_WIDTH);
                break;
            default:
                ret =  Utils.nextInt(0+Allocation.MARGIN, VIRTUAL_WIDTH -Allocation.MARGIN);
        }
        if (Math.abs(ret - beforeRandX) < 100) return randomX(alloc);

        beforeRandX = ret;
        return ret;
    }

    public static int randomY(Allocation alloc)
    {
        int ret;
        switch (alloc) {
            case TOP:
                ret =  Utils.nextInt(0+Allocation.MARGIN, Allocation.BORDER_HEIGHT);
                break;
            case BOTTOM:
                ret =  Utils.nextInt(VIRTUAL_HEIGHT - Allocation.BORDER_HEIGHT, VIRTUAL_HEIGHT - Allocation.MARGIN);
                break;
            case CENTER:
                ret =  Utils.nextInt(Allocation.BORDER_HEIGHT, VIRTUAL_HEIGHT - Allocation.BORDER_HEIGHT);
                break;
            default:
                ret =  Utils.nextInt(0+Allocation.MARGIN, VIRTUAL_HEIGHT -Allocation.MARGIN);
        }
        if (Math.abs(ret - beforeRandY) < 50) return randomY(alloc);

        beforeRandY = ret;
        return ret;
    }

    public static String getStageDataFileName(int num)
    {
        return GameConfig.PATH_XML
                + GameConfig.STAGEDATA_PREFIX
                + String.format("%02d.xml", num);
    }

    public static TargetType randomTargetType()
    {
        int rnd = Utils.nextInt(0, 99);
        if (rnd < 15) return TargetType.rankA;
        if (rnd < 40) return TargetType.rankB;
        return TargetType.rankC;
    }
    
    public static BufferedImage readImage(final String fileName)
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
