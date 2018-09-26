package syoribuShooting;

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
    public static BufferedImage img_target;
    
    static {
        final Toolkit toolkit = Toolkit.getDefaultToolkit();
        PATH_IMAGE = "/images/";

        WINDOW_WIDTH = (int)(toolkit.getScreenSize().getWidth());
        WINDOW_HEIGHT = (int)(toolkit.getScreenSize().getHeight());
        FPS = 40;
        isFullScreen = true;
        
        try {
            img_target = ImageIO.read(Game.class.getResourceAsStream(PATH_IMAGE + "target-test.png"));
        } catch (IOException e){
            e.printStackTrace();
            System.exit(1);
        }
        System.out.println("GetResource: done");
    }

//    private GameConfig()
//    {
//        final Toolkit toolkit = Toolkit.getDefaultToolkit();
//        PATH_IMAGE = "/images/";
//
//        WINDOW_WIDTH = (int)(toolkit.getScreenSize().getWidth());
//        WINDOW_HEIGHT = (int)(toolkit.getScreenSize().getHeight());
//        FPS = 40;
//        isFullScreen = true;
//        
//        try {
//            img_target = ImageIO.read(Game.class.getResourceAsStream(PATH_IMAGE + "target-test.png"));
//        } catch (IOException e){
//            e.printStackTrace();
//            System.exit(1);
//        }
//    }

}
