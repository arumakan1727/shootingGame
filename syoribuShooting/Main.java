package syoribuShooting;

import syoribuShooting.sprite.AnimationProcessor;
import syoribuShooting.system.BufferedResponsivePanel;
import syoribuShooting.system.BufferedVolatilePanel;
import syoribuShooting.system.CursorManager;
import syoribuShooting.system.GameWindow;
import syoribuShooting.system.InputEventManager;
import syoribuShooting.system.MP3Player;
import syoribuShooting.system.ScreenBuffer;

import static syoribuShooting.GameConfig.*;

public class Main {

    private static Game game;

    static InputEventManager getEventManager()
    {
        return game.getEventManager();
    }

    static CursorManager getCursorManager()
    {
        return game.getWindow().getCursorManager();
    }

    static AnimationProcessor getAnimationProcessor()
    {
        return game.getAnimationProcessor();
    }

    static GameWindow getWindow()
    {
        return game.getWindow();
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        ScreenBuffer buffer = null;
        for (int i = 0; i < args.length; ++i)
        {
            final String arg = args[i];
            switch (arg) {
                case "--buff=another":
                    System.out.println("Using Java-BufferedImage for buffering.");
                    buffer = new BufferedResponsivePanel(
                            VIRTUAL_WIDTH,
                            VIRTUAL_HEIGHT,
                            REAL_WIDTH,
                            REAL_HEIGHT);
                    break;
                    
                case "--nosounds":
                    MP3Player.setEnable(false);
                    System.out.println("Sound off");
                    break;

//                case "--scale":
//                    try {
//                        ++i;
//                        double scale = Double.parseDouble(args[i]);
//                        GameConfig.REAL_HEIGHT  = (int)(VIRTUAL_HEIGHT * scale);
//                        GameConfig.REAL_WIDTH   = (int)(VIRTUAL_WIDTH * scale);
//                        System.out.println("scale: " + scale + ",  " + REAL_HEIGHT + "x" + REAL_WIDTH);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                        System.exit(1);
//                    }
//                    break;

                default:
                    System.err.println("Argument `" + arg + "` is undefined.");
                    System.out.println("[Hint]  \'--buff=another\' - Using another buffering mode. ");
                    System.out.println("[Hint]  \'--nosounds\' - Sound off.");
//                    System.out.println("[Hint]  \'--scale {scale}\' - Config window scale.");
            }
        }

        if (buffer == null) {
            System.out.println("Using VolatileImage for buffering.");
            buffer = new BufferedVolatilePanel(
                        VIRTUAL_WIDTH,
                        VIRTUAL_HEIGHT,
                        REAL_WIDTH,
                        REAL_HEIGHT);
        }
        
        game = new Game(buffer);
    }

}
